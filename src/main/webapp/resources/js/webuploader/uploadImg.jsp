<%@page import="com.yc.core.util.GlobalDefine"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/js/webuploader/webuploader.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/js/webuploader/style.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/webuploader/webuploader.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/webuploader/webuploader.init.js"></script>
<script type="text/javascript">

</script>
<div id="uploader">
    <div class="queueList">
        <div id="dndArea" class="placeholder">
            <div id="filePicker"></div>
            <p>或将照片拖到这里，单次最多可选30张</p>
        </div>
    </div>
    <div class="statusBar" style="display:none;">
        <div class="progress">
            <span class="text">0%</span>
            <span class="percentage"></span>
        </div><div class="info"></div>
        <div class="btns">
            <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
        </div>
    </div>
</div>