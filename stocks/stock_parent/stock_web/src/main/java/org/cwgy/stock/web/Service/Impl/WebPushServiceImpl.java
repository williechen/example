package org.cwgy.stock.web.Service.Impl;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.cwgy.stock.web.Service.WebPushService;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.martijndwars.webpush.Base64Encoder;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import nl.martijndwars.webpush.Utils;

@Service
public class WebPushServiceImpl implements WebPushService {
	Logger log = LogManager.getLogger(getClass());

	@Value("classpath:private_key.txt")
	private Resource privateResource;

	@Value("classpath:public_key.txt")
	private Resource publicResource;

	private final Integer TTL = 255;

	public String readPrivateKey() {
		String value = "";

		try {
			InputStream in = privateResource.getInputStream();
			value = IOUtils.toString(in, "UTF-8").replaceAll("\\n", "");
		} catch (IOException e) {
			log.error("", e);
		}

		return value;
	}

	public String readPublicKey() {
		String value = "";

		try {
			InputStream in = publicResource.getInputStream();
			value = IOUtils.toString(in, "UTF-8").replaceAll("\\n", "");
		} catch (IOException e) {
			log.error("", e);
		}

		return value;
	}

	public Subscription getSubscription(String jsonStr) throws JsonMappingException,
			JsonProcessingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {

		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> jsonData = mapper.readValue(jsonStr, Map.class);

		String subscriptionInfo = (String) jsonData.get("sub_token");
		
		Map<String, Object> jsonSubscription = mapper.readValue(subscriptionInfo, Map.class);
		Map<String, Object> jsonKeys = (Map<String, Object>) jsonSubscription.get("keys");

		Subscription.Keys subKeys = new Subscription.Keys(jsonKeys.get("p256dh").toString(), jsonKeys.get("auth").toString());
		Subscription subscription = new Subscription(jsonSubscription.get("endpoint").toString(), subKeys);

		return subscription;
	}

	public void sendPushMessage(String subscription_information, String message)
			throws GeneralSecurityException, IOException, JoseException, ExecutionException, InterruptedException {

		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
		      Security.addProvider(new BouncyCastleProvider());
		}
		
		// Figure out if we should use GCM for this notification somehow
		Subscription sub = this.getSubscription(subscription_information);

		boolean useGcm = shouldUseGcm(sub);
		Notification notification;
		PushService pushService;

		if (!useGcm) {
			// Create a notification with the endpoint, userPublicKey from the subscription
			// and a custom payload
			notification = new Notification(sub, message);

			// Instantiate the push service, no need to use an API key for Push API
			pushService = new PushService();
			pushService.setPrivateKey(this.readPrivateKey());
			pushService.setPublicKey(this.readPublicKey());
			pushService.setSubject("test firefox");
		} else {
			// Or create a GcmNotification, in case of Google Cloud Messaging
			notification = new Notification(sub.endpoint, Utils.loadPublicKey(sub.keys.p256dh),
					Base64Encoder.decode(sub.keys.auth), message.getBytes(), TTL);

			// Instantiate the push service with a GCM API key
			pushService = new PushService("gcm-api-key");
			pushService.setPrivateKey(this.readPrivateKey());
			pushService.setPublicKey(this.readPublicKey());
			pushService.setSubject("test google");
		}

		// Send the notification
		pushService.send(notification);
	}

	private static boolean shouldUseGcm(Subscription sub) {
		if (sub.endpoint.contains("mozilla")) {
			return false;
		}
		return true;
	}
}
