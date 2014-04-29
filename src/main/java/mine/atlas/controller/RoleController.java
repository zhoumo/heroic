package mine.atlas.controller;

import mine.atlas.model.SysRole;
import mine.mythos.controller.CrudController;

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
