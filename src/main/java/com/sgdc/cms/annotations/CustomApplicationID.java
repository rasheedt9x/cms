package com.sgdc.cms.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.annotations.ValueGenerationType;

import com.sgdc.cms.generators.ApplicationIDGenerator;

/**
 * CustomApplicationID
 */
@ValueGenerationType(generatedBy = ApplicationIDGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CustomApplicationID {

	
}
