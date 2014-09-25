define([ "jquery", "custom/util" ], function($, util) {
	return {
		build : function(key, title) {
			var settings = {
				key : key,
				title : title,
				custom : util.syncAjax("getCustom.do", "json")
			};
			this.buildToolbar(settings);
			this.buildTable(settings);
			this.buildDialog(settings);
			this.buildValidate(settings);
		},
		buildToolbar : function(settings) {
			require([ "custom/toolbar" ], function(toolbar) {
				toolbar.render($("[type=toolbar]"), settings);
			});
		},
		buildTable : function(settings) {
			require([ "custom/table" ], function(table) {
				table.render($("[type=table]").filter("[key=" + settings.key + "]"), 1, settings.custom);
			});
		},
		buildDialog : function(settings) {
			require([ "custom/dialog" ], function(dialog) {
				dialog.render($("[type=dialog]").filter("[key=" + settings.key + "]"), settings.custom);
			});
		},
		buildValidate : function(settings) {
			require([ "custom/validate" ], function(validate) {
				validate.register(settings.custom);
			});
		}
	};
});