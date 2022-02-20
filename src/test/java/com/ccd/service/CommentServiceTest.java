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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ccd.exceptions.SpringCCDException;

class CommentServiceTest {

	@Test
	@DisplayName("Test Should Pass When Comment do not Contains Swear Words")
	void shouldNotContainSwearWordsInsideComment() {
		CommentService commentService = new CommentService(null, null, null, null, null, null, null);
		assertThat(commentService.containsSwearWords("This is a comment")).isFalse();
	}

	@Test
	@DisplayName("Should Throw Exception when Exception Contains Swear Words")
	void shouldFailWhenCommentContainsSwearWords() {
		CommentService commentService = new CommentService(null, null, null, null, null, null, null);

		assertThatThrownBy(() -> {
			commentService.containsSwearWords("This is a shitty comment");
		}).isInstanceOf(SpringCCDException.class).hasMessage("Comments contains unacceptable language");
	}
}
