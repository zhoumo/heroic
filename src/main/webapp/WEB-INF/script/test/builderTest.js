describe("Describe: builder", function() {
	var tester = null;
	beforeEach(function(done) {
		require([ "custom/builder" ], function(builder) {
			tester = builder;
			tester.build("user", "用户", "user/");
			done();
		});
	});
	it("Goal: method [build] to have been called", function() {
		spyOn(tester, "build");
		tester.build("user", "用户", "user/");
		expect(tester.build).toHaveBeenCalled();
	});
	it("Goal: [settings] to be defined", function() {
		expect(tester.getSettings()).toBeDefined();
	});
	it("Goal: [settings.key] to be 'user'", function() {
		expect(tester.getSettings().key).toBe("user");
	});
	it("Goal: method [createToolbar] works", function() {
		setFixtures('<div type="toolbar" />');
		tester.createToolbar();
		expect($("[type=toolbar]").text()).toBe("新建用户");
	});
});