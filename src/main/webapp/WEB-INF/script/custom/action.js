define(function() {
	function deleteSuccess() {
		location.reload();
	}
	function deleteError(request) {
		if (request.status == 405) {
			alert("存在关联，不能删除！");
		}
	}
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
					success : deleteSuccess,
					error : deleteError
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