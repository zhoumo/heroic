define([ "custom/base", "custom/selector", "require.text!template/dialog.tmpl" ], function(base, selector, dialogTmpl) {
	var layout = null;
	return {
		decorate : function(key) {
			var container = $("#" + key + "Container");
			container.append($(dialogTmpl));
			$("#dialogTmpl").tmpl({
				key : key
			}).appendTo(container);
			for ( var index = 0; index < layout.length; index++) {
				var control = base.createTag("div");
				control.addClass("controls");
				control.appendTo($("#modalBody"));
				var label = base.createTag("label");
				label.addClass("control-label");
				label.attr("style", "width: 80px; height: 50px");
				label.attr("for", "input");
				label.text(layout[index].title + "：");
				label.appendTo(control);
				if (layout[index].type == "INPUT") {
					var input = base.createTag("input");
					input.addClass("form-control");
					input.attr("id", layout[index].key);
					input.attr("name", layout[index].key);
					input.attr("style", "width:300px");
					input.attr("type", "text");
					input.attr("placeholder", "输入" + layout[index].title);
					input.appendTo(control);
				} else if (layout[index].type == "SELECT") {
					var select = base.createTag("select");
					select.addClass("form-control");
					select.attr("id", layout[index].key);
					select.attr("name", layout[index].key);
					select.attr("style", "width:150px");
					var text = layout[index].text;
					var value = layout[index].value;
					if (layout[index].url != null) {
						var textField = layout[index].text;
						var valueField = layout[index].value;
						$.ajax({
							type : "post",
							url : layout[index].url,
							dataType : "jsonp",
							async : false,
							success : function(data) {
								text = new Array();
								value = new Array();
								for ( var index = 0; index < data.length; index++) {
									text[index] = data[index][textField];
									value[index] = data[index][valueField];
								}
							}
						});
					}
					for ( var item = 0; item < value.length; item++) {
						var option = base.createTag("option");
						option.val(value[item]);
						option.text(text[item]);
						option.appendTo(select);
					}
					select.appendTo(control);
				} else if (layout[index].type == "SELECTOR") {
					selector.decorate(layout[index].url, control);
				}
			}
		},
		load : function(data) {
			layout = data.layout;
		}
	};
});