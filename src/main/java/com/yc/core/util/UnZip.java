package com.yc.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;





/**
 * 可以处理中文文件名
 */
public class UnZip 
{
	private static final int buffer = 2048;
	
//	public static void main(String[] args){
//		unZip("D:\\2013.zip");
//	}
	
	public static List<String> unZip(String path) throws Exception{
    	List<String> list = new ArrayList<>();
    	ZipFile zf =null;
        try {
            zf = new ZipFile(path);//要解压的文件
            String savepath = path.substring(0, path.lastIndexOf("\\")) + "\\";//解压后存放的路径（当前路径）
            ////改成两次循环，是个笨方法，防止先读子目录找不到文件的问题，2次循环，第一次先建子文件夹，第二次解压文件
            for (Enumeration entries =  zf.entries(); entries
                    .hasMoreElements();) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String filename = entry.getName();
                //System.out.println("savepath:"+savepath);
                //System.out.println("filename:"+filename);
                if(!entry.isDirectory()){
/*                     list.add(filename);
                     InputStream in = zf.getInputStream(entry);
                     OutputStream out = new FileOutputStream(savepath + filename);
                     byte[] buf1 = new byte[1024];
                     int len;
                     while ((len = in.read(buf1)) > 0) {
                         out.write(buf1, 0, len);
                     }
                     in.close();
                     out.close();*/
                }else{
                	File file=new File(savepath+"/"+filename);
                	file.mkdirs();
                }
            }
            for (Enumeration entries = zf.entries(); entries
                    .hasMoreElements();) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String filename = entry.getName();
                //System.out.println("savepath:"+savepath);
                //System.out.println("filename:"+filename);
                if(!entry.isDirectory()){
                     list.add(filename);
                     InputStream in = zf.getInputStream(entry);
                     OutputStream out = new FileOutputStream(savepath + filename);
                     byte[] buf1 = new byte[1024];
                     int len;
                     while ((len = in.read(buf1)) > 0) {
                         out.write(buf1, 0, len);
                     }
                     in.close();
                     out.close();
                }else{
/*                	File file=new File(savepath+"/"+filename);
                	file.mkdirs();*/
                }
            }         
          //System.out.println("解压缩完成.");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }finally {
			if(zf!=null){
				zf.close();
			}
		}
        return list; 
    } 
	
	//不改变文件名称
	public static List<String> unZipWithName(String path){
    	List<String> list = new ArrayList<>();
        try {
            ZipFile zf = new ZipFile(path);//要解压的文件
            for (Enumeration entries =zf.entries(); entries
                    .hasMoreElements();) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String zipEntryName = entry.getName();  
                System.out.println(path.substring(0, path.lastIndexOf('\\'))+"------------>>>>>"+zipEntryName);
                //String filename = entry.getName();
                String savaPath = path.substring(0, path.lastIndexOf('\\'))+"/";
                String outPath = (savaPath+zipEntryName).replaceAll("\\*", "/");;  
                File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
                if(!file.exists()){  
                    file.mkdirs();  
                }  
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
                if(new File(outPath).isDirectory()){  
                    continue;  
                }  
                String filename = entry.getName();//解压后文件名随机，后缀不变
                list.add(filename);
                InputStream in = zf.getInputStream(entry);
                OutputStream out = new FileOutputStream(savaPath + filename);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
            }
          //System.out.println("解压缩完成.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list; 
    } 

}
