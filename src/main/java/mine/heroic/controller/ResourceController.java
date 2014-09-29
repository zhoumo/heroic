package mine.heroic.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mine.heroic.common.controller.CrudController;
import mine.heroic.model.SysResource;
import mine.heroic.model.SysRole;
import mine.heroic.security.SecurityMetadataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/resource")
public class ResourceController extends CrudController<SysResource> {

	@Autowired
	private SecurityMetadataSource securityMetadataSource;

	@Override
	protected void configure() {
		super.setIndex("resource");
	}

	@Override
	protected void beforeSaveOrUpdate(SysResource sysResource, HttpServletRequest request) {
		List<SysRole> roles = this.baseService.createCollectionsByIds(new SysRole(), this.selectedIds);
		sysResource.setRoles(roles);
	}

	@Override
	protected void afterSaveOrUpdate(SysResource sysResource, HttpServletRequest request) {
		securityMetadataSource.loadResource(true);
	}

	@RequestMapping("/getMenus.do")
	protected void getMenus(String callback, HttpServletResponse response) {
		try {
			List<SysResource> menuList = baseService.findBy("type", SysResource.TYPE_MENU, SysResource.class);
			jsonpCallback(response, callback, menuList);
		} catch (IOException e) {
			logger.error("Get menus failed", e);
		}
	}

	@RequestMapping("/getParentResource.do")
	protected void getParentResource(String callback, HttpServletResponse response) {
		try {
			List<SysResource> parentResourceList = new ArrayList<SysResource>();
			SysResource resource = new SysResource();
			resource.setId(0L);
			resource.setName("");
			parentResourceList.add(resource);
			List<SysResource> menuList = baseService.findBy("type", SysResource.TYPE_MENU, SysResource.class);
			parentResourceList.addAll(menuList);
			jsonpCallback(response, callback, parentResourceList);
		} catch (IOException e) {
			logger.error("Get menus failed", e);
		}
	}
}
