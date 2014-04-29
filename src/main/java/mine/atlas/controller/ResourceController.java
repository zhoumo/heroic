package mine.atlas.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import mine.atlas.model.SysResource;
import mine.mythos.controller.CrudController;

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
			jsonpCallback(response, callback, baseService.findAll(SysResource.class));
		} catch (IOException e) {
			logger.error("error", e);
		}
	}
}
