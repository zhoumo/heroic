define([ "custom/ajax" ], function(ajax) {
	var key = null;
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
	function compute(records) {
		var start = 1;
		var end = records.totalPages;
		if ((records.pageNo - 2) > 0 && (records.pageNo + 2) <= records.totalPages) {
			start = records.pageNo - 2;
			end = records.pageNo + 2;
		} else if ((records.pageNo - 2) < 1) {
			start = 1;
			if (records.totalPages <= 5) {
				end = records.totalPages;
			} else {
				end = 5;
			}
		} else if ((records.pageNo + 2) > records.totalPages) {
			end = records.totalPages;
			if (records.totalPages > 5) {
				start = records.totalPages - 4;
			} else {
				start = 1;
			}
		}
		return [ start, end ];
	}
	return {
		load : function(pageNo) {
			return ajax.syncAjax("pagination.do?pageNo=" + pageNo, "json");
		},
		render : function(container, pageNo, settings) {
			key = settings.key;
			var records = this.load(pageNo);
			container.empty();
			container.append($("<table class='table table-striped'><tr></tr></table>"));
			for ( var index = 0; index < settings.titles.length; index++) {
				var header = $("<th>" + settings.titles[index] + "</th>");
				header.appendTo(container.find("table tr"));
			}
			for ( var row = 0; row < records.result.length; row++) {
				var dataRow = $("<tr index=" + row + "></tr>");
				dataRow.appendTo(container.find("table"));
				for ( var columnIndex = 0; columnIndex < settings.keys.length; columnIndex++) {
					var column = $("<td></td>");
					var value = records.result[row][settings.keys[columnIndex]];
					if (value instanceof Object) {
						var text = "";
						for ( var number = 0; number < value.length; number++) {
							text += value[number].name + " ";
						}
						column.text(text.trim());
					} else {
						column.text(value);
					}
					column.appendTo(dataRow);
				}
			}
			var result = compute(records);
			var pagination = "<li><span>共" + records.totalCount + "条记录</span></li>";
			pagination += "<li " + (records.pageNo == 1 ? "class='disabled'" : '') + "><a href='#' type='first'>首页</a></li>";
			pagination += "<li " + (records.pageNo == 1 ? "class='disabled'" : '') + "><a href='#' type='previous'>上一页</a></li>";
			for ( var index = result[0]; index <= result[1]; index++) {
				pagination += "<li><a href='#' type='page" + index + "'>" + index + "</a></li>";
			}
			pagination += "<li " + (records.pageNo == records.totalPages ? "class='disabled'" : '') + "><a href='#' type='next'>下一页</a></li>";
			pagination += "<li " + (records.pageNo == records.totalPages ? "class='disabled'" : '') + "><a href='#' type='last'>末页</a></li>";
			$("<div class='pagination'>" + pagination + "</div>").appendTo(container);
			var _this = this;
			$("a[type=first]").click(function() {
				_this.render(container, 1, settings);
			});
			$("a[type=previous]").click(function() {
				if (records.pageNo != 1) {
					_this.render(container, records.pageNo - 1, settings);
				}
			});
			$("a[type=next]").click(function() {
				if (records.pageNo != records.totalPages) {
					_this.render(container, records.pageNo + 1, settings);
				}
			});
			$("a[type=last]").click(function() {
				_this.render(container, records.totalPages, settings);
			});
			for ( var index = result[0]; index <= result[1]; index++) {
				var pageLink = $("a[type=page" + index + "]");
				pageLink.click(function() {
					_this.render(container, $(this).text(), settings);
				});
				if (records.pageNo == index) {
					pageLink.parent().addClass("active");
				} else {
					pageLink.parent().removeClass("active");
				}
			}
			this.registerActions(records, container);
		},
		registerActions : function(records, container) {
			$("<th>操作</th>").insertAfter(container.find("tr th:last"));
			container.find("tr").find("td:last").each(function() {
				var td = $("<td></td>");
				td.insertAfter($(this));
				for ( var index = 0; index < actions.length; index++) {
					var button = $("<span class='" + actions[index].icon + "' index='" + index + "'></span>");
					button.appendTo(td);
					button.click(function() {
						var index = $(this).parent().parent().attr("index");
						actions[$(this).attr("index")].event.call(this, records.result[index]);
					});
				}
			});
		}
	};
});