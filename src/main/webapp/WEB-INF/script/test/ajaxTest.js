describe("Describe: ajax", function() {
	beforeEach(function(done) {
		require([ "bootstrap.plugin" ], function() {
			done();
		});
	});
	it("Goal: test [getCustom.do]", function() {
		var data = $.load("/heroic/user/getCustom.do", "json");
		expect(data.table).toBeDefined();
		expect(data.dialog).toBeDefined();
		expect(data.validate).toBeDefined();
	});
	it("Goal: test [pagination.do]", function() {
		var data = $.load("/heroic/user/pagination.do", "json");
		expect(data.pageNo).toBeDefined();
		expect(data.pageSize).toBeDefined();
		expect(data.totalPages).toBeDefined();
	});
});