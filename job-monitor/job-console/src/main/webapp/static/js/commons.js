(function(){
	var xiaoniu = function(){};
	xiaoniu.prototype.getQueryParams = function(dataGridId){
		 var opts = $('#'+dataGridId).datagrid('options');
		 var data = opts.queryParams;
		 var params = [];
		 for(var name in data.query){
			 if(data.query[name]){
				 params.push("query["+name+"]="+data.query[name]);
			 }
		 }
		 params.push("page="+(opts.pageNumber||1));
		 params.push("rows="+(opts.pageSize));
		 params.push("sort="+(opts.sortName||''));
		 params.push("order="+(opts.sortOrder||''));
		 return params.join("&");
	 }
	return window.xiaoniu = new xiaoniu();
})();