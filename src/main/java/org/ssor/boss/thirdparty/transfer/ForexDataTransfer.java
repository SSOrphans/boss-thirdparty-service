package org.ssor.boss.thirdparty.transfer;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ForexDataTransfer
{
  private final String currency;
  private final Double amount;
  private final Double rate;
}
