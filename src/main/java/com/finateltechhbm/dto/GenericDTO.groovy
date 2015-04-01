package com.finateltechhbm.dto

import groovy.transform.EqualsAndHashCode;
import lombok.ToString;
 
@EqualsAndHashCode(includes="id")
@ToString
class GenericDTO {
	Long id;
	 
	String name;
}
