<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	<meta name="renderer" content="webkit">
	<link href="<%=request.getContextPath() %>/resources/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath() %>/resources/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath() %>/resources/js/datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath() %>/resources/hplus/css/animate.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath() %>/resources/hplus/css/style.min.css?v=4.1.0" rel="stylesheet">
<%--     <link href="<%=request.getContextPath() %>/resources/hplus/css/style.min862f.css?v=4.1.0" rel="stylesheet"> --%>
    <link href="<%=request.getContextPath() %>/resources/hplus/css/plugins/iCheck/custom.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/js/jquery.toast/jquery.toast.css"/> 
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/hplus/css/plugins/sweetalert/sweetalert.css">
	
	
	<!--[if lt IE 9]>
       <script src="<%=request.getContextPath() %>/resources/js/html5shiv.min.js"></script>
       <script src="<%=request.getContextPath() %>/resources/js/respond.min.js"></script>
	<!--<![endif]--> 
     <!-- jQuery 2.0.2  -->
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.js"></script>
     <!-- Bootstrap -->
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.form.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.validate-method.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/datatables/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/datatables/dataTables.bootstrap.js"></script>
<%-- 	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.cxselect.js"></script> --%>
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.toast/jquery.toast.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/hplus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/hplus/js/plugins/layer/layer.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/hplus/js/hplus.min.js?v=4.1.0"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/hplus/js/contabs.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/hplus/js/plugins/pace/pace.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/hplus/js/plugins/iCheck/icheck.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/hplus/js/plugins/sweetalert/sweetalert.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.util.js"></script>
    
    
     <!-- 表单提交动画引用 -->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/submit-loading/dist/ladda-themeless.min.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/submit-loading/css/prism.css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/submit-loading/dist/spin.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/submit-loading/dist/ladda.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/submit-loading/js/prism.js"></script>
    <style>
	<!--
	.page-header{
		padding:10px 0 15px 0;
	}
	.page-header h3 {
	    display: inline-block;
	    border-left: 4px solid #006ec5;
	    margin:0 0 0 10px;
	    padding-left: 10px;
	    color: #333;
	    font-size: 18px;
	}
	.page-header .pull-right{
		padding-right: 20px;
	}
	.input-sm{padding: 0 12px;}
	.thleft th{text-align: right;   padding-right: 10px;background: #f7f7f7;}
	.upimg{text-align: center;    margin-top: 15px;}
	.upimg img{ width:50%;height:50%;}
	.labelbtn span{padding: 10px 15px;border-radius: 5px; border: 1px solid #44a9f;margin-bottom:5px;    background: #44a9ff;color: #fff;}
	#myloader{position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;text-align:center;padding-top: 10%;background: #333;opacity: 0.5;color: #FFF;display:none}
		
	-->
	</style>
	
	
	
	
	
	<script type="text/javascript">
// 	<!--
	$.ajaxSetup({
		  dataType:"json"
		});
	$.extend( $.fn.dataTable.defaults, {
		 dom:'<"toolbar">rt<"row"<"col-xs-6"i><"col-xs-6"p>>',
		    "pageLength": 20,
			stateSave:  true,
			ordering:  true,
			"processing": true,
		    serverSide: true,
		    bLengthChange :true,
// 		    "initComplete":initComplete||(function(){}),
		    language: {
            	url: '<%=request.getContextPath()%>/resources/js/datatables/zh_cn.json'
            }
	} );
	
	
	
	// 表单提交动画开始
	function submitLoadingStart(id){
		 var l = Ladda.create(document.querySelector('#'+id));
		 l.start();
	}
	//表单提交动画结束
	function submitLoadingStop(id){
		 var l = Ladda.create(document.querySelector('#'+id));
		 l.stop();
	}
	//-->
	</script>
     <!-- iCheck -->
