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
 * CSVDataSetOperationTest
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
import java.util.Random;

/**
 * Test creation of data in CSV file
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class CSVDataSetOperationTest {


    //////////////////////////   Ways of build the dataset  //////////////////////////////////

    /**
     * Test a data set with headers
     */
    @Test
    void buidDataSetFromRowsWithHeadersTest() throws IOException {

        List<String> header = Arrays.asList( new String[]{ " ",  "Column0", "Column1", "Column2", "Column3"});
        List<String> row0 = Arrays.asList( new String[]  {"Row0",   "1",       "2",       "3",        "4"} );
        List<String> row1 = Arrays.asList( new String[]  {"Row1",   "5",       "6",       "7",        "8"} );
        List<String> row2 = Arrays.asList( new String[]  {"Row2",   "9",       "10",     "11",       "12"} );

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" );;

        dataSet.addRow(header);
        dataSet.addRow(row0);
        dataSet.addRow(row1);
        dataSet.addRow(row2);

        dataSet.print();


        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "2", "3", "4"} ).equals(dataSet.getRowValues("Row0")));
        Assertions.assertTrue(Arrays.asList( new String[]  {"5", "6", "7", "8"} ).equals(dataSet.getRowValues("Row1")));
        Assertions.assertTrue(Arrays.asList( new String[]  {"9", "10", "11", "12"} ).equals(dataSet.getRowValues("Row2")));

        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "5", "9"} ).equals(dataSet.getColumnValues("Column0")) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"2", "6", "10"} ).equals(dataSet.getColumnValues("Column1")) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"3", "7", "11"} ).equals(dataSet.getColumnValues("Column2")) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"4", "8", "12"} ).equals(dataSet.getColumnValues("Column3")) );

    }


    /**
     * Test a data set with headers
     */
    @Test
    void buidDataSetFromRowsWithColumnHeadersTest() throws IOException {

        List<String> header = Arrays.asList( new String[]{"Column0", "Column1", "Column2", "Column3"});
        List<String> row0 = Arrays.asList( new String[]  {"1",       "2",       "3",        "4"} );
        List<String> row1 = Arrays.asList( new String[]  {"5",       "6",       "7",        "8"} );
        List<String> row2 = Arrays.asList( new String[]  {"9",       "10",     "11",       "12"} );

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" , true, false);

        dataSet.addRow(header);
        dataSet.addRow(row0);
        dataSet.addRow(row1);
        dataSet.addRow(row2);

        dataSet.print();


        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "2", "3", "4"} ).equals(dataSet.getRowValues(0)));
        Assertions.assertTrue(Arrays.asList( new String[]  {"5", "6", "7", "8"} ).equals(dataSet.getRowValues(1)));
        Assertions.assertTrue(Arrays.asList( new String[]  {"9", "10", "11", "12"} ).equals(dataSet.getRowValues(2)));

        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "5", "9"} ).equals(dataSet.getColumnValues("Column0")) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"2", "6", "10"} ).equals(dataSet.getColumnValues("Column1")) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"3", "7", "11"} ).equals(dataSet.getColumnValues("Column2")) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"4", "8", "12"} ).equals(dataSet.getColumnValues("Column3")) );

    }


    /**
     * Test a data set with headers
     */
    @Test
    void buidDataSetFromRowsWithRowHeadersTest() throws IOException {

        List<String> row0 = Arrays.asList( new String[]  {"Row0",   "1",       "2",       "3",        "4"} );
        List<String> row1 = Arrays.asList( new String[]  {"Row1",   "5",       "6",       "7",        "8"} );
        List<String> row2 = Arrays.asList( new String[]  {"Row2",   "9",       "10",     "11",       "12"} );

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", false, true);

        dataSet.addRow(row0);
        dataSet.addRow(row1);
        dataSet.addRow(row2);

        dataSet.print();


        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "2", "3", "4"} ).equals(dataSet.getRowValues("Row0")));
        Assertions.assertTrue(Arrays.asList( new String[]  {"5", "6", "7", "8"} ).equals(dataSet.getRowValues("Row1")));
        Assertions.assertTrue(Arrays.asList( new String[]  {"9", "10", "11", "12"} ).equals(dataSet.getRowValues("Row2")));

        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "5", "9"} ).equals(dataSet.getColumnValues(0)) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"2", "6", "10"} ).equals(dataSet.getColumnValues(1)) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"3", "7", "11"} ).equals(dataSet.getColumnValues(2)) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"4", "8", "12"} ).equals(dataSet.getColumnValues(3)) );

    }



    /**
     * Test a data set with not headers
     */
    @Test
    void buidDataSetFromRowsWithoutHeadersTest() throws IOException {

        List<String> row0 = Arrays.asList( new String[]  {"1",       "2",       "3",        "4"} );
        List<String> row1 = Arrays.asList( new String[]  {"5",       "6",       "7",        "8"} );
        List<String> row2 = Arrays.asList( new String[]  {"9",       "10",     "11",       "12"} );

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", ",", false, false );

        dataSet.addRow(row0);
        dataSet.addRow(row1);
        dataSet.addRow(row2);

        dataSet.print();

        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "2", "3", "4"} ).equals(dataSet.getRowValues(0)));
        Assertions.assertTrue(Arrays.asList( new String[]  {"5", "6", "7", "8"} ).equals(dataSet.getRowValues(1)));
        Assertions.assertTrue(Arrays.asList( new String[]  {"9", "10", "11", "12"} ).equals(dataSet.getRowValues(2)));

        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "5", "9"} ).equals(dataSet.getColumnValues(0)) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"2", "6", "10"} ).equals(dataSet.getColumnValues(1)) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"3", "7", "11"} ).equals(dataSet.getColumnValues(2)) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"4", "8", "12"} ).equals(dataSet.getColumnValues(3)) );

    }


    /**
     * Test build a CSV file adding column
     */
    @Test
    void buidDataSetFromColumnsWithHeadersTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

        dataSet.addColumn(  Arrays.asList(new String[]{ " ", "Row0", "Row1", "Row2", "Row3"}));
        dataSet.addColumn(  Arrays.asList(new String[]{ "Column0", "1", "2", "3", "4"})      );
        dataSet.addColumn(  Arrays.asList(new String[]{ "Column1", "5", "6", "7", "8"})      );
        dataSet.addColumn(  Arrays.asList(new String[]{ "Column2", "9", "10", "11", "12"})   );

        dataSet.print();

        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "2", "3", "4"} ).equals(dataSet.getColumnValues("Column0")));
        Assertions.assertTrue(Arrays.asList( new String[]  {"5", "6", "7", "8"} ).equals(dataSet.getColumnValues("Column1")));
        Assertions.assertTrue(Arrays.asList( new String[]  {"9", "10", "11", "12"} ).equals(dataSet.getColumnValues("Column2")));

        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "5", "9"} ).equals(dataSet.getRowValues("Row0")) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"2", "6", "10"} ).equals(dataSet.getRowValues("Row1")) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"3", "7", "11"} ).equals(dataSet.getRowValues("Row2")) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"4", "8", "12"} ).equals(dataSet.getRowValues("Row3")) );
    }

    /**
     * Test build a CSV file adding column
     */
    @Test
    void buidDataSetFromColumnsWithColumnHeadersTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

        dataSet.addColumn(  Arrays.asList(new String[]{ "Column0", "1", "2", "3", "4"})      );
        dataSet.addColumn(  Arrays.asList(new String[]{ "Column1", "5", "6", "7", "8"})      );
        dataSet.addColumn(  Arrays.asList(new String[]{ "Column2", "9", "10", "11", "12"})   );

        dataSet.print();

        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "2", "3", "4"} ).equals(dataSet.getColumnValues("Column0")));
        Assertions.assertTrue(Arrays.asList( new String[]  {"5", "6", "7", "8"} ).equals(dataSet.getColumnValues("Column1")));
        Assertions.assertTrue(Arrays.asList( new String[]  {"9", "10", "11", "12"} ).equals(dataSet.getColumnValues("Column2")));

        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "5", "9"} ).equals(dataSet.getRowValues(0)) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"2", "6", "10"} ).equals(dataSet.getRowValues(1)) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"3", "7", "11"} ).equals(dataSet.getRowValues(2)) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"4", "8", "12"} ).equals(dataSet.getRowValues(3)) );
    }

    /**
     * Test build a CSV file adding column
     */
    @Test
    void buidDataSetFromColumnsWithRowHeadersTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", false, true );

        dataSet.addColumn(  Arrays.asList(new String[]{"Row0", "Row1", "Row2", "Row3"}));
        dataSet.addColumn(  Arrays.asList(new String[]{ "1", "2", "3", "4"})      );
        dataSet.addColumn(  Arrays.asList(new String[]{ "5", "6", "7", "8"})      );
        dataSet.addColumn(  Arrays.asList(new String[]{ "9", "10", "11", "12"})   );

        dataSet.print();

        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "2", "3", "4"} ).equals(dataSet.getColumnValues(0))    );
        Assertions.assertTrue(Arrays.asList( new String[]  {"5", "6", "7", "8"} ).equals(dataSet.getColumnValues(1))    );
        Assertions.assertTrue(Arrays.asList( new String[]  {"9", "10", "11", "12"} ).equals(dataSet.getColumnValues(2)) );

        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "5", "9"} ).equals(dataSet.getRowValues("Row0")) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"2", "6", "10"} ).equals(dataSet.getRowValues("Row1")) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"3", "7", "11"} ).equals(dataSet.getRowValues("Row2")) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"4", "8", "12"} ).equals(dataSet.getRowValues("Row3")) );
    }

    /**
     * Test build a CSV file adding column
     */
    @Test
    void buidDataSetFromColumnsWithoutHeadersTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", ",", false, false );

        dataSet.addColumn(  Arrays.asList(new String[]{"1", "2", "3", "4"})      );
        dataSet.addColumn(  Arrays.asList(new String[]{"5", "6", "7", "8"})      );
        dataSet.addColumn(  Arrays.asList(new String[]{"9", "10", "11", "12"})   );

        dataSet.print();

        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "2", "3", "4"} ).equals(dataSet.getColumnValues(0)));
        Assertions.assertTrue(Arrays.asList( new String[]  {"5", "6", "7", "8"} ).equals(dataSet.getColumnValues(1)));
        Assertions.assertTrue(Arrays.asList( new String[]  {"9", "10", "11", "12"} ).equals(dataSet.getColumnValues(2)));

        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "5", "9"} ).equals(dataSet.getRowValues( 0) ) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"2", "6", "10"} ).equals(dataSet.getRowValues(1) ) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"3", "7", "11"} ).equals(dataSet.getRowValues(2) ) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"4", "8", "12"} ).equals(dataSet.getRowValues(3) ) );
    }


    //////////////////////////////////////////////////////////////////////////////////

    /**
     * Teste unique column with header
     * @throws IOException
     */
    @Test
    void addUniqueColumnTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet("temp.csv", ",", true, false);

        dataSet.addColumn(Arrays.asList(new String[]{"Column1", "100", "50", "11", "5", "40", "140", "200"}));

        dataSet.print();

        Assertions.assertTrue(Arrays.asList( new String[]  {"100", "50", "11", "5", "40", "140", "200"} ).equals(dataSet.getColumnValues("Column1")));
    }


    /**
     * Teste unique column with header
     * @throws IOException
     */
    @Test
    void addUniqueColumnPositionTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet("temp.csv", ",", true, false);

        dataSet.addColumn(Arrays.asList(new String[]{"Column1", "100", "50", "11", "5", "40", "140", "200"}), 0);

        dataSet.print();

        Assertions.assertTrue(Arrays.asList( new String[]  {"100", "50", "11", "5", "40", "140", "200"} ).equals(dataSet.getColumnValues("Column1")));
    }


    /**
     * Teste unique row with header
     * @throws IOException
     */
    @Test
    void addUniqueRowTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet("temp.csv", ",", false, true);

        dataSet.addRow(Arrays.asList(new String[]{"Row1", "100", "50", "11", "5", "40", "140", "200"}));

        dataSet.print();

        Assertions.assertTrue(Arrays.asList( new String[]  {"100", "50", "11", "5", "40", "140", "200"} ).equals(dataSet.getRowValues("Row1")));
    }

    /**
     * Teste unique row with header
     * @throws IOException
     */
    @Test
    void addUniqueRowPositionTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet("temp.csv", ",", false, true);

        dataSet.addRow(Arrays.asList(new String[]{"Row1", "100", "50", "11", "5", "40", "140", "200"}), 0);

        dataSet.print();

        Assertions.assertTrue(Arrays.asList( new String[]  {"100", "50", "11", "5", "40", "140", "200"} ).equals(dataSet.getRowValues("Row1")));
    }


    /**
     * Teste unique row with header
     * @throws IOException
     */
    @Test
    void addUniqueRowPositionNoExistTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet("temp.csv", ",", false, true);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> { dataSet.addRow(Arrays.asList(new String[]{"Row1", "100", "50", "11", "5", "40", "140", "200"}), 10);  });
        Assertions.assertEquals( "There is not ROW with position: 10", exception.getMessage() );

    }





    /**
     * Test build a CSV file adding row
     */
    @Test
    void addRowTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", ",", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );

        dataSet.print();

        Assertions.assertEquals( 3, dataSet.getRowCount() );
        Assertions.assertEquals( 4, dataSet.getColumnsCount() );

        Assertions.assertTrue(Arrays.asList( new String[]  {"2", "6", "10"} ).equals(dataSet.getColumnValues("Column2")));
    }


    /**
     * Test build a CSV file adding column
     */
    @Test
    void addColumnTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", ";", false, false );

        dataSet.clearData();
        dataSet.addColumn(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addColumn(  Arrays.asList(new String[]{"5", "6", "7", "8"})   );
        dataSet.addColumn(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );

        dataSet.print();

        Assertions.assertEquals( 4, dataSet.getRowCount() );
        Assertions.assertEquals( 3, dataSet.getColumnsCount() );
    }


    /**
     * Test build a CSV add specific row
     */
    @Test
    void addSpecificRowTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", ",", true, true );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"R/C", "Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow( Arrays.asList(new String[]{"Row0", "1", "2", "3", "4"})   );
        dataSet.addRow( Arrays.asList(new String[]{"Row1", "5", "6", "7", "8"})    );
        dataSet.addRow( Arrays.asList(new String[]{"Row3", "9", "10", "11", "12"}) );

        dataSet.print();

        dataSet.addRow(Arrays.asList(new String[]{"Row2", "10", "20", "30", "40"})  , 2);

        dataSet.print();

        Assertions.assertEquals( 4, dataSet.getRowCount() );
        Assertions.assertEquals( 4, dataSet.getColumnsCount() );
        Assertions.assertEquals( Arrays.asList(new String[]{"10", "20", "30", "40"}), dataSet.getRowValues(2) );
        Assertions.assertEquals( Arrays.asList(new String[]{"9", "10", "11", "12"}), dataSet.getRowValues(3) );

        Assertions.assertEquals( Arrays.asList(new String[]{"1", "2", "3", "4"}), dataSet.getRowValues("Row0") );
        Assertions.assertEquals( Arrays.asList(new String[]{"5", "6", "7", "8"}), dataSet.getRowValues("Row1") );
        Assertions.assertEquals( Arrays.asList(new String[]{"10", "20", "30", "40"}), dataSet.getRowValues("Row2") );
        Assertions.assertEquals( Arrays.asList(new String[]{"9", "10", "11", "12"}), dataSet.getRowValues("Row3") );
    }


    /**
     * Test build a CSV add specific column
     */
    @Test
    void addSpecificColumnTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" , ",", true, false);

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );

        dataSet.print();

        dataSet.addColumn(Arrays.asList(new String[]{"Column5", "1", "2", "3"}), 4);

        dataSet.print();

        Assertions.assertEquals( 3, dataSet.getRowCount() );
        Assertions.assertEquals( 5, dataSet.getColumnsCount() );

        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "5", "9"} ).equals(dataSet.getColumnValues("Column1")));
        Assertions.assertTrue(Arrays.asList( new String[]  {"2", "6", "10"} ).equals(dataSet.getColumnValues("Column2")));
        Assertions.assertTrue(Arrays.asList( new String[]  {"3", "7", "11"} ).equals(dataSet.getColumnValues("Column3")));
        Assertions.assertTrue(Arrays.asList( new String[]  {"4", "8", "12"} ).equals(dataSet.getColumnValues("Column4")));
        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "2", "3" } ).equals(dataSet.getColumnValues("Column5")));
    }


    /**
     * Test remove row
     */
    @Test
    void removeRowTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv",",", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );

        dataSet.print();

        dataSet.removeRow(1);

        dataSet.print();

        Assertions.assertEquals( 2, dataSet.getRowCount() );
        Assertions.assertEquals( 4, dataSet.getColumnsCount() );
        Assertions.assertEquals( Arrays.asList(new String[]{"9", "10", "11", "12"}), dataSet.getRowValues(1) );
    }


    /**
     * Test remove columns
     */
    @Test
    void removeColumnTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" ,",", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );

        dataSet.print();

        dataSet.removeColumn(1);

        dataSet.print();

        Assertions.assertEquals( 3, dataSet.getRowCount() );
        Assertions.assertEquals( 3, dataSet.getColumnsCount() );
        Assertions.assertEquals( Arrays.asList(new String[]{"9", "11", "12"}), dataSet.getRowValues(2) );
    }

    /***
     * Test replace a specific row os CSV
     * @throws IOException
     */
    @Test
    void replaceRowTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv",",", true, false  );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );

        dataSet.print();

        dataSet.replaceRow(0, Arrays.asList(new String[]{"10", "20", "30", "40"}));

        dataSet.print();

        Assertions.assertEquals( 3, dataSet.getRowCount() );

        Assertions.assertEquals( Arrays.asList(new String[]{"10", "20", "30", "40"}), dataSet.getRowValues(0) );

    }


    /***
     * Test replace a specific row os CSV
     * @throws IOException
     */
    @Test
    void replaceRowWithLabelTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv"  );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"", "Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"Row1", "1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"Row2", "5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"Row3", "9", "10", "11", "12"}) );

        dataSet.print();

        dataSet.replaceRow(0, Arrays.asList(new String[]{"Row1", "10", "20", "30", "40"}));

        dataSet.print();

        Assertions.assertEquals( 3, dataSet.getRowCount() );

        Assertions.assertEquals( Arrays.asList(new String[]{"10", "20", "30", "40"}), dataSet.getRowValues(0) );

    }

    /**
     * Test replace a specific Column os CSV
     */
    @Test
    void replaceColumnTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv",",", false, false  );

        dataSet.clearData();
        dataSet.addColumn(  Arrays.asList(new String[]{"1", "2", "3", "4"})    );
        dataSet.addColumn(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addColumn(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );

        dataSet.print();

        dataSet.replaceColumn(1, Arrays.asList(new String[]{"50", "60", "70", "80"}));

        dataSet.print();

        Assertions.assertEquals( 3, dataSet.getColumnsCount() );
        Assertions.assertEquals( Arrays.asList(new String[]{"1", "2", "3", "4"}    ), dataSet.getColumnValues(0) );
        Assertions.assertEquals( Arrays.asList(new String[]{"50", "60", "70", "80"}), dataSet.getColumnValues(1) );
        Assertions.assertEquals( Arrays.asList(new String[]{"9", "10", "11", "12"} ), dataSet.getColumnValues(2) );
    }



    /**
     * Test convert values to double
     * @throws IOException
     */
    @Test
    void asDoubleTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv",",", true, false  );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );

        List<Double> result = dataSet.getColumnValuesAsDouble(1);
        List<Double> result2 = dataSet.getRowValuesAsDouble(1);

        Assertions.assertEquals( Arrays.asList(new Double[]{2d, 6d, 10d}), result);
        Assertions.assertEquals( Arrays.asList(new Double[]{5d, 6d, 7d, 8d}), result2);
    }


    /**
     * Test convert values to boolean
     * @throws IOException
     */
    @Test
    void asBooleanTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv",",", true, false  );

        dataSet.clearData();
       dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"true", "false", "true", "false"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"false", "true", "false", "true"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"true", "true", "false", "false"}) );

        List<Boolean> result = dataSet.getColumnValuesAsBoolean("Column3");

        Assertions.assertEquals( Arrays.asList(new Boolean[]{true, false, false}), result);
    }


    /**
     * Test try convert text to boolean
     * @throws IOException
     */
    @Test
    void asBooleanExceptionTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv",",", true, false  );

        dataSet.clearData();
       dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"true", "false", "true", "false"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"false", "true", "Jadson", "true"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"true", "true", "false", "false"}) );

        Exception exception = Assertions.assertThrows(NumberFormatException.class, () -> { dataSet.getColumnValuesAsBoolean("Column3"); });
        Assertions.assertEquals( "value: \"Jadson\" of COLUMN (2) is not a boolean value", exception.getMessage() );
    }

    /**
     * Test get a row that not exit
     */
    @Test
    void getRowOutIndex() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" ,",", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );


        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> { dataSet.getRowValues(3); });
        Assertions.assertEquals( "Row with Position \"3\" does not exits", exception.getMessage() );
    }




    /**
     * operation on a not numeric CSV file
     */
    @Test
    void operationNotNumericTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" ,",", true, false );

        dataSet.clearData();
       dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})     );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})     );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "ABC", "11", "12"}) );

        Exception exception = Assertions.assertThrows(NumberFormatException.class, () -> { dataSet.sumRow(2); });
        Assertions.assertEquals( "value: \"ABC\" of ROW (2) is not a numeric value", exception.getMessage() );

    }


    /**
     * Test add and remove columns and rows
     * @throws IOException
     */
    @Test
    void testOperationsAddAndRemoveColumnsAndRows() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv",",", true, false  );

        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3"})  );
        dataSet.addRow(  Arrays.asList(new String[]{"4", "5", "6"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"7", "8", "9"}) );

        dataSet.print();

        dataSet.addColumn(  Arrays.asList(new String[]{"Column2.1", "2.5", "5.5", "8.5"}), 2  );

        dataSet.print();

        dataSet.removeColumn("Column1");

        dataSet.print();

        dataSet.addRow(Arrays.asList(new String[]{"100", "200", "300"}), 0);

        dataSet.print();

        dataSet.removeRow(0);

        dataSet.print();

        Assertions.assertEquals( 3, dataSet.getColumnsCount() );
        Assertions.assertEquals( 3, dataSet.getRowCount() );

        Assertions.assertTrue( new BigDecimal("16.5").compareTo(dataSet.sumColumn("Column2.1") ) == 0 );


    }


    /**
     * test CSV files that have comma character inside quotes
     */
    @Test
    void splittingOnCommaOutSideQuotesTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv",",", true, false );

        dataSet.clearData();
        dataSet.addRow( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2,300", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8,00"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );

        dataSet.storeData();

        dataSet.print();

        dataSet.clearData();
        dataSet.loadData();

        dataSet.print();

        Assertions.assertEquals( 3, dataSet.getRowCount() );
        Assertions.assertEquals( 4, dataSet.getColumnsCount() );
        Assertions.assertEquals( 4, dataSet.getRowValues(0).size() );
        Assertions.assertEquals( 3, dataSet.getColumnValues(3).size() );

        dataSet.deleteFile();
    }

    /**
     * Test the retorn values not repeated.
     * @throws IOException
     */
    @Test
    void uniqueValuesTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv", false, false );

        dataSet.addColumn(  Arrays.asList(new String[]{"1", "2", "2", "2"})      );
        dataSet.addColumn(  Arrays.asList(new String[]{"5", "2", "7", "8"})      );
        dataSet.addColumn(  Arrays.asList(new String[]{"9", "2", "11", "12"})   );

        dataSet.print();

        Assertions.assertTrue(Arrays.asList( new String[]  {"1", "2"} ).equals(dataSet.getColumnUniqueValues(0)));

        Assertions.assertTrue(Arrays.asList( new String[]  {"2"} ).equals(dataSet.getRowUniqueValues(1)));

    }



    @Test
    void headerValuesTest() throws IOException {

        CSVDataSet dataSet = new CSVDataSet( "temp.csv" );;

        dataSet.addRow( Arrays.asList( new String[]{ " ",  "Column0", "Column0", "Column2", "Column3"}) );
        dataSet.addRow( Arrays.asList( new String[]  {"Row0",   "1",       "2",       "3",        "4"} ) );
        dataSet.addRow( Arrays.asList( new String[]  {"Row0",   "5",       "6",       "7",        "8"} ) );
        dataSet.addRow( Arrays.asList( new String[]  {"Row2",   "9",       "10",     "11",       "12"} ) );

        dataSet.print();


        Assertions.assertTrue(Arrays.asList( new String[]  {"Row0", "Row0", "Row2"} ).equals(dataSet.getRowHeadersValues()) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"Row0", "Row2"} ).equals(dataSet.getRowHeadersUniqueValues())   );

        Assertions.assertTrue(Arrays.asList( new String[]  {"Column0", "Column0", "Column2", "Column3"} ).equals(   dataSet.getColumnHeadersValues()) );
        Assertions.assertTrue(Arrays.asList( new String[]  {"Column2", "Column3", "Column0"} ).equals(  dataSet.getColumnHeadersUniqueValues())   );

    }

}
