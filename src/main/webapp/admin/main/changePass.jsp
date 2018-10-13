<%@page import="com.yc.core.util.GlobalDefine"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
	<%@include file="ref.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/js/jquery.toast/jquery.toast.css" />   
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.toast/jquery.toast.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.validate-method.js"></script>
	<script type="text/javascript">	
	$(function(){			
		$("#password_form").validate({
			 submitHandler: function(form){
			      $(form).ajaxSubmit(function(data){
					    if(data.s){
					    	alert("密码修改成功，请重新登录!");
							window.parent.location='<%=request.getContextPath() %>/admin/login';
					    }else{
						    $.toast(data.i);
					    }			    
		            });     
			   },
			rules: {
				oldpass: {
					required:true,
					remote: {
					    url: "<%=request.getContextPath()%>/admin/base/user/checkPass/"+'${user.id }',     //后台处理程序
					    type: "post",               //数据发送方式
					    dataType: "text",           //接受数据格式   
					    data: {                     //要传递的数据
					    	oldpass: function() {
					            return $("#oldpass").val();
					        }
					    }
					}
				},
				newpass: {
					required: true,
					minlength: 6,
					maxlength:16
				},
				conpass: {
					required: true,
					equalTo: "#newpass"
				}
			},
			messages: {
				oldpass: {
					required: "旧密码不能为空！"
				},
				newpass: {
					required: "新密码不能为空！",
					minlength: "密码长度6~16位！",
					maxlength: "密码长度6~16位！"
				},
				conpass: {
					required: "确认密码不能为空！",
					equalTo: "两次密码不一致！"
				}
			},
			errorPlacement: function(error,element) { 
				if(error.html()!='false'){
					error.appendTo(element.next("div"));
				}
			} 
		});			
	})				
	</script>
    </head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
    	<div class="ibox-content">
			<div class="page-header">
				<h3>密码管理</h3>
			</div>
			<form role="form" id="password_form" action="<%=request.getContextPath()%>/admin/base/user/changePass" 
			 class="form-horizontal" method="post">
                <input type="hidden" id="userId" name="userId" value="${user.id }"/>	            
                <div class="form-group">
					<label class="col-sm-2 control-label">
						原始密码
 					</label>
            		<div class="col-sm-4">
            			<input type="password" class="form-control" id="oldpass" name="oldpass" value="" placeholder="原始密码"/>
            		    <div style=" font-size: 10px; color:#dc0909; clear:both; "></div>
            		</div>
            	</div>
            	<div class="form-group">
					<label class="col-sm-2 control-label">
						新的密码
 					</label>
            		<div class="col-sm-4">
            			<input type="password" class="form-control" id="newpass" name="newpass" value="" placeholder="新的密码"/>
            		    <div style=" font-size: 10px; color:#dc0909; clear:both; "></div>
            		</div>
            	</div>
            	<div class="form-group">
					<label class="col-sm-2 control-label">
						确认密码
 					</label>
            		<div class="col-sm-4">
            			<input type="password" class="form-control" id="conpass" name="conpass" value="" placeholder="确认密码"/>
            		    <div style=" font-size: 10px; color:#dc0909; clear:both; "></div>
            		</div>
            	</div>         	           	
            	<div class="form-group">
        			<div class="col-sm-offset-2 col-sm-10">
		              	<button type="button" class="btn btn-primary" onclick="$('#password_form').submit();">
		                   <i class="fa fa-save"></i> 确定
		           		</button>
		          	</div>
           		</div>
             </form>
		</div>
	</div>
</body>
</html>