package mine.heroic.controller;

import mine.heroic.common.controller.CrudController;
import mine.heroic.model.SysRole;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/role")
public class RoleController extends CrudController<SysRole> {

	@Override
	protected void configure() {
		super.setIndex("role");
	}
}
