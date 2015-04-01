package com.finateltechhbm.ui
import java.util.regex.Matcher;

import com.finateltechhbm.service.ExpUtil; 

import java.util.regex.Pattern;


import java.io.InputStream; 
import java.text.SimpleDateFormat
import java.util.Date;

import javax.annotation.PostConstruct;

import groovy.util.logging.Slf4j;

import org.apache.sanselan.ImageInfo; 
import org.apache.sanselan.Sanselan; 
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

import com.finateltechhbm.dto.CityDto;
import com.finateltechhbm.dto.CompactLocation;
import com.finateltechhbm.dto.ContentDealDTO; 
import com.finateltechhbm.dto.ContentTemplate;
import com.finateltechhbm.dto.LocationDTO;
import com.finateltechhbm.service.RestService;
import com.finateltechhbm.validation.Faces; 
@Service
@Scope("session")
@Slf4j
class ContentBean {
	@Autowired
	public RestService restService;
	@Autowired
	public ProfileBean profileBean;
	@Value('${downloadpath}')
	public  String downloadpath;
	@Value('${MAX_IMAGESIZE_IN_KB}')
	public  String MAX_IMAGESIZE;
	@Value('${MAX_VIDEOSIZE_IN_MB}')
	public  String MAX_VIDEOSIZE;
	@Value('${AD_IMAGE_WIDTH}')
	public  String AD_IMAGE_WIDTH;
	@Value('${AD_IMAGE_HEIGHT}')
	public  String AD_IMAGE_HEIGHT;
	Date startDate = new Date();
	Date endDate = new Date();
	def fileName = "";
	//FileWriterDTO data = new FileWriterDTO();
	def selectedDeal;
	def selectedDealDTO
	ContentDealDTO dealDTO = new ContentDealDTO();
	List<ContentDealDTO> dealDTOList 
	List<ContentDealDTO> dashBoardAdList
	List<LocationDTO> locationList 
	String text
	String action="ADD";
	String keyWord="";
	def imageString ;
	def imageString1 ;
	UploadedFile file;
	UploadedFile thumbNailImage;
	UploadedFile adVideo;
	byte[] image;
	byte[] image1; 
	def locations; 
	List<CityDto> cities;
	String city;
	List<CompactLocation>  loc ;
	List<CompactLocation>  compactLoc ;
	int locationSize = 0; 
	def thumbNailText;
	Map<String,List<CompactLocation>> mapdata = new HashMap<String,List<CompactLocation>>();
	Map<String,List<CompactLocation>> finalLocationMap = new HashMap<String,List<CompactLocation>>();
	Map<String,List<CompactLocation>> selectedLocationMap = new HashMap<String,List<CompactLocation>>();
	Map<String,Boolean> selectedAllMap = new HashMap<String,Boolean>();
	CompactLocation mapcountry;
	CityDto mapcity;
	Map<CompactLocation,CompactLocation> maplocations;
	Map<CityDto,CityDto> mapcities;
	HashSet<CompactLocation>  publishedLocations ;
	HashSet<CompactLocation> selectedLocations;
	List<CompactLocation>  allLocations ;
	boolean thumbPlace = true;
	boolean detailPlace = true;
	def imageWidthXHeight;
	def imageFileSize
	def videoFileSize
	boolean selectedAll;
	String cityName;
	@PostConstruct
	public void setConfig(){ 
		videoFileSize ="${MAX_VIDEOSIZE} MB"
		imageWidthXHeight = "${AD_IMAGE_WIDTH} X ${AD_IMAGE_HEIGHT}"
		imageFileSize = "${MAX_IMAGESIZE} KB"
	}
	public String showLocations() { 
		city = "";
		selectedAll = false;
		allLocations = new ArrayList<CompactLocation>();
		compactLoc = new ArrayList<CompactLocation>();
		publishedLocations = new ArrayList<CompactLocation>();
		selectedLocations = new HashSet<CompactLocation>();
		mapdata = new HashMap<String,List<CompactLocation>>();
		selectedAllMap = new HashMap<String,Boolean>();
		selectedLocationMap = new HashMap<String,List<CompactLocation>>();
		
		//All Cities
		cities = (List<CityDto>)restService.getCities();
		log.info("No of Cities :: "+cities.size());
		//ALready Published Locations
		if(loc != null){
			CompactLocation comLoc = null;
			for(CompactLocation cl : loc){
				comLoc = new CompactLocation();
				comLoc.id = cl.id;
				comLoc.name = cl.name;
				compactLoc.add(comLoc);
			}
		}
		//set all locations and published locations
		HashSet<CompactLocation> tempLocation = new HashSet<CompactLocation>();
		for(CityDto c : cities){
			tempLocation = new HashSet<CompactLocation>();
			locationList = new ArrayList<LocationDTO>();
			locations = new ArrayList<CompactLocation>();
			locationList = (List<LocationDTO>)restService.getLocationByCity(c.id); 
			locations = copyLocation(locationList);
			//Select All CheckBox reset
			selectedAllMap.put(c.name,false);
			if(locations != null){ 
				mapdata.put(c.name, locations); 
				for(CompactLocation cl : locations) {
					if(compactLoc.contains(cl)){
						tempLocation.add(cl)
					} 
				}
			}else { 
				mapdata.put(c.name, new ArrayList<CompactLocation>());
			}
			//Existing Locations for each city
			selectedLocationMap.put(c.name, tempLocation);
		} 
		return "/p/publishlocations.xhtml?faces-redirect=true";
	}
	public void onCityChange(){
		selectedAll = selectedAllMap.get(city);
		log.info("Currently selected city :: "+city+"; Is Selected All Locations << "+selectedAll+" >>");
		/*Set<String> Keys = selectedLocationMap.keySet();
		for(def k : Keys){
			log.info("Key -- "+k+" -----Cities ---- "+selectedLocationMap.get(k));
		}*/
		
		if(city !=null && !city.equals("")) {
			//Previous City selected locations
			selectedLocationMap.put(cityName,selectedLocations);
			log.info("City Name :: on first time check :: "+cityName+" ;;\n  Selected locations :: "+selectedLocations);
			//All Locations for current city
			allLocations = mapdata.get(city) ;
			//Current City selected locations
			selectedLocations = selectedLocationMap.get(city); 
		} else {
			selectedLocations = new HashSet<CompactLocation>();
			allLocations = new ArrayList<CompactLocation>();
		}
		
	}
	public void setTemp(){ 
		//Capture city onclick
		cityName = city;
		log.info("Current city selected locations monitoring : ");
		log.info("Monitored city : "+cityName+"=="+city+" ;; "+selectedLocations);
		
	}
	public void onSelectAll() {
		log.info("On Select city name <<"+city+">> Is Selected << "+selectedAll)
		if(selectedAll) {
			if(city !=null && !city.equals("")) {
				allLocations = mapdata.get(city) ;
				selectedLocations = new HashSet<CompactLocation>(allLocations); 
			}
		} else {
			selectedLocations = new HashSet<CompactLocation>();
		}
		selectedLocationMap.put(city,selectedLocations);
		selectedAllMap.put(city,selectedAll);
		log.info("Select All City <<"+city+">> Checked << "+selectedAll+" >>");
		Set<String> keys = selectedAllMap.keySet();
		for(def k : keys){
			log.info("City Key Name :: "+k+" ; Is Selected : "+selectedAllMap.get(k))
		}
	}
	
