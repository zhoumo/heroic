(function($) {
	function getContextPath() {
		var pathName = document.location.pathname;
		return pathName.substr(0, pathName.substr(1).indexOf("/") + 1);
	}
	function load(url, dataType) {
		var result = null;
		$.ajax({
			url : url,
			type : "post",
			dataType : dataType == null ? "json" : dataType,
			async : false,
			success : function(data) {
				result = eval(data);
			},
			error : function() {
				throw "failed to load.";
			}
		});
		return result;
	}
	$.extend({
		load : load,
		getContextPath : getContextPath
	});
	$.fn.extend({
		navbar : function(settings) {
			var ctx = getContextPath();
			$(this).addClass("navbar navbar-inverse");
			$(this).append($("<div class='navbar-collapse collapse navbar-collapse-01'></div>"));
			$(this).append($("<ul class='nav navbar-nav navbar-left'><li><a href='" + ctx + "'>首页</a></li></ul>"));
			$(this).append($("<ul class='nav navbar-nav navbar-right'><li><a href='" + ctx + settings.logoutUrl + "'>退出</a></li></ul>"));
			var resources = load(ctx + settings.dataUrl, settings.dataType);
			if (resources.length > 0) {
				createNav(0, resources, $(this).find(".nav:first"));
			}
		},
		table : function(settings) {
			var records = load(settings.url + settings.pageNo);
			buildTable($(this), settings, records);
			buildPagination($(this), settings, records);
		},
		dialog : function(settings) {
			$(this).addClass("modal fade");
			$(this).attr("data-backdrop", "static");
			var dialog = $("<div class='modal-dialog' style='width:600px'></div>");
			var content = $("<div class='modal-content'></div>");
			var form = $("<form id='" + settings.key + "Form' action='save.do' method='post'></form>");
			var body = $("<div class='modal-body'></div>");
			body.append($("<input id='id' name='id' type='hidden' />"));
			dialog.appendTo($(this));
			dialog.append(content);
			content.append($("<div class='modal-header'><div class='modal-title'>操作面板</div><a class='close' data-dismiss='modal'>&times;</a></div>"));
			content.append(form);
			form.append(body);
			form.append($("<div class='modal-footer'><input class='btn btn-primary' type='submit' value='提交' /></div>"));
			buildDialog(body, settings);
		},
		selector : function(settings) {
			var selectedIds = $("<input type='hidden' name='selectedIds' value=' ' />");
			selectedIds.appendTo($(this));
			var select = $("<select class='form-control' style='width:150px'></select>");
			var data = load(settings.url, "jsonp");
			for ( var index = 0; index < data.length; index++) {
				$("<option value='" + data[index].id + "'>" + data[index].name + "</option>").appendTo(select);
			}
			select.change(function() {
				$(".repeatWarn").remove();
			});
			select.appendTo($(this));
			var addButton = $("<button class='btn btn-primary' style='margin-left:20px' type='button'>添加</button>");
			addButton.appendTo($(this));
			addButton.click(function() {
				$(".repeatWarn").remove();
				var id = select.val();
				if (selectedIds.val().indexOf(" " + id + " ") == -1) {
					selectedIds.val(selectedIds.val() + id + " ");
					var item = $("<div style='padding-top:5px;padding-left:80px'></div>");
					var deleteButton = $("<button class='btn btn-primary btn-sm' type='button'></button>");
					deleteButton.click(function() {
						selectedIds.val(selectedIds.val().replace(" " + id + " ", " "));
						item.remove();
					});
					deleteButton.append($("<span class='glyphicon glyphicon-trash'></span>"));
					item.append(deleteButton);
					item.append($("<span style='padding-left:10px'>" + select.find("option:selected").text() + "</span>"));
					$(this).parent().append(item);
				} else {
					$("<label class='validError repeatWarn'>不能重复添加</label>").insertAfter(addButton);
				}
			});
		}
	});
	function createNav(id, resources, nav) {
		for ( var index = 0; index < resources.length; index++) {
			var resource = resources[index];
			if (resource.parentId != id) {
				continue;
			}
			var li = $("<li><a href='" + getContextPath() + resource.url + "'>" + resource.name + "</a></li>");
			li.appendTo(nav);
			var ul = $("<ul></ul>");
			ul.appendTo(li);
			createNav(resource.id, resources, ul);
		}
	}
	function buildTable(table, settings, records) {
		table.empty();
		table.append($("<table class='table table-striped'><tr></tr></table>"));
		for ( var index = 0; index < settings.custom.table.length; index++) {
			$("<th key='" + settings.custom.key + "'>" + settings.custom.table[index].title + "</th>").appendTo(table.find("table tr"));
		}
		for ( var row = 0; row < records.result.length; row++) {
			var dataRow = $("<tr index=" + row + "></tr>");
			dataRow.appendTo(table.find("table"));
			for ( var columnIndex = 0; columnIndex < settings.custom.table.length; columnIndex++) {
				var value = records.result[row][settings.custom.table[columnIndex].title];
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
		$("<th>操作</th>").insertAfter(table.find("tr th:last"));
		table.find("tr").find("td:last").each(function() {
			var td = $("<td></td>");
			td.insertAfter($(this));
			for ( var index = 0; index < settings.action.length; index++) {
				var button = $("<span class='" + settings.action[index].icon + "' index='" + index + "'></span>");
				button.appendTo(td);
				button.click(function() {
					var index = $(this).parent().parent().attr("index");
					settings.action[$(this).attr("index")].event.call(this, records.result[index]);
				});
			}
		});
	}
	function pageEvent(table, settings, type, pageNo) {
		var pageLink = $("a[type=" + type + "]");
		pageLink.click(function() {
			table.table({
				url : settings.url,
				pageNo : pageNo == null ? $(this).text() : pageNo,
				custom : settings.custom,
				action : settings.action
			});
		});
		return pageLink;
	}
	function computeStart(records) {
		var start = 1;
		if ((records.pageNo - 2) > 0 && (records.pageNo + 2) <= records.totalPages) {
			start = records.pageNo - 2;
		} else if ((records.pageNo - 2) < 1) {
			start = 1;
		} else if ((records.pageNo + 2) > records.totalPages) {
			records.totalPages > 5 ? start = records.totalPages - 4 : start = 1;
		}
		return start;
	}
	function computeEnd(records) {
		var end = records.totalPages;
		if ((records.pageNo - 2) > 0 && (records.pageNo + 2) <= records.totalPages) {
			end = records.pageNo + 2;
		} else if ((records.pageNo - 2) < 1) {
			records.totalPages <= 5 ? end = records.totalPages : end = 5;
		} else if ((records.pageNo + 2) > records.totalPages) {
			end = records.totalPages;
		}
		return end;
	}
	function buildPagination(table, settings, records) {
		var start = computeStart(records);
		var end = computeEnd(records);
		var pagination = "<li><span>共" + records.totalCount + "条记录</span></li>";
		pagination += "<li " + (records.pageNo == 1 ? "class='disabled'" : '') + "><a href='#' type='first'>首页</a></li>";
		pagination += "<li " + (records.pageNo == 1 ? "class='disabled'" : '') + "><a href='#' type='previous'>上一页</a></li>";
		for ( var index = start; index <= end; index++) {
			pagination += "<li><a href='#' type='page" + index + "'>" + index + "</a></li>";
		}
		pagination += "<li " + (records.pageNo == records.totalPages ? "class='disabled'" : '') + "><a href='#' type='next'>下一页</a></li>";
		pagination += "<li " + (records.pageNo == records.totalPages ? "class='disabled'" : '') + "><a href='#' type='last'>末页</a></li>";
		$("<div class='pagination'>" + pagination + "</div>").appendTo(table);
		pageEvent(table, settings, "first", 1);
		pageEvent(table, settings, "previous", records.pageNo - 1);
		pageEvent(table, settings, "next", records.pageNo + 1);
		pageEvent(table, settings, "last", records.totalPages);
		for ( var index = start; index <= end; index++) {
			var pageLink = pageEvent(table, settings, "page" + index);
			records.pageNo == index ? pageLink.parent().addClass("active") : pageLink.parent().removeClass("active");
		}
	}
	function buildDialog(body, settings) {
		for ( var index = 0; index < settings.dialog.length; index++) {
			var control = $("<div class='controls'></div>");
			control.appendTo(body);
			control.append($("<label key='" + settings.key + "' class='control-label' style='width:80px;height:40px' for='input'>" + settings.dialog[index].title + "</label>"));
			if (settings.dialog[index].type == "INPUT") {
				var input = $("<input placeholder='输入" + settings.dialog[index].title + "' class='form-control' style='width:300px' type='text' />");
				input.attr("id", settings.dialog[index].title);
				input.attr("name", settings.dialog[index].title);
				input.appendTo(control);
			} else if (settings.dialog[index].type == "SELECT") {
				var select = $("<select class='form-control' style='width:150px'></select>");
				select.attr("id", settings.dialog[index].title);
				select.attr("name", settings.dialog[index].title);
				var text = settings.dialog[index].text;
				var value = settings.dialog[index].value;
				if (settings.dialog[index].url != "") {
					var textField = settings.dialog[index].text;
					var valueField = settings.dialog[index].value;
					var data = load(settings.dialog[index].url, "jsonp");
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
				control.selector({
					url : getContextPath() + "/" + settings.dialog[index].url
				});
			}
		}
	}
})(jQuery);