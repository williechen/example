package org.cwgy.stock.web.controller.html;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebPushController {

	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
	public String mainIndex() {
		return "th/index";
	}
}
