define([ "jquery" ], function($) {
	return {
		remove : {
			icon : "glyphicon glyphicon-trash",
			event : function(record) {
				if (!confirm("是否删除?")) {
					return;
				}
				$.ajax({
					type : "post",
					url : "delete.do?id=" + record.id,
					success : function() {
						location.reload();
					},
					error : function(request) {
						if (request.status == 405) {
							alert("存在关联，不能删除！");
						}
					}
				});
			}
		},
		update : {
			icon : "glyphicon glyphicon-list-alt",
			event : function(record) {
				$("[class=modal-dialog]").parent().modal();
				for ( var field in record) {
					$("#" + field).val(record[field]);
				}
			}
		}
	};
});