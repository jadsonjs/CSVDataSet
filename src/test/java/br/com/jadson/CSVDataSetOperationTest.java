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
 * CSVDataSetBerefeTest
 * 25/02/20
 */
package br.com.jadson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * Test creation of data in CSV file
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class CSVDataSetOperationTest {

    /**
     * Test get a row that not exit
     */
    @Test
    void getAddRow() throws IOException {

        CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );

        dataSet.clearData();
        dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );

        dataSet.print();

        Assertions.assertEquals( 3, dataSet.getRowCount() );
        Assertions.assertEquals( 4, dataSet.getColumnsCount() );
    }


    /**
     * Test get a row that not exit
     */
    @Test
    void getRowOutIndex() throws IOException {

        CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );

        dataSet.clearData();
        dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );


        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> { dataSet.getRowValues(3); });
        Assertions.assertEquals( "Row Number 3 not exits", exception.getMessage() );
    }




    /**
     * operation on a not numeric CSV file
     */
    @Test
    void operationNotNumericTest() throws IOException {

        CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );

        dataSet.clearData();
        dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
        dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})     );
        dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})     );
        dataSet.addRow(  Arrays.asList(new String[]{"9", "ABC", "11", "12"}) );

        Exception exception = Assertions.assertThrows(NumberFormatException.class, () -> { dataSet.sumRow(2); });
        Assertions.assertEquals( "CSV File has a no numeric value", exception.getMessage() );

    }


}
