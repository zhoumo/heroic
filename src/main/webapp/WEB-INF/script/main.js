require.config({
	baseUrl : document.location.pathname.substr(0, document.location.pathname.substr(1).indexOf("/") + 1) + "/script",
	paths : {
		"jquery" : "jquery"
	},
	shim : {
		"bootstrap" : [ "jquery" ],
		"jquery.tmpl" : [ "jquery" ],
		"jquery.validate" : [ "jquery" ]
	},
	waitSeconds : 5
});
require([ "custom/navbar" ], function(navbar) {
	navbar.render($("#navbar"));
});
define([ "custom/base", "custom/toolbar", "custom/datatable", "custom/dialog", "custom/validate" ], function(base, toolbar, datatable, dialog, validate) {
	return {
		build : function(key, title) {
			var custom = base.syncAjax("getCustom.do", "json");
			toolbar.render($("#toolbar"), {
				key : key,
				title : title
			});
			datatable.render($("#" + key + "Container"), {
				key : key,
				keys : custom.dataTable.keys,
				titles : custom.dataTable.titles
			});
			dialog.load(custom.dialog);
			dialog.decorate(key);
			validate.register(key, custom.validates);
		}
	};
});