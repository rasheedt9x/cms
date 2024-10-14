package com.sgdc.cms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgdc.cms.dto.DepartmentDto;
import com.sgdc.cms.models.Department;
import com.sgdc.cms.repositories.DepartmentRepository;
@Service
public class DepartmentService{

    DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository dr){
        this.departmentRepository= dr;
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
