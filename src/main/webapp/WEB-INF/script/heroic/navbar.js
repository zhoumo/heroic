NavBar = Base.extend({
	nav : null,
	navbar : null,
	constructor : function(navbar) {
		this.navbar = navbar;
	},
	decorate : function() {
		var collapse = createTag("div");
		collapse.addClass("navbar-collapse collapse navbar-collapse-01");
		this.navbar.addClass("navbar navbar-inverse");
		this.navbar.append(collapse);
		this.nav = createTag("ul");
		this.nav.addClass("nav navbar-nav navbar-left");
		this.nav.appendTo(navbar);
		this.nav.append($("<li><a href='" + getContextPath() + "'>首页</a></li>"));
		var logout = $("<ul class='nav navbar-nav navbar-right'><li><a href='" + getContextPath() + "/j_spring_security_logout'>退出</a></li></ul>");
		logout.appendTo(navbar);
	},
	createNavMenu : function(id, resources, container) {
		if (container == null) {
			container = this.nav;
		}
		for ( var index = 0; index < resources.length; index++) {
			var resource = resources[index];
			if (resource.parentId != id) {
				continue;
			}
			var url = getContextPath() + resource.url;
			var li = $("<li><a href='" + url + "'>" + resource.name + "</a></li>");
			container.append(li);
			var ul = $("<ul></ul>");
			ul.appendTo(li);
			this.createNavMenu(resource.id, resources, ul);
		}
	}
});
(function($) {
	$.fn.extend({
		navigate : function(setting) {
			var defaults = {
				url : null,
				callback : null
			};
			var config = $.extend(defaults, setting || {});
			var navbar = new NavBar($(this));
			navbar.decorate();
			$.ajax({
				type : "post",
				url : getContextPath() + config.url,
				dataType : "jsonp",
				async : false,
				success : function(data) {
					var resources = eval(data);
					if (resources.length > 0) {
						navbar.createNavMenu(0, resources);
					}
				},
				error : function() {
					alert("Loading Failed.");
				}
			});
			if (typeof config.callback == 'function') {
				config.callback.call(this);
			}
		}
	});
})(jQuery);