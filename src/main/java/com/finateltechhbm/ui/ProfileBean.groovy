package com.finateltechhbm.ui

import java.text.SimpleDateFormat;
import java.util.List;

import groovy.util.logging.Slf4j;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent
//import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import com.finateltechhbm.dto.AdSettingsDTO
import com.finateltechhbm.dto.ContentDealDTO;
import com.finateltechhbm.dto.DealDTO;
import com.finateltechhbm.dto.DealDashBoardDTO;
import com.finateltechhbm.dto.DealInventoryDto;
import com.finateltechhbm.dto.GroupUserDTO;
import com.finateltechhbm.dto.MerchantDto;
import com.finateltechhbm.dto.MerchantOrdersDTO;
import com.finateltechhbm.dto.ProfileDTO;
import com.finateltechhbm.service.ApplicationService;
import com.finateltechhbm.service.RestService;
import com.finateltechhbm.validation.Faces;
import com.finateltechhbm.dto.LoginRequest;
@Service
@Scope("session")

@Slf4j
class ProfileBean {
	@Autowired
	public RestService restService;
	@Value('${rest.url}')
	public  def restDomain;
	@Value('${downloadpath}')
	def logopath;
	def logo
	ProfileDTO profileDTO;
	Long merchantId
	def path;
	List<ContentDealDTO> dashBoardAdList
	List<DealDTO> dashBoardDealList
	List<DealDTO> dashBoardPromoList
	List<DealInventoryDto> dealInventoryDtoList 
	MerchantOrdersDTO dealDash
	Date searchDate = new Date();
	def font;
	def font_size;
	def text_color
	def bg_color
	def eMail
	String passWord;
	MerchantDto merchantDetails;
	MerchantDto selectedMerchant;
	GroupUserDTO userInfo
	def merchantCat
	def merchantSpec
	List<AdSettingsDTO> settings
	def view="ad";
	boolean display = true;
	String credential;
	Date maxDate = new Date();
	com.finateltechhbm.dto.LoginRequest loginRequest = new com.finateltechhbm.dto.LoginRequest();
	@PostConstruct
	public void setProfile(){
		maxDate = new Date();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		loginRequest.passWord="";
		if(auth != null){
			userInfo = (GroupUserDTO)auth.getPrincipal();
			if(userInfo != null) {
				log.info("User Entity Id :: "+userInfo.id)
				log.info("User Name :: "+userInfo.userName)
				log.info("User Brand List  :: "+userInfo.merchant)
				log.info("User Email ID :: "+userInfo.emailId)
				eMail = userInfo.emailId
				if(userInfo.merchant != null){
					log.info("User Brand List  :: "+userInfo.merchant.size())
				}
			} 
		}
		RequestContext.getCurrentInstance().execute("PF('BRANDCHOOSEDIALOG').show();");
	}
	public setCredential(String username,String password){
		credential = username+":"+password;
		byte[] encoded = Base64.encode(credential.getBytes());
		credential = new String(encoded)
		//this.username = username;
	}
	public def getHomeBoard(){
		maxDate = new Date();
		log.info("selected brand ----- "+selectedMerchant );
		if(selectedMerchant != null ){
			merchantId = selectedMerchant.id;
			log.info(" Get Dash Board for : "+merchantId );
			dashBoardAdList = (List<ContentDealDTO>)restService.getAdForMerchantDashBoard(merchantId); 
		}
		return "/p/home.xhtml";
	}
	public void homeBoardListener(){
		maxDate = new Date();
		if(selectedMerchant != null ){
			merchantId = selectedMerchant.id; 
			log.info(" Get Dash Board for : "+merchantId );
			dashBoardAdList = (List<ContentDealDTO>)restService.getAdForMerchantDashBoard(merchantId);
		}
	}
	public void getContentBoard(){
		maxDate = new Date();
		log.info(" Get Dash Board for : "+merchantId );
		dashBoardAdList = (List<ContentDealDTO>)restService.getAdForMerchantDashBoard(merchantId); 
	}
	public void getDealBoard(){ 
		maxDate = new Date();
		log.info(" Get Deal Inventory Dash Board for : "+merchantId );
		DealDashBoardDTO data = new DealDashBoardDTO();
		data.merchantId = merchantId;
		data.searchDate = new Date();
		dealDash = restService.getDealInventory(data); 
	}
	public void onDateSelect(SelectEvent event) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		log.info( "Date --------------------------------- "+format.format(event.getObject()));
		searchDate = event.getObject(); 
		DealDashBoardDTO data = new DealDashBoardDTO();
		data.merchantId = merchantId;
		data.searchDate = searchDate;
		dealDash = restService.getDealInventory(data);
	}
	public void getPromoBoard(){ 
		log.info(" Get Promo Dash Board for : "+merchantId ); 
		dashBoardPromoList = (List<DealDTO>)restService.getPromoForMerchantDashBoard(merchantId); 
	}
	public def resetPassword(){
		log.info(" Profile Email : "+eMail +"; Password : "+passWord);
		return "/p/home.xhtml";
	}
	public void chooseBrand(ActionEvent event) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean choosen = false; 
		if(selectedMerchant != null){
			merchantId = selectedMerchant.id
		}
		log.info("New Merchant ::::::  "+selectedMerchant);
		log.info("New Merchant ID ::::::  "+merchantId);
		if( merchantId != null && merchantId >= 1) {
			choosen = true;  
			logo = logopath;
			path= restDomain
			log.info(" authentication name : "+merchantId +"== path :: "+path );
			merchantDetails = (MerchantDto)restService.getMerchantDetails(merchantId);
			if(merchantDetails != null) {
				if(merchantDetails.category != null){
					merchantCat = merchantDetails.category.name
				}
				if(merchantDetails.speciality != null){
					merchantSpec = merchantDetails.speciality.name
				}
			}
			dashBoardAdList = (List<ContentDealDTO>)restService.getAdForMerchantDashBoard(merchantId); 
			settings = (List<AdSettingsDTO>)restService.getAdSettings();
			for(AdSettingsDTO st : settings){
				if(st.settingkey.equals("TEXT_COLOR")) {
					text_color =  st.value
					log.info("text Color : "+text_color)
				}
				if(st.settingkey.equals("TEXT_FONT")) {
					font = st.value
				}
				if(st.settingkey.equals("FONT_SIZE")) {
					font_size = st.value
					log.info("font size : "+font_size)
				}
				if(st.settingkey.equals("BACKGROUND_COLOR")) {
					bg_color = st.value
				}
			}
			File imageFile = new File( "${logo}/images/merchant/MERCHANT_${merchantId}.png");
			log.info("-----------------${logo}/images/merchant/MERCHANT_${merchantId}.png" +" --------"+imageFile.exists());
			if(imageFile.exists() && !imageFile.isDirectory()) {
				log.info("Logo file avail");
				display = false;
			} else {
				log.info("Logo file NOt avail");
				display = true;
			}
		} else {
			choosen = false; 
			Faces.addError("Please Choose Brand")
		} 
		context.addCallbackParam("choosen", choosen);
	}
	public void login(ActionEvent event) {
		RequestContext context = RequestContext.getCurrentInstance(); 
		boolean loggedIn = false;
		
		loginRequest.userName = eMail;
		if( loginRequest.passWord != null && loginRequest.passWord.length()>=6 ) {
			loggedIn = true; 
			log.info("---"+loginRequest.passWord+"-------ASSDFSDFSD---------------"+loginRequest.userName);
			restService.myProfileResetPassword(loginRequest);
		} else {
			loggedIn = false; 
			Faces.error("password.mismatch", null);
		}
		loginRequest.passWord="";
		context.addCallbackParam("loggedIn", loggedIn);
		Faces.addInfo("User password changed sucessfully");
	}
}
