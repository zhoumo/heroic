define([ "custom/ajax", "custom/selector", "bootstrap" ], function(ajax, selector) {
	return {
		setKey : function(obj, key) {
			obj.attr("id", key);
			obj.attr("name", key);
		},
		render : function(container, settings) {
			container.addClass("modal fade");
			container.attr("data-backdrop", "static");
			var dialog = $("<div class='modal-dialog' style='width:600px'></div>");
			var content = $("<div class='modal-content'></div>");
			var form = $("<form id='" + settings.key + "Form' action='save.do' method='post'></form>");
			var header = $("<div class='modal-header'><div class='modal-title'>操作面板</div><a class='close' data-dismiss='modal'>&times;</a></div>");
			var footer = $("<div class='modal-footer'><input class='btn btn-primary' type='submit' value='提交' /></div>");
			var body = $("<div class='modal-body'></div>");
			body.append($("<input id='id' name='id' type='hidden' />"));
			dialog.appendTo(container);
			dialog.append(content);
			content.append(header);
			content.append(form);
			form.append(body);
			form.append(footer);
			for ( var index = 0; index < settings.layout.length; index++) {
				var control = $("<div class='controls'></div>");
				control.appendTo(body);
				control.append($("<label class='control-label' style='width:80px;height:50px' for='input'>" + settings.layout[index].title + "：</label>"));
				if (settings.layout[index].type == "INPUT") {
					var input = $("<input placeholder='输入" + settings.layout[index].title + "' class='form-control' style='width:300px' type='text' />");
					this.setKey(input, settings.layout[index].key);
					input.appendTo(control);
				} else if (settings.layout[index].type == "SELECT") {
					var select = $("<select class='form-control' style='width:150px'></select>");
					this.setKey(select, settings.layout[index].key);
					var text = settings.layout[index].text;
					var value = settings.layout[index].value;
					if (settings.layout[index].url != "") {
						var textField = settings.layout[index].text;
						var valueField = settings.layout[index].value;
						var data = ajax.syncAjax(settings.layout[index].url, "jsonp");
						text = new Array(), value = new Array();
						for ( var item = 0; item < data.length; item++) {
							text[item] = data[item][textField];
							value[item] = data[item][valueField];
						}
					}
					for ( var item = 0; item < value.length; item++) {
						$("<option value='" + value[item] + "'>" + text[item] + "</option>").appendTo(select);
					}
					select.appendTo(control);
				} else if (settings.layout[index].type == "SELECTOR") {
					selector.render(settings.layout[index].url, control);
				}
			}
		}
	};
});