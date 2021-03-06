package mine.heroic.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mine.heroic.common.controller.CrudController;
import mine.heroic.model.SysRole;
import mine.heroic.model.SysUser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
public class UserController extends CrudController<SysUser> {

	@Override
	protected void configure() {
		super.setIndex("user");
	}

	@Override
	protected void beforeSaveOrUpdate(SysUser sysUser, HttpServletRequest request) {
		List<SysRole> roles = this.baseService.createCollectionsByIds(new SysRole(), this.selectedIds);
		sysUser.setRoles(roles);
	}
}
