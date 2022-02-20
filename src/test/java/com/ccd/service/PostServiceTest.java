/*********************************************************************************
 *                                                                               *
 *  Copyright (c) 2022-2022 mats12345678, This source is a part of               * 
 *   CCD Application - sample application source code.                           *
 *                                                                               *
 *   Licensed under the Apache License, Version 2.0 (the "License");             *
 *   you may not use this file except in compliance with the License.            *
 *   You may obtain a copy of the License at                                     *
 *                                                                               *
 *      http://www.apache.org/licenses/LICENSE-2.0                               *
 *                                                                               *
 *   Unless required by applicable law or agreed to in writing, software         *
 *   distributed under the License is distributed on an "AS IS" BASIS,           *
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.    *
 *   See the License for the specific language governing permissions and         *
 *   limitations under the License.                                              *
 *                                                                               *
 *********************************************************************************/
package com.ccd.service;

import static java.util.Collections.emptyList;

import java.time.Instant;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccd.dto.PostRequest;
import com.ccd.dto.PostResponse;
import com.ccd.mapper.PostMapper;
import com.ccd.model.Post;
import com.ccd.model.SubCCD;
import com.ccd.model.User;
import com.ccd.repository.PostRepository;
import com.ccd.repository.SubCCDRepository;
import com.ccd.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

	@Mock
	private PostRepository postRepository;
	@Mock
	private SubCCDRepository subCCDRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private AuthService authService;
	@Mock
	private PostMapper postMapper;

	@Captor
	private ArgumentCaptor<Post> postArgumentCaptor;

	private PostService postService;

	@BeforeEach
	public void setup() {
		postService = new PostService(postRepository, subCCDRepository, userRepository, authService, postMapper);
	}

	@Test
	@DisplayName("Should Retrieve Post by Id")
	public void shouldFindPostById() {
		Post post = new Post(123L, "First Post", "http://url.site", "Test", 0, null, Instant.now(), null);
		PostResponse expectedPostResponse = new PostResponse(123L, "First Post", "http://url.site", "Test", "Test User",
				"Test Subredit", 0, 0, "1 Hour Ago", false, false);

		Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
		Mockito.when(postMapper.mapToDto(Mockito.any(Post.class))).thenReturn(expectedPostResponse);

		PostResponse actualPostResponse = postService.getPost(123L);

		Assertions.assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
		Assertions.assertThat(actualPostResponse.getPostName()).isEqualTo(expectedPostResponse.getPostName());
	}

	@Test
	@DisplayName("Should Save Posts")
	public void shouldSavePosts() {
		User currentUser = new User(123L, "test user", "secret password", "user@email.com", Instant.now(), true);
		SubCCD subCCD = new SubCCD(123L, "First SubCCD", "SubCCD Description", emptyList(),
				Instant.now(), currentUser);
		Post post = new Post(123L, "First Post", "http://url.site", "Test", 0, null, Instant.now(), null);
		PostRequest postRequest = new PostRequest(null, "First SubCCD", "First Post", "http://url.site", "Test");

		Mockito.when(subCCDRepository.findByName("First SubCCD")).thenReturn(Optional.of(subCCD));
		Mockito.when(authService.getCurrentUser()).thenReturn(currentUser);
		Mockito.when(postMapper.map(postRequest, subCCD, currentUser)).thenReturn(post);

		postService.save(postRequest);
		Mockito.verify(postRepository, Mockito.times(1)).save(postArgumentCaptor.capture());

		Assertions.assertThat(postArgumentCaptor.getValue().getPostId()).isEqualTo(123L);
		Assertions.assertThat(postArgumentCaptor.getValue().getPostName()).isEqualTo("First Post");
	}
}
