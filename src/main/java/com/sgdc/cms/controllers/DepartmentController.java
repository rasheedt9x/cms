package com.sgdc.cms.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgdc.cms.dto.DepartmentDto;
import com.sgdc.cms.services.DepartmentService;

/**
 * DepartmentController
 */
@RestController
@RequestMapping("/api/v1/dept")
public class DepartmentController{

	private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService ds){
        this.departmentService= ds;
    }

    @PostMapping("/new")
    public String newGroup(@RequestBody DepartmentDto dto){
        logger.info(dto.getName());
        return departmentService.saveDepartment(dto);
    }	
}
