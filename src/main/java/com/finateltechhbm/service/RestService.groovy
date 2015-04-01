package com.finateltechhbm.service

import groovy.util.logging.Slf4j

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

import com.finateltechhbm.dto.CompactLocation
import com.finateltechhbm.dto.ContentDealDTO
import com.finateltechhbm.dto.DealDTO
import com.finateltechhbm.dto.DealDashBoardDTO
import com.finateltechhbm.dto.DealOrdersDto
import com.finateltechhbm.dto.DealSearchDTO;
import com.finateltechhbm.dto.GroupUserDTO
import com.finateltechhbm.dto.LoginRequest
import com.finateltechhbm.dto.MerchantDto
import com.finateltechhbm.dto.MerchantOrdersDTO
import com.finateltechhbm.dto.OrderDetailsCustomHBMDto
import com.finateltechhbm.dto.SalesReportDTO
import com.finateltechhbm.validation.Faces
@Service
@Slf4j
class RestService {
	@Value('${rest.url}')
	String baseUrl;

	@Value('${rest.timeout}')
	Integer timeout;
	private def parseError(HttpClientErrorException ex){
		log.error("Parse Error : "+ex.statusCode)
		if((ex.statusCode ==  HttpStatus.BAD_REQUEST||ex.statusCode ==  HttpStatus.CONFLICT) && ex.responseHeaders['Error-Code'] !=null && ex.responseHeaders['Error-Code'].size() ==1 ) {
			log.error("----------  "+ex.responseHeaders['Error-Code'][0])
			return ex.responseHeaders['Error-Code'][0]
		}
		return "999"
	}
	public GroupUserDTO auth(LoginRequest request) {
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			log.info("base url "+baseUrl+"; request.userName"+request.userName);	
			return restTemplate.postForObject("${baseUrl}/groupuser/auth",request, GroupUserDTO.class);
		}catch(HttpClientErrorException e){
            log.error("HttpClientErrorException : ",e.responseHeaders['Error-Code'].toString())
            errorCode = parseError(e)
        }catch(Exception e){
			log.error("Exception on authentication :: "+e)
			
		}
		return false;
	}
	public boolean groupuserAuth(LoginRequest request) {
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			log.info("base url "+baseUrl+"; request.userName"+request.userName);
			return restTemplate.postForObject("${baseUrl}/groupuser/auth",request, Boolean.class);
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException : ",e.responseHeaders['Error-Code'].toString())
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("Exception on authentication :: "+e)
			
		}
		return false;
	}
	boolean resetPassword(String emailId){
		log.info("Reset password in Rest Services "+emailId)
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.postForLocation("${baseUrl}/mailer/reset/${emailId}/password", boolean.class)
			return  true;
		}catch(HttpClientErrorException e){
            log.error("HttpClientErrorException : ",e.responseHeaders['Error-Code'].toString())
            errorCode = parseError(e)
			
        }catch(Exception e){
			log.error("Error on Reset Password",e)
			throw e
		}
		return false;
	}
	def getMerchantByUserName(String userName){
		def errorCode = "999"
		log.info("get Merchant ID BY UserName : "+userName)
		try{
			RestTemplate restTemplate = new RestTemplate();
			
			return restTemplate.getForObject("${baseUrl}/merchant/getMerchantBranch/${userName}/bymerchant", long.class)
		}catch(HttpClientErrorException e){
            log.error("HttpClientErrorException : ",e.responseHeaders['Error-Code'].toString())
            errorCode = parseError(e)
        }catch(Exception e){
			log.error("error on get Merchant by user name",e)
		}
		return null;
	}
	def getMerchantBranchesById(long merchantId){
		def errorCode = "999"
		log.info("get Merchant Branches ID : "+merchantId)
		try{
			RestTemplate restTemplate = new RestTemplate();
			
			def response = restTemplate.getForObject("${baseUrl}/merchant/getmerchantbrancheswithidandname/${merchantId}", ArrayList.class)
			return response;
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException : ",e.responseHeaders['Error-Code'].toString())
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get merchant branches by id",e)
		}
		return null;
	}
	DealDTO addDealForMerchantBranch(DealDTO dealDTO){
		log.info("Add Deal -  Deal Name : "+dealDTO)
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate(); 
			return restTemplate.postForObject("${baseUrl}/deal/add", dealDTO , DealDTO.class);
		}catch(HttpClientErrorException e){
            log.error("HttpClientErrorException ::",e)
            errorCode = parseError(e)
        }catch(Exception e){
			log.error("error on add deal for merchant branches",e)
		}
		Faces.warn(errorCode, null)
		return null;
	}
	List<DealDTO> getAllDealsForMerchantBranch(long merchantId , long merchantBranchId ){
		log.info("merchantId : "+merchantId+" ;  merchantBranchId : "+merchantBranchId);
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/deal/getdealbymerchant/${merchantId}" , ArrayList.class);
			List<DealDTO>  dealOrdersList = new ArrayList<DealDTO>(list);
			return dealOrdersList;
		}catch(HttpClientErrorException e){
			log.error(e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get all deals for merchant branches",e)
		}
		Faces.warn(errorCode, null)
		return null;
	}
	List<DealDTO> getAllDealsByMerchant(long merchantId){
		log.info("merchantId : "+merchantId);
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/deal/getdealbymerchant/${merchantId}" , ArrayList.class);
			List<DealDTO>  dealOrdersList = new ArrayList<DealDTO>(list);
			return dealOrdersList;
		}catch(HttpClientErrorException e){
			log.error(e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on all deals for merchant ",e)
		}
		Faces.warn(errorCode, null)
		return null;
	}
	List<DealDTO> getAllDealsByMerchant(String contentType, long merchantId){
		log.info("merchantId : "+merchantId+" ;contentType : "+contentType);
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/deal/getdealbymerchant/${contentType}/${merchantId}" , ArrayList.class);
			List<DealDTO>  dealOrdersList = new ArrayList<DealDTO>(list);
			return dealOrdersList;
		}catch(HttpClientErrorException e){
			log.error(e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on all deals for merchant ",e)
		}
		Faces.warn(errorCode, null)
		return null;
	}
	List<DealDTO> getAllDealsByMerchantAndKeyWord(String contentType, long merchantId, String keyWord){
		log.info("merchantId : "+merchantId+" ;contentType : "+contentType);
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/deal/getdealbymerchant/${contentType}/${merchantId}/${keyWord}" , ArrayList.class);
			List<DealDTO>  dealOrdersList = new ArrayList<DealDTO>(list);
			return dealOrdersList;
		}catch(HttpClientErrorException e){
			log.error(e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on all deals for merchant ",e)
		}
		Faces.warn(errorCode, null)
		return null;
	}
	public def updateDeal(DealDTO dealDto){
		log.info("dealId : "+dealDto.id ); 
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			 def merchantEntity = restTemplate.put("${baseUrl}/deal/update", dealDto , DealDTO.class);
			return "Success";
		}catch(HttpClientErrorException e){
			log.error("error on update deal ",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on update deal ",e)
		}
		Faces.warn(errorCode, null)
		return null;
	}
	public boolean deleteDeal(long dealId){
		log.info("dealId : "+dealId );
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.delete("${baseUrl}/deal/delete/${dealId}" );
			
			return true;
		}catch(HttpClientErrorException e){
			log.error(e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on delete deal ",e)
		}
		return false;
	}
	public def getAllCusines( ){ 
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/cusine/get",ArrayList.class );
			log.info("Cusine List size : "+list.size());
			return list;
		}catch(HttpClientErrorException e){
			log.error(e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get all Cusines ",e)
		}
		return null;
	}
	public def getAllFoodCategories( ){
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/foodcategory/get",ArrayList.class );
			log.info("Cusine List size : "+list.size());
			return list;
		}catch(HttpClientErrorException e){
			log.error(e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get all Food Catogories",e)
		}
		return null;
	}
	List<DealOrdersDto> getAllMyDealsOrderByMerchant(long merchantId){
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/dealorder/getorder/${merchantId}",ArrayList.class );
			log.info("ORders List size : "+list.size());
			List<DealOrdersDto>  dealOrdersList = new ArrayList<DealOrdersDto>(list);
			return dealOrdersList ;
		}catch(HttpClientErrorException e){
			log.error(e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get All MyDeals OrderBy Merchant ",e)
		}
		return null;
	}
	List<DealOrdersDto> getAllMyDealsOrderByMerchantAndKeyWord(long merchantId, String keyWord){
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/dealorder/getorder/${merchantId}/${keyWord}",ArrayList.class );
			log.info("ORders List size : "+list.size());
			List<DealOrdersDto>  dealOrdersList = new ArrayList<DealOrdersDto>(list);
			return dealOrdersList ;
		}catch(HttpClientErrorException e){
			log.error(e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get All MyDeals OrderBy Merchant ",e)
		}
		return null;
	}
	public boolean deliverDeal(long dealId, boolean deliveryStatus){
		log.info("dealId : "+dealId );
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.delete("${baseUrl}/dealorder/deliver/${dealId}/${deliveryStatus}");
			
			return true;
		}catch(HttpClientErrorException e){
			log.error(e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on delete deal ",e)
		}
		return false;
	}
	def addContentForMerchant(long merchantId , ContentDealDTO  DTO){
		log.info("Add Content: "+DTO)
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			def response = restTemplate.postForObject("${baseUrl}/content/add/${merchantId}", DTO , Object.class);
			return response;
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on add COntent for merchant",e)
		}
		Faces.warn(errorCode, null)
		return null;
	}
	def updateContentForMerchant(long merchantId , ContentDealDTO  DTO){
		log.info("Update Advertisement Content: "+DTO)
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			def response = restTemplate.postForObject("${baseUrl}/content/update/${merchantId}", DTO , Object.class);
			return response;
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on add COntent for merchant",e)
		}
		Faces.warn(errorCode, null)
		return null;
	}
	def getContentForMerchant(long merchantId){
		log.info("Get Content By: "+merchantId)
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/content/get/${merchantId}",ArrayList.class );
			log.info("Content List : "+list.size())
			return list;
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get COntent for merchant",e)
		}
		Faces.error(errorCode, null)
		return null;
	}
	 
	def getContentForMerchantByKeyWord(long merchantId, String keyWord){
		log.info("Get Content By: "+merchantId+" Key Word :: "+keyWord)
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/content/get/${merchantId}/${keyWord}",ArrayList.class );
			log.info("Content List : "+list.size())
			return list;
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get COntent for merchant",e)
		}
		Faces.error(errorCode, null)
		return null;
	}
	def getAllLocations( ){
		log.info("Get All Locations Called")
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/location/get",ArrayList.class );
			log.info("Content List : "+list.size())
			return list;
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get Location for Content",e)
		}
		Faces.error(errorCode, null)
		return null;
	}
	def updateLocationsForAds(Long contentId,List<CompactLocation> compactLocationList ){
		log.info("Update Location for Advertisement Id : "+contentId)
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			def response = restTemplate.postForObject("${baseUrl}/content/addlocations/${contentId}", compactLocationList , Object.class);
			return response; 
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("Error on Update Location for Advertisement",e)
		}
		Faces.error(errorCode, null)
		return null;
	}
	 
	def getAllDeliveryTypes( ){
		log.info("Get All Locations Called")
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/delivery/get",ArrayList.class );
			log.info("Content List : "+list.size())
			return list;
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get DElivery types for Deal",e)
		}
		Faces.error(errorCode, null)
		return null;
	}
	def getAdForMerchantDashBoard(def merchantId){
		log.info("Get All Adds For Merchant DashBoard")
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/content/merchant/get/${merchantId}",ArrayList.class );
			log.info("Content List : "+list.size())
			return list;
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException on get advertisement  ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get advertisement ",e)
		}
		Faces.warn(errorCode, null)
		return null;
		
	}
	def getDealForMerchantDashBoard(def merchantId){
		log.info("Get All Deal For Merchant DashBoard")
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/deal/merchant/BUYOUT/${merchantId}",ArrayList.class );
			log.info("Content List : "+list.size())
			return list;
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException on get Deal  ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get Deal ",e)
		}
		Faces.warn(errorCode, null)
		return null;
		
	}
	def getPromoForMerchantDashBoard(def merchantId){
		log.info("Get All Promo For Merchant DashBoard")
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/deal/merchant/PROMOTION/${merchantId}",ArrayList.class );
			log.info("Content List : "+list.size())
			return list;
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException on get Promo  ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get Promo ",e)
		}
		Faces.warn(errorCode, null)
		return null;
		
	}
	def getAdSettings(){
		log.info("Get advertisement  Settings::")
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/adsettings/get",ArrayList.class );
			log.info("Content List : "+list.size())
			return list;
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException on get advertisement  Settings::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get advertisement Settings ",e)
		}
		Faces.warn(errorCode, null)
		return null;
		
	}
	def getCities( ){
		log.info("Get All Cities")
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/city/get",ArrayList.class );
			log.info("City List : "+list.size())
			return list;
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException on get City  ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get City ",e)
		}
		Faces.warn(errorCode, null)
		return null;
		
	}

	def getLocationByCity(Long cityId ){
		log.info("Get All Cities")
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			List list = restTemplate.getForObject("${baseUrl}/location/bycity/${cityId}",ArrayList.class );
			log.info("City List : "+list.size())
			return list;
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException on get City  ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get City ",e)
		}
		Faces.warn(errorCode, null)
		return null;
		
	}
	def getDealInventory(DealDashBoardDTO data ){
		log.info("Get deals Inventory")
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			//sandi
			MerchantOrdersDTO list = restTemplate.postForObject("${baseUrl}/merchant/dashboard", data , MerchantOrdersDTO.class);
			return list;
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException on get deals Inventory ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on deals Inventory ",e)
		}
		Faces.warn(errorCode, null)
		return null;
		
	}
	
	def myProfileResetPassword(LoginRequest request){
		log.info("myProfile Reset Password: "+request)
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			def response = restTemplate.postForObject("${baseUrl}/user/changepwd", request , Object.class);
			return response;
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on add deal for merchant branches",e)
		}
		Faces.warn(errorCode, null)
		return null;
	}
	def getMerchantDetails(def merchantId){
		def errorCode = "999"
		log.info("get Merchant ID BY UserName : "+merchantId)
		try{
			RestTemplate restTemplate = new RestTemplate();
			
			return restTemplate.getForObject("${baseUrl}/merchant/get/${merchantId}", MerchantDto.class)
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException : ",e.responseHeaders['Error-Code'].toString())
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get Merchant by user name",e)
		}
		return null;
	}
	List<DealOrdersDto> getOrderSalesList(SalesReportDTO data){
		log.info("get Order Sales List : "+data)
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			DealOrdersDto[] list = restTemplate.postForObject("${baseUrl}/dealorder/report", data , DealOrdersDto[].class);
			return Arrays.asList(list);
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get Order Sales List ",e)
		}
		Faces.warn(errorCode, null)
		return null;
	}

	List<DealDTO> searchDeals(DealSearchDTO data){
		log.info("get search Deals List : "+data)
		def errorCode = "999"
		try{
			RestTemplate restTemplate = new RestTemplate();
			DealDTO[] list = restTemplate.postForObject("${baseUrl}/deal/getsearchdeal", data , DealDTO[].class);
			return Arrays.asList(list);
		}catch(HttpClientErrorException e){
			log.error("HttpClientErrorException ::",e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get Deals List ",e)
		}
		Faces.warn(errorCode, null)
		return null;
	}
	
	OrderDetailsCustomHBMDto getOrderDetailsByOrderId(String orderId){
		def errorCode = "999"
		OrderDetailsCustomHBMDto detailsHBMDto=new OrderDetailsCustomHBMDto();
		try{
			RestTemplate restTemplate = new RestTemplate();
			detailsHBMDto = restTemplate.postForObject(baseUrl+"/dealorder/getOrderDetails/"+orderId,null,OrderDetailsCustomHBMDto.class );
			return detailsHBMDto ;
		}catch(HttpClientErrorException e){
			log.error(e)
			errorCode = parseError(e)
		}catch(Exception e){
			log.error("error on get Order Detais By OrderId ",e)
		}
		return null;
	}
}
