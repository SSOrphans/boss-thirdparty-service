package org.ssor.boss.thirdparty.service;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

@Service
public class BossRequestFromVisaAPI
{
  String urlString = "https://sandbox.api.visa.com/forexrates/v2/";
  @Value("${VISA_KEYSTORE_FILE}")
  String keystorePath;
  @Value("${VISA_KEYSTORE_PASSWORD}")
  String keystorePassword; //"visaks"
  @Value("${VISA_USER_ID}")
  String userId;
  @Value("${VISA_USER_PASSWORD}")
  String password;

  public SSLConnectionSocketFactory setTransportLayer() throws
      NoSuchAlgorithmException,
      KeyStoreException,
      IOException, CertificateException, UnrecoverableKeyException, KeyManagementException
  {
    var ks = KeyStore.getInstance("PKCS12");
    try (var fis = new FileInputStream(keystorePath))
    {
      ks.load(fis, keystorePassword.toCharArray());
    }

    return new SSLConnectionSocketFactory(
        new SSLContextBuilder()
            .loadTrustMaterial(null, new TrustSelfSignedStrategy())
            .loadKeyMaterial(ks, keystorePassword.toCharArray())
            .build()
    );
  }

  public ResponseEntity<String> createRequest(String jsonPayload, String uri) throws
      UnrecoverableKeyException,
      CertificateException,
      NoSuchAlgorithmException,
      KeyStoreException,
      IOException,
      KeyManagementException
  {
    String auth = userId + ":" + password;
    byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
    String authHeaderValue = "Basic " + new String(encodedAuth);

    CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(setTransportLayer()).build();
    ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
        client);

    var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", authHeaderValue);

    HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

    var template = new RestTemplate(requestFactory);

    return template.postForEntity(urlString + uri, entity, String.class);
  }
}
