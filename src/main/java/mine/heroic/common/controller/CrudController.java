package mine.heroic.common.controller;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mine.heroic.common.BaseEntity;
import mine.heroic.common.service.BaseService;
import mine.heroic.common.service.CustomService;
import mine.heroic.vo.Page;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@SuppressWarnings("unchecked")
public abstract class CrudController<T extends BaseEntity> extends BaseController {

	@Autowired
	protected BaseService<T> baseService;

	@Autowired
	protected CustomService<T> customService;

	protected Class<T> clazz;

	protected String selectedIds = StringUtils.EMPTY;

	protected String index = StringUtils.EMPTY;

	protected String saveReturn = "redirect:index.do";

	protected String deleteReturn = "redirect:index.do";

	protected void setIndex(String index) {
		this.index = index;
	}

	protected void setSaveReturn(String saveReturn) {
		this.saveReturn = saveReturn;
	}

	protected void setDeleteReturn(String deleteReturn) {
		this.deleteReturn = deleteReturn;
	}

	protected void beforeSaveOrUpdate(@ModelAttribute T entity, HttpServletRequest request) {
	}

	protected void afterSaveOrUpdate(@ModelAttribute T entity, HttpServletRequest request) {
	}

	protected void beforeDelete(@ModelAttribute T entity, HttpServletRequest request) {
	}

	protected void afterDelete(@ModelAttribute T entity, HttpServletRequest request) {
	}

	public CrudController() {
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void setBaseService(BaseService<T> baseService) {
		this.baseService = baseService;
	}

	public void setCustomService(CustomService<T> customService) {
		this.customService = customService;
	}

	@RequestMapping("/index.do")
	public ModelAndView index() {
		configure();
		return new ModelAndView(this.index, this.model);
	}

	@RequestMapping("/pagination.do")
	@ResponseBody
	public Page<T> pagination(Page<T> page) {
		if (page.getPageSize() < 1) {
			page.setPageSize(10);
		}
		return baseService.pagination(page, clazz);
	}

	@RequestMapping("/save.do")
	public String saveOrUpdate(@ModelAttribute T entity, HttpServletRequest request) {
		configure();
		this.selectedIds = request.getParameter("selectedIds");
		this.selectedIds = (this.selectedIds == null ? "" : this.selectedIds.trim());
		beforeSaveOrUpdate(entity, request);
		baseService.saveOrUpdate(entity);
		afterSaveOrUpdate(entity, request);
		return this.saveReturn;
	}

	@RequestMapping("/delete.do")
	public String delete(@ModelAttribute T entity, HttpServletRequest request) {
		configure();
		beforeDelete(entity, request);
		baseService.delete(entity);
		afterDelete(entity, request);
		return this.deleteReturn;
	}

	@RequestMapping("/get.do")
	@ResponseBody
	public BaseEntity get(Long id) {
		return baseService.get(id, clazz);
	}

	@RequestMapping("/getAll.do")
	public void getAll(String callback, HttpServletResponse response) {
		try {
			jsonpCallback(response, callback, baseService.findAll(clazz));
		} catch (IOException e) {
			logger.error("error", e);
		}
	}

	@RequestMapping("/checkUnique.do")
	@ResponseBody
	public boolean checkUnique(String field, String value, Long id) {
		return baseService.checkUnique(field, value, id, clazz);
	}

	@RequestMapping("/getCustom.do")
	@ResponseBody
	public String getCustom() {
		String json = "";
		try {
			json = customService.resolveCustom(clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
}
