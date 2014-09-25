describe("test: util.js", function() {
	var tester = null;
	beforeEach(function(done) {
		require([ "custom/util" ], function(util) {
			tester = util;
			done();
		});
	});
	it("method: getContextPath", function() {
		expect(tester.getContextPath()).toBe("/heroic");
	});
	it("method: syncAjax", function() {
		var custom = tester.syncAjax("/heroic/user/getCustom.do", "json");
		expect(custom.dataTable).toBeDefined();
		expect(custom.dialog).toBeDefined();
		expect(custom.validates).toBeDefined();
	});
});