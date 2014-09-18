define([ "custom/base" ], function(base) {
	var container = null;
	var selectedIds = null;
	function createItem(id, text) {
		var item = base.createTag("div");
		item.attr("style", "padding-top: 5px");
		item.appendTo(container);
		var deleteButton = base.createTag("button");
		deleteButton.addClass("btn btn-primary btn-sm");
		deleteButton.attr("type", "button");
		deleteButton.click(function() {
			selectedIds.val(selectedIds.val().replace("|" + id + "|", "|"));
			item.remove();
		});
		deleteButton.appendTo(item);
		var icon = base.createTag("span");
		icon.addClass("glyphicon glyphicon-trash");
		icon.appendTo(deleteButton);
		var display = base.createTag("span");
		display.attr("style", "padding-left: 10px");
		display.text(text);
		display.appendTo(item);
	}
	return {
		decorate : function(url, container_) {
			container = container_;
			selectedIds = base.createTag("input");
			selectedIds.attr("type", "hidden");
			selectedIds.attr("name", "selectedIds");
			selectedIds.val("|");
			selectedIds.appendTo(container);
			var select = base.createTag("select");
			select.addClass("form-control");
			select.attr("style", "width: 150px");
			$.ajax({
				type : "post",
				url : base.getContextPath() + "/" + url,
				dataType : "jsonp",
				async : false,
				success : function(data) {
					for ( var index = 0; index < data.length; index++) {
						var option = base.createTag("option");
						option.val(data[index].id);
						option.text(data[index].name);
						option.appendTo(select);
					}
				}
			});
			select.change(function() {
				$(".repeatWarn").remove();
			});
			select.appendTo(container);
			var addButton = base.createTag("button");
			addButton.addClass("btn btn-primary");
			addButton.attr("style", "margin-left: 20px");
			addButton.attr("type", "button");
			addButton.text("添加");
			addButton.appendTo(container);
			addButton.click(function() {
				$(".repeatWarn").remove();
				var id = select.val();
				if (selectedIds.val().indexOf("|" + id + "|") == -1) {
					selectedIds.val(selectedIds.val() + id + "|");
					createItem(id, select.find("option:selected").text());
				} else {
					var warn = base.createTag("label");
					warn.addClass("validError repeatWarn");
					warn.text("不能重复添加");
					warn.insertAfter(addButton);
				}
			});
		}
	};
});