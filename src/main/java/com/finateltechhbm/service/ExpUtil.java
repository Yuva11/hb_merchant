package com.finateltechhbm.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpUtil {
	public static String CheckAndReplace(String input) {
		if(input == null)
			return "";
		
		String stri[] =  input.split(" "); 
		String result="";
		int i=0; 
		for(String s : stri){ 
			if(i != 0) 
				result = result+" "+ ExpUtil.Replace( s); 
			else 
				result = ExpUtil.Replace( s);
			i++;
		}
		System.out.println("result : "+result);
		return result;
	}
	
	public static String Replace(String s){ 
		String regex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";// "((https?|ftp|file)://)?[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		String res ="";
		Pattern p = Pattern.compile(regex);
	    // get a matcher object
		if(!s.contains("href=") && !s.equals("<a")){
		    Matcher m = p.matcher(s); 
		    s = m.replaceAll("<a href=\'"+s+"\'>Click Here</a>");
		    System.out.println("----------------------"+s);
		}
		return s;
	}
}
