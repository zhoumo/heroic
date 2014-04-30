package mine.heroic.common.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mine.heroic.common.BaseEntity;
import mine.heroic.common.service.BaseService;
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

	protected String index = StringUtils.EMPTY;

	protected void setIndex(String index) {
		this.index = index;
	}

	protected String saveReturn = "redirect:index.do";

	protected void setSaveReturn(String saveReturn) {
		this.saveReturn = saveReturn;
	}

	protected String deleteReturn = "redirect:index.do";

	protected void setDeleteReturn(String deleteReturn) {
		this.deleteReturn = deleteReturn;
	}

	@RequestMapping("/index.do")
	protected ModelAndView index() {
		configure();
		return new ModelAndView(this.index, this.model);
	}

	@RequestMapping("/pagination.do")
	@ResponseBody
	protected Page<T> pagination(Page<T> page) {
		if (page.getPageSize() < 1) {
			page.setPageSize(10);
		}
		return baseService.pagination(page, getGenericType());
	}

	@RequestMapping("/save.do")
	protected String saveOrUpdate(@ModelAttribute T entity, HttpServletRequest request) {
		configure();
		beforeSaveOrUpdate(entity, request);
		baseService.saveOrUpdate(entity);
		return this.saveReturn;
	}

	protected void beforeSaveOrUpdate(@ModelAttribute T entity, HttpServletRequest request) {
	}

	@RequestMapping("/delete.do")
	protected String delete(@ModelAttribute T entity, HttpServletRequest request) {
		configure();
		beforeDelete(entity, request);
		baseService.delete(entity);
		return this.deleteReturn;
	}

	protected void beforeDelete(@ModelAttribute T entity, HttpServletRequest request) {
	}

	@RequestMapping("/get.do")
	@ResponseBody
	protected BaseEntity get(Long id) {
		return baseService.get(id, getGenericType());
	}

	@RequestMapping("/getAll.do")
	protected void getAll(String callback, HttpServletResponse response) {
		try {
			jsonpCallback(response, callback, baseService.findAll(getGenericType()));
		} catch (IOException e) {
			logger.error("error", e);
		}
	}

	@RequestMapping("/checkUnique.do")
	@ResponseBody
	protected boolean checkUnique(String field, String value, Long id) {
		return baseService.checkUnique(field, value, id, getGenericType());
	}

	@RequestMapping("/getCustomStyle.do")
	@ResponseBody
	protected String getCustomStyle() {
		String json = "";
		try {
			json = baseService.resolveCustom(getGenericType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
}