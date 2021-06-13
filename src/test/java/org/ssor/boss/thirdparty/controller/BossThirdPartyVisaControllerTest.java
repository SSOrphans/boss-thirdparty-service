package org.ssor.boss.thirdparty.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ssor.boss.thirdparty.service.BossThirdPartyVisaService;
import org.ssor.boss.thirdparty.transfer.ForexDataTransfer;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class BossThirdPartyVisaControllerTest
{

  @MockBean
  BossThirdPartyVisaService thirdPartyVisaService;

  @InjectMocks
  BossThirdPartyVisaController controller;

  @Test
  void test_canReturnTransferObject() throws
      UnrecoverableKeyException,
      CertificateException,
      IOException,
      KeyStoreException,
      NoSuchAlgorithmException,
      KeyManagementException
  {
    var transfer = new ForexDataTransfer("USD", 50D, .3D);
    Mockito.doReturn(transfer).when(thirdPartyVisaService)
           .getForex(Mockito.anyString(), Mockito.anyString(), Mockito.anyDouble());

    assertEquals(transfer, controller.exchangeValue(50D,"USD","EUR"));

  }
}
