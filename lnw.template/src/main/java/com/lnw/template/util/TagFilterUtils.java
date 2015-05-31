package com.lnw.template.util;

public class TagFilterUtils {
	
	public static String convertTag(String value){
		if(value==null)
		{
			return null;
		}
		
		StringBuffer strBuff = new StringBuffer();

		for (int i = 0; i < value.length(); i++) 
		{
			char c = value.charAt(i);
			switch (c) {
			
			case '&':
				strBuff.append("&amp;");
				break;
				
			case '<':
				strBuff.append("&lt;");
				break;
				
			case '>':
				strBuff.append("&gt;");
				break;
			
			case '"':
				strBuff.append("&quot;");
				break;
			
			case '\'':
				strBuff.append("&#039;");
				break;	
				
			default:
				strBuff.append(c);
				break;
			}
		}
		
		return strBuff.toString();
	}
}
