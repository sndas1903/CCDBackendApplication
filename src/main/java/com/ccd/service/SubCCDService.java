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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccd.dto.SubCCDDto;
import com.ccd.exceptions.SpringCCDException;
import com.ccd.mapper.SubCCDMapper;
import com.ccd.model.SubCCD;
import com.ccd.repository.SubCCDRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubCCDService {

	@Autowired
	private SubCCDRepository subCCDRepository;

	@Autowired
	private SubCCDMapper subCCDMapper;

	@Transactional
	public SubCCDDto save(SubCCDDto subCCDDto) {
		SubCCD save = subCCDRepository.save(subCCDMapper.mapDtoToSubCCD(subCCDDto));
		subCCDDto.setId(save.getId());
		return subCCDDto;
	}

	@Transactional(readOnly = true)
	public List<SubCCDDto> getAll() {
		return subCCDRepository.findAll().stream().map(subCCDMapper::mapSubCCDToDto).collect(toList());
	}

	public SubCCDDto getSubCCD(Long id) {
		SubCCD subCCD = subCCDRepository.findById(id)
				.orElseThrow(() -> new SpringCCDException("No subccd found with ID - " + id));
		return subCCDMapper.mapSubCCDToDto(subCCD);
	}
}
