package org.ssor.boss.thirdparty.transfer;

import lombok.*;

/**
 *
 **/

@Setter
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class VisaForexPayload
{

  private final String rateProductCode;
  private final String destinationCurrencyCode;
  private final String sourceCurrencyCode;
  private String sourceAmount;
  private String destinationAmount;

}
