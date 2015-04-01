package com.finateltechhbm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.finateltechhbm.dto.DealOrdersDto;

public class Util {
	private static final Logger log = LoggerFactory.getLogger(Util.class); 
	public byte[] generateSalesExcelReport(List<DealOrdersDto> dealOrdersList, Date startDate, Date endDate){
		 HSSFWorkbook workbook = null;
			HSSFSheet dataSheet;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
			try {
				InputStream fis = this.getClass().getResourceAsStream("/SalesReport.xls");
				workbook = new HSSFWorkbook(fis);
			} catch (IOException e) {
				log.info("Exception in Opening File :" + e);
			}
			
				log.info("Excel batch Data size not 0");
				dataSheet = workbook.getSheetAt(0);	
				HSSFCellStyle mainHeadercellStyle = setHeaderStyle(workbook);
				HSSFCellStyle cellStyle = setHeaderStyle(workbook);
				HSSFCellStyle cellStyle1 = setHeaderStyle(workbook);
				cellStyle1.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
				
				
				HSSFFont font = workbook.createFont();
				font.setFontName("Calibri");
				font.setFontHeightInPoints((short) 14);
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				mainHeadercellStyle.setFont(font);
				Integer rowIndex = 0;
				if(dealOrdersList != null && dealOrdersList.size() > 0){
					//Brand name
					HSSFRow row1 = dataSheet.getRow(rowIndex) == null ? dataSheet.createRow(rowIndex) : dataSheet.getRow(rowIndex);
					HSSFCell headerCell = row1.createCell(2);
					if(dealOrdersList.get(0)!= null && dealOrdersList.get(0).getMerchant() != null ) {
						headerCell.setCellValue("Brand : "+ dealOrdersList.get(0).getMerchant().getName());
						headerCell.setCellStyle(cellStyle);
					} else {
						headerCell.setCellValue("Brand : ");
						headerCell.setCellStyle(cellStyle);
					}
					// OutLet Name
					
					row1 = dataSheet.getRow(rowIndex) == null ? dataSheet.createRow(rowIndex) : dataSheet.getRow(rowIndex);
					headerCell = row1.createCell(3);

					if(dealOrdersList.get(0)!= null && dealOrdersList.get(0).getOrderDetails() != null && dealOrdersList.get(0).getOrderDetails().size() >0 && dealOrdersList.get(0).getOrderDetails().get(0) != null ) {
						headerCell.setCellValue("Outlet : "+ dealOrdersList.get(0).getOrderDetails().get(0).getDeal().getMerchantbranch().getBranchName());
						headerCell.setCellStyle(cellStyle);
					} else {
						headerCell.setCellValue("Outlet : ");
						headerCell.setCellStyle(cellStyle);
					}
					//From Date
					row1 = dataSheet.getRow(rowIndex) == null ? dataSheet.createRow(rowIndex) : dataSheet.getRow(rowIndex);
					headerCell = row1.createCell(4);
					headerCell.setCellValue("From : "+dateFormat.format(startDate));
					headerCell.setCellStyle(cellStyle);
					//To Date
					row1 = dataSheet.getRow(rowIndex) == null ? dataSheet.createRow(rowIndex) : dataSheet.getRow(rowIndex);
					headerCell = row1.createCell(5);
					headerCell.setCellValue("To : "+dateFormat.format(endDate));
					headerCell.setCellStyle(cellStyle);
					//Set Row Index After Header Row
					int cell =0;
					int tempCell = cell;
					HSSFCell firstHeaderCell1 = null; 
					int cellIndex =0;
					 
					rowIndex=2;
					int index = 1;
					int i=0;

					try {
					for(DealOrdersDto data :dealOrdersList){
						HSSFRow dataRow1 = getRow(dataSheet, i+2);
						getCell(dataRow1, cell).setCellValue(index);
						getCell(dataRow1, ++cell).setCellValue( data.getDealordersdate()==null?"":dateFormat.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(data.getDealordersdate())));
						getCell(dataRow1, ++cell).setCellValue(data.getOrderId());
						getCell(dataRow1, ++cell).setCellValue(data.getStatus().toString());
						getCell(dataRow1, ++cell).setCellValue(data.getCustomer().getFirstName());
						getCell(dataRow1, ++cell).setCellValue(data.getCustomer().getEmail());
						getCell(dataRow1, ++cell).setCellValue(data.getCustomer().getMobileNumber());
						getCell(dataRow1, ++cell).setCellValue(data.getOrderDetails().get(0).getDeal().getName());
						getCell(dataRow1, ++cell).setCellValue(data.getOrderDetails().get(0).getDeal().getDealPrice());
						getCell(dataRow1, ++cell).setCellValue(data.getOrderDetails().get(0).getQuantity());
						getCell(dataRow1, ++cell).setCellValue( Double.parseDouble(new DecimalFormat("#.00").format(data.getAmount())));
						cell = 0;
						i++;
						index++;
					}
					} catch (ParseException e) {
						log.info("Generate Excel Date Parse Exception : ",e);
					} catch (Exception ex) {
						log.info("Generate Excel Exception : ",ex);
					}
				} 
			File temp = null;
			try {
			    temp = File.createTempFile("Reports", ".xls");
			} catch (Exception e) {
				e.printStackTrace();
			}
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(temp);
				workbook.write(fos);
				fos.flush();
				fos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
			byte[] bytes =null;
		       try {
		    	   File file = new File(temp.getAbsolutePath());
		    	   InputStream inputStream = new FileInputStream(file); 
		    	   bytes = new byte[(int) file.length()];
		    	   inputStream.read(bytes);
		       } catch (Exception e) {
		    	   log.error("Generate Excel report ",e);
		       } 
		       temp.deleteOnExit();
		 return bytes;  	
	
	}
	 private HSSFCell getCell(HSSFRow row,int idx) {
			HSSFCell cell=row.getCell(idx);
			if(cell==null)
				cell=row.createCell(idx);
			return cell;
		}
	 private  HSSFRow getRow(HSSFSheet dataSheet,int idx) {
			HSSFRow row=dataSheet.getRow(idx);
			if(row==null)
				row=dataSheet.createRow(idx);
			return row;
		}
	 private static HSSFCellStyle setHeaderStyle(HSSFWorkbook sampleWorkBook)
		{
			HSSFFont font = sampleWorkBook.createFont();
			font.setFontName("Calibri");
			font.setFontHeightInPoints((short) 11);
			//font.setColor(IndexedColors.PLUM.getIndex());
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			
			HSSFCellStyle cellStyle = sampleWorkBook.createCellStyle();
		
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND );
			cellStyle.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cellStyle.setBorderBottom(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderTop(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderLeft(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderRight(HSSFCellStyle.SOLID_FOREGROUND);
			
			return cellStyle;
		}
}
