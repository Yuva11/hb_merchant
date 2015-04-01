package com.finateltechhbm.ui

import java.text.SimpleDateFormat;
import java.util.List;

import groovy.util.logging.Slf4j

import javax.annotation.PostConstruct

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

import com.finateltechhbm.dto.DealOrdersDto;
import com.finateltechhbm.dto.MerchantBranchDto
import com.finateltechhbm.dto.MerchantDto
import com.finateltechhbm.dto.SalesReportDTO
import com.finateltechhbm.service.RestService;
import com.finateltechhbm.validation.Faces;
import com.finateltechhbm.util.Util;
@Service
@Scope('session')
@Slf4j
class ReportBean {
	@Autowired
	public RestService restService;
	@Autowired
	public ProfileBean profileBean;
	
	SalesReportDTO salesReport;
	List<MerchantDto> brands;
	MerchantDto selectedBrand;
	List<MerchantDto> selectedBrands;
	List<MerchantBranchDto> selectedOutlets;
	Map<MerchantDto,List<MerchantBranchDto>> branchMap = new HashMap<MerchantDto,List<MerchantBranchDto>>();
	
	Date maxDate = new Date();
	@PostConstruct
	public void setData(){
		log.info("Group user Id :: << "+profileBean.userInfo.id+" >>");
		brands = new ArrayList<MerchantDto>();
		selectedOutlets = new ArrayList<MerchantBranchDto>();
		salesReport = new SalesReportDTO() 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		salesReport.startDate  = sdf.parse(sdf.format(new Date()));
		
		salesReport.endDate = new Date()
		brands = profileBean.userInfo.merchant;
		for(MerchantDto b : brands) {
			branchMap.put(b,(List<MerchantBranchDto>)restService.getMerchantBranchesById(b.id));
		}
		/*Set<MerchantDto> mKeys = branchMap.keySet();
		for(def k : mKeys){
			salesReport.merchant = k;
			selectedOutlets = branchMap.get(k);
			log.info("Brand Key Name :: "+k.name+" ; OUTLETS : "+branchMap.get(k))
			continue;
		}*/
		log.info("Brands Associated With user :: << "+brands+" >>");
	}
	public String showReport(){
		salesReport = new SalesReportDTO(); 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		salesReport.startDate  = sdf.parse(sdf.format(new Date()));
		
		salesReport.endDate = new Date()
		return "/p/reports.xhtml";
	}
	public void onChangeBrand(){
		
		log.info("Before selectedOutlets : ---------"+selectedOutlets);
		log.info("salesReport.merchant : ---------"+salesReport.merchant );
		selectedOutlets = branchMap.get(salesReport.merchant);
		log.info("After selectedOutlets : ---------"+selectedOutlets);
	}
	
	public String submit(){
		if(salesReport.merchant == null){
			Faces.addWarn("Please Select Brand") 
			return "/p/reports.xhtml";
		}
		if(salesReport.merchantBranch== null || salesReport.merchantBranch < 1L){
			Faces.addWarn("Please Select Outlet")
			return "/p/reports.xhtml";
		}
		log.info("salesReport.merchantBranch :: "+salesReport.merchantBranch)
		if(salesReport.startDate >= salesReport.endDate ){
			Faces.addWarn("End Date Must Be Greater Than start date")
			return "/p/reports.xhtml";
		}
		
		return "/p/reports.xhtml";
	}
	public StreamedContent getDownloadSalesReport(){
		if(salesReport.merchant == null){
			Faces.addWarn("Please Select Brand")
			return null;
		}
		if(salesReport.merchantBranch== null || salesReport.merchantBranch < 1L){
			Faces.addWarn("Please Select Outlet")
			return null;
		}
		log.info("salesReport.merchantBranch :: "+salesReport.merchantBranch)
		if(salesReport.startDate >= salesReport.endDate ){
			Faces.addWarn("End Date Must Be Greater Than start date")
			return null;
		}
		
	 List<DealOrdersDto> dealOrdersList = restService.getOrderSalesList(salesReport);
	 log.info("-----------------"+dealOrdersList)
			Util util = new Util();
			InputStream is =  new ByteArrayInputStream(util.generateSalesExcelReport(dealOrdersList,salesReport.startDate ,salesReport.endDate  ));
			return new DefaultStreamedContent(is, "application/xls", "SalesReport.xls");
		 
				
	}
}
