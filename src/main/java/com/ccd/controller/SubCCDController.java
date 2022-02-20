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

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ccd.dto.SubCCDDto;
import com.ccd.service.SubCCDService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/subccd")
@AllArgsConstructor
@Slf4j
public class SubCCDController {

	private final SubCCDService subCCDService;

	@PostMapping
	public ResponseEntity<SubCCDDto> createSubCCD(@RequestBody SubCCDDto subCcdDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(subCCDService.save(subCcdDto));
	}

	@GetMapping
	public ResponseEntity<List<SubCCDDto>> getAllSubCCDs() {
		return ResponseEntity.status(HttpStatus.OK).body(subCCDService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<SubCCDDto> getSubCCD(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(subCCDService.getSubCCD(id));
	}
}