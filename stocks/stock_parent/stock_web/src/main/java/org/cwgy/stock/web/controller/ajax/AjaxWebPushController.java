package org.cwgy.stock.web.controller.ajax;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	public Map<String, Object> subscription(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("public_key", wpService.readPublicKey());
		
		return map;
	}
	
	@RequestMapping(value = "/push_v1", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> pushData(@RequestBody String dataStr) {
		
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
	
	@RequestMapping(value = "/getRadioData", method = { RequestMethod.GET, RequestMethod.POST })
	public List<Map<String, String>> radioData(@RequestBody String dataStr) {
		
		List<Map<String, String>> radios = new ArrayList<Map<String, String>>();
		try {
			Map<String, String> radio1 = new HashMap<String, String>();
			radio1.put("code", "1");
			radio1.put("name", "t");
			radios.add(radio1);
			
			Map<String, String> radio2 = new HashMap<String, String>();
			radio2.put("code", "2");
			radio2.put("name", "e");
			radios.add(radio2);
			
			Map<String, String> radio3 = new HashMap<String, String>();
			radio3.put("code", "3");
			radio3.put("name", "s");
			radios.add(radio3);
			
			Map<String, String> radio4 = new HashMap<String, String>();
			radio4.put("code", "4");
			radio4.put("name", "t");
			radios.add(radio4);
			
		} catch (Exception e) {
			log.error("", e);
			
		}
		
		return radios;
	}
}
