package org.cwgy.stock.web.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ExecutionException;

import org.jose4j.lang.JoseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import nl.martijndwars.webpush.Subscription;

public interface WebPushService {
	
	/**
	 * 讀取私鑰(解密用)
	 * @return
	 */
	public String readPrivateKey();
	
	/**
	 * 讀取公鑰(加密用)
	 * @return
	 */
	public String readPublicKey();
	
	/**
	 * 取得訂閱瀏覽器資訊(json 字串)
	 * @param subscription_information(
	 *   endpoint => 推送伺服器域名
	 *   p256dh => 
	 *   auth => 
	 * )
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidKeySpecException
	 */
	public Subscription getSubscription(String subscription_information) throws JsonMappingException,
	JsonProcessingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException;
	
	/**
	 * 傳送訊息
	 * @param subscription_information 訂閱者資訊
	 * @param message 要傳送的訊息
	 * @throws GeneralSecurityException
	 * @throws IOException
	 * @throws JoseException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void sendPushMessage(String subscription_information, String message)
			throws GeneralSecurityException, IOException, JoseException, ExecutionException, InterruptedException;
}
