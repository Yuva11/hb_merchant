package com.finateltechhbm.ui;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import sun.misc.BASE64Encoder;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.Sanselan;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.finateltechhbm.dto.ContentType;
import com.finateltechhbm.dto.CusineCompactDTO;
import com.finateltechhbm.dto.CusineDto;
import com.finateltechhbm.dto.DealDTO;
import com.finateltechhbm.dto.DealSearchDTO;
import com.finateltechhbm.dto.DeliveryTypeDTO 
import com.finateltechhbm.dto.FoodCategoryCompactDTO;
import com.finateltechhbm.dto.FoodCategoryDto;
import com.finateltechhbm.dto.GenericDTO;
import com.finateltechhbm.dto.MerchantBranchDto;
import com.finateltechhbm.dto.MerchantBranchSelectDTO; 
import com.finateltechhbm.service.CopyUtil;
import com.finateltechhbm.service.RestService;
import com.finateltechhbm.validation.Faces;

import groovy.util.logging.Slf4j; 
@Service
@Scope("session")
@Slf4j
public class DealBean implements Serializable {
	@Autowired
	public RestService restService; 
	@Autowired
	public ProfileBean profileBean;
	@Autowired
	public CopyUtil copyUtils; 
	@Value('${downloadpath}')
	public  String downloadpath; 
	@Value('${MAX_IMAGESIZE_IN_KB}')
	public  String MAX_IMAGESIZE;
	@Value('${AD_IMAGE_WIDTH}')
	public  String AD_IMAGE_WIDTH;
	@Value('${AD_IMAGE_HEIGHT}')
	public  String AD_IMAGE_HEIGHT;
	String style;
	String contentType;
	DealDTO dealDto;
	List<DealDTO> filteredDeal; 
	List<DealDTO> dealDtoList = new ArrayList<DealDTO>(); 
	def selectedDeal;
	List<CusineDto> dealCusines;	
	List<FoodCategoryDto> foodCategories;
	def selectedFoodCategory;
	def selectedCusine;
	def selectedMerchantBranch;
	Set<CusineDto> selectedDealCusines;
	List<MerchantBranchDto> merchantBranches;
	String deliveryType;
	Date startDate = new Date();
	Date endDate = new Date();
	Date fromTime,dealDeliveryTime,toTime; 
	Authentication auth;
	long dealId;
	long merchantId; 
	String action="ADD";
	String merchantName;
	String keyWord="";
	boolean isNew;
	boolean dateDisable=true;
	boolean timeDisable=true;
	byte[] image;
	byte[] bytes = new byte[10240];
	def imageString ;
	UploadedFile file;
	String listenting;
	def imageName;
	MerchantBranchSelectDTO  selectDTO
	List<MerchantBranchSelectDTO> selectDTOList
	Long mBID;
	Long cusineID;
	Long foodCategoryID;
	List<CusineCompactDTO> cusineCompactDTOList
	
