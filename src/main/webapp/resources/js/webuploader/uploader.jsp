<%@page import="org.springframework.util.FileCopyUtils"%>
<%@page import="java.io.File"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%
response.setContentType("text/html");
response.setCharacterEncoding("UTF-8");

//文件保存目录路径
String savePath = null;
String dirName = null;
//定义允许上传的文件扩展名
HashMap<String, String> extMap = new HashMap<String, String>();
extMap.put("image", "gif,jpg,jpeg,png,bmp");
extMap.put("flash", "swf,flv");
extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,pdf");

//最大文件大小
long maxSize = 10 * 1000 * 1000;

FileItemFactory factory = new DiskFileItemFactory();
ServletFileUpload upload = new ServletFileUpload(factory);
upload.setHeaderEncoding("UTF-8");
List<FileItem> items = upload.parseRequest(request);
//处理参数
for(FileItem item:items){
	if(!item.isFormField()){
		continue;
	}
	if(item.getFieldName().equals("dir")){
		dirName=item.getString();
	}
	if(item.getFieldName().equals("savePath")){
		savePath=item.getString();
		savePath = savePath.startsWith("/")?savePath.substring(1):savePath;
		savePath = pageContext.getServletContext().getRealPath("/") + savePath;
	}
}
if (dirName == null) {
	dirName = "image";
}

for(FileItem item:items){
	if(item.isFormField()){
		continue;
	}
	String sourceName = item.getName();
	long fileSize = item.getSize();
	String fileExt = sourceName.substring(sourceName.lastIndexOf(".") + 1).toLowerCase();
	if(!contains(extMap.get(dirName).split(","), fileExt)){
		out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
		return;
	}
	//检查文件大小
	if(item.getSize() > maxSize){
		out.println(getError("上传文件大小超过限制。"));
		return;
	}
	String extName=null;
	// UUID + 扩展名
	String sfileName = java.util.UUID.randomUUID().toString() +'.'+ fileExt;
	try{
		File uploadedFile = new File(savePath + sfileName);
		item.write(uploadedFile);
		out.println(success(sfileName,sourceName));
	}catch(Exception e){
		out.println(getError("上传文件失败。"));
		return;
	}
}

%>


<%!
private String success(String fileName,String sourceName) throws Exception{
	Map<String, Object> result = new HashMap<>();//定义map  
	result.put("s", true);
	result.put("fileName",fileName);
	result.put("sourceName",sourceName);
	result.put("i","上传成功");
 	String jsonStr=new ObjectMapper().writeValueAsString(result);
	return jsonStr;
}
private String getError(String message) throws Exception{
	Map<String, Object> result = new HashMap<>();//定义map  
	result.put("s", false);
	result.put("i",message);
 	String jsonStr=new ObjectMapper().writeValueAsString(result);
	return jsonStr;
}
private static <T> boolean contains( final T[] array, final T v ) {
    for ( final T e : array )
        if ( e == v || v != null && v.equals( e ) )
            return true;

    return false;
}
%>