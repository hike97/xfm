<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
		<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
		<jsp:include page="../../main/ref.jsp"/>
		<script type="text/javascript">
		var table;
		$(function(){
			table=$('#dataTable').DataTable({
			    "ajax": {
		               "url":"<%=request.getContextPath() %>/admin/base/comInfo/list?type="+${type},
		               "data": function ( d ) {
		                   //添加额外的参数传给服务器
		                   d.nameTJ=$('#nameTJ').val();
		               }},
			    "columns": [
							{ "data": "order" },
			                { "data": "code" },
			                { "data": "name" },
			                { "data": "status.name"},
			                { "data": "id",render: function(data, id, full, iRow) { // 返回自定义内容
			                	var html = '';
			                		html+=' <button class="btn btn-white btn-sm" style="width:60px;" onclick="edit(1,'+iRow.row+')"><i class="fa fa-edit"></i> 编辑</button>';
				                if(full.status.code=='enable'){
				                	html+=' <button class="btn btn-white btn-sm" style="width:60px;" onclick="updateStatus("disable",'+iRow.row+')"><i class="fa fa-plus"></i> 停用</button>';
				                }			                		
			                	if(full.status.code=='disable'){
			                		html+=' <button class="btn btn-white btn-sm" style="width:60px;" onclick="updateStatus("enable",'+iRow.row+')"><i class="fa fa-plus"></i> 启用</button>';
			                		html+=' <button class="btn btn-white btn-sm" style="width:60px;" onclick="updateStatus("delete",'+iRow.row+')"><i class="fa fa-trash-o"></i> 删除</button>';
			                	}
						      	return html;
								}
			                }
			            ]
			});
			 
        });
		//新增和修改
		function edit(type,index){
			$("#data-form").resetForm();
			//修改
			if(type==1){
				$('#code').attr("disabled", true);
				var row=table.rows().data()[index];
				$('#code').val(row.code);
				$('#oldCode').val(row.code);
				$('#name').val(row.name);
				$('#order').val(row.order);
				$("#data-form").validate({
					submitHandler: function(form){
					$(form).ajaxSubmit(function(data){
			    	  		$.toast(data.i,data.s,function(){
			    	  			table.ajax.reload( null, false );
				    	  		$("#myModal").modal("hide");
			    	  		});
			    	  		
			            });     
				   },
				   errorPlacement:function(error,element) {  
						error.appendTo(element.parent().parent());
				  	},
					rules: {
						name:{
							required:true
						}
					},
					messages: {
						name:{
							required:"请填写名称！"
						}
					}
				});				
			}
			//新增
			else{
				$('#oldCode').val('');
				$('#code').attr("disabled", false);
				$("#data-form").validate({
					submitHandler: function(form){
					$(form).ajaxSubmit(function(data){
							$.toast(data.i,data.s,function(){
			    	  			table.ajax.reload( null, false );
				    	  		$("#myModal").modal("hide");
			    	  		});
			            });     
				   },
				   errorPlacement:function(error,element) {  
						error.appendTo(element.parent().parent());
				  	},
					rules: {
						code:{
							required:true,
							minlength:0,
							maxlength:20,
							remote: {
							    url: "<%=request.getContextPath() %>/admin/base/comInfo/judgeCode",     //后台处理程序
							    type: "post",               //数据发送方式
							    dataType: "text",           //接受数据格式   
							    data: {                     //要传递的数据
							    	code: function() {
							    		var code = $("#code").val();
							            return code;
							        }
							    }
							}
						},
						name:{
							required:true
						}
					},
					messages: {
						code:{
							required:"请填写编码！",
							minlength:"编码不可超过20位！",
							maxlength:"编码不可超过20位！",
							remote:"编码已存在！"
						},
						name:{
							required:"请填写名称！"
						}
					}
				});	
			}
			$("#myModal").modal("show");
		}
		//更改状态
 		function updateStatus(status,index){
			var row=table.rows().data()[index];
			var url = "<%=request.getContextPath()%>/admin/base/comInfo/updateStatus";
			$.post(url,{status:status,id:row.id},function(data){
				$.toast(data.i,data.s);
				table.ajax.reload( null, false );
			});
		}		
		</script>
    </head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
    	<div class="ibox-content">
    		<div class="page-header">
				<h3>
              		数据列表
				</h3> 	
			</div>
			<div class="form-inline" style="line-height: 50px;">
				    <div class="input-group">
				      <span class="input-group-addon">名称</span>
				      <input type="text" id="nameTJ" name="nameTJ" class="form-control" placeholder="名称" />
				    </div><!-- /input-group -->
				  
				  	<button class="btn btn-primary btn-sm" onclick="table.ajax.reload();"><i class="fa fa-search"></i> 查询</button>
				  	<button class="btn btn-primary btn-sm" onclick="edit(0,0)"><i class="fa fa-plus"></i> 新增</button>
            </div>
             <table id="dataTable" class="table table-bordered table-hover">
                 <thead>
                     <tr>
                     	 <th width="100">排序号</th>
		                 <th>编码</th>
		                 <th>名称</th>
		                 <th>数据状态</th>
		                 <th width="300">操作</th>
                     </tr>
                 </thead>
                 <tbody></tbody>
             </table>
         </div>
     </div>
	  <!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel" style="text-align: center;">编辑数据</h4>
            </div>
            <div class="modal-body" id="modal-body">
            	<form role="form" id="data-form" action="<%=request.getContextPath()%>/admin/base/comInfo/add" method="post">
            		<input type="hidden" id="oldCode" name="oldCode" value="" />
            		<input type="hidden" id="type" name="type" value="${type }" />
					<div class="form-group">
						<label>编码:</label>
						<input type="text" class="form-control" id="code" name="code" placeholder="请输入1~20字符" value="">
					</div>            		
					<div class="form-group">
						<label>名称</label>
						<input type="text" class="form-control" id="name" name="name" placeholder="请输入1~100字符" value="">
					</div>
					<div class="form-group">
						<label>排序号</label>
						<input type="text" class="form-control" id="order" name="order" placeholder="请输入整数" value="">
					</div>					
				</form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="$('#data-form').submit()"><i class="fa fa-save"></i> 保存</button>
            </div>
        </div>
    </div>
</div>		
    </body>
</html>
