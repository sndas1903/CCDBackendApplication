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

import static org.springframework.http.ResponseEntity.status;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ccd.dto.PostRequest;
import com.ccd.dto.PostResponse;
import com.ccd.service.PostService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
		postService.save(postRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<PostResponse>> getAllPosts() {
		return status(HttpStatus.OK).body(postService.getAllPosts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
		return status(HttpStatus.OK).body(postService.getPost(id));
	}

	@GetMapping("by-subreddit/{id}")
	public ResponseEntity<List<PostResponse>> getPostsBySubreddit(Long id) {
		return status(HttpStatus.OK).body(postService.getPostsByCCD(id));
	}

	@GetMapping("by-user/{name}")
	public ResponseEntity<List<PostResponse>> getPostsByUsername(String username) {
		return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
	}
}
