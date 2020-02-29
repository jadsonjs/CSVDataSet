/*
 *
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
 * CSVDataSetAlgorithmTest
 * 25/02/20
 */
package br.com.jadson.csvdataset;

import br.com.jadson.csvdataset.CSVDataSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Test creation of data in CSV file
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class CSVDataSetAlgorithmTest {

    /**
     * Test sum a row or a column
     */
    @Test
    void rowAndColumnSumAlgorithmTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

        dataSet.clearData();
        dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );

        BigDecimal sumRow = dataSet.sumRow(2);
        BigDecimal sumCol = dataSet.sumColumn(3);

        Assertions.assertTrue( new BigDecimal("42").compareTo(sumRow) == 0 );
        Assertions.assertTrue (new BigDecimal("24").compareTo(sumCol) == 0 );
    }


    /**
     * Test sum a row or a column
     */
    @Test
    void columnSumAlgorithmByLabelTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

        dataSet.clearData();
        dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );

        BigDecimal sumCol = dataSet.sumColumn("Column3");

        Assertions.assertTrue (new BigDecimal("21").compareTo(sumCol) == 0 );
    }


    /**
     * Test mean a row or a column
     */
    @Test
    void rowAndColumnMeanAlgorithmTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

        dataSet.clearData();
        dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );


        BigDecimal meanRow = dataSet.meanRow(2);
        BigDecimal meanCol = dataSet.meanColumn(3);

        Assertions.assertTrue( new BigDecimal("10.5").compareTo(meanRow) == 0 );
        Assertions.assertTrue (new BigDecimal("8").compareTo(meanCol) == 0 );
    }


    /**
     * Test median a row or a column
     */
    @Test
    void rowAndColumnMedianAlgorithmTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

        dataSet.clearData();
        dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );


        BigDecimal medianRow = dataSet.medianRow(2);
        BigDecimal medianCol = dataSet.medianColumn(3);

        Assertions.assertTrue( new BigDecimal("10.5").compareTo(medianRow) == 0 );
        Assertions.assertTrue (new BigDecimal("8").compareTo(medianCol) == 0 );
    }


    /**
     * Test median a row or a column
     */
    @Test
    void rowAndColumnVarianceAlgorithmTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

        dataSet.clearData();
        dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );


        BigDecimal varianceRow = dataSet.varianceRow(2);
        BigDecimal varianceCol = dataSet.varianceColumn("Column4");

        Assertions.assertTrue( new BigDecimal("1.25000").compareTo(varianceRow) == 0 );
        Assertions.assertTrue (new BigDecimal("10.66667").compareTo(varianceCol) == 0 );
    }


    /**
     * Test standard deviation a row or a column
     */
    @Test
    void rowAndColumnStandardDeviationAlgorithmTest() throws IOException {


        CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

        dataSet.clearData();
        dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4", "Column5", "Column6", "Column7", "Column8", "Column9" }) );
        dataSet.addRow(  Arrays.asList(new String[]{"4", "9", "11", "12", "17", "5", "8", "12", "14"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8", "5", "6", "7", "8", "1"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8", "5", "6", "7", "8", "1"}) );


        BigDecimal stdDevRow = dataSet.stdDevRow(0);

        Assertions.assertTrue( new BigDecimal("3.9378").compareTo(stdDevRow) == 0 );
    }


    /**
     * Test row normalization
     *
     * @throws IOException
     */
    @Test
    void rowNormalizationAlgorithmTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

        dataSet.clearData();
        dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4", "Column5", "Column6", "Column7"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"100", "50", "11", "5", "40", "140", "200"})   );


        List<String> normalizedRowValues = dataSet.normalizeRow(0, true);

        List<String> normalizedValues =  Arrays.asList(new String[]{"0.48718", "0.23077", "0.03077", "0.00000", "0.17949", "0.69231", "1.00000"});

        Assertions.assertTrue( normalizedRowValues.equals(dataSet.getRowValues(0)) );
        Assertions.assertTrue (normalizedRowValues.equals( normalizedValues ));
    }


    /**
     * Test all column normalization
     *
     * @throws IOException
     */
    @Test
    void normalizeAllColumnAlgorithmTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

        dataSet.clearData();
        dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3"}) );
        dataSet.addRow(  Arrays.asList(new String[]{ "100", "100", "100"}) );
        dataSet.addRow(  Arrays.asList(new String[]{ "50",   "50",  "50"}) );
        dataSet.addRow(  Arrays.asList(new String[]{ "11",   "11",  "11"}) );
        dataSet.addRow(  Arrays.asList(new String[]{ "5",     "5",   "5"}) );
        dataSet.addRow(  Arrays.asList(new String[]{ "40",   "40",  "40"}) );
        dataSet.addRow(  Arrays.asList(new String[]{ "140", "140", "140"}) );
        dataSet.addRow(  Arrays.asList(new String[]{ "200", "200", "200"}) );

        for (int qtdColumn =0 ;  qtdColumn < dataSet.getColumnsCount() ; qtdColumn++){
            dataSet.normalizeColumn(qtdColumn, true);
        }

        dataSet.print();

        List<String> normalizedValues0 =  Arrays.asList(new String[]{"0.48718", "0.23077", "0.03077", "0.00000", "0.17949", "0.69231", "1.00000"});
        List<String> normalizedValues1 =  Arrays.asList(new String[]{"0.48718", "0.23077", "0.03077", "0.00000", "0.17949", "0.69231", "1.00000"});
        List<String> normalizedValues2 =  Arrays.asList(new String[]{"0.48718", "0.23077", "0.03077", "0.00000", "0.17949", "0.69231", "1.00000"});

        Assertions.assertTrue( normalizedValues0.equals(dataSet.getColumnValues(0)) );
        Assertions.assertTrue( normalizedValues1.equals(dataSet.getColumnValues(1)) );
        Assertions.assertTrue( normalizedValues2.equals(dataSet.getColumnValues(2)) );
    }

    /**
     * Test column normalization
     *
     * @throws IOException
     */
    @Test
    void columnNormalizationAlgorithmTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

        dataSet.clearData();
        dataSet.addColumn(  Arrays.asList(new String[]{"100", "50", "11", "5", "40", "140", "200"}) , "Column1"  );

        dataSet.print();

        List<String> normalizedRowValues = dataSet.normalizeColumn(0, true);

        List<String> normalizedValues =  Arrays.asList(new String[]{"0.48718", "0.23077", "0.03077", "0.00000", "0.17949", "0.69231", "1.00000"});

        dataSet.print();

        Assertions.assertTrue( normalizedRowValues.equals(dataSet.getColumnValues(0)) );
        Assertions.assertTrue (normalizedRowValues.equals( normalizedValues ));
    }



    /**
     * Test column normalization
     *
     * @throws IOException
     */
    @Test
    void columnNormalizationAlgorithmByColumnValueTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

        dataSet.clearData();
        dataSet.addColumn(  Arrays.asList(new String[]{"100", "50", "11", "5", "40", "140", "200"}) , "Column1"  );

        dataSet.print();

        List<String> normalizedRowValues = dataSet.normalizeColumn("Column1", true);

        List<String> normalizedValues =  Arrays.asList(new String[]{"0.48718", "0.23077", "0.03077", "0.00000", "0.17949", "0.69231", "1.00000"});

        dataSet.print();

        Assertions.assertTrue( normalizedRowValues.equals(dataSet.getColumnValues(0)) );
        Assertions.assertTrue (normalizedRowValues.equals( normalizedValues ));
    }


    /**
     * Test column normalization without Replase
     *
     * @throws IOException
     */
    @Test
    void columnNormalizationWithoutReplaceAlgorithmTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

        dataSet.clearData();
        dataSet.addColumn(  Arrays.asList(new String[]{"100", "50", "11", "5", "40", "140", "200"}) , "Column1"  );

        List<String> noNormalizedValues =  Arrays.asList(new String[]{"100", "50", "11", "5", "40", "140", "200"});
        List<String> normalizedValues =  Arrays.asList(new String[]{"0.48718", "0.23077", "0.03077", "0.00000", "0.17949", "0.69231", "1.00000"});


        List<String> normalizedRowValues = dataSet.normalizeColumn(0, false);

        dataSet.print();

        Assertions.assertTrue (normalizedRowValues.equals( normalizedValues ));
        Assertions.assertTrue( noNormalizedValues.equals(dataSet.getColumnValues(0)) );
    }
}