package com.finateltechhbm.dto
 
class ContentDealDTO implements Serializable{
	Long id;
	
	MerchantDto merchant;
	
	String name;
	
	String contentType;//Promotional OR Non-promotional
	
	ContentTemplate contentTemplate;
/*  1) Text only ( Text + Text),
 *  2) Image Only ( Image+image),
 *  3) Video only ( IMAGE OF VIDEO + video),
 *  4) Video + embedded text,
 *  5) Text + Image&Text*/
	
	String thumbnailText; // contentTemplate -1
	
	String detailText;   
	
	String thumbNailURL;  
	
	String displayURL;   
	
	String videoURL;   
	
	String startDate;  
	
	String endDate; 
		
	String createdDate; //Content Created Date
	
	String modifiedDate; //Last Modified Date 
	
	List<CompactLocation> location;
	
	Integer tileSize;
	
	DealStatus status;
	
	List<CustomerDto> customer;
	
	Boolean isliked;
	
	List<AdFeedbackDTO> feedbacks;
	
	List<AdViewsDTO> views;
	
	Integer likeCount;
	
	Integer feedBackCount;
	
	
	Integer viewCount;
	
	
	
}
