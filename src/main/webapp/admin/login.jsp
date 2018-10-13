<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="main/ref.jsp" %>
</head>
<script type="text/javascript">
$(function(){
	$("#loginForm").validate({
		 submitHandler: function(form){
		      $(form).ajaxSubmit(function(data){
		    	  	
	                if(data.s){
	                	window.location='<%=request.getContextPath() %>/admin/main';
	                }else{
	                	alert("用户名或密码错误！")
	                }
	            });     
		   },
		rules: {
			name:{
				required:true,
			},
			password: {
				required: true,
			}
		},
		messages: {
			name:{
				required:"用户名不能为空！"
			},
			password: {
				required: "密码不能为空！"
			}
		},
		errorPlacement: function(error,element) { 
			if(error.html()!='false'){
				error.appendTo(element.next("div"));
			}
		} 
	});
});
</script>
<style>
.containerbox{}
.logo_login{padding:10% 0 2% 0; text-align:center;}
.loginbox{width:420px;margin:0 auto;}
.login-panel{margin-top:15%;}
.form-group,.btn-info{margin:0; margin:15px 0;}
</style>
<style>
    input:-webkit-autofill {
        -webkit-box-shadow: 0 0 0px 1000px #fff inset !important;
        -webkit-text-fill-color: #000 !important;
    }

</style>
<body  onkeydown="keyLogin();" style="background:url(<%=request.getContextPath() %>/resources/images/xt_bg.gif) no-repeat 50% 0; background-size:cover;">
<div class="containerbox">
	<div class="logo_login"><img src="<%=request.getContextPath() %>/resources/images/logo_login.png"></div>
        <div class="loginbox">
           
                <div class="login-panel panel panel-default" style="border-radius:8px;border-bottom:none;">
                    <div class="panel-heading" style="border-top-left-radius: 8px; border-top-right-radius:8px; padding:12px 15px; color:#666;">
                        <h3 class="" style="text-align: center; ">用户登录</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" action="<%=request.getContextPath() %>/admin/login" id="loginForm" method="post">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="帐号" id="name" name="name" type="text" autofocus>
                                    <div class="jmp_error"></div>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="密码" id="password" name="password" type="password" value="">
                                    <div class="jmp_error"></div>
                                </div>
                                <a href="javascript:void(0)" class="btn btn-lg btn-info btn-block" onclick="$('#loginForm').submit();">登 录</a>
                            </fieldset>
                        </form>
                    </div>
                </div>
           
        </div>
        
        <!--  
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default" style="margin-top:100%; border-radius:8px;border-bottom:none;">
                    <div class="panel-heading" style="border-top-left-radius: 8px; border-top-right-radius:8px; padding:12px 15px; color:#666;">
                        <h3 class="" style="text-align: center; ">用户登录</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" action="<%=request.getContextPath() %>/admin/login" id="loginForm" method="post">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="帐号" id="name" name="name" type="text" autofocus>
                                    <div class="jmp_error"></div>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="密码" id="password" name="password" type="password" value="">
                                    <div class="jmp_error"></div>
                                </div>
                                <a href="javascript:void(0)" class="btn btn-lg btn-info btn-block" onclick="$('#loginForm').submit();">登 录</a>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
       --> 
        
    </div>
   <div class="footer fixed" style="margin:0;background:#3c3f43;color:#fff;">
	                <div style="text-align:center"> 技术支持：吉林省澳东软件有限公司
	           	</div>
	 </div>          	
	<script type="text/javascript">
		function keyLogin(){
			if(event.keyCode==13){
				$('#loginForm').submit();
			}
		}
	</script>
</body>
</html>
