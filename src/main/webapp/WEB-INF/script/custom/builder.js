define([ "jquery", "custom/ajax", "custom/toolbar", "custom/datatable", "custom/dialog", "custom/validate" ], function($, ajax, toolbar, datatable, dialog, validate) {
	return {
		build : function(key, title) {
			var custom = ajax.syncAjax("getCustom.do", "json");
			toolbar.render($("#toolbar"), {
				key : key,
				title : title
			});
			datatable.render($("#" + key + "Datatable"), 1, {
				key : key,
				keys : custom.dataTable.keys,
				titles : custom.dataTable.titles
			});
			dialog.render($("#" + key + "Dialog"), custom.dialog);
			validate.register(key, custom.validates);
		}
	};
});