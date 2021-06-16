package com.big.cumulativetransfer.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CumulativeTransferData {

    public final static Map<Long, Address> addressesData = new ConcurrentHashMap<>();
    public final static Table<Long, Long, CumulativeTransfer> transfersData = HashBasedTable
        .create();

}
