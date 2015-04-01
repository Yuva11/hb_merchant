package com.finateltechhbm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import lombok.Getter;
import lombok.Setter;

import com.finateltechhbm.dto.OrderStatus;
import com.finateltechhbm.model.OrderDetail;

public
class DealOrders {
	
	@Getter @Setter private Long id;
	@Getter @Setter private Double amount;
	
	@Getter @Setter private Date dealordersdate;
	
	@Getter @Setter private OrderStatus status;
	 
	@Getter @Setter private Customer customer;
	
	//@Cascade({})
	@Getter @Setter private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
	
	//@Cascade({})
	@Getter @Setter private PaymentAtempt paymentAtempt;

	@Getter @Setter private boolean deliveryStatus;
}
