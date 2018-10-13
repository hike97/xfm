(function($) {
	/*
	 * 参数title：显示的标题。
	 * fn：回调方法
	 * 例：$.cfm("确定要删除吗？", function(){
					toggle(row.id,1)
				});
	 * */
	$.cfm = function (title,fn,text,type) {
		swal({   
			title: title,  
			text:text,
			type: type,   
			showCancelButton: true,   
			confirmButtonColor: "#DD6B55",   
			confirmButtonText: "确定",   
			cancelButtonText: "取消"
			}, 
			function(isConfirm){   
				if (isConfirm) { 
					fn();
				} 
			});
	}
	/*
	 * 参数title：显示的标题。
	 * type：图标类型 为空则不显示图标
	 * 例：$.toast(data.i,data.s);
	 * */
	$.toast = function(title,type,fn){
		if(type==true){
			type="success";
		}else if(type==false){
			type="error";
		}
		swal({   
			title: title,   
			timer: 1500,
			type: type,
			showConfirmButton: false 
		},function(){
			if(fn){
				fn();
			}
			swal.close();
		});
	}
	$.alert = function(title){
		swal({   
			title: title,   
			timer: 1500,   
			showConfirmButton: false 
		});
	}
	/*checkbox全选。
	 * 参数cb：要全选的区域，空则全选document范围内的所有checkbox。
	 * 例：$('#chb').selectAll($('#cha'));
	 *	$('#chb')为触发全选的checkbox；
	 *	$('#cha')为全选的区域，空则全选所有checkbox。
	 * */
	$.fn.selectAll = function (cb){
			var obj = $(this);
		 	obj.bind('click', function() {
		 		var status=obj.attr('checked');
			 	var cbs;
			 	if(cb){
			 		cbs=cb.find(":checkbox");
			 	}else{
			 		cbs=$(":checkbox");
			 	}
				for(var i=0;i<cbs.length;i++){
					cbs[i].checked=status;
				}
		 	});
		 };
	String.prototype.endWith=function(s){
		  if(s==null||s==""||this.length==0||s.length>this.length)
		     return false;
		  if(this.substring(this.length-s.length)==s)
		     return true;
		  else
		     return false;
		  return true;
		 };

	 String.prototype.startWith=function(s){
	  if(s==null||s==""||this.length==0||s.length>this.length)
	   return false;
	  if(this.substr(0,s.length)==s)
	     return true;
	  else
	     return false;
	  return true;
	 };
})(jQuery);