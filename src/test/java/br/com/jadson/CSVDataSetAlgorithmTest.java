package br.com.jadson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVDataSetAlgorithmTest {

    /**
     * Test sum a row or a column
     */
    @Test
    void rowAndColumnSumAlgorithmTest() throws IOException {

        CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );

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
     * Test mean a row or a column
     */
    @Test
    void rowAndColumnMeanAlgorithmTest() throws IOException {

        CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );

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

        CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );

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
     * Test standard deviation a row or a column
     */
    @Test
    void rowAndColumnStandardDeviationAlgorithmTest() throws IOException {


        CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );

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

        CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );

        dataSet.clearData();
        dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4", "Column5", "Column6", "Column7"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"100", "50", "11", "5", "40", "140", "200"})   );


        List<String> normalizedRowValues = dataSet.normalizeRow(0, true);

        List<String> normalizedValues =  Arrays.asList(new String[]{"0.48718", "0.23077", "0.03077", "0.00000", "0.17949", "0.69231", "1.00000"});

        Assertions.assertTrue( normalizedRowValues.equals(dataSet.getRowValues(0)) );
        Assertions.assertTrue (normalizedRowValues.equals( normalizedValues ));
    }

    /**
     * Test column normalization
     *
     * @throws IOException
     */
    @Test
    void columnNormalizationAlgorithmTest() throws IOException {

        CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );

        dataSet.clearData();
        dataSet.setHeaders( Arrays.asList(new String[]{"Column1"}) );
        dataSet.addColumn(  Arrays.asList(new String[]{"100", "50", "11", "5", "40", "140", "200"})   );


        List<String> normalizedRowValues = dataSet.normalizeColumn(0, true);

        List<String> normalizedValues =  Arrays.asList(new String[]{"0.48718", "0.23077", "0.03077", "0.00000", "0.17949", "0.69231", "1.00000"});

        Assertions.assertTrue( normalizedRowValues.equals(dataSet.getColumnValues(0)) );
        Assertions.assertTrue (normalizedRowValues.equals( normalizedValues ));
    }
}