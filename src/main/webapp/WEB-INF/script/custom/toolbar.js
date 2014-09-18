define([ "custom/base" ], function(base) {
	return {
		render : function(container, params) {
			var button = base.createTag("button", container);
			button.addClass("btn btn-primary");
			button.attr("style", "width: 100%");
			button.text("新建" + params.title);
			button.click(function() {
				$("#" + params.key + "Dialog").modal();
			});
		}
	};
});