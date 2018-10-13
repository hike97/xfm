<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <form role="form" id="pass_form" action="<%=request.getContextPath()%>/admin/uPassword" method="get">
          <div class="input-group">
	          <span class="input-group-addon">
	               		原始密码
	              </span>
	            <input type="password" class="form-control" id="oldPassword" name="oldPassword"  placeholder="原始密码" value="">
	       </div>
	       <br/>
	       <div class="input-group">
	           <span class="input-group-addon">新的密码</span>
	           <input type="password" class="form-control" id="password" name="password" placeholder="新的密码"  value="">
	       </div>
	        <br/>
	       <div class="input-group">
	           <span class="input-group-addon">确认密码</span>
	           <input type="password" class="form-control" id="repassword" name="repassword" placeholder="确认新密码"  value="">
	       </div>
	       <br>
	       <div class="input-group">
	       		<span class="input-group-addon">用户昵称</span>
	           <input type="text" class="form-control" id="nickName" name="nickName" value="${user.nickName }">
	       </div>
	</form>