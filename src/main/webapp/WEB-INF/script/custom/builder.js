define([ "jquery", "bootstrap.plugin" ], function($) {
	var context = null;
	var settings = null;
	return {
		build : function(key, title, ctx) {
			context = ctx || "";
			settings = {
				key : key,
				title : title,
				custom : $.load(context + "getCustom.do", "json")
			};
			this.createToolbar();
			this.createTable();
			this.createDialog();
			this.bindValidate();
			this.internationalize();
		},
		getSettings : function() {
			return settings;
		},
		createToolbar : function() {
			var button = $("<button class='btn btn-primary' style='width:100%'>新建" + settings.title + "</button>");
			button.click(function() {
				$("[type=dialog]").filter("[key=" + settings.key + "]").modal();
			});
			$("[type=toolbar]").append(button);
		},
		createTable : function() {
			require([ "custom/actions" ], function(actions) {
				$("[type=table]").filter("[key=" + settings.key + "]").table({
					url : context + "pagination.do?pageNo=",
					pageNo : 1,
					custom : settings.custom,
					action : [ actions.remove, actions.update ]
				});
			});
		},
		createDialog : function() {
			$("[type=dialog]").filter("[key=" + settings.key + "]").dialog({
				key : settings.key,
				dialog : settings.custom.dialog
			});
		},
		bindValidate : function() {
			require([ "custom/validate" ], function(validate) {
				validate.bind(settings.custom);
			});
		},
		internationalize : function() {
			require([ "i18n!nls/locale" ], function(locale) {
				$("th, label").each(function() {
					var key = $(this).attr("key");
					$(this).text(locale[key + "-" + $(this).text()]);
				});
			});
		}
	};
});