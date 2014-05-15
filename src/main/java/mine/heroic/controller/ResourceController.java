package mine.heroic.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import mine.heroic.common.controller.CrudController;
import mine.heroic.model.SysResource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/resource")
public class ResourceController extends CrudController<SysResource> {

	@Override
	protected void configure() {
		super.setIndex("resource");
	}

	@RequestMapping("/getMenus.do")
	protected void getMenus(String callback, HttpServletResponse response) {
		try {
			jsonpCallback(response, callback, baseService.findBy("type", SysResource.TYPE_MENU, SysResource.class));
		} catch (IOException e) {
			logger.error("Get menus failed", e);
		}
	}
}
