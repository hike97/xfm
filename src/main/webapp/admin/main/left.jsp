<%@page import="com.yc.core.util.GlobalDefine"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!--左侧导航开始-->
<nav class="navbar-default navbar-static-side navwrap" role="navigation">
	<div class="nav-close"><i class="fa fa-times-circle"></i></div>
    <div class="sidebar-collapse" style="width: auto; height: 100%;">
        <ul class="nav" id="side-menu">
            <li class="nav-header">
                <div class="dropdown profile-element">
                    <span><img alt="image" class="img-circle" src="http://ozwpnu2pa.bkt.clouddn.com/profile_small.jpg"></span>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                           <span class="clear">
                           <span class="block m-t-xs"><strong class="font-bold">Beaut-zihan</strong></span>
                           <span class="text-muted text-xs block">超级管理员<b class="caret"></b></span>
                           </span>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight m-t-xs">
                        <li>
                            <a class="J_menuItem" href="form_avatar.html">修改密码</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="login.html">安全退出</a>
                        </li>
                    </ul>
                </div>
                <div class="logo-element">H+
                </div>
            </li>
            <li class="">
                <a href="#">
                    <i class="fa fa-home"></i> <span class="nav-label">基础管理</span> <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level collapse" aria-expanded="false" style="height: 0px;">
                    <li>
                        <a class="J_menuItem" href="<%=request.getContextPath()%>/admin/login">用户管理</a>
                    </li>
                    <li>
                        <a class="J_menuItem" href="<%=request.getContextPath()%>/admin/a">区域管理</a>
                    </li>
                    <li>
                        <a class="J_menuItem" href="<%=request.getContextPath()%>/admin/b">部门管理</a>
                    </li>
                    <li>
                        <a class="J_menuItem" href="<%=request.getContextPath()%>/admin/base/comInfo?type=1">民族管理</a>
                    </li>
                    <li>
                        <a class="J_menuItem" href="<%=request.getContextPath()%>/admin/base/comInfo?type=2">文化程度管理</a>
                    </li>
                    <li>
                        <a class="J_menuItem" href="<%=request.getContextPath()%>/admin/base/comInfo?type=3">政治面貌管理</a>
                    </li>
                </ul>

            </li>
            <li>
                <a href="#"><i class="fa fa-columns"></i>
                    <span class="nav-label">重点人员管理</span><span class="fa arrow"></span>
                </a>
                 <ul class="nav nav-second-level collapse">
                 	<li>
                        <a class="J_menuItem" href="<%=request.getContextPath()%>/admin/goods">录入重点人员信息</a>
                    </li>
                    <li>
                        <a class="J_menuItem" href="graph_echarts.html">重点人员信息管理</a>
                    </li>
                    <li>
                        <a class="J_menuItem" href="graph_flot.html">重点人员信息查询</a>
                    </li>
                </ul>
            </li>

          
        </ul>
	</div>
</nav>
<!--左侧导航结束-->
        