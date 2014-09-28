require.config({
	baseUrl : "/heroic/script",
	paths : {
		"jquery" : "jquery",
		"jquery.validate" : "jquery.validate",
		"bootstrap" : "bootstrap",
		"bootstrap.plugin" : "bootstrap.plugin",
		"text" : "require.plugin.text",
		"i18n" : "require.plugin.i18n"
	},
	shim : {
		"jquery" : {
			exports : "$"
		},
		"jquery.validate" : {
			exports : "$.fn.validate",
			deps : [ "jquery" ]
		},
		"bootstrap" : {
			exports : "$.fn.popover",
			deps : [ "jquery" ]
		},
		"bootstrap.plugin" : {
			exports : "$.fn.popover",
			deps : [ "jquery", "bootstrap" ]
		}
	},
	waitSeconds : 0
});
define(function() {
	require([ "jquery", "bootstrap.plugin" ], function($) {
		$("div[type=navbar]").navbar({
			dataUrl : "/resource/getMenus.do",
			dataType : "jsonp",
			logoutUrl : "/j_spring_security_logout"
		});
	});
});