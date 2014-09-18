define([ "jquery.tmpl", "bootstrap" ], function() {
	return {
		createTag : function(tag, parent) {
			var tagObj = $("<" + tag + "></" + tag + ">");
			if (parent != null) {
				tagObj.appendTo(parent);
			}
			return tagObj;
		},
		getContextPath : function() {
			var pathName = document.location.pathname;
			var index = pathName.substr(1).indexOf("/");
			var result = pathName.substr(0, index + 1);
			return result;
		},
		syncAjax : function(url, dataType) {
			var result = null;
			$.ajax({
				url : url,
				type : "post",
				dataType : dataType,
				async : false,
				success : function(data) {
					result = eval(data);
				},
				error : function() {
					throw "sync ajax failed.";
				}
			});
			return result;
		}
	};
});