package com.yc.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UploadUtil {
	// 上传文件
		public static String uploadFile(HttpServletRequest request, 
				MultipartFile fi, UploadConfig config) throws Exception {
			List<String> list = uploadFiles(request, fi, config);
			String fileName=null;
			if(list!=null&&list.size()>0){
				fileName=list.get(0);
			}
			return fileName;
		}
	// 上传文件
	public static List<String> uploadFiles(HttpServletRequest request, 
			MultipartFile fi, UploadConfig config)
			throws Exception {
		List<String> list = new ArrayList<>();
		String path=config.dir==null?config.path:config.path+"/"+config.dir;
		String sourcePath = request.getSession().getServletContext().getRealPath(path);
		File saveSourceFile = new File(sourcePath);
		try {
			if (!saveSourceFile.exists()) {
				saveSourceFile.mkdirs();
			}
			ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
			sfu.setHeaderEncoding("UFT-8");
			String sourceName = "";
			sourceName = fi.getOriginalFilename();
			if (sourceName == null || "".equals(sourceName.trim())) {
				throw new Exception("文件找不到");
			}
			long size=fi.getSize();
			System.out.println("文件大小是："+size/1024+"k");
			//限制上传大小
			if(config.maxSize>0&&size>config.maxSize){
				throw new Exception("文件大小是："+size+"，文件超过"+config.maxSize/1000000+"m");
			}
			if (sourceName.lastIndexOf(".") >= 0) {
				// 扩展名
				sourceName = sourceName.substring(sourceName.lastIndexOf("."));
			}
			// UUID + 扩展名
			sourceName = java.util.UUID.randomUUID().toString() + sourceName;
			saveSourceFile = new File(sourcePath + "/" + sourceName);
			FileCopyUtils.copy(fi.getBytes(), saveSourceFile);
			sourcePath = saveSourceFile.getAbsolutePath();
			System.out.println(sourcePath);
			if (sourceName.indexOf(".zip") > 0) {
				list = UnZip.unZip(sourcePath);
				//System.out.println("&&"+list.size());
			} else {
				list.add(sourceName);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (config.delSource && saveSourceFile != null && saveSourceFile.exists()) {
				System.out.println("删除文件：" + saveSourceFile.delete());
			}
		}
		return list;
	}
	/**chencong 上传excel**/
	public static String uploadFile(MultipartHttpServletRequest request,
			String sourcePath, long size) throws Exception {
		ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
		sfu.setHeaderEncoding("UFT-8");
		String sourceName = "";
		String extName = "";
		String sfileName = "";
		if(size==0l)
			size = 500000*1000;
		for (String s:request.getFileMap().keySet()) {
			MultipartFile  fi =request.getFileMap().get(s);
			sourceName = fi.getOriginalFilename();
			if (sourceName == null || "".equals(sourceName.trim())) {
				continue;
			}
			long fileSize=fi.getSize();
			if(fileSize>size){
				throw new Exception("文件大小超过"+size);
			}
			if (sourceName.lastIndexOf(".") >= 0) {
				// 扩展名
				extName = sourceName.substring(sourceName
						.lastIndexOf("."));
			}
			// UUID + 扩展名
			sfileName = java.util.UUID.randomUUID().toString() + extName;
			File saveSourceFile = new File(sourcePath + sfileName);
			FileCopyUtils.copy(fi.getBytes(),saveSourceFile);
			sourcePath=saveSourceFile.getAbsolutePath();
		}
		return sourcePath;
	}	
	public static List<Map<Integer, String>> uploadExcel(String path) throws Exception {
		POIFSFileSystem fs = null;
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(new FileInputStream(path));
		} catch (Exception ex) {
			fs = new POIFSFileSystem(new FileInputStream(path));
			wb = new HSSFWorkbook(fs);
		}
		List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rowit = sheet.rowIterator();
		Row row;
		Iterator<Cell> cellit;
		Cell cell;
		String rowValue = "", cellValue;
		Map<Integer, String> map = null;
		while (rowit.hasNext()) {
			row = rowit.next();
			if (row.getRowNum() == 0)
				continue;
			cellit = row.cellIterator();
			map = new HashMap();
			rowValue = "";
			while (cellit.hasNext()) {
				cell = cellit.next();
				if (cell == null)
					continue;
				if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
					if (cell.getColumnIndex() == 0) {
						DecimalFormat df = new DecimalFormat("###");// 保留两位小数且不用科学计数法
						cellValue = df.format(cell.getNumericCellValue());
					} else {
						cellValue = String.valueOf(cell.getNumericCellValue());
					}
				} else if (cell.getCellType() == cell.CELL_TYPE_FORMULA) {
					cellValue = cell.getCellFormula();
				} else {
					cellValue = cell.getStringCellValue();
				}
				if (row.getRowNum() == 1 && (cellValue == null || cellValue.equals("")))
					continue;
				rowValue += cellValue;
				map.put(cell.getColumnIndex(), cellValue.trim());
			}
			if (rowValue.equals(""))
				break;
			list.add(map);
		}
		return list;
	}
}

