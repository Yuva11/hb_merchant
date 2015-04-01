package com.finateltechhbm.model

import com.finateltechhbm.dto.Role;
import com.finateltechhbm.model.Merchant;

class User {
	Long id;
	
	String userName; // --> email
	
	String passwordHash;
	
	Merchant merchant;
	
	Role userType;
	Set<Role> roles;
	
}
