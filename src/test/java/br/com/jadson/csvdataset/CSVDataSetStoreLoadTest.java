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
package br.com.jadson.csvdataset;

import br.com.jadson.csvdataset.CSVDataSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Test store CSV to a file and load to a file
 * Jadson Santos - jadsonjs@gmail.com
 */
public class CSVDataSetStoreLoadTest {


    /**
     * Test store  and load from CSV
     */
    @Test
    void storeAndLoadDataTest() throws IOException {

        List<String> header = Arrays.asList( new String[]{"Column1", "Column2", "Column3", "Column4"});
        List<String> row1 = Arrays.asList( new String[]{"1", "2", "3", "4"} );
        List<String> row2 = Arrays.asList( new String[]{"5", "6", "7", "8"} );
        List<String> row3 = Arrays.asList( new String[]{"9", "10", "11", "12"} );

        List<String> column1 = Arrays.asList( new String[]{"1", "5", "9"} );
        List<String> column2 = Arrays.asList( new String[]{"2", "6", "10"} );
        List<String> column3 = Arrays.asList( new String[]{"3", "7", "11"} );
        List<String> column4 = Arrays.asList( new String[]{"4", "8", "12"} );

        CSVDataSet dataSet = new CSVDataSet( "temp"+new Random().nextInt() +".csv" );;

        dataSet.setHeaders(header);
        dataSet.addRow(row1);
        dataSet.addRow(row2);
        dataSet.addRow(row3);
        dataSet.storeData();

        dataSet.clearData();

        dataSet.loadData();

        List<String> row1Loaded = dataSet.getRowValues(0);
        List<String> column1Loaded = dataSet.getColumnValues(0);

        List<String> row2Loaded = dataSet.getRowValues(1);
        List<String> column2Loaded = dataSet.getColumnValues(1);

        List<String> row3Loaded = dataSet.getRowValues(2);
        List<String> column3Loaded = dataSet.getColumnValues(2);

        List<String> column4Loaded = dataSet.getColumnValues(3);



        Assertions.assertTrue(row1.equals(row1Loaded));
        Assertions.assertTrue(column1.equals(column1Loaded));

        Assertions.assertTrue(row2.equals(row2Loaded));
        Assertions.assertTrue(column2.equals(column2Loaded));

        Assertions.assertTrue(row3.equals(row3Loaded));
        Assertions.assertTrue(column3.equals(column3Loaded));

        Assertions.assertTrue(column4.equals(column4Loaded));

        dataSet.deleteFile();

    }



    /**
     * Test store  and load from CSV
     */
    @Test
    void storeAndLoadDataDifferentSeparatorTest() throws IOException {

        List<String> header = Arrays.asList( new String[]{"Column1", "Column2", "Column3", "Column4"});
        List<String> row1 = Arrays.asList( new String[]{"1", "2", "3", "4"} );
        List<String> row2 = Arrays.asList( new String[]{"5", "6", "7", "8"} );
        List<String> row3 = Arrays.asList( new String[]{"9", "10", "11", "12"} );

        List<String> column1 = Arrays.asList( new String[]{"1", "5", "9"} );
        List<String> column2 = Arrays.asList( new String[]{"2", "6", "10"} );
        List<String> column3 = Arrays.asList( new String[]{"3", "7", "11"} );
        List<String> column4 = Arrays.asList( new String[]{"4", "8", "12"} );

        CSVDataSet dataSet = new CSVDataSet( "temp"+new Random().nextInt() +".csv", ";" );

        dataSet.setHeaders(header);
        dataSet.addRow(row1);
        dataSet.addRow(row2);
        dataSet.addRow(row3);
        dataSet.storeData();

        dataSet.clearData();

        dataSet.loadData();

        List<String> row1Loaded = dataSet.getRowValues(0);
        List<String> column1Loaded = dataSet.getColumnValues(0);

        List<String> row2Loaded = dataSet.getRowValues(1);
        List<String> column2Loaded = dataSet.getColumnValues(1);

        List<String> row3Loaded = dataSet.getRowValues(2);
        List<String> column3Loaded = dataSet.getColumnValues(2);

        List<String> column4Loaded = dataSet.getColumnValues(3);



        Assertions.assertTrue(row1.equals(row1Loaded));
        Assertions.assertTrue(column1.equals(column1Loaded));

        Assertions.assertTrue(row2.equals(row2Loaded));
        Assertions.assertTrue(column2.equals(column2Loaded));

        Assertions.assertTrue(row3.equals(row3Loaded));
        Assertions.assertTrue(column3.equals(column3Loaded));

        Assertions.assertTrue(column4.equals(column4Loaded));

        dataSet.deleteFile();

    }

    /**
     * Test load a file with 1.000 lines
     */
    @Test
    void loadMediumFileTest() throws IOException {

        Instant start = Instant.now();

        String userDirectory = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
        CSVDataSet dataSet = new CSVDataSet( userDirectory+"/src/test/resources/mediumfile.csv" );;
        dataSet.loadData();

        System.out.println("load in: "+Duration.between(start, Instant.now()).toMillis());

        // the sum of all elements of column 0, is equals to 1.000
        Assertions.assertTrue(new BigDecimal(1000).compareTo(dataSet.sumColumn(0)) == 0);

        // load a file with 1.000 lines less then 1000ms or 1s
        Assertions.assertTrue(Duration.between(start, Instant.now()).toMillis() < 1000 );
    }

    /**
     * Test load a file with 10.000 lines
     */
    @Test
    void loadBigFileTest() throws IOException {

        Instant start = Instant.now();

        String userDirectory = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
        CSVDataSet dataSet = new CSVDataSet( userDirectory+"/src/test/resources/bigfile.csv" );;
        dataSet.loadData();

        System.out.println("load in: "+Duration.between(start, Instant.now()).toMillis());

        // the sum of all elements of column 0, is equals to 10.000
        Assertions.assertTrue(new BigDecimal(10000).compareTo(dataSet.sumColumn(0)) == 0);

        // load a file with 10.000 lines less then 1000ms or 1s
        Assertions.assertTrue(Duration.between(start, Instant.now()).toMillis() < 1000 );
    }


    /**
     * Test load a file with 100.000 lines
     */
    @Test
    void loadHugeFileTest() throws IOException {

        Instant start = Instant.now();

        String userDirectory = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
        CSVDataSet dataSet = new CSVDataSet( userDirectory+"/src/test/resources/hugefile.csv" );;
        dataSet.loadData();

        System.out.println("load in: "+Duration.between(start, Instant.now()).toMillis());

        // load a file with 100.000 lines less then 80000ms or 1m:20s
        Assertions.assertTrue(Duration.between(start, Instant.now()).toMillis() < 80000 );

        // the standard deviation of all elements of column 0, is equals to 0, because all them have the value "1"
        Assertions.assertTrue(new BigDecimal(0).compareTo(dataSet.stdDevColumn(0)) == 0);

    }

}
