define([ "custom/ajax" ], function(ajax) {
	function createNav(id, resources, container) {
		for ( var index = 0; index < resources.length; index++) {
			var resource = resources[index];
			if (resource.parentId != id) {
				continue;
			}
			var li = $("<li><a href='" + ctx + resource.url + "'>" + resource.name + "</a></li>");
			container.append(li);
			var ul = $("<ul></ul>");
			ul.appendTo(li);
			createNav(resource.id, resources, ul);
		}
	}
	return {
		render : function(container, settings) {
			container.empty();
			container.addClass("navbar navbar-inverse");
			var collapse = $("<div class='navbar-collapse collapse navbar-collapse-01'></div>");
			var nav_left = $("<ul class='nav navbar-nav navbar-left'><li><a href='" + ctx + "'>首页</a></li></ul>");
			var nav_right = $("<ul class='nav navbar-nav navbar-right'><li><a href='" + settings.logout + "'>退出</a></li></ul>");
			container.append(collapse);
			container.append(nav_left);
			container.append(nav_right);
			this.load(settings.dataUrl, $(".nav:first"));
		},
		load : function(url, nav) {
			var resources = ajax.syncAjax(ctx + url, "jsonp");
			if (resources.length > 0) {
				createNav(0, resources, nav);
			}
		}
	};
});