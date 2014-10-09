package mine.heroic;

import mine.heroic.common.controller.CrudController;
import mine.heroic.common.service.BaseService;
import mine.heroic.controller.UserController;
import mine.heroic.model.SysUser;

import org.apache.struts.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CrudController.class)
@PowerMockIgnore("org.apache.log4j.*")
public class CrudControllerTest {

	private CrudController<SysUser> userController;

	private SysUser sysUser;

	@Before
	@SuppressWarnings("unchecked")
	public void before() throws Exception {
		sysUser = new SysUser();
		userController = new UserController();
		userController.setBaseService(PowerMockito.mock(BaseService.class));
	}

	@Test
	public void testSaveOrUpdate() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		Assert.assertEquals("redirect:index.do", userController.saveOrUpdate(sysUser, request));
	}
}
