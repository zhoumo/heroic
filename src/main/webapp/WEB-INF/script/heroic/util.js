String.prototype.removeLastChar = function(target) {
	if (target.length == 0) {
		return "";
	}
	return target.substring(0, target.length - 1);
};
function getContextPath() {
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
	return result;
}
function createTag(tag) {
	return $("<" + tag + "></" + tag + ">");
}
function buildCustom(key, title, toolbar) {
	$.ajax({
		type : "post",
		url : "getCustom.do",
		dataType : "json",
		success : function(data) {
			$("#" + key + "Table").datatable({
				key : key,
				url : "pagination.do",
				config : data.dataTable,
				actions : [ {
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
						$("#" + this.key + "Dialog").modal();
						for ( var field in record) {
							$("#" + field).val(record[field]);
						}
					}
				} ]
			});
			$("#" + key + "Dialog").createDialog({
				key : data.dialog.key,
				type : "custom",
				layout : data.dialog.layout
			});
			$("#" + key + "Form").validate({
				submitHandler : function(form) {
					form.submit();
				},
				messages : validateMessages(data.validates),
				rules : validateRules(data.validates)
			});
		},
		error : function() {
			alert("Get custom failed.");
		}
	});
}
function validateRules(validates) {
	var rules = new Object();
	for ( var index = 0; index < validates.length; index++) {
		rules[validates[index].key] = validates[index].constraints;
		if (rules[validates[index].key].unique) {
			rules[validates[index].key].remote = jsonCheckUnique(validates[index].key);
		}
		if (rules[validates[index].key].maxlength == 0) {
			delete rules[validates[index].key].maxlength;
		}
		if (rules[validates[index].key].minlength == 0) {
			delete rules[validates[index].key].minlength;
		}
		delete rules[validates[index].key].unique;
	}
	return rules;
}
function validateMessages(validates) {
	var messages = new Object();
	for ( var index = 0; index < validates.length; index++) {
		if (validates[index].constraints.unique) {
			messages[validates[index].key] = jQuery.parseJSON('{"remote":"该值必须唯一"}');
		}
	}
	return messages;
}
function addButton(config) {
	var button = createTag("button");
	button.addClass("btn btn-primary");
	button.attr("style", "width: 100%");
	button.text(config.text);
	button.click(function() {
		if (typeof config.callback == 'function') {
			config.callback.call(button);
		}
	});
	button.appendTo(config.parent);
}
function jsonCheckUnique(field) {
	var jsonCheckUnique = {
		type : "POST",
		url : "checkUnique.do",
		data : {
			field : field,
			value : function() {
				return $("#" + field).val().trim();
			},
			id : function() {
				return $("#id").val();
			}
		}
	};
	return jsonCheckUnique;
}