package com.finateltechhbm.ui

import java.util.Set;

import groovy.util.logging.Slf4j;

import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.finateltechhbm.dto.DealOrdersDto; 
import com.finateltechhbm.dto.OrderDetailsCustomHBMDto
import com.finateltechhbm.service.RestService;
@Service
@Scope("session")
@Slf4j
class OrdersBean {
	@Autowired
	public RestService restService;
	@Autowired
	public ProfileBean profileBean; 
	List<DealOrdersDto> filterList;
	List<DealOrdersDto> dealOrdersList;
	Authentication auth; 
	
	String keyWord="";
	boolean deliveryStatus;
	
	long merchantId;
	long orderId;
	double serviceTax = 10.0;
	OrderDetailsCustomHBMDto orderDetailsCustomHBMDto;
	String orderDetailsId;
	public String showOrders() {
		if(profileBean.merchantId == null ){
			return "/p/home.xhtml";
		}
		/*
		auth = SecurityContextHolder.getContext().getAuthentication();
		merchantId = restService.getMerchantByUserName(auth.getName());*/
		log.info("Show Orders  Called Mercahnt ID "+profileBean.merchantId); 
		dealOrdersList = restService.getAllMyDealsOrderByMerchant(profileBean.merchantId);
		log.info("No of DealOrders :: "+dealOrdersList.size())
		return "/p/orders.xhtml";
	}
	
	public String searchByKeyWord() {
		if(profileBean.merchantId == null ){
			return "/p/home.xhtml";
		}
		if(keyWord != null && keyWord.trim().length() > 0) {
			log.info("Show Orders For Merchant Id:: <<"+profileBean.merchantId+">> Search By KeyWord :: <<"+keyWord+">>" );
			dealOrdersList = restService.getAllMyDealsOrderByMerchantAndKeyWord(profileBean.merchantId, keyWord);
			return "/p/orders.xhtml";
		}
		return showOrders();
	}
	public String deliverDeal() {
		restService.deliverDeal(orderId,deliveryStatus);
		/*auth = SecurityContextHolder.getContext().getAuthentication();
		merchantId = restService.getMerchantByUserName(auth.getName());*/
		log.info("Show Orders  Called Mercahnt ID "+profileBean.merchantId+" ;; orderId : "+orderId); 
		dealOrdersList = restService.getAllMyDealsOrderByMerchant(profileBean.merchantId);
		log.info("No of DealOrders :: "+dealOrdersList.size())
		return "/p/viewdeals.xhtml";
	}
	public boolean filterByOrderId(def value, def filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if(filterText == null||filterText.equals("")) {
			return true;
		}
		if(value == null) {
			return false;
		}
		 
		return ((Comparable) value.toString().toUpperCase()).compareTo(filterText.toUpperCase()) > 0;
	}
	public void showOrderDetails(){
		if(orderDetailsId!=null){
			orderDetailsCustomHBMDto = restService.getOrderDetailsByOrderId(orderDetailsId);
		} else {
			log.info("<--Merchant Module--> Order Id found null,can't fetch order details***");
		}
	}
}
