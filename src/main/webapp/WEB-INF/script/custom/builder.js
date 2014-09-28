define([ "jquery", "bootstrap.plugin" ], function($) {
	return {
		build : function(key, title) {
			var settings = {
				key : key,
				title : title,
				custom : $.load("getCustom.do", "json")
			};
			this.createToolbar(settings);
			this.createTable(settings);
			this.createDialog(settings);
			this.bindValidate(settings);
			this.internationalize();
		},
		createToolbar : function(settings) {
			var button = $("<button class='btn btn-primary' style='width:100%'>新建" + settings.title + "</button>");
			button.click(function() {
				$("[type=dialog]").filter("[key=" + settings.key + "]").modal();
			});
			$("[type=toolbar]").append(button);
			return button;
		},
		createTable : function(settings) {
			require([ "custom/actions" ], function(actions) {
				$("[type=table]").filter("[key=" + settings.key + "]").table({
					url : "pagination.do?pageNo=",
					pageNo : 1,
					custom : settings.custom,
					action : [ actions.remove, actions.update ]
				});
			});
		},
		createDialog : function(settings) {
			$("[type=dialog]").filter("[key=" + settings.key + "]").dialog({
				key : settings.key,
				dialog : settings.custom.dialog
			});
		},
		bindValidate : function(settings) {
			require([ "custom/validate" ], function(validate) {
				validate.bind(settings.custom);
			});
		},
		internationalize : function() {
			require([ "i18n!nls/locale" ], function(locale) {
				$("th").each(function() {
					var key = $(this).attr("key");
					$(this).text(locale[key + "-" + $(this).text()]);
				});
				$("label").each(function() {
					var key = $(this).attr("key");
					$(this).text(locale[key + "-" + $(this).text()]);
				});
			});
		}
	};
});