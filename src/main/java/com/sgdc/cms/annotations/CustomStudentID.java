package com.sgdc.cms.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.annotations.ValueGenerationType;

import com.sgdc.cms.generators.StudentIDGenerator;

@ValueGenerationType(generatedBy = StudentIDGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CustomStudentID {

}
