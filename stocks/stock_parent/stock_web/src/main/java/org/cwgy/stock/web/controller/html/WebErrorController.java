package org.cwgy.stock.web.controller.html;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebErrorController implements ErrorController  {

	@RequestMapping("/error")
    public String handleError() {
        return "th/error";
    }
	
	@Override
	public String getErrorPath() {
		return null;
	}

}
