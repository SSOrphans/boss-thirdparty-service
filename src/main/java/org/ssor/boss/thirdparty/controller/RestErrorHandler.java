package org.ssor.boss.thirdparty.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestControllerAdvice
public class RestErrorHandler
{
  @ExceptionHandler(value = { UnrecoverableKeyException.class,
                              KeyStoreException.class,
                              NoSuchAlgorithmException.class,
                              KeyManagementException.class,
                              IllegalArgumentException.class })
  @GetMapping(produces = { APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE })
  public ResponseEntity<String> visaRequestFailedHandler()
  {
    return new ResponseEntity<>("Visa API Certification Failed", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = { CertificateException.class,
                              IOException.class })
  @GetMapping(produces = { APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE })
  public ResponseEntity<String> visaCertificationNotFound()
  {
    return new ResponseEntity<>("Visa API Certification File Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
