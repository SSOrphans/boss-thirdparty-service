package org.ssor.boss.thirdparty.service;

import org.apache.http.client.HttpResponseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ssor.boss.thirdparty.transfer.ForexDataTransfer;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class BossRequestFromVisaServiceTest
{
  @MockBean
  BossRequestFromVisaAPI requestFromVisaAPI;

  @InjectMocks
  BossThirdPartyVisaService service;

  @Test
  void can_exchangeCurrency() throws
      UnrecoverableKeyException,
      CertificateException,
      NoSuchAlgorithmException,
      KeyStoreException,
      IOException,
      KeyManagementException
  {
    ResponseEntity<String> stubbedResponse
        = ResponseEntity.ok(
        "{\n" +
        "    \"conversionRate\": \"0.7700000\",\n" +
        "    \"destinationAmount\": \"0.77\",\n" +
        "    \"rateProductCode\": \"A\"\n" +
        "}");
    Mockito.doReturn(stubbedResponse).when(requestFromVisaAPI).createRequest(Mockito.anyString(), Mockito.anyString());
    var expected = new ForexDataTransfer("USD", 0.77D, .3D);
    var actual = service.getForex("EUR", "USD", 50D);
    assertEquals(expected, actual);
  }

  @Test
  void can_exchangeCurrencyWithBadResponse() throws
      UnrecoverableKeyException,
      CertificateException,
      NoSuchAlgorithmException,
      KeyStoreException,
      IOException,
      KeyManagementException
  {
    ResponseEntity<String> stubbedResponse = ResponseEntity.internalServerError().body("{}");
    Mockito.doReturn(stubbedResponse).when(requestFromVisaAPI).createRequest(Mockito.anyString(), Mockito.anyString());
    HttpResponseException exception =
        assertThrows(HttpResponseException.class, () ->
            service.getForex("EUR", "USD", 50D)
        );
    assertEquals(500, exception.getStatusCode());
    assertEquals("Visa API call failed", exception.getReasonPhrase());
  }

  @Test
  void can_exchangeCurrencyWithBadCurrency() throws
      UnrecoverableKeyException,
      CertificateException,
      NoSuchAlgorithmException,
      KeyStoreException,
      IOException,
      KeyManagementException
  {
    ResponseEntity<String> stubbedResponse = ResponseEntity.internalServerError().body("{}");
    Mockito.doReturn(stubbedResponse).when(requestFromVisaAPI).createRequest(Mockito.anyString(), Mockito.anyString());
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () ->
            service.getForex("EUR0", "USD", 50D)
        );
  }
}

