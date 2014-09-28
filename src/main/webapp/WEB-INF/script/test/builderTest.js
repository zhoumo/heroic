describe("test: builder.js", function() {
	var tester = null;
	beforeEach(function(done) {
		require([ "custom/builder" ], function(builder) {
			tester = builder;
			done();
		});
	});
	it("method: createToolbar", function() {
		expect(tester.createToolbar({})).toBeDefined();
	});
});