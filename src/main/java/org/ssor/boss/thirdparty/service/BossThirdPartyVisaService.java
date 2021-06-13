package org.ssor.boss.thirdparty.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.ssor.boss.thirdparty.transfer.ForexDataTransfer;
import org.ssor.boss.thirdparty.transfer.VisaForexPayload;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Currency;

@Service
public class BossThirdPartyVisaService
{

  @Autowired
  BossRequestFromVisaAPI requestFromVisaAPI;

  public ForexDataTransfer getForex(String fromCurrency, String toCurrency, Double amountToConvert) throws
      IOException,
      KeyStoreException,
      CertificateException,
      NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, IllegalArgumentException
  {
    var fromCurrencyCode = Currency.getInstance(fromCurrency.toUpperCase());
    var toCurrencyCode = Currency.getInstance(toCurrency.toUpperCase());
    var payload = new VisaForexPayload("A", toCurrencyCode.getNumericCodeAsString(), fromCurrencyCode.getNumericCodeAsString());
    payload.setSourceAmount(amountToConvert.toString());

    var payloadMapper = new ObjectMapper();
    var jsonPayload = payloadMapper.writeValueAsString(payload);

    ResponseEntity<String> response = requestFromVisaAPI.createRequest(jsonPayload, "foreignexchangerates");

    if (response.getStatusCode() != HttpStatus.OK)
    {
      throw new HttpResponseException(500, "Visa API call failed");
    }

    var responseMapper = new ObjectMapper();
    JsonNode responseJson = responseMapper.readTree(response.getBody());
    Double amount = Double.parseDouble(responseJson.get("destinationAmount").textValue());
    return new ForexDataTransfer(toCurrency, amount);
  }
}
