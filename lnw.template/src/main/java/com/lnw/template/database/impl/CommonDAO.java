package com.lnw.template.database.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;

import com.lnw.template.database.ICommonDAO;


/**
 * DAO 기본 인터페이스
 * 
 *  수정일                          수정자                       수정내용
 *  -----------    ----------    ----------------------------------
 *  2013. 07. 13.   wooti		 ICommonDAO추가
 *  2013. 11. 09.   wooti		 쿼리결과 key 소문자 치환 및 null 빈문자열로 치환
 */
public class CommonDAO extends DaoSupport implements ICommonDAO{
	
	private SqlSession sqlSession;

    private boolean externalSqlSession;

    public final void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        if (!this.externalSqlSession) {
            this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
        }
    }

    public final void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSession = sqlSessionTemplate;
        this.externalSqlSession = true;
    }

    public final SqlSession getSqlSession() {
        return this.sqlSession;
    }

    protected void checkDaoConfig() {
        Assert.notNull(this.sqlSession, "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
    }
    
    
    public Object selectObject(String mapperId) throws Exception {
		return this.sqlSession.selectOne(mapperId);
	}
	
	public Object selectObject(String mapperId, Object parameter) throws Exception {
		return this.sqlSession.selectOne(mapperId, parameter);
	}

	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectMap(String mapperId) throws Exception {
		Map<String, Object> resultMap = null;
		
		Map<String, Object> map = (Map<String, Object>)this.sqlSession.selectOne(mapperId);
		if(map!=null){
			resultMap = new HashMap<String, Object>(); 
		
			Set<String> keys = map.keySet();
			Iterator<String> it = keys.iterator();
			while(it.hasNext()){
				String key = it.next();
				Object val = map.get(key);
				//null일경우 빈문자열로 치환
				if(val==null){
					val = "";
				}
				key = key.toLowerCase();
				resultMap.put(key, val);
			}
		}
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectMap(String mapperId, Object parameter) throws Exception {
		Map<String, Object> resultMap = null;
		
		Map<String, Object> map = (Map<String, Object>)this.sqlSession.selectOne(mapperId, parameter);
		if(map!=null){
			resultMap = new HashMap<String, Object>(); 
		
			Set<String> keys = map.keySet();
			Iterator<String> it = keys.iterator();
			while(it.hasNext()){
				String key = it.next();
				Object val = map.get(key);
				//null일경우 빈문자열로 치환
				if(val==null){
					val = "";
				}
				key = key.toLowerCase();
				resultMap.put(key, val);
			}
		}
		return resultMap;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List selectList(String mapperId) throws Exception {
		List resultList = null;
		
		List data = this.sqlSession.selectList(mapperId);
		if(data!=null && data.size() > 0){
			resultList = new ArrayList();
			for(int i=0; i<data.size(); i++){
				Map resultMap = null;
				
				Map map = (Map) data.get(i);
				if(map!=null){
					resultMap = new HashMap(); 
				
					Set keys = map.keySet();
					Iterator it = keys.iterator();
					while(it.hasNext()){
						String key = (String) it.next();
						Object val = map.get(key);
						//null일경우 빈문자열로 치환
						if(val==null){
							val = "";
						}
						key = key.toLowerCase();
						resultMap.put(key, val);
					}
				}
				resultList.add(resultMap);
			}
		}
		
		return resultList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List selectList(String mapperId, Object parameter) throws Exception {
		List resultList = null;
		
		List data = this.sqlSession.selectList(mapperId, parameter);
		if(data!=null && data.size() > 0){
			resultList = new ArrayList();
			for(int i=0; i<data.size(); i++){
				Map resultMap = null;
				
				Map map = (Map) data.get(i);
				if(map!=null){
					resultMap = new HashMap(); 
				
					Set keys = map.keySet();
					Iterator it = keys.iterator();
					while(it.hasNext()){
						String key = (String) it.next();
						Object val = map.get(key);
						//null일경우 빈문자열로 치환
						if(val==null){
							val = "";
						}
						key = key.toLowerCase();
						resultMap.put(key, val);
					}
				}
				resultList.add(resultMap);
			}
		}
		
		return resultList;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public List selectListDto(String mapperId, Object parameter) throws Exception {
		List data = this.sqlSession.selectList(mapperId, parameter);
		return data;
	}
	
	public int insert(String mapperId) throws Exception {
		return this.sqlSession.insert(mapperId);
	}
	
	public int insert(String mapperId, Object parameter) throws Exception {
		return this.sqlSession.insert(mapperId, parameter);
	}
	
	
	
	public int update(String mapperId) throws Exception {
		return this.sqlSession.update(mapperId);
	}
	
	public int update(String mapperId, Object parameter) throws Exception {
		return this.sqlSession.update(mapperId, parameter);
	}
	
	
	
	public int delete(String mapperId) throws Exception {
		return this.sqlSession.delete(mapperId);
	}
	
	public int delete(String mapperId, Object parameter) throws Exception {
		return this.sqlSession.delete(mapperId, parameter);
	}

	@Override
	public List<Map<String, String>> selectListMap(String mapperId, Object parameter) throws Exception {
		List<Map<String, String>> resultList = null;
		
		List<Map<String, String>> data = this.sqlSession.selectList(mapperId, parameter);
		if(data!=null && data.size() > 0){
			resultList = new ArrayList<Map<String, String>>();
			for(int i=0; i<data.size(); i++){
				Map<String, String> resultMap = null;
				
				Map<String, String> map = data.get(i);
				if(map!=null){
					resultMap = new HashMap<String, String>(); 
				
					Set<String> keys = map.keySet();
					Iterator<String> it = keys.iterator();
					while(it.hasNext()){
						String key = it.next();
						String val = map.get(key);
						//null일경우 빈문자열로 치환
						if(val==null){
							val = "";
						}
						key = key.toLowerCase();
						resultMap.put(key, val);
					}
				}
				resultList.add(resultMap);
			}
		}
		
		return resultList;
	}
}
