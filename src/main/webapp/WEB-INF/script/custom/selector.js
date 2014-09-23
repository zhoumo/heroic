define([ "custom/ajax" ], function(ajax) {
	var container = null;
	var selectedIds = null;
	function createItem(id, text) {
		var item = $("<div style='padding-top:5px;padding-left:80px'></div>");
		item.appendTo(container);
		var deleteButton = $("<button class='btn btn-primary btn-sm' type='button'></button>");
		deleteButton.click(function() {
			selectedIds.val(selectedIds.val().replace("|" + id + "|", "|"));
			item.remove();
		});
		deleteButton.appendTo(item);
		deleteButton.append($("<span class='glyphicon glyphicon-trash'></span>"));
		var display = $("<span style='padding-left:10px'>" + text + "</span>");
		display.appendTo(item);
	}
	return {
		render : function(url, container_) {
			container = container_;
			selectedIds = $("<input type='hidden' name='selectedIds' value='|' />");
			selectedIds.appendTo(container);
			var select = $("<select class='form-control' style='width:150px'></select>");
			var data = ajax.syncAjax(ctx + "/" + url, "jsonp");
			for ( var index = 0; index < data.length; index++) {
				$("<option value='" + data[index].id + "'>" + data[index].name + "</option>").appendTo(select);
			}
			select.change(function() {
				$(".repeatWarn").remove();
			});
			select.appendTo(container);
			var addButton = $("<button class='btn btn-primary' style='margin-left:20px' type='button'>添加</button>");
			addButton.appendTo(container);
			addButton.click(function() {
				$(".repeatWarn").remove();
				var id = select.val();
				if (selectedIds.val().indexOf("|" + id + "|") == -1) {
					selectedIds.val(selectedIds.val() + id + "|");
					createItem(id, select.find("option:selected").text());
				} else {
					$("<label class='validError repeatWarn'>不能重复添加</label>").insertAfter(addButton);
				}
			});
		}
	};
});