	List<DeliveryTypeDTO> dealDeliveryType
	List<DeliveryTypeDTO> selectedDeliveryType
	Integer deliverySize;
	Integer checkBoxIndex;
	boolean display = true;
	def imageWidthXHeight;
	def imageFileSize 
	DualListModel<MerchantBranchDto> pickBranch;
	List<MerchantBranchDto> sourceOutlets;
	List<MerchantBranchDto> targetOutlets;
	GenericDTO selectedFCAT = new GenericDTO();
	List<GenericDTO> foodCATS = new ArrayList<GenericDTO>();
	GenericDTO selectedCUISINE = new GenericDTO();
	List<GenericDTO> cusines = new ArrayList<GenericDTO>();
	DealSearchDTO searchDealDTO = new DealSearchDTO();
	@PostConstruct
	public void setConfig(){ 
		searchDealDTO = new DealSearchDTO();
		imageWidthXHeight = "${AD_IMAGE_WIDTH} X ${AD_IMAGE_HEIGHT}"
		imageFileSize = "${MAX_IMAGESIZE} KB"
	}
	public void handleTransfer(TransferEvent event) {
	//event.getItems() : List of items transferred
		log.info("Event Object : >> "+event)
	//event.isAdd() : Is transfer from source to target
	//event.isRemove() : Is transfer from target to source
	}
	public String showAdd() {  
		selectedFCAT = new GenericDTO();
		cusines = new ArrayList<GenericDTO>();
		display = true;
		endDate = fromTime = dealDeliveryTime = toTime= new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sourceOutlets = new ArrayList<MerchantBranchDto>();
		targetOutlets = new ArrayList<MerchantBranchDto>();
		startDate = sdf.parse(sdf.format(new Date()));
		 
		file = null;
		imageString = null;
		dealDto = new DealDTO(); 
		dealDto.foodType = "VEG" 
		merchantBranches = (List<MerchantBranchDto>)restService.getMerchantBranchesById(profileBean.merchantId);  
		if(merchantBranches.size() <= 0 ) { 
			Faces.addWarn("You must have atleast One Branch to add Deal"); 
			return "/p/viewdeals.xhtml";
		}
		
		for(MerchantBranchDto b : merchantBranches) {
			sourceOutlets.add(b);
		}
		
		pickBranch = new DualListModel<MerchantBranchDto>(sourceOutlets, targetOutlets);
		selectDTOList = copyUtils.merchantBranchesToCompact(merchantBranches)
		
		log.info("Compact Branches : "+selectDTOList);
		isNew=true;
		action="ADD";
		def cusinesList = restService.getAllCusines(); 
		def foodcategoriesList = restService.getAllFoodCategories(); 
		foodCategories= new ArrayList<FoodCategoryDto>(foodcategoriesList); 
		foodCATS = copyUtils.foodCategoryLIST_TO_GENERICS(foodCategories);
		dealCusines = new ArrayList<CusineDto>(cusinesList);
		cusines = copyUtils.cuisneLIST_TO_GENERICS(dealCusines)
		/*cusineCompactDTOList  = copyUtils.cusinesToCompact(dealCusines)
		log.info("dealCusines : "+cusineCompactDTOList);
		*/
		dealDeliveryType = (List<DeliveryTypeDTO>)restService.getAllDeliveryTypes()
		deliverySize = dealDeliveryType.size()
		int i=0;
		for(DeliveryTypeDTO ind : dealDeliveryType){
			
			if(ind.type.equals("HOMEDELIVERY")){
				checkBoxIndex = i
			}
			i++
		}
		log.info("deal Delivery Types : "+dealDeliveryType);
		if(style.equals("BUYOUT")) {
			return "/p/deal.xhtml";
		} 
		return "/p/promo.xhtml";  
	}
	public String showEdit() { 
		
		selectedCUISINE = new GenericDTO();
		cusines = new ArrayList<GenericDTO>();
		selectedFCAT = new GenericDTO();
		foodCATS = new ArrayList<GenericDTO>();
		//selected = new GenericDTO();
		isNew=true;
		action="EDIT";
		
		dealDto = (DealDTO)selectedDeal  
		selectedDeliveryType = dealDto.deliveryType
		log.info("On edit deal delivery Type : "+dealDto.deliveryType)
		/*fromTime = stringToTime(dealDto.availableTimeFrom)
		log.info("Content Type :: "+dealDto.contentType) 
		toTime = stringToTime(dealDto.availableTimeTo)*/
		startDate = stringToDate(dealDto.startDate)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//startDate = sdf.parse(sdf.format(startDate));
		endDate = stringToDate(dealDto.endDate)
		imageName  = profileBean.merchantId+"_"+dealDto.id+".png" 
		dealDeliveryType = (List<DeliveryTypeDTO>)restService.getAllDeliveryTypes()
		deliverySize = dealDeliveryType.size()
		File imageFile = new File( "${downloadpath}images/merchant/deal/"+imageName );
		if(imageFile.exists() && !imageFile.isDirectory()) {  
			display = false;
			image = imageFile.getBytes()
			BASE64Encoder encoder = new BASE64Encoder();
			imageString = encoder.encode(image);
		} else {
			imageString = null;
			display = true;
		}
		mBID = dealDto.merchantbranch.id
		merchantBranches = (List<MerchantBranchDto>)restService.getMerchantBranchesById(profileBean.merchantId);
		selectDTOList = copyUtils.merchantBranchesToCompact(merchantBranches)
		def cusinesList = restService.getAllCusines();
		def foodcategoriesList = restService.getAllFoodCategories();
		foodCategories= new ArrayList<FoodCategoryDto>(foodcategoriesList);
		foodCATS = copyUtils.foodCategoryLIST_TO_GENERICS(foodCategories);
		
		selectedFCAT = copyUtils.foodCategory_TO_GENERIC(dealDto.foodcategory)
		log.info("Selected Categories :: :: >>"+foodCATS.contains(selectedFCAT)) 
		
		
		dealCusines = new ArrayList<CusineDto>(cusinesList);
		cusines = copyUtils.cuisneLIST_TO_GENERICS(dealCusines)
		selectedCUISINE = copyUtils.cusineSET_TO_GENERIC(dealDto.dealCusines)
		log.info("selectedCUISINE : "+selectedCUISINE);
		log.info("Is Contains :: "+cusines.contains(selectedCUISINE))
		int i=0;
		for(DeliveryTypeDTO ind : dealDeliveryType){ 
			if(ind.type.equals("HOMEDELIVERY")){
				checkBoxIndex = i
			}
			i++
		}
		if(style.equals("BUYOUT")) {
			return "/p/deal.xhtml";
		} 
		return "/p/promo.xhtml"; 
	}
	
