package com.sgdc.cms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgdc.cms.dto.StudentGroupDto;
import com.sgdc.cms.services.StudentGroupService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StudentGroupController
 */
@RestController
@RequestMapping("/api/v1/groups")
public class StudentGroupController {


//	private final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	private final Logger logger = LoggerFactory.getLogger(StudentGroupController.class);
    private StudentGroupService studentGroupService;

    @Autowired
    public StudentGroupController(StudentGroupService sgs){
        this.studentGroupService = sgs;
    }

    @PostMapping("/new")
    public String newGroup(@RequestBody StudentGroupDto dto){
        logger.info(dto.getGroupname());
        return studentGroupService.saveGroup(dto);
    }	
}
