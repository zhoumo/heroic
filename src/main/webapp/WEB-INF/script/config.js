require.config({
	baseUrl : ctx + "/script",
	paths : {
		"jquery" : "jquery",
		"jquery.validate" : "jquery.validate",
		"bootstrap" : "bootstrap"
	},
	shim : {
		"jquery" : {
			exports : "$"
		},
		"jquery.validate" : {
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
	require([ "jquery", "custom/navbar" ], function($, navbar) {
		navbar.render($("[type=navbar]"), {
			logout : ctx + "/j_spring_security_logout",
			dataUrl : "/resource/getMenus.do"
		});
	});
});