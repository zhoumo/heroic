define([ "jquery", "custom/ajax", "custom/toolbar", "custom/datatable", "custom/dialog", "custom/validate" ], function($, ajax, toolbar, datatable, dialog, validate) {
	return {
		build : function(key, title) {
			var custom = ajax.syncAjax("getCustom.do", "json");
			toolbar.render($("[type=toolbar]"), {
				key : key,
				title : title
			});
			datatable.render($("[type=datatable]").filter("[key=" + key + "]"), 1, {
				keys : custom.dataTable.keys,
				titles : custom.dataTable.titles
			});
			dialog.render($("[type=dialog]").filter("[key=" + key + "]"), custom.dialog);
			validate.register(key, custom.validates);
		}
	};
});