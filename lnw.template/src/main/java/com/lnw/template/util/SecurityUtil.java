package com.cilab.platform.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cilab.platform.security.CustomUserDetail;
import com.cilab.platform.security.CustomUsernamePasswordAuthenticationToken;

public class SecurityUtil {
	
	//로그인 정보
	public static CustomUserDetail getUserDetail(){
		CustomUserDetail userDetail = null;
		if(SecurityContextHolder.getContext()!=null){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth != null) {
				Object obj = auth.getDetails();
				if (obj instanceof CustomUserDetail) {
					userDetail = (CustomUserDetail) obj;
				}
			}
		}
		return userDetail;
	}

	//로그인 계정
	public static String getWorkId() {
		String workId = "";
		if(SecurityContextHolder.getContext()!=null){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth != null) {
				Object obj = auth.getDetails();
				if (obj instanceof CustomUserDetail) {
					CustomUserDetail userDetail = (CustomUserDetail) obj;
					workId = userDetail.getUserId();
				}
			}
		}
		
		return workId;
	}
	
	//유저가 해당권한을 모두 가지고있는지 판별
	public static boolean allGranted(String[] checkForAuths) {
		Set<String> userAuths = getUserAuthorities();
		for (String auth : checkForAuths) {
			if (userAuths.contains(auth))
				continue;
			return false;
		}
		return true;
	}

	//유저가 특정 권한을 갖고있는지 판별
	public static boolean anyGranted(String[] checkForAuths) {
		Set<String> userAuths = getUserAuthorities();
		for (String auth : checkForAuths) {
			if (userAuths.contains(auth))
				return true;
		}
		return false;
	}


	//유저의 권한 모두 가져오기
	public static Set<String> getUserAuthorities() {
		Set<String> roles = new HashSet<String>();
		
		if(SecurityContextHolder.getContext().getAuthentication()!=null){
			Object obj = SecurityContextHolder.getContext().getAuthentication();
			
			if (obj instanceof CustomUsernamePasswordAuthenticationToken) {
				for (GrantedAuthority ga : ((CustomUsernamePasswordAuthenticationToken) obj).getAuthorities()) {
					roles.add(ga.getAuthority());
				}
			}
		}
		return roles;
	}
		
}
