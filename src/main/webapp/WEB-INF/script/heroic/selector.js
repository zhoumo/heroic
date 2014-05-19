Selector = Base.extend({
	selector: null,
	url: null,
	selectedIds: null,
	constructor: function(selector, settings) {
		this.selector = selector;
		this.url = settings.url;
		this.selectedIds = createTag("input");
		this.selectedIds.attr("type", "hidden");
		this.selectedIds.attr("name", "selectedIds");
		this.selectedIds.val("|");
		this.selectedIds.appendTo(this.selector);
	},
	decorate: function() {
		var _this = this;
		this.selector.attr("style", "padding-left: 80px; margin-top: -55px;");
		var select = createTag("select");
		select.addClass("form-control");
		select.attr("style", "width: 150px");
		$.ajax({
			type: "post",
			url: getContextPath() + "/" + _this.url,
			dataType: "jsonp",
			async: false,
			success: function(data) {
				for (var index = 0; index < data.length; index++) {
					var option = createTag("option");
					option.val(data[index].id);
					option.text(data[index].name);
					option.appendTo(select);
				}
			}
		});
		select.change(function() {
			$(".repeatWarn").remove();
		});
		select.appendTo(this.selector);
		var addButton = createTag("button");
		addButton.addClass("btn btn-primary");
		addButton.attr("style", "margin-left: 20px");
		addButton.attr("type", "button");
		addButton.text("添加");
		addButton.appendTo(this.selector);
		addButton.click(function() {
			$(".repeatWarn").remove();
			var id = select.val();
			if (_this.selectedIds.val().indexOf("|" + id + "|") == -1) {
				_this.selectedIds.val(_this.selectedIds.val() + id + "|");
				_this.createItem(id, select.find("option:selected").text());
			} else {
				var warn = createTag("label");
				warn.addClass("validError repeatWarn");
				warn.text("不能重复添加");
				warn.insertAfter(addButton);
			}
		});
	},
	createItem: function(id, text) {
		var _this = this;
		var item = createTag("div");
		item.attr("style", "padding-top: 5px");
		item.appendTo(this.selector);
		var deleteButton = createTag("button");
		deleteButton.addClass("btn btn-primary btn-sm");
		deleteButton.attr("type", "button");
		deleteButton.click(function() {
			_this.selectedIds.val(_this.selectedIds.val().replace("|" + id + "|", "|"));
			item.remove();
		});
		deleteButton.appendTo(item);
		var icon = createTag("span");
		icon.addClass("glyphicon glyphicon-trash");
		icon.appendTo(deleteButton);
		var display = createTag("span");
		display.attr("style", "padding-left: 10px");
		display.text(text);
		display.appendTo(item);
	}
});

(function($) {
	$.fn.extend({
		createSelector: function(setting) {
			var defaults = {
				url: null
			};
			var selector = new Selector($(this), $.extend(defaults, setting || {}));
			selector.decorate();
		}
	});
})(jQuery);