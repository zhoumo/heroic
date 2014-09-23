define(function() {
	return {
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