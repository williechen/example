package org.cwgy.stock.web.controller.ajax;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cwgy.stock.web.Service.WebPushService;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ajax")
public class AjaxWebPushController {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	private WebPushService wpService;

	public String mainIndex() {
		return "ok";
	}

	@RequestMapping(value = "/subscription", method = { RequestMethod.GET, RequestMethod.POST })
	public Map subscription(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("public_key", wpService.readPublicKey());
		
		return map;
	}
	
	@RequestMapping(value = "/push_v1", method = { RequestMethod.GET, RequestMethod.POST })
	public Map pushData(@RequestBody String dataStr) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			wpService.sendPushMessage(dataStr, "Guan-yu Willie Chen");
			
			map.put("success", 1);
		} catch (GeneralSecurityException | IOException | JoseException | ExecutionException | InterruptedException e) {
			log.error("", e);
			map.put("failed", e);
		}
		
		return map;
	}
}
