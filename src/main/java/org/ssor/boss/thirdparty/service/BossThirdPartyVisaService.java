package org.ssor.boss.thirdparty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.ssor.boss.thirdparty.transfer.VisaForexPayload;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

@Service
public class BossThirdPartyVisaService
{

  @Autowired
  BossRequestFromVisaAPI requestFromVisaAPI;

  public ResponseEntity<String> getForex() throws
      IOException,
      KeyStoreException,
      CertificateException,
      NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException
  {
    VisaForexPayload payload = new VisaForexPayload();
    payload.setBin("408999");
    payload.setCurrencyCode("840");
    payload.setRateProductCode("A");
    payload.setMarkupRate("0.07");
    payload.setSourceCurrencyCode("840");
    payload.setDestinationAmount("75.85");
    payload.setDestinationCurrencyCode("826");

    ObjectMapper mapper = new ObjectMapper();
    String jsonPayload = mapper.writeValueAsString(payload);

    return requestFromVisaAPI.createRequest(jsonPayload, "foreignexchangerates");
  }
}
