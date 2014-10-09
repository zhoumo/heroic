package mine.heroic.common.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public abstract class SimpleController extends BaseController {

	protected String homePage = "index";

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	@RequestMapping("/")
	public ModelAndView homePage() {
		configure();
		return new ModelAndView(homePage, model);
	}

	@RequestMapping("/{index}.do")
	public ModelAndView index(@PathVariable(value = "index") String index) {
		model.put("index", index);
		return new ModelAndView("/" + index, model);
	}
}