	public String searchByKeyWord() {
		if(profileBean.merchantId == null ){
			return "/p/home.xhtml";
		}
		if(keyWord != null && keyWord.trim().length() > 0) {
			log.info("Show Deals For Merchant Id:: <<"+profileBean.merchantId+">> Search By KeyWord :: <<"+keyWord+">> ContentType :: <<"+contentType+">>");
			dealDtoList = restService.getAllDealsByMerchantAndKeyWord(contentType,profileBean.merchantId, keyWord);
			return "/p/viewdeals.xhtml";
		}
		isNew = true;
		action = "VIEW";
		return showDeals();
	}
	public String showDeals() {
		searchDealDTO = new DealSearchDTO();
		if(profileBean.merchantId == null ){
			return "/p/home.xhtml";
		}
		log.info("Show Deals For Merchant Id:: "+profileBean.merchantId);
		isNew = true;
		action = "VIEW";
		/*auth = SecurityContextHolder.getContext().getAuthentication();
		merchantId = restService.getMerchantByUserName(auth.getName());*/
		dealDtoList = restService.getAllDealsByMerchant(contentType,profileBean.merchantId);
		return "/p/viewdeals.xhtml";
	}
	public String searchDeals() {
		if(profileBean.merchantId == null ){
			return "/p/home.xhtml";
		}
		if(searchDealDTO.startDate == null || searchDealDTO.endDate == null ){
			return showDeals();
		}
		if(searchDealDTO.startDate >= searchDealDTO.endDate) {
			Faces.addWarn("Start Date must be less than End Date");
			return showDeals();
		}
		log.info("Show Deals For Merchant Id:: "+profileBean.merchantId);
		isNew = true;
		action = "VIEW";
		searchDealDTO.contenttype = contentType
		searchDealDTO.merchantId = profileBean.merchantId
		dealDtoList = restService.searchDeals(searchDealDTO);
		return "/p/viewdeals.xhtml";
	}
	public String delete() { 
		restService.deleteDeal(dealId);
		/*auth = SecurityContextHolder.getContext().getAuthentication();
		merchantId = restService.getMerchantByUserName(auth.getName());*/
		dealDtoList = restService.getAllDealsByMerchant(contentType,profileBean.merchantId);
		Faces.addInfo("Deal deleted Sucessfully");
		log.info("DealDtoList : "+dealDtoList.size());
		return "/p/viewdeals.xhtml";
	}
	public void originalPriceEvent() {
		dealDto.originalPrice = dealDto.originalPrice   
	}
	public void discountEvent() {
		int discount = 0;
		if(dealDto.dealPrice > dealDto.originalPrice){
			Faces.addWarn("Deal Price must be less than original Price");
			dealDto.dealPrice = dealDto.dealDiscountPercent = 0;
		} else {
		discount = (dealDto.dealPrice/dealDto.originalPrice)*100 
		dealDto.dealDiscountPercent = 100- discount
		}
	}
	static Date dateTOTimeTODate(Date date){
		if(date == null ){
			return null
		}
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return new SimpleDateFormat("HH:mm:ss").parse(dateFormat.format(date.getTime()));
	}
	@SuppressWarnings("deprecation")
	public def addDeals() {
		
		log.info("Selected Cuisine :: "+selectedCUISINE.name);
		dealDto.deliveryType = selectedDeliveryType
		log.info("dealDto.contentType.equals(ContentType.BUYOUT) "+dealDto.contentType.equals(ContentType.BUYOUT))
		dealDto.contentType = contentType;
		log.info("action :: "+action) 
		log.info("Image File Is Empty or Not : "+file); 
		
		MerchantBranchDto merchantBranchDto = new MerchantBranchDto();
		merchantBranchDto.id = mBID;
		dealDto.merchantbranch = merchantBranchDto
		
		log.info("Merchant Branch id ::  "+mBID )
		
		if(!checkAddDeal()){ 
			return null
		}
		if(startDate <=  endDate){
			//Date Conversions  
			dealDto.startDate  = dateToFormattedString(startDate,1);
			dealDto.endDate = dateToFormattedString(endDate,1);
			//Set the selected food category to deal 
			FoodCategoryDto fc = new FoodCategoryDto();
			//########################################
			fc.id = selectedFCAT.id;
			log.info("Selected Category :: "+selectedFCAT.name)
			dealDto.foodcategory  = fc
			//Set the selected Cusine to deal
			Set<CusineDto> dealCusines = new HashSet<CusineDto>();
			
			CusineDto cusine = new CusineDto();
			log.info("Selected Cuisine :: "+selectedCUISINE.name);
			cusine.id = selectedCUISINE.id
			dealCusines.add(cusine ); 
			dealDto.setDealCusines(dealCusines); 
			DealDTO response
			if(!action.equals("EDIT")){
				if(style.equals("BUYOUT")) {
					for(MerchantBranchDto b : pickBranch.getTarget()) {
						
						log.info("Target Branch Id <<"+b.id+">> Branch Name ----------->> "+b.branchName);
						dealDto.merchantbranch = b
						log.info("deal Merchant Branch Id <<"+dealDto.merchantbranch.id+">> Branch Name ----------->> "+dealDto.merchantbranch.branchName);
						response = restService.addDealForMerchantBranch(dealDto);
						log.info("Check Image File is Empty :: "+file);
						if(file != null && response != null) {
							DealDTO deal = (DealDTO)response;
							def dealId = deal.id;
							def filename ="${downloadpath}images/merchant/deal/"+profileBean.merchantId+"_"+deal.id+".png" 
				            try {  
					            copyFile(filename, file.getInputstream() );
					        } catch (IOException e) {
					            e.printStackTrace();
					        }
				        }else{
							log.info("File Not found"+file);
				        }
						
					}
				} else {
					response = restService.addDealForMerchantBranch(dealDto);
					log.info("Check Image File is Empty :: "+file);
					if(file != null && response != null) {
						DealDTO deal = (DealDTO)response;
						def dealId = deal.id;
						def filename ="${downloadpath}images/merchant/deal/"+profileBean.merchantId+"_"+deal.id+".png"
						try {
							copyFile(filename, file.getInputstream() );
						} catch (IOException e) {
							e.printStackTrace();
						}
					}else{
						log.info("File Not found"+file);
					}
				}
				if (response != null) {
					log.info("Deal  Id : " + response);
					Faces.addInfo("Deal Has Been successfully saved for Merchant ");
					dealDtoList = restService.getAllDealsByMerchant(contentType, profileBean.merchantId);
					log.info("DealDtoList : "+dealDtoList.size());
					 dealDto = new DealDTO();
					 
					 imageString=null;
					 
					 return "/p/viewdeals.xhtml";
				}
			} else{
				log.info("Check Image File is Empty :: "+file); 
				if(file != null ) {  
					def filename = "${downloadpath}images/merchant/deal/"+profileBean.merchantId+"_"+dealDto.id+".png"
					try {
						copyFile(filename, file.getInputstream() ); 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					log.info("File Not found"+file);
				}
				
				def updateResponse = restService.updateDeal(dealDto)
				dealDtoList = restService.getAllDealsByMerchant(contentType, profileBean.merchantId);
				if (updateResponse != null) {
					Faces.addInfo("Deal Updated Sucessfully");
					log.info("DealDtoList : "+dealDtoList.size());
					dealDto = new DealDTO();
					
					imageString=null;
					return "/p/viewdeals.xhtml";
				}
			} 
			 
		}
		if(dealDto.contentType.equals(ContentType.BUYOUT)){
			return "/p/deal.xhtml";
		}else{
			return "/p/promo.xhtml";
		}
	} 
	 
	public static String  dateToString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return sdf.format(date).toString();
	}
	public static def  dateToFormattedString(Date date,int i){
		SimpleDateFormat sdf
		if(i == 1){
			sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		}else{
			sdf = new SimpleDateFormat("HH:mm a");
		}
		return sdf.format(date);
	} 
 
