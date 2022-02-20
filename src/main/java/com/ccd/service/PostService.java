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

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccd.dto.PostRequest;
import com.ccd.dto.PostResponse;
import com.ccd.exceptions.PostNotFoundException;
import com.ccd.exceptions.SubCCDNotFoundException;
import com.ccd.mapper.PostMapper;
import com.ccd.model.Post;
import com.ccd.model.SubCCD;
import com.ccd.model.User;
import com.ccd.repository.PostRepository;
import com.ccd.repository.SubCCDRepository;
import com.ccd.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

	private final PostRepository postRepository;
	private final SubCCDRepository subCCDRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final PostMapper postMapper;

	public void save(PostRequest postRequest) {
		SubCCD subCCD = subCCDRepository.findByName(postRequest.getSubCCDName())
				.orElseThrow(() -> new SubCCDNotFoundException(postRequest.getSubCCDName()));
		postRepository.save(postMapper.map(postRequest, subCCD, authService.getCurrentUser()));
	}

	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
		return postMapper.mapToDto(post);
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		return postRepository.findAll().stream().map(postMapper::mapToDto).collect(toList());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByCCD(Long subccdId) {
		SubCCD subCCD = subCCDRepository.findById(subccdId)
				.orElseThrow(() -> new SubCCDNotFoundException(subccdId.toString()));
		List<Post> posts = postRepository.findAllBySubCCD(subCCD);
		return posts.stream().map(postMapper::mapToDto).collect(toList());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return postRepository.findByUser(user).stream().map(postMapper::mapToDto).collect(toList());
	}
}