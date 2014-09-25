package mine.heroic;

import java.util.Date;
import java.util.List;

import mine.heroic.common.BaseDao;
import mine.heroic.model.SysUser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring/applicationContext.xml")
public class baseDaoTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public BaseDao<SysUser> userDao;

	@Test
	public void testSaveAndFind() {
		String name = String.valueOf(new Date().getTime());
		SysUser user = new SysUser();
		user.setName(name);
		userDao.saveOrUpdate(user);
		logger.info("save user[id=" + user.getId() + "]");
		Assert.assertNotNull(user.getId());
		Integer size = userDao.find("from SysUser where name = ?", name).size();
		logger.info("find user size : " + size);
		Assert.assertTrue(size == 1);
	}

	@Test
	public void testDeleteAndFindAll() {
		List<SysUser> userList = userDao.findAll(SysUser.class.getName());
		logger.info("find all user size : " + userList.size());
		for (SysUser user : userList) {
			logger.info("delete user[id=" + user.getId() + "]");
			userDao.delete(user);
			Assert.assertNull(user.getId());
		}
	}
}
