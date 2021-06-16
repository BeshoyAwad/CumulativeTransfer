package com.big.cumulativetransfer.util;

import com.big.cumulativetransfer.model.CumulativeTransfer;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvUtil {

    public static void createClusterCsv(String filePath, List<CumulativeTransfer> transfers) {

        File file = new File(filePath);

        try {

            CSVWriter writer;
            FileWriter fileWriter = new FileWriter(file);

            writer = new CSVWriter(fileWriter,
                    CSVWriter.DEFAULT_SEPARATOR,
                    '\"',
                    CSVWriter.NO_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);


            // adding header to csv
            String[] header = {"sender", "receiver", "cumul_native", "cumul_other"};
            writer.writeNext(header);

            // add data to csv
            for (CumulativeTransfer transfer : transfers) {
                String[] data = {transfer.getSender().toString(),
                        transfer.getReceiver().toString(),
                        transfer.getCumNative().toString(),
                        transfer.getCumOther().toString()};
                writer.writeNext(data);
            }

            // closing writer connection
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
