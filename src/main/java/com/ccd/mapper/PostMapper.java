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
package com.ccd.mapper;

import static com.ccd.model.VoteType.DOWNVOTE;
import static com.ccd.model.VoteType.UPVOTE;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.ccd.dto.PostRequest;
import com.ccd.dto.PostResponse;
import com.ccd.model.Post;
import com.ccd.model.SubCCD;
import com.ccd.model.User;
import com.ccd.model.Vote;
import com.ccd.model.VoteType;
import com.ccd.repository.CommentRepository;
import com.ccd.repository.VoteRepository;
import com.ccd.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private AuthService authService;

	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "description", source = "postRequest.description")
	@Mapping(target = "subCCD", source = "subCCD")
	@Mapping(target = "voteCount", constant = "0")
	@Mapping(target = "user", source = "user")
	public abstract Post map(PostRequest postRequest, SubCCD subCCD, User user);

	@Mapping(target = "id", source = "postId")
	@Mapping(target = "subCCDName", source = "subCCD.name")
	@Mapping(target = "userName", source = "user.username")
	@Mapping(target = "commentCount", expression = "java(commentCount(post))")
	@Mapping(target = "duration", expression = "java(getDuration(post))")
	@Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
	@Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
	public abstract PostResponse mapToDto(Post post);

	Integer commentCount(Post post) {
		return commentRepository.findByPost(post).size();
	}

	String getDuration(Post post) {
		return TimeAgo.using(post.getCreatedDate().toEpochMilli());
	}

	boolean isPostUpVoted(Post post) {
		return checkVoteType(post, UPVOTE);
	}

	boolean isPostDownVoted(Post post) {
		return checkVoteType(post, DOWNVOTE);
	}

	private boolean checkVoteType(Post post, VoteType voteType) {
		if (authService.isLoggedIn()) {
			Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
					authService.getCurrentUser());
			return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
		}
		return false;
	}

}