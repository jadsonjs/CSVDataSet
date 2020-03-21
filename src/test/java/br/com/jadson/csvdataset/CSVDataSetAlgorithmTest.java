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

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );

        BigDecimal sumRow = dataSet.sumRow(2);
        BigDecimal sumCol = dataSet.sumColumn(3);

        Assertions.assertTrue( new BigDecimal("42").compareTo(sumRow) == 0 );
        Assertions.assertTrue (new BigDecimal("24").compareTo(sumCol) == 0 );
    }


    /**
     * Test sum a row or a column by specific column value
     */
    @Test
    void rowAndColumnSumAlgorithmByColumnValueTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4", "Column5"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4", "true"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8", "false"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12", "true"}) );

        // sum the values of column position 3 just where column5 == true
        BigDecimal sumCol = dataSet.sumColumnByMatching(3, "Column5", "true");

        // sum the values of column "Column3" just where "Column5" value == true
        BigDecimal sumCol2 = dataSet.sumColumnByMatching("Column3", "Column5", "true");

        Assertions.assertTrue (new BigDecimal("16").compareTo(sumCol) == 0 );
        Assertions.assertTrue (new BigDecimal("14").compareTo(sumCol2) == 0 );
    }


    /**
     * Test sum a row or a column
     */
    @Test
    void columnSumAlgorithmByLabelTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );

        BigDecimal sumCol = dataSet.sumColumn("Column3");

        Assertions.assertTrue (new BigDecimal("21").compareTo(sumCol) == 0 );
    }


    /**
     * Count a number of values for a column by column label
     * @throws IOException
     */
    @Test
    void countAlgorithmTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" , true, false);

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "8"}) );

        BigDecimal countRow = dataSet.countRowValues(1, "8");

        BigDecimal countCol1 = dataSet.countColumnValues(3, "8");
        BigDecimal countCol2 = dataSet.countColumnValues("Column4", "8");

        Assertions.assertTrue (new BigDecimal("1").compareTo(countRow) == 0 );

        Assertions.assertTrue (new BigDecimal("2").compareTo(countCol1) == 0 );
        Assertions.assertTrue (new BigDecimal("2").compareTo(countCol2) == 0 );
    }


    /**
     * Test mean a row or a column
     */
    @Test
    void rowAndColumnMeanAlgorithmTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );


        BigDecimal meanRow = dataSet.meanRow(2);
        BigDecimal meanCol = dataSet.meanColumn(3);

        Assertions.assertTrue( new BigDecimal("10.5").compareTo(meanRow) == 0 );
        Assertions.assertTrue (new BigDecimal("8").compareTo(meanCol) == 0 );
    }


    @Test
    void columnMeanAlgorithmByColumnValueTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" , true, false);

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4", "Column5"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4", "true"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8", "false"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12", "true"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "1", "1", "1", "true"}) );

        BigDecimal meanCol = dataSet.meanColumnByMatching("Column4", "Column5", "true");

        Assertions.assertTrue (new BigDecimal("5.66667").compareTo(meanCol) == 0 );
    }


    /**
     * Test median a row or a column
     */
    @Test
    void rowAndColumnMedianAlgorithmTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );


        BigDecimal medianRow = dataSet.medianRow(2);
        BigDecimal medianCol = dataSet.medianColumn(3);

        Assertions.assertTrue( new BigDecimal("10.5").compareTo(medianRow) == 0 );
        Assertions.assertTrue (new BigDecimal("8").compareTo(medianCol) == 0 );
    }


    @Test
    void columnMedianAlgorithmByColumnValueTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4", "Column5"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4", "true"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8", "false"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12", "true"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "1", "1", "1", "true"}) );

        BigDecimal meanCol = dataSet.medianColumnByMatching("Column4", "Column5", "true");

        Assertions.assertTrue (new BigDecimal("4").compareTo(meanCol) == 0 );
    }


    /**
     * Test median a row or a column
     */
    @Test
    void rowAndColumnVarianceAlgorithmTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" , true, false);

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
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


        CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4", "Column5", "Column6", "Column7", "Column8", "Column9" }) );
        dataSet.addRow(  Arrays.asList(new String[]{"4", "9", "11", "12", "17", "5", "8", "12", "14"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8", "5", "6", "7", "8", "1"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8", "5", "6", "7", "8", "1"}) );


        BigDecimal stdDevRow = dataSet.stdDevRow(0);

        Assertions.assertTrue( new BigDecimal("3.9378").compareTo(stdDevRow) == 0 );
    }


    @Test
    void rowMeanAlgorithmRowValuesTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4", "Column5", "Column6", "Column7", "Column8", "Column9", "Column10" }) );
        dataSet.addRow(  Arrays.asList(new String[]{"4", "100", "9", "11", "12", "17", "5", "8", "12", "14"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "100", "6", "7", "8", "5", "6", "7", "8", "1"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "100", "6", "7", "8", "5", "6", "7", "8", "1"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"YES", "NO", "YES", "YES", "YES", "YES", "YES", "YES", "YES", "YES"}) );

        BigDecimal meanRow = dataSet.meanRowByMatching(0, 3, "YES");

        Assertions.assertTrue( new BigDecimal("10.22222").compareTo(meanRow) == 0 );
    }


    @Test
    void rowStandardDeviationAlgorithmRowValuesTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4", "Column5", "Column6", "Column7", "Column8", "Column9", "Column10" }) );
        dataSet.addRow(  Arrays.asList(new String[]{"4", "100", "9", "11", "12", "17", "5", "8", "12", "14"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "100", "6", "7", "8", "5", "6", "7", "8", "1"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "100", "6", "7", "8", "5", "6", "7", "8", "1"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"YES", "NO", "YES", "YES", "YES", "YES", "YES", "YES", "YES", "YES"}) );


        BigDecimal stdDevRow = dataSet.stdDevRowByMatching(0, 3, "YES");

        Assertions.assertTrue( new BigDecimal("3.9378").compareTo(stdDevRow) == 0 );
    }



    @Test
    void columnStandardDeviationAlgorithmColumnValuesTest() throws IOException {


        CSVDataSet dataSet = new CSVDataSet( "temp.csv" , true, false);

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4" }) );
        dataSet.addRow(  Arrays.asList(new String[]{"4", "9", "4", "YES"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "200", "NO"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "9", "YES"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "11", "YES"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "12", "YES"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "17", "YES"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "100", "NO"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "5", "YES"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "8", "YES"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "12", "YES"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "14", "YES"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "1", "NO"}) );


        BigDecimal stdDevRow = dataSet.stdDevColumnByMatching("Column3", "Column4", "YES" );

        Assertions.assertTrue( new BigDecimal("3.9378").compareTo(stdDevRow) == 0 );
    }

    /**
     * Test row normalization
     *
     * @throws IOException
     */
    @Test
    void rowNormalizationAlgorithmTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4", "Column5", "Column6", "Column7"}) );
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

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3"}) );
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

        List<String> normalizedValues =  Arrays.asList(new String[]{"0.48718", "0.23077", "0.03077", "0.00000", "0.17949", "0.69231", "1.00000"});

        Assertions.assertTrue( normalizedValues.equals(dataSet.getColumnValues(0)) );
        Assertions.assertTrue( normalizedValues.equals(dataSet.getColumnValues(1)) );
        Assertions.assertTrue( normalizedValues.equals(dataSet.getColumnValues(2)) );
    }

    /**
     * Test column normalization
     *
     * @throws IOException
     */
    @Test
    void columnNormalizationAlgorithmTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false);

        dataSet.addColumn(  Arrays.asList(new String[]{"Column1", "100", "50", "11", "5", "40", "140", "200"}) );

        dataSet.print();

        List<String> normalizedColumnValues = dataSet.normalizeColumn(0, true);

        List<String> normalizedValues =  Arrays.asList(new String[]{"0.48718", "0.23077", "0.03077", "0.00000", "0.17949", "0.69231", "1.00000"});

        dataSet.print();

        System.out.println("returned values: "+dataSet.getColumnValues(0));

        Assertions.assertTrue( normalizedValues.equals( dataSet.getColumnValues(0)) );

    }



    /**
     * Test column normalization
     *
     * @throws IOException
     */
    @Test
    void columnNormalizationAlgorithmByColumnValueTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

        dataSet.clearData();
        dataSet.addColumn(  Arrays.asList(new String[]{"Column1", "100", "50", "11", "5", "40", "140", "200"}) );

        List<String> normalizedRowValues = dataSet.normalizeColumn("Column1", true);

        dataSet.print();

        Assertions.assertTrue(  dataSet.getColumnValues("Column1").equals(
            Arrays.asList(new String[]{"0.48718", "0.23077", "0.03077", "0.00000", "0.17949", "0.69231", "1.00000"})
                ) );
    }


    /**
     * Test column normalization without Replase
     *
     * @throws IOException
     */
    @Test
    void columnNormalizationWithoutReplaceAlgorithmTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

        dataSet.clearData();
        dataSet.addColumn(  Arrays.asList(new String[]{"Column1", "100", "50", "11", "5", "40", "140", "200"})  );

        List<String> noNormalizedValues =  Arrays.asList(new String[]{"100", "50", "11", "5", "40", "140", "200"});
        List<String> normalizedValues =  Arrays.asList(new String[]{"0.48718", "0.23077", "0.03077", "0.00000", "0.17949", "0.69231", "1.00000"});


        List<String> normalizedRowValues = dataSet.normalizeColumn(0, false);

        dataSet.print();

        Assertions.assertTrue (normalizedRowValues.equals( normalizedValues ));
        Assertions.assertTrue( noNormalizedValues.equals(dataSet.getColumnValues(0)) );
    }
}