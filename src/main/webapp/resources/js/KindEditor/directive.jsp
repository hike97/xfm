<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link href="<%=request.getContextPath()%>/facetype/manager/css/style.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">
function insertClist(){
	var id=clistf.clistId.value;
	var size=clistf.clistSize.value;
	var length=clistf.clistLength.value;
	var target=clistf.clistTarget.value;
	var time;
	var timeformat=clistf.clistTimeformat.value;
	var withpage;
	var cssclass=clistf.clistClass.value;
	var message='';
		if(id!=''&&!/^[0-9]+$/.test(id)){
			if(message!=''){
				message+='\n';
			}
			message+='id必须为数字！';
	}
	if(size!=''&&!/^[0-9]+$/.test(size)){
		if(message!=''){
			message+='\n';
		}
		message+='显示行数必须为数字！';
		
	}
	if(length!=''&&!/^[0-9]+$/.test(length)){
		if(message!=''){
			message+='\n';
		}
		message+=length+'标题长度必须为数字！';
	}
	for(var i=0;i<clistf.clistTime.length;i++){
		if(clistf.clistTime[i].checked)time=clistf.clistTime[i].value;
	}
	for(var i=0;i<clistf.clistWithpage.length;i++){
		if(clistf.clistWithpage[i].checked)withpage=clistf.clistWithpage[i].value;
	}
	if(message!=''){
		alert(message);
	}else{
		var str='';
		if(id){
			str+=' id="'+id+'"';
		}
		if(size){
			str+=' size="'+size+'"';
		}
		if(length){
			str+=' length="'+length+'"';
		}
		if(target){
			str+=' target="'+target+'"';
		}
		if(time=='true'){
			str+=' time="'+time+'"';
		}
		if(timeformat){
			str+=' timeformat="'+timeformat+'"';
		}
		if(withpage=='true'){
			str+=' withpage="'+withpage+'"';
		}
		if(cssclass){
			str+=' class="'+cssclass+'"';
		}
		parent.insertPlugin(str);
		alert(str);
		parent.fb_remove();
	}
}
</script>
<form name="clistf">
<table align="center" class="teble_list" style="width: 95%;">
	<tr>
		<td colspan="4">新闻列表标签<%=request.getParameter("plugin") %></td>
	</tr>
	<tr>
		<td><b>*</b> 栏目id：</td><td><input name="clistId" type="text"> </td>
		<td><b>*</b>显示行数：</td><td><input name="clistSize" type="text"> </td>
	</tr>
	<tr>
		<td><b>*</b> 栏目名称：</td><td colspan="3"><input name="clistName" type="text"> </td>
	</tr>
	<tr>
		<td> 标题长度：</td><td><input name="clistLength" type="text"> </td>
		<td>Target：</td><td><input name="clistTarget" type="text"> </td>
	</tr>
	<tr>
		<td>显示时间：</td>
		<td><input name="clistTime" type="radio" value="true">是
		<input name="clistTime" checked="checked" type="radio" value="false">否 </td>
		<td>时间格式：</td><td><input name="clistTimeformat" type="text"> </td>
	</tr>
	<tr>
		<td>显示分页：</td>
		<td><input name="clistWithpage" type="radio" value="true">是
			<input name="clistWithpage" checked="checked" type="radio" value="false">否 </td>
		<td>css样式：</td><td><input name="clistClass" type="text"> </td>
	</tr>
		<tr>
		<td>排序方式：</td>
		<td><input name="clistOrder" type="radio" value="true">升
			<input name="clistOrder" checked="checked" type="radio" value="false">降 </td>
		<td>排序字段：</td>
		<td><select>
				<option value="">发布时间</option>
				<option value="">浏览次数</option>
			</select> </td>
	</tr>
</table>
<div class="btn_box"><input type="button" class="button"  value="插入标签" onclick="insertClist();"/></div>
</form>