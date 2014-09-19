describe("test: base.js", function() {
	var base = null;
	beforeEach(function(done) {
		require([ 'base' ], function(b) {
			base = b;
			done();
		});
	});
	it("method: getContextPath", function() {
		expect(base.getContextPath()).toBe("/heroic");
	});
});