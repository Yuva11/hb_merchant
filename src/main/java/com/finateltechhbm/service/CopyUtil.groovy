package com.finateltechhbm.service

import groovy.util.logging.Slf4j;

import org.springframework.stereotype.Service;

import com.finateltechhbm.dto.CusineCompactDTO;
import com.finateltechhbm.dto.CusineDto;
import com.finateltechhbm.dto.FoodCategoryCompactDTO;
import com.finateltechhbm.dto.FoodCategoryDto;
import com.finateltechhbm.dto.GenericDTO;
import com.finateltechhbm.dto.MerchantBranchDto;
import com.finateltechhbm.dto.MerchantBranchSelectDTO;

@Service
@Slf4j
class CopyUtil {
	def merchantBranchesToCompact(List<MerchantBranchDto> merchantBranchDtoList){
		List<MerchantBranchSelectDTO> selectDTOList = new ArrayList<MerchantBranchSelectDTO>(); 
		 MerchantBranchSelectDTO selectDTO =null;
		 log.info("Branch List Size :: "+merchantBranchDtoList.size());
		 for(MerchantBranchDto merchantBranchDto : merchantBranchDtoList) {
			 selectDTO = new MerchantBranchSelectDTO();
			 selectDTO.id = merchantBranchDto.id;
			 selectDTO.branchName = merchantBranchDto.branchName; 
			 selectDTOList.add(selectDTO);
		 }
		 return selectDTOList;
	}
	
	def cusinesToCompact(List<CusineDto> cusineDtoList){
		List<CusineCompactDTO> selectDTOList = new ArrayList<CusineCompactDTO>();
		 CusineCompactDTO selectDTO =null;
		 log.info("Branch List Size :: "+cusineDtoList.size());
		 for(CusineDto cusineDto : cusineDtoList) {
			 selectDTO = new CusineCompactDTO();
			 selectDTO.id = cusineDto.id;
			 selectDTO.name = cusineDto.name;
			 selectDTOList.add(selectDTO);
		 }
		 return selectDTOList;
	}
	def foodCategoryToCompact(List<FoodCategoryDto> foodCategoryDtoList){
		List<FoodCategoryCompactDTO> selectDTOList = new ArrayList<FoodCategoryCompactDTO>();
		 FoodCategoryCompactDTO selectDTO =null;
		 log.info("Branch List Size :: "+foodCategoryDtoList.size());
		 for(FoodCategoryDto foodCategoryDto : foodCategoryDtoList) {
			 selectDTO = new FoodCategoryCompactDTO();
			 selectDTO.id = foodCategoryDto.id;
			 selectDTO.name = foodCategoryDto.name;
			 selectDTOList.add(selectDTO);
		 }
		 return selectDTOList;
	}
	List<GenericDTO> foodCategoryLIST_TO_GENERICS(List<FoodCategoryDto> foodCategoryDtoList){
		List<GenericDTO> dataLIST = new ArrayList<GenericDTO>();
		 GenericDTO data =null;
		 for(FoodCategoryDto foodCategoryDto : foodCategoryDtoList) {
			 data = new GenericDTO();
			 data.id = foodCategoryDto.id;
			 data.name = foodCategoryDto.name;
			 log.info("Food Category name in List  :: :: >> "+data.name)
			 dataLIST.add(data);
		 }
		 return dataLIST;
	}
	GenericDTO foodCategory_TO_GENERIC(FoodCategoryDto entity){
		if(entity == null){
			return new GenericDTO(); 	
		}
		GenericDTO data = new GenericDTO();
		data.id = entity.id;
		data.name = entity.name;
		log.info("Food Category name in data :: :: >> "+data.name)
		return data;
	}
	List<GenericDTO> cuisneLIST_TO_GENERICS(List<CusineDto> entityList){
		List<GenericDTO> dataLIST = new ArrayList<GenericDTO>();
		 GenericDTO data =null;
		 for(FoodCategoryDto foodCategoryDto : entityList) {
			 data = new GenericDTO();
			 data.id = foodCategoryDto.id;
			 data.name = foodCategoryDto.name;
			 log.info("Cuisine name in List  :: :: >> "+data.name)
			 dataLIST.add(data);
		 }
		 return dataLIST;
	}
	GenericDTO cusine_TO_GENERIC(CusineDto entity){
		if(entity == null){
			return new GenericDTO();
		}
		GenericDTO data = new GenericDTO();
		data.id = entity.id;
		data.name = entity.name;
		log.info("Cuisine name in data :: :: >> "+data.name)
		return data;
	}
	GenericDTO cusineSET_TO_GENERIC(Set<CusineDto> entities){
		if(entities == null){
			return new GenericDTO();
		}
		GenericDTO data = new GenericDTO();
		for(CusineDto entity : entities) {
			 data = new GenericDTO();
			 data.id = entity.id;
			 data.name = entity.name; 
		 }
		log.info("Cuisine name in data :: :: >> "+data.name)
		return data;
	}
}
