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
 * CSVDataSet
 * 25/02/20
 */
package br.com.jadson;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p> CSVDataSet is a library to manipulate a DataSet stored in CSV files for Java language. <p/>
 *
 * <p>All CSV files need to have header that are the first row of the CSV files. The other rows, from the second to N row, are considered the data of CSV.</p>
 *
 * <p>IF the CSV data are numeric we can make some operation over row or column, like sum, average, median, standard deviation</p>
 *
 * <p>
 *     THe indexes are zero based. So, for a CSV with 3 rows and 5 columns, the indexes are: <br/>
 *          (0)(1)(2)(3)(4) <br/>
 *      (0)  1, 2, 3, 4, 5  <br/>
 *      (1)  6, 7, 8, 9, 0  <br/>
 *      (2)  1, 2, 3, 4, 5  <br/>
 * </p>
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
public class CSVDataSet {

    /**
     * Default separator of CSV file
     */
    private String separator = ",";

    /**
     * Complete file name with path. /home/user/file.csv
     */
    private String fileName = "";


    private static CSVDataSet singleton = null;


    private CSVRecord header;

    /************************************************************
     * Keep rows and columns of the CSV in separated list
     *
     * This turn it slow O(N+Nˆ2) for add and remove rows or columns
     * but some operation over a row or column give us O(N) is the most of cases.
     *
     ************************************************************/

    private List<CSVRecord> rows;

    private List<CSVRecord> columns;

    /************************************************************
     *
     ************************************************************/

    @Deprecated
    private CSVDataSet(){  }

    private CSVDataSet(String fileName){
        if(fileName == null || fileName.trim().isEmpty())
            throw new IllegalArgumentException("File Name can't be null");

        if( ! fileName.endsWith(".csv"))
            throw new IllegalArgumentException("Invalid File Name. It have to end with .csv");

        this.fileName = fileName;
    }

    private CSVDataSet(String fileName, String separator){
        this(fileName);
        if(separator == null || separator.trim().isEmpty())
            throw new IllegalArgumentException("Separator can't be null");
        this.separator = separator;
    }


    public static CSVDataSet getInstance(String fileName){
        if(singleton == null){
            singleton = new CSVDataSet(fileName);
        }
        return singleton;
    }


    public static CSVDataSet getInstance(String fileName, String separator){
        if(singleton == null){
            singleton = new CSVDataSet(fileName, separator);
        }
        return singleton;
    }



    //////////////////////// Load from a file /////////////////////////////

