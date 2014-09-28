define([ "jquery.validate" ], function() {
	function jsonCheckUnique(field) {
		return {
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
	}
	return {
		bind : function(settings) {
			var _this = this;
			$("#" + settings.key + "Form").validate({
				submitHandler : function(form) {
					form.submit();
				},
				messages : _this.validateMessages(settings.validate),
				rules : _this.validateRules(settings.validate)
			});
		},
		validateRules : function validateRules(settings) {
			var rules = new Object();
			for ( var index = 0; index < settings.length; index++) {
				rules[settings[index].key] = settings[index].constraints;
				if (rules[settings[index].key].unique) {
					rules[settings[index].key].remote = jsonCheckUnique(settings[index].key);
				}
				if (rules[settings[index].key].maxlength == 0) {
					delete rules[settings[index].key].maxlength;
				}
				if (rules[settings[index].key].minlength == 0) {
					delete rules[settings[index].key].minlength;
				}
				delete rules[settings[index].key].unique;
			}
			return rules;
		},
		validateMessages : function(settings) {
			var messages = new Object();
			for ( var index = 0; index < settings.length; index++) {
				if (settings[index].constraints.unique) {
					messages[settings[index].key] = jQuery.parseJSON('{"remote":"该值必须唯一"}');
				}
			}
			return messages;
		}
	};
});