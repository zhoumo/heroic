require.config({
	baseUrl : "/heroic/script",
	paths : {
		"jquery" : "jquery",
		"jquery.validate" : "jquery.validate",
		"bootstrap" : "bootstrap",
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
		}
	},
	waitSeconds : 0
});
define(function() {
	require([ "jquery", "custom/util" ], function($, util) {
		require([ "custom/navbar" ], function(navbar) {
			navbar.render($("[type=navbar]"), {
				logout : util.getContextPath() + "/j_spring_security_logout",
				dataUrl : "/resource/getMenus.do"
			});
		});
	});
});