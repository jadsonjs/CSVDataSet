/*
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 *
 * CSVDataSet
 * br.com.jadson
 * CSVDataSetStoreLoadTest
 * 25/02/20
 */
package br.com.jadson;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Test store CSV to a file and load to a file
 * Jadson Santos - jadsonjs@gmail.com
 */
public class CSVDataSetStoreLoadTest {

    File csvFile;

    /**
     * Test store  and load from CSV
     */
    @Test
    void storeAndLoadDataTest() throws IOException {

        csvFile = File.createTempFile("temp", ".csv");

        List<String> header = Arrays.asList( new String[]{"Column1", "Column2", "Column3", "Column4"});
        List<String> row1 = Arrays.asList( new String[]{"1", "2", "3", "4"} );
        List<String> row2 = Arrays.asList( new String[]{"5", "6", "7", "8"} );
        List<String> row3 = Arrays.asList( new String[]{"9", "10", "11", "12"} );

        CSVDataSet dataSet = CSVDataSet.getInstance( csvFile.getName() );

        dataSet.clearData();
        dataSet.setHeaders(header);
        dataSet.addRow(row1);
        dataSet.addRow(row2);
        dataSet.addRow(row3);
        dataSet.storeData();

        dataSet.clearData();

        dataSet.loadData();

        List<String> row2Loaded = dataSet.getRowValues(1);
        List<String> column2Loaded = dataSet.getColumnValues(1);

        List<String> column2 = Arrays.asList( new String[]{"2", "6", "10"} );

        Assertions.assertTrue(row2.equals(row2Loaded));
        Assertions.assertTrue(column2.equals(column2Loaded));

    }

    @AfterEach
    public void cleanUpEach(){
        System.out.println("After Each cleanUpEach() method called");
        if(csvFile != null ) csvFile.delete();
    }

}
