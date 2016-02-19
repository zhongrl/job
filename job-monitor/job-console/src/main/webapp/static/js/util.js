//公用js

//讲form序列化数组转换为JSON
function jsonFromt(data) {
	var json = {};
	for (var i = 0; i < data.length; i++) {
		json[data[i].name] = data[i].value;
	}
	return json;
}

//构建编辑窗体
function buildEditWindow(width,height,title,html,windowId,buttonId){
	var windowHTML= [];
		windowHTML.push('<div id="'+windowId+'" class="easyui-window" title="'+title+'" data-options="iconCls:\'icon-save\',closed:true" style="width:'+width+'px;height:'+height+'px;padding:5px;">');
		windowHTML.push('	<div class="easyui-layout" data-options="fit:true">');
		windowHTML.push('		<div name="center" data-options="region:\'center\'" style="padding:10px;">');
		windowHTML.push(html);
		windowHTML.push('		</div>');
		windowHTML.push('		<div data-options="region:\'south\',border:false" style="text-align:right;padding:5px 0 0;">');
		windowHTML.push('			<button  id="'+buttonId+'" class="easyui-linkbutton" data-options="iconCls:\'icon-ok\'" href="javascript:void(0)" style="width:80px">提  交</button>');
		windowHTML.push('			<a class="easyui-linkbutton" data-options="iconCls:\'icon-cancel\'" href="javascript:void(0)" onclick="javascript:$(\'#'+windowId+'\').window(\'close\');" style="width:80px">取  消</a>');
		windowHTML.push('		</div>');
		windowHTML.push('	</div>');
		windowHTML.push('	</div>');
	    return windowHTML.join('');
}

//构建Combogrid
function buildCombogrid(param){
	defaults = {
			panelWidth : 450,
			panelHeight : 370,
//			editable:false,
			idField : 'fId',
			textField : 'fName',
			url : $('#path').val() + "/activity/list",
			toolbar:"#combogrid",
			value:'',
			pagination : true
//			rownumbers : true,
//			collapsible : false,
//			fit : true
//			fitColumns:true,
//			diplayText:''
//			pageSize : 10,
//			pageList : [ 10 ],
//			method : 'post'
	}
	return $.extend(defaults, param);
}

//构建活动Combogrid
function buildCombogridActivity(param){
	var columns = [[
	                {field : 'fName',title : '名称',width : 240,align : 'left'},
	                {field : 'fType',title : '活动类型',width : 100,align : 'left',
						formatter : function(value, row, index) {
							if ('REDPAPER_INVEST' == value) {
								return '投资红包';
							}else if ('REDPAPER_WEIXIN' == value) {
								return '微信红包';
							}else if('T_RED_PAPER_TYPE' ==value){
								return '红包';
							}    else {
								return value;
							}
						}
					},
					{field : 'fStatus',title : '状态',width : 80,align : 'left',
						formatter : function(value, row, index) {
							if ('NEW' == value) {
								return '新建';
							} else if ('EXECUTING' == value) {
								return '执行中';
							} else if ('PAUSE' == value) {
								return '暂停';
							} else if ('FINISHED' == value) {
								return '已完成';
							} else if ('DISCARDED' == value) {
								return '已作废';
							} else {
								return value;
							}
						}
					}
	             ]];
	
	return buildCombogrid($.extend({url:$('#path').val() + "/activity/list",columns : columns}, param));
}

//构建用户
function buildCombogridUser(param){
	var columns = [[
	                {field : 'fName',title : '真实姓名',width : 100,align : 'left'},
	                {field : 'fLoginName',title : '登录名',width : 100,align : 'left'},
	                {field : 'fMobile',title : '手机',width : 100,align : 'left'},
	                {field : 'fEmployeeNumber',title : '员工工号',width : 100,align : 'left'} 
	             ]];
	return buildCombogrid($.extend({url:$('#path').val() + "/user/list",columns : columns}, param));
}

//构建红包类型
function buildCombogridRedpaperType(param){
	var columns = [[
	                {field : 'fName',title : '名称',width : 100,align : 'left'},
	                {field : 'fMoney',title : '金额',width : 100,align : 'left'},
//	                {field : 'fValue',title : '类型值',width : 100,align : 'left',
//	                	formatter : function(value, row, index) {
//		            		if(value == 'INVEST'){
//		            			return '投资';
//		            		}else if(value == 'WEIXIN'){
//		            			return '微信';
//		            		}else{
//		            			return value;
//		            		}
//						}
//	                },
	                {field : 'fRemark',title : '备注',width : 200,align : 'left'} 
	             ]];
	return buildCombogrid($.extend({url:$('#path').val() + "/redPaperType/list",columns : columns}, param));
}

//构建产品
function buildCombogridProduct(param){
	var columns = [ [
		           {field : 'ck',width : 50,checkbox : true}, 
		           {field : 'productName',title : '产品名称',width : 200,align : 'left'},
		           {field : 'buyMinMoney',title : '购买最小额度',width : 150,align : 'left',
		        	   formatter : function(value, row, index) {
		        		   return accounting.formatMoney(value, '');
					   }
		           },
		           {field : 'buyMaxMoney',title : '购买最大额度',width : 150,align : 'left',
		        	   formatter : function(value, row, index) {
		        		   return accounting.formatMoney(value, '');
					   }
		           },
		           {field : 'buyTotalMoney',title : '产品总额度',width : 150,align : 'left',
		        	   formatter : function(value, row, index) {
		        		   return accounting.formatMoney(value, '');
					   }
		           },
		           {field : 'buyTotalPeople',title : '购买总人数',width : 150,align : 'left',
		        	   formatter : function(value, row, index) {
		        		   return accounting.toFixed(value, 0);
					   }
		           },
		           {field : 'buyedTotalMoney',title : '投资总额',width : 150,align : 'left',
		        	   formatter : function(value, row, index) {
		        		   return accounting.formatMoney(value, '');
					   }
		           },
		           {field : 'buyedTotalPeople',title : '已投资人数',width : 150,align : 'left',
		        	   formatter : function(value, row, index) {
		        		   return accounting.toFixed(value, 0);
					   }
		           },
		           {field : 'isRedemption',title : '是否可赎回',width : 150,align : 'left',
		        	   formatter : function(value, row, index) {
						 if(1 == value){
							return '否';
						 }else{
							return '是';
						 }
					   }
		           }
		] ];
	return buildCombogrid($.extend({idField:'id',textField:'productName',url:$('#path').val() + "/productInfo/list",columns : columns}, param));
}

$.extend($.fn.datagrid.methods, {
    fixRownumber : function (jq) {
        return jq.each(function () {
            var panel = $(this).datagrid("getPanel");
            //获取最后一行的number容器,并拷贝一份
            var clone = $(".datagrid-cell-rownumber", panel).last().clone();
            //由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
            clone.css({
                "position" : "absolute",
                left : -1000
            }).appendTo("body");
            var width = clone.width("auto").width();
            //默认宽度是25,所以只有大于25的时候才进行fix
            if (width > 25) {
                //多加5个像素,保持一点边距
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
                //修改了宽度之后,需要对容器进行重新计算,所以调用resize
                $(this).datagrid("resize");
                //一些清理工作
                clone.remove();
                clone = null;
            } else {
                //还原成默认状态
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
            }
        });
    }
});




