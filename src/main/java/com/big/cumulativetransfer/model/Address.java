package com.big.cumulativetransfer.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Address {

    private Long id;
    private BigDecimal nativeTo = BigDecimal.ZERO;
    private BigDecimal nativeFrom = BigDecimal.ZERO;
    private BigDecimal otherTo = BigDecimal.ZERO;
    private BigDecimal otherFrom = BigDecimal.ZERO;

}