	public   boolean checkAddDeal() { 
		 
		if(dealDto.dealPrice > dealDto.originalPrice && dealDto.contentType.equals(ContentType.BUYOUT)){
			Faces.addWarn("Deal Price must be less than original price");
			return false;
		}
		log.info("===================================== openingQuantity ======"+dealDto.openingQuantity)
		if(dealDto.openingQuantity <= 0 && dealDto.contentType.equals(ContentType.BUYOUT) ){
			Faces.addWarn("Deal Quantity must be greater than zero");
			return false;
		}
		if(file == null && !action.equals("EDIT")){
			Faces.addWarn("Please Upload the deal Image");
			return false;
		}
		if(action.equals("EDIT") && endDate < new Date() ){
			Faces.addWarn("End Date must be greater than current date");
			return false;
		}
		if((action.equals("EDIT") || !style.equals("BUYOUT"))&& mBID == null){
			Faces.addWarn("You have to choose Outlet");
			return false;
		}
		if(selectedCUISINE == null){
			Faces.addWarn("You have to choose Cusine to add Deal");
			return false;
		}
		if(selectedFCAT == null){
			Faces.addWarn("You have to choose Food Category to add Deal");
			return false;
		}
		if(endDate <= startDate ){
			Faces.addWarn("Start Date must be less than end Date");
			return false
		} 
		if(dateTOTimeTODate(endDate) <= dateTOTimeTODate(startDate) ){
			Faces.addWarn("Start Time must be less than end Time");
			return false
		}
		if(action.equals("ADD") && style.equals("BUYOUT") && (pickBranch.getTarget() == null || pickBranch.getTarget().size() < 1)) {
			Faces.addWarn("You have to choose atleast one Outlet");
			return false;
		}
		return true;
	}
	public static void copyFile(def fileName, InputStream input ) {
		log.info("Deal Image Copy into "+fileName);
		try {
		
			File file = new File(fileName);
			 OutputStream out = new FileOutputStream(file);
			 int read = 0;
			 byte[] bytes = new byte[10240];
			 while ((read = input.read(bytes)) != -1) {
				 out.write(bytes, 0, read);
			 }
			 input.close();
			 out.flush();
			 out.close();
			 System.out.println("New file created!");
		} catch (IOException e) {
			 System.out.println(e.getMessage());
		}
	}
	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[20480];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}
		is.close();
		os.close();
	} 
	public void myFileUpload(FileUploadEvent event) {
		log.info("File upload Event called file Size :: "+event.getFile().getSize()); 
		def type=event.getFile().getContentType();

		if((!type.contains("png")) &&
				(!type.contains("jpg")) && 
				(!type.contains("jpeg") )){
			Faces.addWarn("Image type should be .png or .jp(e)g");
			return;
		} 
		if((event.getFile().getSize()> (1024 * Integer.parseInt(MAX_IMAGESIZE)))) {
			Faces.addWarn("Image size must be lesser than  "+MAX_IMAGESIZE+" KB");
			return;
		}
		file = event.getFile();
		image = event.getFile().getContents();
		BASE64Encoder encoder = new BASE64Encoder();
		imageString = encoder.encode(image);
		ImageInfo imageInfo = Sanselan.getImageInfo(image)
		
		if(imageInfo.width < Integer.parseInt(AD_IMAGE_WIDTH) || imageInfo.height < Integer.parseInt(AD_IMAGE_HEIGHT)) {
			Faces.addWarn("Image : Width by Height must be greater than "+AD_IMAGE_WIDTH+" by "+ AD_IMAGE_HEIGHT);
			imageString = null;
			display = true;
			return;
		}
		int physicalWidthDpi = imageInfo.getWidth();
		int physicalHeightDpi = imageInfo.getPhysicalHeightDpi();
		log.info("Width : "+imageInfo.getWidth())
		log.info("height : "+imageInfo.height) 
		log.info("File size :: "+ event.getFile().getSize()); 
		display = false;
	}
	Date stringToTime(String dateString){
		log.info("Date :: "+dateString);
		if(dateString == null){
			return new Date();
		} else { 
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString); 
			log.info("Date :: "+date);
			return date
		}
	}
	Date stringToDate(String dateString){
		log.info("Date :: "+dateString);
		if(dateString == null){
			return new Date();
		} else {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
			log.info("Date :: "+date);
			return date
		}
	}
} 