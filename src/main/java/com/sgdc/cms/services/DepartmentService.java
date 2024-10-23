package com.sgdc.cms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgdc.cms.dto.DepartmentDto;
import com.sgdc.cms.models.Department;
import com.sgdc.cms.repositories.DepartmentRepository;

import jakarta.annotation.PostConstruct;
@Service
public class DepartmentService{
    DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository dr){
        this.departmentRepository= dr;
    }

    
    @PostConstruct
    public void initDepts() {
        String[] depts = new String[] { "MANAGEMENT", "Computer Science", "Sciences", "Humanities","Commerce"};

        for (String g : depts) {
            try {
                Department group = departmentRepository.findByDepartmentName(g);
                if (group == null) {
                    group = new Department();
                    group.setDepartmentName(g);
                    departmentRepository.save(group);
                } else {
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to save dep", e);
            }
        }

    }
    
    public String saveDepartment(DepartmentDto dto){
        try {
            Department dept = departmentRepository.findByDepartmentName(dto.getName());
            if(dept == null) {
                dept = new Department();
                dept.setDepartmentName(dto.getName());
                departmentRepository.save(dept);
                return "saved";
            } else {
                return "Department already present";
            }
        	
        } catch (Exception e ) {
        	// TODO: handle exception
        	e.printStackTrace();	
            throw new RuntimeException("Failed to save Department",e);
        }
	}
}
