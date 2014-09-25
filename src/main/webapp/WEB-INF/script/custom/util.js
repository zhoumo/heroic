define([ "jquery", "i18n!nls/locale" ], function($, locale) {
	return {
		getContextPath : function() {
			var pathName = document.location.pathname;
			var index = pathName.substr(1).indexOf("/");
			return pathName.substr(0, index + 1);
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
		},
		locale : function(text, key) {
			var value = locale[key + "-" + text];
			return value == null ? text : value;
		}
	};
});