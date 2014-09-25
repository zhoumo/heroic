define([ "jquery", "custom/util" ], function($, util) {
	return {
		build : function(key, title) {
			var settings = {
				key : key,
				title : title,
				custom : util.syncAjax("getCustom.do", "json")
			};
			this.buildToolbar(settings);
			this.buildDatatable(settings);
			this.buildDialog(settings);
			this.buildValidate(settings);
		},
		buildToolbar : function(settings) {
			require([ "custom/toolbar" ], function(toolbar) {
				toolbar.render($("[type=toolbar]"), settings);
			});
		},
		buildDatatable : function(settings) {
			require([ "custom/datatable" ], function(datatable) {
				datatable.render($("[type=datatable]").filter("[key=" + settings.key + "]"), 1, settings.custom.dataTable);
			});
		},
		buildDialog : function(settings) {
			require([ "custom/dialog" ], function(dialog) {
				dialog.render($("[type=dialog]").filter("[key=" + settings.key + "]"), settings.custom.dialog);
			});
		},
		buildValidate : function(settings) {
			require([ "custom/validate" ], function(validate) {
				validate.register(settings.key, settings.custom.validates);
			});
		}
	};
});