define([ "custom/util" ], function(util) {
	var ctx = util.getContextPath();
	function createNav(id, resources, container) {
		for ( var index = 0; index < resources.length; index++) {
			var resource = resources[index];
			if (resource.parentId != id) {
				continue;
			}
			var li = $("<li><a href='" + ctx + resource.url + "'>" + resource.name + "</a></li>");
			li.appendTo(container);
			var ul = $("<ul></ul>");
			ul.appendTo(li);
			createNav(resource.id, resources, ul);
		}
	}
	return {
		render : function(container, settings) {
			container.empty();
			container.addClass("navbar navbar-inverse");
			container.append($("<div class='navbar-collapse collapse navbar-collapse-01'></div>"));
			container.append($("<ul class='nav navbar-nav navbar-left'><li><a href='" + ctx + "'>首页</a></li></ul>"));
			container.append($("<ul class='nav navbar-nav navbar-right'><li><a href='" + settings.logout + "'>退出</a></li></ul>"));
			var resources = this.load(settings.dataUrl);
			if (resources.length > 0) {
				createNav(0, resources, $(".nav:first"));
			}
		},
		load : function(url) {
			return util.syncAjax(ctx + url, "jsonp");
		}
	};
});