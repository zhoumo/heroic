define([ "custom/util", "custom/selector", "bootstrap" ], function(util, selector) {
	function buildDialog(container, key) {
		var dialog = $("<div class='modal-dialog' style='width:600px'></div>");
		var content = $("<div class='modal-content'></div>");
		var form = $("<form id='" + key + "Form' action='save.do' method='post'></form>");
		var body = $("<div class='modal-body'></div>");
		body.append($("<input id='id' name='id' type='hidden' />"));
		dialog.appendTo(container);
		dialog.append(content);
		content.append($("<div class='modal-header'><div class='modal-title'>操作面板</div><a class='close' data-dismiss='modal'>&times;</a></div>"));
		content.append(form);
		form.append(body);
		form.append($("<div class='modal-footer'><input class='btn btn-primary' type='submit' value='提交' /></div>"));
		return body;
	}
	return {
		setKey : function(obj, key) {
			obj.attr("id", key);
			obj.attr("name", key);
		},
		render : function(container, settings) {
			container.addClass("modal fade");
			container.attr("data-backdrop", "static");
			var body = buildDialog(container, settings.key);
			for ( var index = 0; index < settings.dialog.length; index++) {
				var control = $("<div class='controls'></div>");
				control.appendTo(body);
				control.append($("<label class='control-label' style='width:80px;height:50px' for='input'>" + util.locale(settings.dialog[index].title, settings.key) + "：</label>"));
				if (settings.dialog[index].type == "INPUT") {
					var input = $("<input placeholder='输入" + settings.dialog[index].title + "' class='form-control' style='width:300px' type='text' />");
					this.setKey(input, settings.dialog[index].title);
					input.appendTo(control);
				} else if (settings.dialog[index].type == "SELECT") {
					var select = $("<select class='form-control' style='width:150px'></select>");
					this.setKey(select, settings.dialog[index].title);
					var text = settings.dialog[index].text;
					var value = settings.dialog[index].value;
					if (settings.dialog[index].url != "") {
						var textField = settings.dialog[index].text;
						var valueField = settings.dialog[index].value;
						var data = util.syncAjax(settings.dialog[index].url, "jsonp");
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
				} else if (settings.dialog[index].type == "SELECTOR") {
					selector.render(settings.dialog[index].url, control);
				}
			}
		}
	};
});