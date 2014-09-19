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