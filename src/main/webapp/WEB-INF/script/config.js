require.config({
	baseUrl : ctx + "/script",
	paths : {
		"jquery" : "jquery",
		"validate" : "jquery.validate",
		"bootstrap" : "bootstrap"
	},
	shim : {
		"jquery" : {
			exports : "$"
		},
		"validate" : {
			exports : "validate",
			deps : [ "jquery" ]
		},
		"bootstrap" : {
			exports : "$.fn.popover",
			deps : [ "jquery" ]
		}
	},
	// enforceDefine : true,
	waitSeconds : 5
});
define(function() {
	require([ "jquery", "custom/navbar" ], function($, navbar) {
		navbar.render($("#navbar"), {
			logout : ctx + "/j_spring_security_logout",
			dataUrl : "/resource/getMenus.do"
		});
	});
});