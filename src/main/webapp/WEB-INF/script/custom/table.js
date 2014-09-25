define([ "custom/util", "custom/action" ], function(util, action) {
	var actions = [ action.remove, action.update ];
	function compute(records) {
		var start = 1;
		var end = records.totalPages;
		if ((records.pageNo - 2) > 0 && (records.pageNo + 2) <= records.totalPages) {
			start = records.pageNo - 2;
			end = records.pageNo + 2;
		} else if ((records.pageNo - 2) < 1) {
			start = 1;
			records.totalPages <= 5 ? end = records.totalPages : end = 5;
		} else if ((records.pageNo + 2) > records.totalPages) {
			end = records.totalPages;
			records.totalPages > 5 ? start = records.totalPages - 4 : start = 1;
		}
		return [ start, end ];
	}
	return {
		load : function(pageNo) {
			return util.syncAjax("pagination.do?pageNo=" + pageNo, "json");
		},
		render : function(container, pageNo, settings) {
			var records = this.load(pageNo);
			container.empty();
			this.buildTable(container, settings, records);
			var result = compute(records);
			this.buildPagination(container, pageNo, settings, records, result[0], result[1]);
			this.registerActions(records, container);
		},
		buildTable : function(container, settings, records) {
			container.append($("<table class='table table-striped'><tr></tr></table>"));
			for ( var index = 0; index < settings.table.length; index++) {
				$("<th>" + util.locale(settings.table[index].title, settings.key) + "</th>").appendTo(container.find("table tr"));
			}
			for ( var row = 0; row < records.result.length; row++) {
				var dataRow = $("<tr index=" + row + "></tr>");
				dataRow.appendTo(container.find("table"));
				for ( var columnIndex = 0; columnIndex < settings.table.length; columnIndex++) {
					var value = records.result[row][settings.table[columnIndex].title];
					if (value instanceof Object) {
						var text = "";
						for ( var number = 0; number < value.length; number++) {
							text += value[number].name + " ";
						}
						value = text.trim();
					}
					dataRow.append("<td>" + value + "</td>");
				}
			}
		},
		buildPagination : function(container, pageNo, settings, records, start, end) {
			var pagination = "<li><span>共" + records.totalCount + "条记录</span></li>";
			pagination += "<li " + (records.pageNo == 1 ? "class='disabled'" : '') + "><a href='#' type='first'>首页</a></li>";
			pagination += "<li " + (records.pageNo == 1 ? "class='disabled'" : '') + "><a href='#' type='previous'>上一页</a></li>";
			for ( var index = start; index <= end; index++) {
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
			for ( var index = start; index <= end; index++) {
				var pageLink = $("a[type=page" + index + "]");
				pageLink.click(function() {
					_this.render(container, $(this).text(), settings);
				});
				records.pageNo == index ? pageLink.parent().addClass("active") : pageLink.parent().removeClass("active");
			}
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