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
package com.ccd.security;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtProvider {

	@Autowired
	private JwtEncoder jwtEncoder;

	@Value("${jwt.expiration.time}")
	private Long jwtExpirationInMillis;

	public String generateToken(Authentication authentication) {
		User principal = (User) authentication.getPrincipal();
		return generateTokenWithUserName(principal.getUsername());
	}

	public String generateTokenWithUserName(String username) {
		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(Instant.now())
				.expiresAt(Instant.now().plusMillis(jwtExpirationInMillis)).subject(username)
				.claim("scope", "ROLE_USER").build();

		return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	public Long getJwtExpirationInMillis() {
		return jwtExpirationInMillis;
	}
}
