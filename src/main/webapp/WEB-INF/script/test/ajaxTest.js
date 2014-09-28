describe("test: util.js", function() {
	beforeEach(function(done) {
		require([ "jquery", "bootstrap.plugin" ], function() {
			done();
		});
	});
	it("method: getContextPath", function() {
		var custom = $.load("/heroic/user/getCustom.do", "json");
		expect(custom.table).toBeDefined();
		expect(custom.dialog).toBeDefined();
		expect(custom.validate).toBeDefined();
	});
});