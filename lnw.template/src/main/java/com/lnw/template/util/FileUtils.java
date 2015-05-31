package com.lnw.template.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	private final String _fileTypeSeperator = ",";			//fileType 구분자
	private String rootPath;								//기본 경로
    private String resourcePath;							//리소스보관 디렉토리
    private String tempPath;								//temp디렉토리
    private String[] fileType;								//허용가능한 fileType[]
    
	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
	
	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	
	/**
     * 파일 디렉토리 생성자
     * @param resourcePath
     * @param tempPath
     * by woo
     */
    public FileUtils(String rootPath, String resourcePath, String tempPath, String fileType) {
    	this.setRootPath(rootPath);
    	this.setResourcePath(resourcePath);
    	this.setTempPath(tempPath);
    	
    	if(fileType!=null && !"".equals(fileType)){
    		this.fileType = fileType.split(this._fileTypeSeperator);
    	}
        
    	if(this.rootPath!=null&&!"".equals(this.rootPath)){
			//임시폴더생성
	    	if(this.tempPath!=null&&!"".equals(this.tempPath)){
	    		makeDir(this.rootPath + this.tempPath);   
	    	}
	    	
	        //리소스폴더 생성
	    	if(this.resourcePath!=null&&!"".equals(this.resourcePath)){
	    		makeDir(this.rootPath + this.resourcePath);
	    	}
    	}
    }
    
    //디렉토리 생성 함수
    public void makeDir(String path){
    	File saveFolder = new File(path);
        if(!saveFolder.exists() || saveFolder.isFile()){
            saveFolder.mkdirs();
        }
    }
	
	
	/**
	 * 파일 다운로드 메소드
	 * @param HttpServletResponse response
	 * @param String fileName
	 * by woo
	 * @throws IOException 
	 */
	public boolean fileDown(HttpServletResponse response, String targetFile, String fileType, boolean fileDel, String checkSum) throws IOException{
		boolean userAbort = false;
		boolean result = false;	//파일다운성공여부
		File file = new File(targetFile);	//파일읽어옴
		
		response.setHeader("Content-Type", "application/" + fileType);	//파일 타입 명시
		
		if(file.exists()){
			response.setHeader("Content-Disposition", "attachment; filename="+file.getName());	//파일이름 명시
			if(checkSum!=null&&!"".equals(checkSum)){
				response.setHeader("CK", checkSum);
			}
			response.setContentLength((int)file.length());	//파일 길이 명시
			
			OutputStream out = null;
			InputStream fis = null;
			try{
				out = (response.getOutputStream());
			
				if(file.exists()){
					fis = new FileInputStream(file); 
					byte[] buf = new byte[2048];
					int bytes_read;
					while ((bytes_read = fis.read(buf)) != -1) {
						out.write(buf, 0, bytes_read);
					}
				}
				out.flush();
				result = true;
			}catch(IOException ie){
				if(ie.getClass().getName().equals("org.apache.catalina.connector.ClientAbortException")){
					userAbort = true;
				}else{
					new Exception("파일전송 중 IOException");
					result = false;
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
					if(out!=null){
						out.close();
						out = null;
					}
				} catch (IOException e) {}
				try{
					if(fis!=null){
						fis.close();
						fis = null;
					}
				} catch (IOException e){}
				
				if(fileDel&&file.exists()){
					boolean del = file.delete();
					System.out.println("전송한 파일 삭제 여부 : " + del);
				}
				
				if(userAbort){
					throw new IOException("사용자 취소로 인한 IOException");
				}
			}
		}else{
			result = false;
//				try {
//					response.sendError(404);
//					logger.info("**파일 존재하지 않음(404)**");
//				} catch (IOException e) {
//					logger.info(e.getMessage());
//				}
		}
		return result;
	}
	
	
	/**
	 * 파일저장
	 * @param sourcefile
	 * @param fileName
	 * @return
	 * @throws IllegalStateException 
	 * @throws IOException
	 * by woo
	 */
    public boolean saveFile(MultipartFile sourcefile, String fileName) {
        if ((sourcefile==null)||(sourcefile.isEmpty())) return false;
        boolean result = false;
        
        File file = new File(fileName);
        file.mkdirs();
        
		try {
			sourcefile.transferTo(file);
			result = true;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
        return result;
    }


    /**
     * 파일삭제 (패턴이용)
     * @param path
     * @param namePattern
     */
    public boolean deleteFilePattern(String path, String namePattern){
    	File file = new File(path);
    	String pattern = "(?i)" + namePattern + ".*";	//대소문자구분 X (?!)    패턴 이용
    	if(file.exists()){
    		File[] files = file.listFiles();
    		for(int i=0; i<files.length; i++){
    			if(files[i].getName().matches(pattern)){
    				files[i].delete();
    			}
    		}
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
     * 단일 파일 삭제
     * @param path
     * @param namePattern
     * @return
     */
    public boolean deleteFile(String fileName){
    	File file = new File(fileName);
    	if(file.exists()){
    		return file.delete();
    	}else{
    		return false;
    	}
    }
    
    public boolean deleteDir(String dirName){
    	boolean success = false;
    	File file = new File(dirName);
		if(file.isDirectory()){
			File[] subFile = file.listFiles();
			
			for(int i=0; i<subFile.length; i++){
				deleteDir(subFile[i].getAbsolutePath());
			}
			
			success = file.delete();
		}else{
			success = deleteFile(file.getAbsolutePath());
		}
		return success;
    }
    
    //파일 확장자 체크
	public boolean uploadFileTypeCheck(String fileType) {
		boolean check = false;
		for(int i=0; i<this.fileType.length; i++){
			if(this.fileType[i].equalsIgnoreCase(fileType)){
				check = true;
				break;
			}
		}
		return check;
	}


}
