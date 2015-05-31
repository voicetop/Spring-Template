package com.lnw.template.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {

	public static void unzipFile(String zipFile, String path) {
		try {
			// 압축파일 열기
			ZipInputStream in = new ZipInputStream(new FileInputStream(zipFile));
			// 엔트리 취득
			ZipEntry entry = null;
			while ((entry = in.getNextEntry()) != null) {
				if(!entry.getName().startsWith("__MACOSX")){
					//System.out.println(entry.getName() + "에 압축을 풉니다.");
					if(entry.isDirectory()){
						new File(path + entry.getName()).mkdirs();
					}else{
						OutputStream out = new FileOutputStream(path + entry.getName());
						// 버퍼
						byte[] buf = new byte[1024];
						int len;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
		
						in.closeEntry();
						// out닫기
						out.close();
					}
				}else{
					in.closeEntry();
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
