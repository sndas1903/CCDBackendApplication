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
package com.ccd.controller;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ccd.dto.PostResponse;
import com.ccd.security.JwtProvider;
import com.ccd.service.PostService;
import com.ccd.service.UserDetailsServiceImpl;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

	@MockBean
	private PostService postService;

	@MockBean
	private UserDetailsServiceImpl userDetailsService;

	@MockBean
	private JwtProvider jwtProvider;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Should List All Posts When making GET request to endpoint - /api/posts/")
	void shouldCreatePost() throws Exception {
		PostResponse postRequest1 = new PostResponse(1L, "Post Name", "http://url.site", "Description", "User 1",
				"SubCCD Name", 0, 0, "1 day ago", false, false);
		PostResponse postRequest2 = new PostResponse(2L, "Post Name 2", "http://url2.site2", "Description2", "User 2",
				"SubCCD Name 2", 0, 0, "2 days ago", false, false);

		Mockito.when(postService.getAllPosts()).thenReturn(asList(postRequest1, postRequest2));

		mockMvc.perform(get("/api/posts/")).andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.size()", Matchers.is(2))).andExpect(jsonPath("$[0].id", Matchers.is(1)))
				.andExpect(jsonPath("$[0].postName", Matchers.is("Post Name")))
				.andExpect(jsonPath("$[0].url", Matchers.is("http://url.site")))
				.andExpect(jsonPath("$[1].url", Matchers.is("http://url2.site2")))
				.andExpect(jsonPath("$[1].postName", Matchers.is("Post Name 2")))
				.andExpect(jsonPath("$[1].id", Matchers.is(2)));
	}
}