	public String addLocationsForAd() {
		ContentDealDTO deal  = new ContentDealDTO();
		deal = (ContentDealDTO)selectedDeal;
		//HashSet<CompactLocation> tempLocation;
		Set<String> Keys = selectedLocationMap.keySet();
		log.info("Select All City <<"+city+">> Checked << "+selectedLocations+" >>");
		selectedLocationMap.put(city, selectedLocations)
		selectedLocations = new HashSet<CompactLocation>();
		for(def k : Keys){
			log.info("Key -- "+k+" -----Cities ---- "+selectedLocationMap.get(k));
			if(selectedLocationMap.get(k)!= null && selectedLocationMap.get(k).size()>0) {
				selectedLocations.addAll(selectedLocationMap.get(k));
			}
		}
		log.info("selectedLocations -------------------- "+selectedLocations);
		
		restService.updateLocationsForAds(deal.id,new ArrayList<CompactLocation>(selectedLocations) );
		selectedLocationMap = new HashMap<String,List<CompactLocation>>();
		//restService.updateLocationsForAds(deal.id,publishedLocations );
		Faces.addInfo("Locations added for Advertisement")
		return showContents();
	} 
	public String showAdd() {
		log.info("Show Add Form function Called");
		file = thumbNailImage = adVideo = selectedDeal = image = image1 = null;
		imageString = null;
		imageString1 = null; 
		thumbPlace = detailPlace = true;
		dealDTO = new ContentDealDTO();
		dealDTO.contentTemplate = ContentTemplate.TEXT_ONLY; 
		action="ADD";
		return "/p/content.xhtml";
	}
	public String showContents() {
		if(profileBean.merchantId == null ){
			return "/p/home.xhtml";
		}
		loc = new ArrayList<CompactLocation>();
		log.info("Show Contents function Called");
		file = thumbNailImage = selectedDeal = image = image1 = null;
		adVideo = null;
		dealDTOList = (List<ContentDealDTO>)restService.getContentForMerchant(profileBean.merchantId)  
		locationList = (List<LocationDTO>)restService.getAllLocations()
		locations = copyLocation(locationList); 
		locationSize = locationList.size()
		log.info("Location List Size -- "+locationList.size())
		return "/p/viewcontents.xhtml";
	}
	public String searchByKeyWord() {
		if(profileBean.merchantId == null ){
			return "/p/home.xhtml";
		}
		if(keyWord != null && keyWord.trim().length() > 0) {
			log.info("Show Deals For Merchant Id:: <<"+profileBean.merchantId+">> Search By KeyWord :: <<"+keyWord+">> ");
			dealDTOList = (List<ContentDealDTO>)restService.getContentForMerchantByKeyWord(profileBean.merchantId, keyWord);
			return "/p/viewcontents.xhtml";
		} 
		return showContents();
	}
	public String showEdit() { 
		action="EDIT";  
		log.info("Selected Deal"+selectedDealDTO)
		//ContentDealDTO d  = new ContentDealDTO();
		dealDTO = (ContentDealDTO)selectedDealDTO
		startDate = stringToDate(dealDTO.startDate)
		endDate = stringToDate(dealDTO.endDate)
		log.info("Deal "+dealDTO) 
		File imageFile = new File( "${downloadpath}images/merchant/deal/THUMBNAIL_"+profileBean.merchantId+"_"+dealDTO.id+".png");
		if(dealDTO.thumbnailText != null && dealDTO.thumbnailText.length()>50){
			thumbNailText = dealDTO.thumbnailText.substring(0, 50).concat('....');
		}else{
		thumbNailText = dealDTO.thumbnailText
		}
		
		if(imageFile.exists() && !imageFile.isDirectory()) {
			image = imageFile.getBytes()
			BASE64Encoder encoder = new BASE64Encoder();
			imageString1 = encoder.encode(image);
			thumbPlace = false;
		} else {
			imageString1 = null;
			thumbPlace = true;
		}
		File imageFile1 = new File( "${downloadpath}images/merchant/deal/DISPLAY_"+profileBean.merchantId+"_"+dealDTO.id+".png");
	 
		if(imageFile1.exists() && !imageFile1.isDirectory()) {
			image1 = imageFile1.getBytes()
			BASE64Encoder encoder = new BASE64Encoder();
			imageString  = encoder.encode(image1);
			detailPlace = false;
		} else {
			imageString  = null;
			detailPlace = true;
		}
		return "/p/content.xhtml";
	}
	public List<CompactLocation> copyLocation(List<LocationDTO> list) {
		 if(list == null) {
			 return null;
		 }
		 List<CompactLocation> complocationList = new ArrayList<CompactLocation>();
		 CompactLocation location =null;
		 for(LocationDTO entity : list){
			 location = new CompactLocation();
			 location.id = entity.id;
			 location.name = entity.name;
			 complocationList.add(location);
		 } 
		 return complocationList;
	}
	public def submit() {
		if(startDate >=  endDate){
			Faces.addWarn("Start Date must be greater than End date");
			return null;
		}
		thumbPlace = detailPlace = true;
	/*	if(dealDTO.detailText != null){ 
			dealDTO.detailText = ExpUtil.CheckAndReplace(dealDTO.detailText);
		}*/
		dealDTO.startDate = dateToFormattedString(startDate)
		dealDTO.endDate = dateToFormattedString(endDate)
		log.info("Submit function Called action :: "+action );
		if(action.equals("ADD") || action.equals("EDIT")){ 
			if((!dealDTO.contentTemplate.equals(ContentTemplate.TEXT_ONLY)) && (!dealDTO.contentTemplate.equals(ContentTemplate.IMAGE_AND_TEXT))){
				log.info("image Only Video Check Image File is Empty :: "+file+"\n  Content Template :: "+dealDTO.contentTemplate );
				if((file == null || thumbNailImage == null) && !action.equals("EDIT")) {   
					Faces.addWarn("Please Upload the Advertisement Images"); 
					return "/p/content.xhtml";
				}  
			}
			if(dealDTO.contentTemplate.equals(ContentTemplate.IMAGE_AND_TEXT)){
				log.info("Image and text Check Image File is Empty :: "+file+"\n  Content Template :: "+dealDTO.contentTemplate );
				if((thumbNailImage == null) && !action.equals("EDIT")) {
					Faces.addWarn("Please Upload the Advertisement Images");
					return "/p/content.xhtml";
				}
			}
			if(dealDTO.contentTemplate.equals(ContentTemplate.VIDEO_AND_TEXT) && !action.equals("EDIT")){
				if(adVideo == null  ) { 
					Faces.addWarn("Please Upload the Advertisement Video "); 
					return "/p/content.xhtml";
				} 
				def type=adVideo.getContentType();  
			}
			ContentDealDTO contentDTO
			if(action.equals("ADD") ){
				contentDTO = (ContentDealDTO)restService.addContentForMerchant(profileBean.merchantId,dealDTO)
			} else {
				contentDTO = (ContentDealDTO)restService.updateContentForMerchant(profileBean.merchantId,dealDTO)
			}
			if(contentDTO == null){
				return "/p/content.xhtml";
			}
			if(!dealDTO.contentTemplate.equals(ContentTemplate.TEXT_ONLY) && contentDTO != null){
				log.info( "\n  Content reference Id :: "); 
				if(thumbNailImage != null || file != null || (dealDTO.contentTemplate.equals(ContentTemplate.VIDEO_AND_TEXT) && adVideo !=null)) {
					def filename = "${downloadpath}images/merchant/deal/THUMBNAIL_"+profileBean.merchantId+"_"+contentDTO.id+".png"
					log.info("PATH -------- "+filename)
					try { 
						if(thumbNailImage != null) {
							copyFile(filename, thumbNailImage.getInputstream() );
							log.info( "Thumb Nail Image Copied");
							thumbPlace = false;
						} 
						if(file != null){
							filename = "${downloadpath}images/merchant/deal/DISPLAY_"+profileBean.merchantId+"_"+contentDTO.id+".png" 
							copyFile(filename, file.getInputstream() );
							log.info("PATH -------- "+filename)
							log.info( "DISPLAY Image Copied");
							detailPlace = false;
						}
						if(dealDTO.contentTemplate.equals(ContentTemplate.VIDEO_AND_TEXT)  && adVideo !=null){
							filename = "${downloadpath}videos/"+profileBean.merchantId+"_"+contentDTO.id+".mp4" 
							copyAdVideoFile(filename, adVideo.getInputstream() );
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}  
			}  
			selectedDeal = contentDTO;
			loc = contentDTO.location;
			log.info("Selected ad :"+ selectedDeal)
			Faces.addInfo("Advertisement Sucessfully Added");
		}
		dealDTOList = (List<ContentDealDTO>)restService.getContentForMerchant(profileBean.merchantId)
		return showLocations();
	}
	public void thumbNailEvent() {
		dealDTO.thumbnailText = dealDTO.thumbnailText; 
	}
	public void thumbNailTextOnlyEvent() { 
		if(dealDTO.thumbnailText.length() > 50){
			thumbNailText = dealDTO.thumbnailText.substring(0, 50).concat('....');
		} else {
			thumbNailText = dealDTO.thumbnailText
		} 
	}
	public void detailEvent() {  
		if(dealDTO.detailText.length() > 400 && (dealDTO.contentTemplate.equals(ContentTemplate.TEXT_ONLY) )){
			dealDTO.detailText = dealDTO.detailText.substring(0, 400).concat('....');
		}
		if(dealDTO.detailText.length() > 80 && (dealDTO.contentTemplate.equals(ContentTemplate.IMAGE_AND_TEXT) || dealDTO.contentTemplate.equals(ContentTemplate.VIDEO_AND_TEXT) )){
			dealDTO.detailText = dealDTO.detailText.substring(0, 80).concat('....'); 
		}else {
			dealDTO.detailText = dealDTO.detailText; 
		}
		
		log.info('detailed text :'+dealDTO.detailText)
	}
	public void imageEvent() {
		dealDTO.imageText = dealDTO.imageText;
	}
	public void adNameChangeListener() {
		dealDTO.name = dealDTO.name;
	}
	public void myFileUpload(FileUploadEvent event) {
		log.info("------ in event function File Size :: "+event.getFile().getSize());
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
		ImageInfo imageInfo = Sanselan.getImageInfo(image);
		log.info("Width : "+imageInfo.getWidth())
		log.info("height : "+imageInfo.height)
		if(imageInfo.width < Integer.parseInt(AD_IMAGE_WIDTH) || imageInfo.height < Integer.parseInt(AD_IMAGE_HEIGHT)) { 
			Faces.addWarn("Image : Width by Height must be greater than "+AD_IMAGE_WIDTH+" by "+ AD_IMAGE_HEIGHT);
			image = null;
			detailPlace = true;
			return;
		}
		BASE64Encoder encoder = new BASE64Encoder();
		imageString = encoder.encode(image);
		detailPlace = false;
	}
	public void thumbNailUpload(FileUploadEvent event) {
		log.info("Max file size of Image : On thumbNailUpload :: "+MAX_IMAGESIZE+" ; VIDEO : "+MAX_VIDEOSIZE+" video File size in Integer : "+Integer.parseInt(MAX_VIDEOSIZE));
		log.info("Thumb Nail Image Upload event called File Size :: "+event.getFile().getSize());
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
		
		thumbNailImage = event.getFile();
		image1 = event.getFile().getContents();
		ImageInfo imageInfo = Sanselan.getImageInfo(image1);
		log.info("Width : "+imageInfo.getWidth())
		log.info("height : "+imageInfo.height)
		if(imageInfo.width < Integer.parseInt(AD_IMAGE_WIDTH) || imageInfo.height < Integer.parseInt(AD_IMAGE_HEIGHT)) { 
			Faces.addWarn("Image : Width by Height must be greater than "+AD_IMAGE_WIDTH+" by "+ AD_IMAGE_HEIGHT);
			image = null;
			thumbPlace = true;
			return;
		}
		BASE64Encoder encoder = new BASE64Encoder();
		imageString1 = encoder.encode(image1);
		thumbPlace = false;
	}
	public void videoUpload(FileUploadEvent event) {
		log.info("Max file size of Image : On videoUpload :: "+MAX_IMAGESIZE+" ; VIDEO : "+MAX_VIDEOSIZE+" video File size in Integer : "+Integer.parseInt(MAX_VIDEOSIZE));
		log.info("Video Upload event Called File Size :: "+event.getFile().getSize());
		log.info("Video Upload event Called File Name :: "+event.getFile().getFileName());
		
		def type=event.getFile().getContentType();

		if(!type.contains("mp4")){
			fileName = "";
			Faces.addWarn("Video type should be mp4");
			return;
		}
		if((event.getFile().getSize()> (1024 * 1024 * Integer.parseInt(MAX_VIDEOSIZE)))) {
			fileName = "";
			Faces.addWarn("Video size must be lesser than  "+MAX_VIDEOSIZE+" MB ; in Bytes :: "+ (1024 * 1024 * Integer.parseInt(MAX_VIDEOSIZE)));
			return;
		}
		fileName = event.getFile().getFileName()
		adVideo = event.getFile();
	}
	public static void copyAdVideoFile(def fileName, InputStream input ) {
		log.info("Advertisement video copy function called :: "+fileName);
		try {
			File file = new File( fileName); 
			 OutputStream out = new FileOutputStream(file);
			 int read = 0;
			 byte[] bytes = new byte[10240];
			 while ((read = input.read(bytes)) != -1) {
				 out.write(bytes, 0, read);
			 }
			 input.close();
			 out.flush();
			 out.close(); 
		} catch (IOException e) {
			 log.error("error on advertisement Video Upload ",e.getMessage());
		}
	}
	public static void copyFile(def fileName, InputStream input ) {
		log.info("Thumb NailCopy File function called :: "+fileName);
		try { 
			File file = new File( fileName);
			 OutputStream out = new FileOutputStream(file);
			 int read = 0;
			 byte[] bytes = new byte[10240];
			 while ((read = input.read(bytes)) != -1) {
				 out.write(bytes, 0, read);
			 }
			 input.close();
			 out.flush();
			 out.close(); 
		} catch (IOException e) {
			 log.error("error on advertisement Image Upload ",e.getMessage());
		}
	}
	 
	public static def  dateToFormattedString(Date date ){
		if(date == null){
			date = new Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
		return sdf.format(date);
	}
	Date stringToDate(String dateString){
		log.info("Date :: "+dateString);
		if(dateString == null){
			return new Date();
		} else {
			Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dateString); 
			log.info("Date :: "+date);
			return date
		}
	}
	
	
	
	
}
