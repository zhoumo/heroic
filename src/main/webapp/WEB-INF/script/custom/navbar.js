define([ "custom/base", "require.text!template/navbar.tmpl" ], function(base, navbarTmpl) {
	var contextPath = base.getContextPath();
	function createNav(id, resources, container) {
		for ( var index = 0; index < resources.length; index++) {
			var resource = resources[index];
			if (resource.parentId != id) {
				continue;
			}
			var li = $("<li><a href='" + contextPath + resource.url + "'>" + resource.name + "</a></li>");
			container.append(li);
			var ul = base.createTag("ul");
			ul.appendTo(li);
			createNav(resource.id, resources, ul);
		}
	}
	return {
		render : function(container) {
			container.html(navbarTmpl);
			$("#navbarTmpl").tmpl({
				contextPath : contextPath
			}).replaceAll(container);
			this.load("/resource/getMenus.do", $(".nav:first"));
		},
		load : function(url, nav) {
			var resources = base.syncAjax(contextPath + url, "jsonp");
			if (resources.length > 0) {
				createNav(0, resources, nav);
			}
		}
	};
});