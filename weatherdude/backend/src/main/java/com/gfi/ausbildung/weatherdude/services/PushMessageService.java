package com.gfi.ausbildung.weatherdude.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfi.ausbildung.weatherdude.facade.dto.pushnotification.Subscription;
import com.gfi.ausbildung.weatherdude.facade.dto.pushnotification.SubscriptionEndpoint;
import com.gfi.ausbildung.weatherdude.utils.crypto.CryptoService;
import com.gfi.ausbildung.weatherdude.utils.crypto.ServerKeys;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@CrossOrigin
public class PushMessageService
{

  private final ServerKeys                serverKeys;
  private final CryptoService             cryptoService;
  private final Map<String, Subscription> subscriptions = new ConcurrentHashMap<>();
  private final HttpClient                httpClient;
  private final Algorithm                 jwtAlgorithm;
  private final ObjectMapper              objectMapper;

  public PushMessageService(ServerKeys serverKeys, CryptoService cryptoService, ObjectMapper objectMapper)
  {
    this.serverKeys = serverKeys;
    this.cryptoService = cryptoService;
    this.httpClient = HttpClient.newHttpClient();
    this.objectMapper = objectMapper;

    this.jwtAlgorithm = Algorithm.ECDSA256(this.serverKeys.getPublicKey(), this.serverKeys.getPrivateKey());
  }

  public byte[] getPublicSigningKey()
  {
    return this.serverKeys.getPublicKeyUncompressed();
  }

  public String getPublicSigningKeyBase64()
  {
    return this.serverKeys.getPublicKeyBase64();
  }

  public void subscribe(Subscription subscription)
  {
    this.subscriptions.put(subscription.getEndpoint(), subscription);
  }

  public void unsubscribe(SubscriptionEndpoint subscription)
  {
    this.subscriptions.remove(subscription.getEndpoint());
  }

  public boolean isSubscribed(SubscriptionEndpoint subscription)
  {
    return this.subscriptions.containsKey(subscription.getEndpoint());
  }

  public void sendPushMessageToAllSubscribers(Object message) throws JsonProcessingException
  {

    Set<String> failedSubscriptions = new HashSet<>();

    for (Subscription subscription : subscriptions.values())
    {
      try
      {
        byte[] result = this.cryptoService.encrypt(this.objectMapper.writeValueAsString(message),
            subscription.getKeys().getP256dh(), subscription.getKeys().getAuth(), 0);
        boolean remove = sendPushMessage(subscription, result);
        if (remove)
        {
          failedSubscriptions.add(subscription.getEndpoint());
        }
      }
      catch (InvalidKeyException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | IllegalStateException
          | InvalidKeySpecException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e)
      {
        log.error("Exception while sending encrypted message", e);
      }
    }

    failedSubscriptions.forEach(subscriptions::remove);
  }

  /**
   * @return true if the subscription is no longer valid and can be removed, false if everything is okay
   */
  private boolean sendPushMessage(Subscription subscription, byte[] body)
  {
    String origin = null;
    try
    {
      URL url = new URL(subscription.getEndpoint());
      origin = url.getProtocol() + "://" + url.getHost();
    }
    catch (MalformedURLException e)
    {
      log.error("create origin", e);
      return true;
    }

    Date today = new Date();
    Date expires = new Date(today.getTime() + 12 * 60 * 60 * 1000);

    String token = JWT.create().withAudience(origin).withExpiresAt(expires).withSubject("mailto:example@example.com")
        .sign(this.jwtAlgorithm);

    URI endpointURI = URI.create(subscription.getEndpoint());

    Builder httpRequestBuilder = HttpRequest.newBuilder();
    if (body != null)
    {
      httpRequestBuilder.POST(BodyPublishers.ofByteArray(body)).header("Content-Type", "application/octet-stream")
          .header("Content-Encoding", "aes128gcm");
    }
    else
    {
      httpRequestBuilder.POST(BodyPublishers.noBody());
    }

    HttpRequest request = httpRequestBuilder.uri(endpointURI).header("TTL", "180")
        .header("Authorization", "vapid t=" + token + ", k=" + this.serverKeys.getPublicKeyBase64()).build();
    try
    {
      HttpResponse<Void> response = this.httpClient.send(request, BodyHandlers.discarding());

      switch (response.statusCode())
      {
        case 201:
          log.info("Push message successfully sent: {}", subscription.getEndpoint());
          break;
        case 404:
        case 410:
          log.warn("Subscription not found or gone: {}", subscription.getEndpoint());
          // remove subscription from our collection of subscriptions
          return true;
        case 429:
          log.error("Too many requests: {}", request);
          break;
        case 400:
          log.error("Invalid request: {}", request);
          break;
        case 413:
          log.error("Payload size too large: {}", request);
          break;
        default:
          log.error("Unhandled status code: {} / {}", response.statusCode(), request);
      }
    }
    catch (IOException | InterruptedException e)
    {
      log.error("Sending push message", e);
    }

    return false;
  }

}
