<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <div class="row border-bottom">
     <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
         <div class="navbar-header">
         	<a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
         </div>
         <ul class="nav navbar-top-links navbar-right">
             <li class="dropdown">
                 <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                     <i class="fa fa-bell"></i> 
                     <c:if test="${messageNum>0 }"><span class="label label-primary" id="messageNum">${messageNum}</span></c:if>
                 </a>
                 <ul class="dropdown-menu dropdown-alerts">
                     <li>
                         <a href="<%=request.getContextPath() %>/admin/work/message/my" class="J_menuItem">
                             <div>
                                 <i class="fa fa-envelope fa-fw"></i> 您有<span id="messageNum2">${messageNum}</span>条未读消息
                                 <span class="pull-right text-muted small"><fmt:formatDate value="${messageDate }" pattern="yyyy-MM-dd"/></span>
                             </div>
                         </a>
                     </li>
                     <!-- <li class="divider"></li> -->
                 </ul>
             </li>
         </ul>
     </nav>
 </div>