describe("Describe: ajax", function() {
	beforeEach(function(done) {
		require([ "jquery", "bootstrap.plugin" ], function() {
			done();
		});
	});
	it("Goal: test [getCustom.do]", function() {
		var custom = $.load("/heroic/user/getCustom.do", "json");
		expect(custom.table).toBeDefined();
		expect(custom.dialog).toBeDefined();
		expect(custom.validate).toBeDefined();
	});
});