define([ "custom/base", "require.text!template/datatable.tmpl" ], function(base, datatableTmpl) {
	var key = null;
	var datatable = null;
	var actions = [ {
		icon : "glyphicon glyphicon-trash",
		event : function(record) {
			if (!confirm("是否删除?")) {
				return;
			}
			$.ajax({
				type : "post",
				url : "delete.do",
				data : {
					id : record.id
				},
				success : function() {
					location.reload();
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					if (XMLHttpRequest.status == 405) {
						alert("存在关联，不能删除！");
					}
				}
			});
		}
	}, {
		icon : "glyphicon glyphicon-list-alt",
		event : function(record) {
			$("#" + key + "Dialog").modal();
			for ( var field in record) {
				$("#" + field).val(record[field]);
			}
		}
	} ];
	return {
		render : function(container, params) {
			var records = this.load();
			container.append(datatableTmpl);
			key = params.key;
			$("#datatableTmpl").tmpl({
				key : key,
				titles : params.titles,
				records : records
			}).appendTo(container);
			datatable = $("#" + key + "Datatable");
			for ( var row = 0; row < records.result.length; row++) {
				var dataRow = base.createTag("tr");
				dataRow.attr("index", row);
				dataRow.appendTo(datatable);
				for ( var columnIndex = 0; columnIndex < params.keys.length; columnIndex++) {
					var column = base.createTag("td");
					var value = records.result[row][params.keys[columnIndex]];
					if (value instanceof Object) {
						var text = "";
						for ( var number = 0; number < value.length; number++) {
							text += value[number].name + ", ";
						}
						column.text(text.trim());
					} else {
						column.text(value);
					}
					column.appendTo(dataRow);
				}
			}
			this.registerActions(records);
		},
		load : function() {
			return base.syncAjax("pagination.do", "json");
		},
		registerActions : function(records) {
			$("<th>操作</th>").insertAfter(datatable.find("tr th:last"));
			datatable.find("tr").find("td:last").each(function() {
				var td = base.createTag("td");
				td.insertAfter($(this));
				for ( var index = 0; index < actions.length; index++) {
					var button = base.createTag("span", td);
					button.addClass(actions[index].icon);
					button.attr("index", index);
					button.click(function() {
						var index = $(this).parent().parent().attr("index");
						actions[$(this).attr("index")].event.call(this, records.result[index]);
					});
				}
			});
		}
	};
});