package com.finateltechhbm.dto;
 

import groovy.transform.EqualsAndHashCode;

import java.io.Serializable;
 
@EqualsAndHashCode(includes=['id'])
public class CompactLocation implements Serializable {
	Long id;
	String name;
}
