package mine.heroic;

import java.util.Date;
import java.util.List;

import mine.heroic.common.BaseDao;
import mine.heroic.model.SysUser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
public class BaseDaoTest {

	@Autowired
	public BaseDao<SysUser> userDao;

	@Test
	public void testSaveAndFind() {
		String name = String.valueOf(new Date().getTime());
		SysUser user = new SysUser();
		user.setName(name);
		userDao.saveOrUpdate(user);
		Assert.assertNotNull(user.getId());
		Integer size = userDao.find("from SysUser where name = ?", name).size();
		Assert.assertTrue(size == 1);
	}

	@Test
	public void testDeleteAndFindAll() {
		List<SysUser> userList = userDao.findAll(SysUser.class.getName());
		for (SysUser user : userList) {
			userDao.delete(user);
			Assert.assertNull(user.getId());
		}
	}
}
