define([ "custom/base", "jquery.validate" ], function(base, validate) {
	function validateRules(params) {
		var rules = new Object();
		for ( var index = 0; index < params.length; index++) {
			rules[params[index].key] = params[index].constraints;
			if (rules[params[index].key].unique) {
				// rules[params[index].key].remote = jsonCheckUnique(params[index].key);
			}
			if (rules[params[index].key].maxlength == 0) {
				delete rules[params[index].key].maxlength;
			}
			if (rules[params[index].key].minlength == 0) {
				delete rules[params[index].key].minlength;
			}
			delete rules[params[index].key].unique;
		}
		return rules;
	}
	function validateMessages(params) {
		var messages = new Object();
		for ( var index = 0; index < params.length; index++) {
			if (params[index].constraints.unique) {
				messages[params[index].key] = jQuery.parseJSON('{"remote":"该值必须唯一"}');
			}
		}
		return messages;
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
	return {
		register : function(key, params) {
			$("#" + key + "Form").validate({
				submitHandler : function(form) {
					form.submit();
				},
				messages : validateMessages(params),
				rules : validateRules(params)
			});
		}
	};
});