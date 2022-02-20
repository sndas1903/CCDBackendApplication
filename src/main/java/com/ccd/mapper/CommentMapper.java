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

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ccd.dto.CommentsDto;
import com.ccd.model.Comment;
import com.ccd.model.Post;
import com.ccd.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "text", source = "commentsDto.text")
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "post", source = "post")
	@Mapping(target = "user", source = "user")
	Comment map(CommentsDto commentsDto, Post post, User user);

	@Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
	@Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
	CommentsDto mapToDto(Comment comment);
}