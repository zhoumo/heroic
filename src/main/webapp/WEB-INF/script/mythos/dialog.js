Dialog = Base.extend({
	dialog: null,
	key: null,
	width: null,
	type: null,
	layout: null,
	constructor: function(dialog, settings) {
		this.dialog = dialog;
		this.key = settings.key;
		this.width = settings.width;
		this.type = settings.type;
		this.layout = settings.layout;
	},
	decorate: function() {
		var _this = this;
		this.dialog.addClass("modal fade");
		this.dialog.attr("data-backdrop", "static");
		var modalDialog = createTag("div");
		modalDialog.appendTo(this.dialog);
		modalDialog.addClass("modal-dialog");
		modalDialog.attr("style", "width: " + (this.width == null ? "600px": this.width));
		var modalContent = createTag("div");
		modalContent.appendTo(modalDialog);
		modalContent.addClass("modal-content");
		var modalHeader = createTag("div");
		modalHeader.addClass("modal-header");
		modalHeader.appendTo(modalContent);
		var modalTitle = createTag("div");
		modalTitle.text("操作面板");
		modalTitle.addClass("modal-title");
		modalTitle.appendTo(modalHeader);
		var form = createTag("form");
		form.attr("id", this.key + "Form");
		form.attr("action", "save.do");
		form.attr("method", "post");
		form.appendTo(modalContent);
		var idInput = createTag("input");
		idInput.attr("id", "id");
		idInput.attr("name", "id");
		idInput.attr("type", "hidden");
		idInput.appendTo(form);
		var modalBody = createTag("div");
		modalBody.addClass("modal-body");
		modalBody.appendTo(form);
		if (this.type == "custom") {
			this.customInput(modalBody);
		}
		modalBody.attr("id", this.key + "ModalBody");
		var modalFooter = createTag("div");
		modalFooter.addClass("modal-footer");
		modalFooter.appendTo(form);
		var closeButton = createTag("input");
		closeButton.addClass("btn btn-default");
		closeButton.attr("type", "button");
		closeButton.attr("data-dismiss", "modal");
		closeButton.val("关闭");
		closeButton.click(function() {
			_this.reset();
		});
		closeButton.appendTo(modalFooter);
		var submitButton = createTag("input");
		submitButton.addClass("btn btn-primary");
		submitButton.attr("type", "submit");
		submitButton.val("确定");
		submitButton.appendTo(modalFooter);
	},
	customInput: function(body) {
		for (var index = 0; index < this.layout.length; index++) {
			var control = createTag("div");
			control.addClass("controls");
			control.appendTo(body);
			var label = createTag("label");
			label.addClass("control-label");
			label.attr("style", "width: 80px; height: 50px");
			label.attr("for", "input");
			label.text(this.layout[index].title + "：");
			label.appendTo(control);
			if (this.layout[index].type == "INPUT") {
				var input = createTag("input");
				input.addClass("form-control");
				input.attr("id", this.layout[index].key);
				input.attr("name", this.layout[index].key);
				input.attr("style", "width:300px");
				input.attr("type", "text");
				input.attr("placeholder", "输入" + this.layout[index].title);
				input.appendTo(control);
			} else if (this.layout[index].type == "SELECT") {
				var select = createTag("select");
				select.addClass("form-control");
				select.attr("id", this.layout[index].key);
				select.attr("name", this.layout[index].key);
				select.attr("style", "width:150px");
				var text = this.layout[index].text;
				var value = this.layout[index].value;
				if (this.layout[index].url != null) {
					var textField = this.layout[index].text;
					var valueField = this.layout[index].value;
					$.ajax({
						type: "post",
						url: this.layout[index].url,
						dataType: "jsonp",
						async: false,
						success: function(data) {
							text = new Array();
							value = new Array();
							for (var index = 0; index < data.length; index++) {
								text[index] = data[index][textField];
								value[index] = data[index][valueField];
							}
						}
					});
				}
				for (var item = 0; item < value.length; item++) {
					var option = createTag("option");
					option.val(value[item]);
					option.text(text[item]);
					option.appendTo(select);
				}
				select.appendTo(control);
			} else if (this.layout[index].type == "SELECTOR") {
				var selector = createTag("div");
				selector.createSelector({
					url: this.layout[index].url
				});
				selector.appendTo(control);
			}
		}
	},
	reset: function() {
		$("input[type='text']").val("");
		$("option").attr("selected", false);
		$("option:first").attr("selected", true);
		$("label[class='validError']").empty();
		$("input").removeClass("validError");
	}
});

(function($) {
	$.fn.extend({
		createDialog: function(setting) {
			var defaults = {
				key: null,
				width: null,
				type: null,
				layout: null
			};
			var dialog = new Dialog($(this), $.extend(defaults, setting || {}));
			dialog.decorate();
		}
	});
})(jQuery);