package com.lnw.template.database;

import java.util.List;
import java.util.Map;

/**
 * Query Method
 * @author 우띠
 *
 */
public interface ICommonDAO {
	
    public Object selectObject(String mapperId) throws Exception;
	
	public Object selectObject(String mapperId, Object parameter) throws Exception;
	
	public Map<String, Object> selectMap(String mapperId) throws Exception;
	
	public Map<String, Object> selectMap(String mapperId, Object parameter) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List selectList(String mapperId) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public List selectList(String mapperId, Object parameter) throws Exception;
	
	public List<Map<String, String>> selectListMap(String mapperId, Object parameter) throws Exception;
	
	public int insert(String mapperId) throws Exception;
	
	public int insert(String mapperId, Object parameter) throws Exception;
	
	public int update(String mapperId) throws Exception;
	
	public int update(String mapperId, Object parameter) throws Exception;
	
	public int delete(String mapperId) throws Exception;
	
	public int delete(String mapperId, Object parameter) throws Exception;
}
