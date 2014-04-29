module("user.js");
test("pagination",
function() {
	stop();
	$.getJSON("user/pagination.do",
	function(result) {
		equal(result.pageNo, 1);
		start();
	});
});