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
package com.ccd.model;

import java.util.Arrays;

import com.ccd.exceptions.SpringCCDException;

public enum VoteType {
	UPVOTE(1), DOWNVOTE(-1),;

	private int direction;

	VoteType(int direction) {
	}

	public static VoteType lookup(Integer direction) {
		return Arrays.stream(VoteType.values()).filter(value -> value.getDirection().equals(direction)).findAny()
				.orElseThrow(() -> new SpringCCDException("Vote not found"));
	}

	public Integer getDirection() {
		return direction;
	}
}
