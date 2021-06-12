package org.ssor.boss.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
public class BossThirdPartyServiceApplication
{

  public static void main(String[] args)
  {
    SpringApplication.run(BossThirdPartyServiceApplication.class, args);
  }

}