    /**
     * Load the file data to memory
     */
    public void loadData() {

        initializeRowAndColumns();

        try{

            FileReader reader = new FileReader(fileName);

            try ( BufferedReader br = new BufferedReader(reader) ) {

                String line = "";

                boolean firstLine = true;


                while ((line = br.readLine()) != null) {

                    // each position in a line of CSV file
                    String[] rowValues = line.split(separator);

                    if(firstLine){
                        setHeaders(Arrays.asList(rowValues));
                        firstLine = false;
                        continue;
                    }else{
                        addRow( Arrays.asList(rowValues) );
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //////////////////////// Store to a file /////////////////////////////

    /**
     * Save a CSV file to disk
     */
    public void storeData(){

        if(header == null || rows == null || columns == null)
            throw new IllegalArgumentException("DataSet not initialized properly. Call addHeaders and addRow before");

        if(header.getValues().size() != rows.get(0).getValues().size() )
            throw new IllegalArgumentException("Size of header "+header.getValues().size() +" not equal to the size of columns: "+rows.get(0).getValues().size() );

        try( FileWriter csvWriter = new FileWriter( new File(fileName) ) ) {

            csvWriter.append(String.join(separator, header.getValues()));
            csvWriter.append("\n");

            // store data
            for(CSVRecord record : rows ){
                csvWriter.append(String.join(separator, record.getValues()));
                csvWriter.append("\n");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //////////////////////// Clear the memory data  /////////////////////////////

    public void clearData() {
        this.header = null;
        this.rows = null;
        this.columns = null;
    }


    //////////////////////// Create or Delete Data  /////////////////////////////


    public void setHeaders(List<String> headers) {
        header = new CSVRecord(CSVRecord.CSVRecordType.ROW, 0, headers);
    }


    /**
     * Add a new row in the end of CSV
     *
     * <p>
     * 1,2,3,4,5<br/>
     * 6,7,8,9,0<br/>
     * 1,2,3,4,5<br/>
     * </p>
     *
     * <p>
     * addRow({1,2,3,4,5}])
     * </p>
     *
     * <p>returns:</p>
     *
     * <p>
     * 1,2,3,4,5<br/>
     * 6,7,8,9,0<br/>
     * 1,2,3,4,5<br/>
     * 1,2,3,4,5<br/>
     * </p>
     *
     * @param rowValues
     */
    public void addRow(List<String> rowValues) {

        initializeRowAndColumns();

        int rowNumber = rows.size();
        while(containsRow(rowNumber))
            rowNumber++;

        // in row N of CSV file I have all this values
        rows.add( new CSVRecord(CSVRecord.CSVRecordType.ROW, rowNumber, new ArrayList<String>( rowValues) ) );

        // add each value for all columns in the end
        int columnNumber = 0;
        for (String columnValue : rowValues) {
            if( ! containsColumn(columnNumber) )
                columns.add(new CSVRecord(CSVRecord.CSVRecordType.COLUMN, columnNumber));
            columns.get(columnNumber).addValue(columnValue);
            columnNumber++;
        }

        validatedRowSize(rowValues);
    }


    /**
     * Add a new row in specific
     *
     * <p>
     * 1,2,3,4,5<br/>
     * 6,7,8,9,0<br/>
     * 1,2,3,4,5<br/>
     * </p>
     *
     * <p>
     * addRow({1,2,3,4,5}], 1)
     * </p>
     *
     * <p>returns:</p>
     *
     * <p>
     * 1,2,3,4,5<br/>
     * 1,2,3,4,5<br/>
     * 6,7,8,9,0<br/>
     * 1,2,3,4,5<br/>
     * </p>
     *
     * @param rowValues
     * @param position
     */
    public void addRow(List<String> rowValues, int position) {

        initializeRowAndColumns();

        int rowNumber = rows.size();
        while(containsRow(rowNumber))
            rowNumber++;

        // in row "position" of CSV file I have all this values
        rows.add(position, new CSVRecord(CSVRecord.CSVRecordType.ROW, rowNumber, new ArrayList<String>( rowValues) ) );

        // add each value for all columns in the "position"
        int columnNumber = 0;
        for (String colunmValue : rowValues) {
            if( ! containsColumn(columnNumber) )
                columns.add(new CSVRecord(CSVRecord.CSVRecordType.COLUMN, columnNumber));
            columns.get(columnNumber).addValue(colunmValue, position);
            columnNumber++;
        }

        validatedRowSize(rowValues);
    }

    /**
     * remove a specific row of CSV
     *
     * <p>
     * 1,2,3,4,5<br/>
     * 6,7,8,9,0<br/>
     * 1,2,3,4,5<br/>
     * </p>
     *
     * <p>
     * removeRow(1)
     * </p>
     *
     * <p>returns:</p>
     *
     * <p>
     * 1,2,3,4,5<br/>
     * 1,2,3,4,5<br/>
     * </p>
     *
     * @param position
     */
    public void removeRow(int position) {

        if(rows == null || rows.size() < position){
            throw new IllegalArgumentException("There is no row at position: "+position);
        }

        // remove a entire row at "position" of CSV
        rows.remove(position);

        // and for each column, remove one value in the "position"
        int indexColumn = 0;
        for (CSVRecord column : columns) {
            columns.get(indexColumn).getValues().remove(position);
            indexColumn++;
        }
    }


    /**
     * Add a new column in the end of CSV
     *
     * <p>
     * 1,2,3,4,5<br/>
     * 6,7,8,9,0<br/>
     * 1,2,3,4,5<br/>
     * </p>
     *
     * <p>
     * addColumn({1,2,3})
     * </p>
     *
     * <p>returns:</p>
     *
     * <p>
     * 1,2,3,4,5,1<br/>
     * 6,7,8,9,0,2<br/>
     * 1,2,3,4,5,3<br/>
     * </p>
     *
     * @param columnValues
     */
    public void addColumn(List<String> columnValues) {

        initializeRowAndColumns();

        int columnNumber = columns.size();
        while(containsColumn(columnNumber))
            columnNumber++;

        // in column N of CSV file I have all this values
        columns.add( new CSVRecord(CSVRecord.CSVRecordType.COLUMN, columnNumber, new ArrayList<String>( columnValues) ) );

        // add each value for all row in the last
        int rowNumber = 0;
        for (String colunmValue : columnValues) {
            if( ! containsRow(rowNumber) )
                rows.add(new CSVRecord(CSVRecord.CSVRecordType.ROW, rowNumber));
            rows.get(rowNumber).addValue(colunmValue);
            rowNumber++;
        }

        validatedColumnSize(columnValues);
    }


    /**
     * Add a new column in specific position
     *
     * <p>
     * 1,2,3,4,5<br/>
     * 6,7,8,9,0<br/>
     * 1,2,3,4,5<br/>
     * </p>
     *
     * <p>
     * addColumn({1,2,3}, 2)
     * </p>
     *
     * <p>returns:</p>
     *
     * <p>
     * 1,2,1,3,4,5<br/>
     * 6,7,2,8,9,0<br/>
     * 1,2,3,3,4,5<br/>
     * </p>
     *
     * @param columnValues
     * @param position
     */
    public void addColumn(List<String> columnValues, int position) {

        initializeRowAndColumns();

        int columnNumber = columns.size();
        while(containsColumn(columnNumber))
            columnNumber++;

        // in column "position" of CSV file I have all this values
        columns.add(position, new CSVRecord(CSVRecord.CSVRecordType.COLUMN, columnNumber, new ArrayList(columnValues) ) );

        // add each value for all row in the "position"
        int rowNumber = 0;
        for (String colunmValue : columnValues) {
            if( ! containsRow(rowNumber) )
                rows.add(new CSVRecord(CSVRecord.CSVRecordType.ROW, rowNumber));
            rows.get(rowNumber).addValue(colunmValue, position);
            rowNumber++;
        }

        validatedColumnSize(columnValues);
    }

    /**
     * <p>remove a specific column of CSV</p>
     *
     * <p>
     * 1,2,3,4,5<br/>
     * 6,7,8,9,0<br/>
     * 1,2,3,4,5<br/>
     * </p>
     *
     * <p>
     * removeColumn(4)
     * </p>
     *
     * <p>returns:</p>
     *
     * <p>
     * 1,2,3,4<br/>
     * 6,7,8,9<br/>
     * 1,2,3,4<br/>
     * </p>
     *
     * @param position
     */
    public void removeColumn(int position) {

        if(columns == null || columns.size() < position){
            throw new IllegalArgumentException("There is no column at position: "+position);
        }

        // remove entire column at "position" of CSV
        columns.remove(position);

        // and for  each row, remove a specific value at "position"
        int indexRow = 0;
        for (CSVRecord row : rows) {
            rows.get(indexRow).getValues().remove(position);
            indexRow++;
        }
    }



    public int getRowCount(){ return rows != null ? rows.size() : 0 ; }

    public int getColumnsCount(){ return columns != null ? columns.size() : 0 ; }

    public List<String> getRowValues(int rowNumber){
        return getRow(rowNumber).getValues();
    }

    public List<BigDecimal> getRowValuesAsBigDecimal(int rowNumber){ return getRow(rowNumber).getValuesAsBigDecimal(); }

    public List<Double> getRowValuesAsDouble(int rowNumber){ return getRow(rowNumber).getValuesAsDouble(); }

    public List<Integer> getRowValuesAsInteger(int rowNumber){ return getRow(rowNumber).getValuesAsInteger(); }

    public List<String> getColumnValues(int columnNumber){ return getColumn(columnNumber).getValues(); }

    public List<BigDecimal> getColumnValuesAsBigDecimal(int columnNumber){ return getColumn(columnNumber).getValuesAsBigDecimal(); }

    public List<Double> getColumnValuesAsDouble(int columnNumber){ return getColumn(columnNumber).getValuesAsDouble(); }

    public List<Integer> getColumnValuesAsInteger(int columnNumber){ return getColumn(columnNumber).getValuesAsInteger(); }





    ////////////////////   Algorithm over numeric data   ///////////////////////



    public BigDecimal sumColumn(int columnNumber){
        CSVRecord record = getColumn(columnNumber);
        return record.sumValues();
    }

    public BigDecimal sumRow(int rowNumber){
        CSVRecord record = getRow(rowNumber);
        return record.sumValues();
    }

    public BigDecimal meanColumn(int columnNumber){
        CSVRecord record = getColumn(columnNumber);
        return record.meanValues();
    }

    public BigDecimal meanRow(int rowNumber){
        CSVRecord record = getRow(rowNumber);
        return record.meanValues();
    }


    public BigDecimal medianColumn(int columnNumber){
        CSVRecord record = getColumn(columnNumber);
        return record.medianValues();
    }

    public BigDecimal medianRow(int rowNumber){
        CSVRecord record = getRow(rowNumber);
        return record.medianValues();
    }

    public BigDecimal varianceColumn(int columnNumber){
        CSVRecord record = getColumn(columnNumber);
        return record.varianceValues();
    }

    public BigDecimal varianceRow(int rowNumber){
        CSVRecord record = getRow(rowNumber);
        return record.varianceValues();
    }

    public BigDecimal stdDevColumn(int columnNumber){
        CSVRecord record = getColumn(columnNumber);
        return record.stdDevValues();
    }

    public BigDecimal stdDevRow(int rowNumber){
        CSVRecord record = getRow(rowNumber);
        return record.stdDevValues();
    }

    /**
     * Normalize the CSV column values
     *
     * @param columnNumber
     * @param replace IF the old values of the column will be replaced by  normalized Values
     * @return
     */
    public List<String> normalizeColumn(int columnNumber, boolean replace){
        CSVRecord record = getColumn(columnNumber);
        List<String> normalizedValues = record.normalizeValues();
        if(replace) {
            removeColumn(columnNumber);
            addColumn(normalizedValues, columnNumber);
        }
        return normalizedValues;
    }

    /**
     * Normalize the CSV row values
     *
     * @param rowNumber
     * @param replace IF the old values of the row will be replaced by  normalized Values
     * @return
     */
    public List<String> normalizeRow(int rowNumber, boolean replace){
        CSVRecord record = getRow(rowNumber);
        List<String> normalizedValues = record.normalizeValues();

        if(replace) {
            removeRow(rowNumber);
            addRow(normalizedValues, rowNumber);
        }
        return normalizedValues;
    }

    /////////////////////////////////////////////////////////////

    /**
     * Print the CSV
     */
    public void print(){

        System.out.println("Headers");
        System.out.println(header.getValues());

        System.out.println("Row");
        for (CSVRecord row : rows){
            System.out.println("["+row.getType()+"]"+"("+row.getRecordNumber()+")"+row.getValues());
        }

        System.out.println("Columns");
        for (CSVRecord column : columns) {
            System.out.println("["+column.getType()+"]"+"("+column.getRecordNumber()+")");
            for (String value : column.getValues()) {
                System.out.println(value + " ");
            }
        }

    }


    /////////////////////////////////////////////////////////////


    private CSVRecord getColumn(int columnNumber){
        if(columnNumber< 0 || columnNumber >= columns.size())
            throw new IllegalArgumentException("Columns Number "+columnNumber+" not exits");
        return columns.get(columnNumber);
    }

    private CSVRecord getRow(int rowNumber){
        if(rowNumber< 0 || rowNumber >= rows.size())
            throw new IllegalArgumentException("Row Number "+rowNumber+" not exits");
        return rows.get(rowNumber);
    }

    private boolean containsColumn(int columnNumber) {
        for (CSVRecord record : columns){
            if( record.getRecordNumber() == columnNumber )
                return true;
        }
        return false;
    }


    private boolean containsRow(int rowNumber) {
        for (CSVRecord record : rows){
            if( record.getRecordNumber() == rowNumber )
                return true;
        }
        return false;
    }

    private void initializeRowAndColumns() {
        if (rows == null || columns == null) {
            rows = new ArrayList<>();
            columns = new ArrayList<>();
        }
    }

    private void validatedRowSize(List<String> rowValues) {
//        if (rowValues.size() != columns.size() ) {
//            throw new IllegalArgumentException("Invalid number of rows elements. Allow: "  + columns.size());
//        }
//
//        if (rowValues.size() != header.getValues().size() ) {
//            throw new IllegalArgumentException("Invalid number of columns elements. Not same of headers elements: "  + header.getValues().size());
//        }

    }

    private void validatedColumnSize(List<String> columnValues) {
//        if (columnValues.size() != rows.size()) {
//            throw new IllegalArgumentException("Invalid number of columns elements. Allow: "  + rows.size());
//        }
//
//        if (columnValues.size() != header.getValues().size() ) {
//            throw new IllegalArgumentException("Invalid number of columns elements. Not same of headers elements: "  + header.getValues().size());
//        }
    }

}
