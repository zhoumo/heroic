define(function() {
	return {
		render : function(container, settings) {
			var button = $("<button class='btn btn-primary' style='width:100%'></button>");
			button.appendTo(container);
			button.text("新建" + settings.title);
			button.click(function() {
				$("[type=dialog]").filter("[key=" + settings.key + "]").modal();
			});
		}
	};
});