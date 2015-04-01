package com.finateltechhbm.model


class Merchant {
	
	Long id;
	String name;
	String status;
	
	private Set<MerchantBranch> branches = new HashSet<MerchantBranch>();
	User user;
}
