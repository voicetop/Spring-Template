package com.lnw.template.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lnw.template.database.ICommonDAO;

@Service
public class HomeService {
	
	@Qualifier("commonDAO")
	@Autowired
	private ICommonDAO commonDAO;
	
	/**
	 * 기본 기능
	 */
	public List getBaseList(Map<String, Object> params) throws Exception {
		return commonDAO.selectList("base.getBaseList", params);
	}
	
}
