package org.ssor.boss.thirdparty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.ssor.boss.thirdparty.service.BossThirdPartyVisaService;
import org.ssor.boss.thirdparty.transfer.ForexDataTransfer;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@RestController
@RequestMapping(value = { "api/v1/thirdparty/visa" },
                produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class BossThirdPartyVisaController
{
  @Autowired
  BossThirdPartyVisaService bossThirdPartyVisaService;

  @GetMapping(value = { "/forex" })
  @ResponseStatus(value = HttpStatus.OK)
  public ForexDataTransfer exchangeValue(
      @PathParam("amount") Double amount,
      @PathParam("toCurrency") String toCurrency,
      @PathParam("fromCurrency") String fromCurrency
  ) throws
      UnrecoverableKeyException,
      CertificateException,
      IOException,
      KeyStoreException,
      NoSuchAlgorithmException,
      KeyManagementException
  {
    System.out.println( bossThirdPartyVisaService.getForex());
    return new ForexDataTransfer("USD", 50D);
  }
}
