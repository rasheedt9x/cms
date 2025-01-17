package com.sgdc.cms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgdc.cms.dto.StudentGroupDto;
import com.sgdc.cms.models.StudentGroup;
import com.sgdc.cms.repositories.StudentGroupRepository;

import jakarta.annotation.PostConstruct;

/**
 * StudentGroupService
 */
@Service
public class StudentGroupService {

    StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentGroupService(StudentGroupRepository sgr) {
        this.studentGroupRepository = sgr;
    }

    @PostConstruct
    public void initGroups() {
        String[] groups = new String[] { "BCA", "BCOM", "BSC", "BZC", "BIOTECH","BBA" };

        for (String g : groups) {
            try {
                StudentGroup group = studentGroupRepository.findByGroupName(g);
                if (group == null) {
                    group = new StudentGroup();
                    group.setGroupname(g);
                    studentGroupRepository.save(group);
                } else {
                }

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                throw new RuntimeException("Failed to save group", e);
            }
        }

    }

    public String saveGroup(StudentGroupDto dto) {
        // try{

        // StudentGroup group = new StudentGroup();
        // group.setGroupname(dto.getGroupname());
        // studentGroupRepository.save(group);
        // } catch(Exception e) {
        // e.printStackTrace();
        // throw new RuntimeException("Failed to save group",e);
        // }
        try {
            StudentGroup group = studentGroupRepository.findByGroupName(dto.getGroupname());
            if (group == null) {
                group = new StudentGroup();
                group.setGroupname(dto.getGroupname());
                studentGroupRepository.save(group);
                return "saved";
            } else {
                return "Group already present";
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            throw new RuntimeException("Failed to save group", e);
        }
    }
}
