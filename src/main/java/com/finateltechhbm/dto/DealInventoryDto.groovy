package com.finateltechhbm.dto

import java.io.Serializable;

import com.finateltechhbm.dto.DealDTO;

class DealInventoryDto implements Serializable{
	Long id;
	DealDTO dealinvent;
	Date dealDate;
	Integer openingQty;
	Integer lockedQty;
	Integer bookedQty;
}
