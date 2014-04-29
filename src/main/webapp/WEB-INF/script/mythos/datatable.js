DataTable = Base.extend({
	datatable: null,
	pageNo: null,
	key: null,
	url: null,
	keys: null,
	titles: null,
	actions: null,
	startIndex: 1,
	endIndex: 1,
	constructor: function(datatable, pageNo, settings) {
		this.datatable = datatable;
		this.pageNo = (pageNo == null ? 1 : pageNo);
		this.key = settings.key;
		this.url = settings.url;
		this.keys = settings.config.keys;
		this.titles = settings.config.titles;
		this.actions = settings.actions;
	},
	load: function() {
		var _this = this;
		$.ajax({
			type: "post",
			url: _this.url + "?pageNo=" + _this.pageNo,
			dataType: "json",
			success: function(data) {
				var page = eval(data);
				_this.decorate(page);
				_this.registerEvents(page);
			},
			error: function() {
				alert("Loading Failed");
			}
		});
	},
	refresh: function(pageNo) {
		this.pageNo = pageNo;
		this.load();
	},
	computeIndex: function(page) {
		this.startIndex = 1;
		this.endIndex = page.totalPages;
		if ((page.pageNo - 2) > 0 && (page.pageNo + 2) <= page.totalPages) {
			this.startIndex = page.pageNo - 2;
			this.endIndex = page.pageNo + 2;
		} else if ((page.pageNo - 2) < 1) {
			this.startIndex = 1;
			if (page.totalPages <= 5) {
				this.endIndex = page.totalPages;
			} else {
				this.endIndex = 5;
			}
		} else if ((page.pageNo + 2) > page.totalPages) {
			this.endIndex = page.totalPages;
			if (page.totalPages > 5) {
				this.startIndex = page.totalPages - 4;
			} else {
				this.startIndex = 1;
			}
		}
	},
	decorate: function(page) {
		var _this = this;
		this.datatable.addClass("table table-striped");
		var titleRow = createTag("tr");
		titleRow.appendTo(this.datatable);
		for (var index = 0; index < this.titles.length; index++) {
			var header = createTag("th");
			header.text(this.titles[index]);
			header.appendTo(titleRow);
		}
		for (var row = 0; row < page.result.length; row++) {
			var dataRow = createTag("tr");
			dataRow.attr("index", row);
			dataRow.appendTo(this.datatable);
			for (var columnIndex = 0; columnIndex < this.keys.length; columnIndex++) {
				var column = createTag("td");
				var value = page.result[row][this.keys[columnIndex]];
				if (value instanceof Object) {
					var text = "";
					for (var number = 0; number < value.length; number++) {
						text += value[number].name + ", ";
					}
					column.text(text.removeLastChar(text.trim()));
				} else {
					column.text(value);
				}
				column.appendTo(dataRow);
			}
		}
		var ul = createTag("div");
		ul.addClass("pagination");
		ul.appendTo(this.datatable.parent());
		ul.append("<li><span>共" + page.totalCount + "条记录</span></li>");
		var firstItem = $("<li " + (page.pageNo == 1 ? "class='disabled'": '') + "></li>");
		firstItem.appendTo(ul);
		var firstLink = $("<a href='#'>首页</a>");
		firstLink.appendTo(firstItem);
		firstLink.click(function() {
			_this.refresh(1);
		});
		var previousItem = $("<li " + (page.pageNo == 1 ? "class='disabled'": '') + "></li>");
		previousItem.appendTo(ul);
		var previousLink = $("<a href='#'>上一页</a>");
		previousLink.appendTo(previousItem);
		previousLink.click(function() {
			if (page.pageNo != 1) {
				_this.refresh(page.pageNo - 1);
			}
		});
		this.computeIndex(page);
		for (var index = this.startIndex; index <= this.endIndex; index++) {
			var indexItem = createTag("li");
			indexLink = $("<a href='#'>" + index + "</a>");
			indexLink.click(function() {
				_this.refresh($(this).text());
			});
			if (page.pageNo == index) {
				indexItem.addClass("active");
			} else {
				indexItem.removeClass("active");
			}
		}
		var nextItem = $("<li " + (page.pageNo == page.totalPages ? "class='disabled'": '') + "></li>");
		nextItem.appendTo(ul);
		var nextLink = $("<a href='#'>下一页</a>");
		nextLink.appendTo(nextItem);
		nextLink.click(function() {
			if (page.pageNo != page.totalPages) {
				_this.refresh(page.pageNo + 1);
			}
		});
		var lastItem = $("<li " + (page.pageNo == page.totalPages ? "class='disabled'": '') + "></li>");
		lastItem.appendTo(ul);
		var lastLink = $("<a href='#'>末页</a>");
		lastLink.appendTo(lastItem);
		lastLink.click(function() {
			_this.refresh(page.totalPages);
		});
	},
	registerEvents: function(page) {
		if (this.actions.length == 0) {
			return
		}
		var _this = this;
		$("<th>操作</th>").insertAfter(this.datatable.find("tr th:last"));
		this.datatable.find("tr").find("td:last").each(function() {
			var td = createTag("td");
			td.insertAfter($(this));
			for (var index = 0; index < _this.actions.length; index++) {
				var button = createTag("span");
				button.addClass(_this.actions[index].icon);
				button.attr("index", index);
				button.click(function() {
					var index = $(this).parent().parent().attr("index");
					_this.actions[$(this).attr("index")].event.call(_this, page.result[index]);
				});
				button.appendTo(td);
			}
		});
	}
});

(function($) {
	$.fn.extend({
		datatable: function(setting) {
			var defaults = {
				key: null,
				url: null,
				config: null,
				actions: null
			};
			var dataTable = new DataTable($(this), 1, $.extend(defaults, setting || {}));
			dataTable.load();
		}
	});
})(jQuery);