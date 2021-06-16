package com.big.cumulativetransfer.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CumulativeTransfer {

    private Long sender;
    private Long receiver;
    private BigDecimal cumNative = BigDecimal.ZERO;
    private BigDecimal cumOther = BigDecimal.ZERO;

}
