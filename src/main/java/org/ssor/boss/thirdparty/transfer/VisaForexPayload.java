package org.ssor.boss.thirdparty.transfer;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
public class VisaForexPayload
{
  @Getter
  @Setter
  private static class AcquirerDetails{
    @Getter
    @Setter
    private static class Settlement {
      String currencyCode;
    }

    String bin;
    Settlement settlement = new Settlement();
  }

  @Setter(AccessLevel.NONE)
  @EqualsAndHashCode.Exclude
  private AcquirerDetails acquirerDetails = new AcquirerDetails();
  private String rateProductCode;
  private String markupRate;
  private String destinationCurrencyCode;
  private String sourceCurrencyCode;
  private String destinationAmount;

  public void setBin(String bin){
    acquirerDetails.setBin(bin);
  }

  public void setCurrencyCode(String currencyCode){
    acquirerDetails.settlement.setCurrencyCode(currencyCode);
  }

}
