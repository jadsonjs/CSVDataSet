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
package br.com.jadson.csvdataset;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

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

    /**
     * Possition [0][0] when you have columnsHeader and rowsHeader
     * usually this value does make no sense and is ignored
     */
    private String crossHeader = "";

    /** By default the values of first line of CSV are the headers by default */
    private CSVRecord columnsHeader;

    /** By default the  values of first columns of CSV are the headers by default */
    private CSVRecord rowsHeader;

    /* If the CSV first line will be consider not a valid data, but header */
    boolean containsColumnsHeaders = true;

    /* If the CSV first column will be consider not a valid data, but header */
    boolean containsRowsHeaders = true;

    /************************************************************
     * THis is the real values, exclude headers algorithms are executed only over this data. Headers are not computed.
     *
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

    /**
     * Default constructor with a file name
     * @param fileName
     */
    public CSVDataSet(String fileName){
        if(fileName == null || fileName.trim().isEmpty())
            throw new IllegalArgumentException("File Name can't be null");

        if( ! fileName.endsWith(".csv"))
            throw new IllegalArgumentException("Invalid File Name. It have to end with .csv");

        this.fileName = fileName;
    }

    /**
     * Constructor with CSV separator
     * @param fileName
     * @param separator
     */
    public CSVDataSet(String fileName, String separator){
        this(fileName);
        if(separator == null || separator.trim().isEmpty() || separator.length() != 1)
            throw new IllegalArgumentException("Separator can't be null and have to be a character.");
        this.separator = separator;
    }

    /**
     * Full constructor with the dataset does not contains headers.
     * @param fileName
     * @param containsColumnsHeaders
     * @param containsRowsHeaders
     */
    public CSVDataSet(String fileName, boolean containsColumnsHeaders, boolean containsRowsHeaders){
        this(fileName);
        this.containsColumnsHeaders = containsColumnsHeaders;
        this.containsRowsHeaders = containsRowsHeaders;
    }

    /**
     * Full constructor with the dataset does not contains headers.
     * @param fileName
     * @param separator
     * @param containsColumnsHeaders
     * @param containsRowsHeaders
     */
    public CSVDataSet(String fileName, String separator, boolean containsColumnsHeaders, boolean containsRowsHeaders){
        this(fileName, separator);
        this.containsColumnsHeaders = containsColumnsHeaders;
        this.containsRowsHeaders = containsRowsHeaders;
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

                List<String> rowsHeaders = new ArrayList<>();

                while ((line = br.readLine()) != null) {

                    // https://stackoverflow.com/questions/18893390/splitting-on-comma-outside-quotes
                    // that is followed by an even number of double quotes. In other words, it splits on comma outside the double quotes.
                    // This will work provided you have balanced quotes in your string.
                    String[] rowValues = line.split(separator+"(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                    if(firstLine && containsColumnsHeaders){ // fist line is column header

                        List<String> columnsHeaders = new ArrayList<>();
                        boolean firstElementOfColumn = true;

                        for (String value : rowValues) {

                            if (value.startsWith("\"") && value.endsWith("\"")) {
                                value = value.substring(1, value.length() - 1);
                            }

                            if(firstElementOfColumn && containsRowsHeaders ) {
                                firstElementOfColumn = false;
                                crossHeader = value;
                            }else{
                                columnsHeaders.add(value);
                            }

                        }

                        if(containsColumnsHeaders)
                            setColumnsHeaders(columnsHeaders);

                        firstLine = false;
                        continue;

                    }else{

                        firstLine = false;

                        boolean firstElementOfRow = true;

                        List<String> row = new ArrayList<>();
                        for (String value : rowValues){
                            if(value.startsWith("\"") && value.endsWith("\"")) {
                                value = value.substring(1, value.length()-1);
                            }

                            if(firstElementOfRow && containsRowsHeaders ) {
                                rowsHeaders.add(value);
                                firstElementOfRow = false;
                            }

                            row.add(value);

                        }

                        addRow( row );
                    }

                }

                if(containsRowsHeaders)
                    setRowsHeaders(rowsHeaders);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //////////////////////// Store to a file /////////////////////////////



    /**
     * Save a CSV data in memory to file
     */
    public void storeData(){

        if( rows == null || columns == null)
            throw new IllegalArgumentException("DataSet not initialized properly. Call addHeaders and addRow before");


        try( FileWriter csvWriter = new FileWriter( new File(fileName) ) ) {

            if(containsColumnsHeaders) {
                // the first element is the crossHeader that is just a label and is disregarded
                if(containsRowsHeaders)
                    columnsHeader.getValues().add(0, crossHeader);

                csvWriter.append(String.join(separator, columnsHeader.getValues()));
                csvWriter.append("\n");
            }

            // store data to a CSV file.
            int rowCount = 0;
            for(CSVRecord record : rows ){

                if(containsRowsHeaders)
                    csvWriter.append( rowsHeader.getValues().get(rowCount)+separator );

                int size = record.getValues().size();
                for (int i = 0 ; i < record.getValues().size() ; i++ ){
                    String value = record.getValues().get(i);
                    writerValuereplacingSeparator(csvWriter, size, i, value);
                }
                csvWriter.append("\n");

                rowCount++;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // if some data has contains separator character, skip this put the value between double quotes "12,00"
    private void writerValuereplacingSeparator(FileWriter csvWriter, int size, int i, String value) throws IOException {
        if(value.contains(separator))
            csvWriter.append( "\""+value+"\""+  ( i < size-1 ? separator : "" ) );
        else
            csvWriter.append( value+( i < size-1 ? separator : "" ) );
    }


    //////////////////////// Clear the memory data  /////////////////////////////

    /**
     * Clear all CSV data
     */
    public void clearData() {
        this.columnsHeader = null;
        this.rows = null;
        this.columns = null;
    }

    /**
     * Delete the csv file
     */
    public void deleteFile() {
        try {
            File f= new File(fileName);           //file to be delete
            if(f.delete()){                      //returns Boolean value
                System.out.println(f.getName() + " deleted");   //getting and printing the file name
            } else {
                System.out.println("failed");
            }
        } catch(Exception e) { e.printStackTrace(); }
    }


    //////////////////////// Create or Delete Data  /////////////////////////////


    /**
     * Set CSV headers values
     * @param headers
     */
    private void setColumnsHeaders(List<String> headers) {
        columnsHeader = new CSVRecord(CSVRecord.CSVRecordType.COLUMN, 0, new ArrayList<>( headers) );
    }

    private void setRowsHeaders(List<String> headers) {
        rowsHeader = new CSVRecord(CSVRecord.CSVRecordType.ROW, 0, new ArrayList<>( headers) );
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

        if(rowValues == null || rowValues.size() == 0 ){
            throw new IllegalArgumentException("Row does not have values");
        }

        initializeData();

        boolean isFirstRow = rows.size() == 0 && ! columnsHeader.containsValues();

        // element [0][0] is eliminated
        if(isFirstRow && containsRowsHeaders && containsColumnsHeaders) {
            crossHeader = rowValues.get(0);
            rowValues = rowValues.subList(1, rowValues.size());
            setColumnsHeaders(rowValues);
            return;
        }

        List<String> rowsRealValues = new ArrayList<>();

        // the fist values of rows is a row headers, the other values are the row values
        if(containsRowsHeaders) {
            rowsHeader.addValue(rowValues.get(0));
            rowsRealValues = rowValues.subList(1, rowValues.size());
        }else{
            rowsRealValues = rowValues;
        }

        if(isFirstRow && containsColumnsHeaders){
            setColumnsHeaders(rowValues); // all values are column heards
        }else{
            // all values are rows values
            rows.add( new CSVRecord(CSVRecord.CSVRecordType.ROW, rows.size(), new ArrayList<String>( rowsRealValues) ) );

            // for all columns , add a row value
            int columnPosition = 0;
            for (String columnValue : rowsRealValues) {
                if( ! containsColumn(columnPosition) )
                    columns.add(new CSVRecord(CSVRecord.CSVRecordType.COLUMN, columnPosition));
                columns.get(columnPosition).addValue(columnValue);
                columnPosition++;
            }

            validatedRowSize(rowsRealValues);
        }
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

        if(rowValues == null || rowValues.size() == 0 ){
            throw new IllegalArgumentException("Row does not have values");
        }

        initializeData();

        for (int index = position; index < rows.size() ; index++){
            CSVRecord record = rows.get(index);
            record.incrementPosition();
        }

        List<String> rowsRealValues = new ArrayList<>();

        // the first element is the header
        if(containsRowsHeaders){
            rowsHeader.addValue(rowValues.get(0), position);
            rowsRealValues = rowValues.subList(1, rowValues.size());
        }else{
            rowsRealValues = rowValues;
        }

        // in row "position" of CSV file I have all this values
        rows.add(position, new CSVRecord(CSVRecord.CSVRecordType.ROW, position, new ArrayList<String>( rowsRealValues ) ) );

        // add each value for all columns in the "position"
        int columnNumber = 0;
        for (String colunmValue : rowsRealValues) {
            if( ! containsColumn(columnNumber) )
                columns.add(new CSVRecord(CSVRecord.CSVRecordType.COLUMN, columnNumber));
            columns.get(columnNumber).addValue(colunmValue, position);
            columnNumber++;
        }

        validatedRowSize(rowsRealValues);

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

        for (int index = position+1; index < rows.size() ; index++){
            CSVRecord record = rows.get(index);
            record.decrementPosition();
        }

        // remove a entire row at "position" of CSV
        rows.remove(position);

        // and for each column, remove one value in the "position"
        int indexColumn = 0;
        for (CSVRecord column : columns) {
            columns.get(indexColumn).getValues().remove(position);
            indexColumn++;
        }

        if(containsRowsHeaders)
            rowsHeader.getValues().remove(position);

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

        if(columnValues == null || columnValues.size() == 0 ){
            throw new IllegalArgumentException("Column does not have values");
        }

        initializeData();

        boolean isFirstColumn = columns.size() == 0 && ! rowsHeader.containsValues();

        // element [0][0] is eliminated
        if(isFirstColumn && containsRowsHeaders && containsColumnsHeaders) {
            crossHeader = columnValues.get(0);
            columnValues = columnValues.subList(1, columnValues.size());
            setRowsHeaders(columnValues);
            return;
        }

        List<String> columnsRealValues = new ArrayList<>();

        // the fist values of column is a column headers, the other values are the row values
        if(containsColumnsHeaders) {
            columnsHeader.addValue(columnValues.get(0));
            columnsRealValues = columnValues.subList(1, columnValues.size());
        }else{
            columnsRealValues = columnValues;
        }

        if(isFirstColumn && containsRowsHeaders){
            setRowsHeaders(columnValues); // all values are column heards
        }else{
            // all values are rows values
            columns.add( new CSVRecord(CSVRecord.CSVRecordType.COLUMN, columns.size(), new ArrayList<String>( columnsRealValues) ) );

            // for all columns , add a row value
            int rowNumber = 0;
            for (String colunmValue : columnsRealValues) {
                if( ! containsRow(rowNumber) )
                    rows.add(new CSVRecord(CSVRecord.CSVRecordType.ROW, rowNumber));
                rows.get(rowNumber).addValue(colunmValue);
                rowNumber++;
            }

            validatedColumnSize(columnsRealValues);
        }
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

        if(columnValues == null || columnValues.size() == 0 ){
            throw new IllegalArgumentException("Column does not have values");
        }

        initializeData();

        for (int index = position; index < columns.size() ; index++){
            CSVRecord record = columns.get(index);
            record.incrementPosition();
        }

        List<String> columnsRealValues = new ArrayList<>();

        // the first element is the header
        if(containsColumnsHeaders){
            columnsHeader.addValue(columnValues.get(0), position);
            columnsRealValues = columnValues.subList(1, columnValues.size());
        }else{
            columnsRealValues = columnValues;
        }


        // in column "position" of CSV file I have all this values
        columns.add(position, new CSVRecord(CSVRecord.CSVRecordType.COLUMN, position, new ArrayList(columnsRealValues) ) );

        // add each value for all row in the "position"
        int rowNumber = 0;
        for (String colunmValue : columnsRealValues) {
            if( ! containsRow(rowNumber) )
                rows.add(new CSVRecord(CSVRecord.CSVRecordType.ROW, rowNumber));
            rows.get(rowNumber).addValue(colunmValue, position);
            rowNumber++;
        }

        validatedColumnSize(columnsRealValues);

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

        for (int index = position+1; index < columns.size() ; index++){
            CSVRecord record = columns.get(index);
            record.decrementPosition();
        }

        // remove entire column at "position" of CSV
        columns.remove(position);

        // and for  each row, remove a specific value at "position"
        int indexRow = 0;
        for (CSVRecord row : rows) {
            rows.get(indexRow).getValues().remove(position);
            indexRow++;
        }

        if(containsColumnsHeaders)
            columnsHeader.getValues().remove(position);
    }

    /**
     * Remove a column by label
     * @param columnLabel
     */
    public void removeColumn(String columnLabel) {
        removeColumn(getColumnPositionByHeaderLabel(columnLabel));
    }

    public List<String> getRowHeadersValues( )                { return rowsHeader.getValues();                         }
    public List<String> getRowHeadersUniqueValues( )          { return getUniqueValues(rowsHeader.getValues() );       }
    public List<String> getColumnHeadersValues( )             { return columnsHeader.getValues();                      }
    public List<String> getColumnHeadersUniqueValues( )       { return getUniqueValues(columnsHeader.getValues() );    }

    public int getRowCount(){ return rows != null ? rows.size() : 0 ; }
    public int getColumnsCount(){ return columns != null ? columns.size() : 0 ; }

    public List<String> getRowValues(int rowPosition)                { return getRow(rowPosition).getValues(); }
    public List<String> getRowUniqueValues(int rowPosition)          { return getUniqueValues(getRowValues(rowPosition)); }

    public List<BigDecimal> getRowValuesAsBigDecimal(int rowPosition){ return getRow(rowPosition).getValuesAsBigDecimal(); }
    public List<Double> getRowValuesAsDouble(int rowPosition)        { return getRow(rowPosition).getValuesAsDouble(); }
    public List<Integer> getRowValuesAsInteger(int rowPosition)      { return getRow(rowPosition).getValuesAsInteger(); }
    public List<Boolean> getRowValuesAsBoolean(int rowPosition)      { return getRow(rowPosition).getValuesAsBoolean(); }

    public List<String> getRowValues(String rowLabel)                { return getRowByHeaderLabel(rowLabel).getValues(); }
    public List<String> getRowUniqueValues(String rowLabel)          { return getUniqueValues( getRowValues(rowLabel)) ; }

    public List<BigDecimal> getRowValuesAsBigDecimal(String rowLabel){ return getRowByHeaderLabel(rowLabel).getValuesAsBigDecimal(); }
    public List<Double> getRowValuesAsDouble(String rowLabel)        { return getRowByHeaderLabel(rowLabel).getValuesAsDouble(); }
    public List<Integer> getRowValuesAsInteger(String rowLabel)      { return getRowByHeaderLabel(rowLabel).getValuesAsInteger(); }
    public List<Boolean> getRowValuesAsBoolean(String rowLabel)      { return getRowByHeaderLabel(rowLabel).getValuesAsBoolean(); }

    public List<String> getColumnValues(int columnPosition)                { return getColumn(columnPosition).getValues(); }
    public List<String> getColumnUniqueValues(int columnPosition)         {   return getUniqueValues(getColumnValues(columnPosition)); }

    public List<BigDecimal> getColumnValuesAsBigDecimal(int columnPosition){ return getColumn(columnPosition).getValuesAsBigDecimal(); }
    public List<Double> getColumnValuesAsDouble(int columnPosition)        { return getColumn(columnPosition).getValuesAsDouble(); }
    public List<Integer> getColumnValuesAsInteger(int columnPosition)      { return getColumn(columnPosition).getValuesAsInteger(); }
    public List<Boolean> getColumnValuesAsBoolean(int columnPosition)      { return getColumn(columnPosition).getValuesAsBoolean(); }


    public List<String> getColumnValues(String columnLabel)                { return getColumnByHeaderLabel(columnLabel).getValues(); }
    public List<String> getColumnUniqueValues(String columnLabel)                { return getUniqueValues(getColumnValues(columnLabel)); }

    public List<BigDecimal> getColumnValuesAsBigDecimal(String columnLabel){ return getColumnByHeaderLabel(columnLabel).getValuesAsBigDecimal(); }
    public List<Double> getColumnValuesAsDouble(String columnLabel)        { return getColumnByHeaderLabel(columnLabel).getValuesAsDouble(); }
    public List<Integer> getColumnValuesAsInteger(String columnLabel)      { return getColumnByHeaderLabel(columnLabel).getValuesAsInteger(); }
    public List<Boolean> getColumnValuesAsBoolean(String columnLabel)      { return getColumnByHeaderLabel(columnLabel).getValuesAsBoolean(); }


    private List<String> getUniqueValues( List<String> aList) {
        Set<String> hSet = new HashSet<String>();
        for (String x : aList)
            hSet.add(x);
        return  new ArrayList<>(hSet);
    }

    /**
     * Return the values of a row that match with values of other row
     *
     * Example:
     *    dataSet.getRowValuesByMatching(0, 3, "Q4")
     *
     * return all row(0) values where row(3) row has the value "Q4"
     *
     * @param rowNumber
     * @param referenceRowNumber
     * @param referenceRowValue
     * @return
     */
    public List<String> getRowValuesByMatching(int rowNumber, int referenceRowNumber, String referenceRowValue) {
        CSVRecord referenceRow = getRow(referenceRowNumber);
        List<Integer> indexes = referenceRow.getIndexesOfValue(referenceRowValue);
        CSVRecord record = getRow(rowNumber);
        return record.getValues(indexes);
    }


    /**
     * Return the values of a row that match with values of other row
     *
     * Example:
     *    dataSet.getRowValuesByMatching("project", "Quarter", "Q4")
     *
     * return all row "project" values where "Quarter" row has the value "Q4"
     *
     * @param rowLabel
     * @param referenceRowLabel
     * @param referenceRowValue
     * @return
     */
    public List<String> getRowValuesByMatching(String rowLabel, String referenceRowLabel, String referenceRowValue) {
        CSVRecord referenceColumn = getRowByHeaderLabel(referenceRowLabel);
        List<Integer> indexes = referenceColumn.getIndexesOfValue(referenceRowValue);
        CSVRecord record = getRowByHeaderLabel(rowLabel);
        return record.getValues(indexes);
    }


    /**
     * Return the values of a column that match with values of other column
     *
     * Example:
     *    dataSet.getColumnValuesByMatching(0, 3, "Q4")
     *
     * return all column(0) values where column(0) column has the value "Q4"
     *
     * @param columnNumber
     * @param referenceColumnNumber
     * @param referenceColumnValue
     * @return
     */
    public List<String> getColumnValuesByMatching(int columnNumber, int referenceColumnNumber, String referenceColumnValue) {
        CSVRecord referenceColumn = getColumn(referenceColumnNumber);
        List<Integer> indexes = referenceColumn.getIndexesOfValue(referenceColumnValue);
        CSVRecord record = getColumn(columnNumber);
        return record.getValues(indexes);
    }

    /**
     * Return the values of a column that match with values of other column
     *
     * Example:
     *    dataSet.getColumnValuesByMatching("project", "Quarter", "Q4")
     *
     * return all "project" column values where "Quarter" column has the value "Q4"
     *
     * @param columnLabel
     * @param referenceColumnLabel
     * @param referenceColumnValue
     * @return
     */
    public List<String> getColumnValuesByMatching(String columnLabel, String referenceColumnLabel, String referenceColumnValue) {
        CSVRecord referenceColumn = getColumnByHeaderLabel(referenceColumnLabel);
        List<Integer> indexes = referenceColumn.getIndexesOfValue(referenceColumnValue);
        CSVRecord record = getColumnByHeaderLabel(columnLabel);
        return record.getValues(indexes);
    }



    ////////////////////   Algorithm over numeric data   ///////////////////////




    //////////////////////////  operation over column  //////////////////////////////////////


    public BigDecimal countColumnValues(int columnNumber, String matchingValue){
        CSVRecord record = getColumn(columnNumber);
        return record.countValues(matchingValue);
    }

    public BigDecimal countColumnValues(String columnLabel, String matchingValue) {
        CSVRecord record = getColumnByHeaderLabel(columnLabel);
        return record.countValues(matchingValue);
    }

    public BigDecimal sumColumn(int columnNumber){
        CSVRecord record = getColumn(columnNumber);
        return record.sumValues();
    }

    public BigDecimal sumColumn(String columnLabel){
        CSVRecord record = getColumnByHeaderLabel(columnLabel);
        return record.sumValues();
    }

    /**
     * Operation sum column elements values in the position that match with values of a reference column.
     *
     * @param columnNumber
     * @param referenceColumnLabel
     * @param referenceColumnValue
     * @return
     */
    public BigDecimal sumColumnByMatching(int columnNumber, String referenceColumnLabel, String referenceColumnValue) {
        CSVRecord referenceColumn = getColumnByHeaderLabel(referenceColumnLabel);
        List<Integer> indexes = referenceColumn.getIndexesOfValue(referenceColumnValue);
        CSVRecord record = getColumn(columnNumber);
        return record.sumValues(indexes);
    }

    public BigDecimal sumColumnByMatching(String columnLabel, String referenceColumnLabel, String referenceColumnValue) {
        CSVRecord referenceColumn = getColumnByHeaderLabel(referenceColumnLabel);
        List<Integer> indexes = referenceColumn.getIndexesOfValue(referenceColumnValue);
        CSVRecord record = getColumnByHeaderLabel(columnLabel);
        return record.sumValues(indexes);
    }




    public BigDecimal meanColumn(int columnNumber){
        CSVRecord record = getColumn(columnNumber);
        return record.meanValues();
    }

    public BigDecimal meanColumn(String columnLabel){
        CSVRecord record = getColumnByHeaderLabel(columnLabel);
        return record.meanValues();
    }

    public BigDecimal meanColumnByMatching(int columnNumber, String referenceColumnLabel, String referenceColumnValue) {
        CSVRecord referenceColumn = getColumnByHeaderLabel(referenceColumnLabel);
        List<Integer> indexes = referenceColumn.getIndexesOfValue(referenceColumnValue);
        CSVRecord record = getColumn(columnNumber);
        return record.meanValues(indexes);
    }

    public BigDecimal meanColumnByMatching(String columnLabel, String referenceColumnLabel, String referenceColumnValue) {
        CSVRecord referenceColumn = getColumnByHeaderLabel(referenceColumnLabel);
        List<Integer> indexes = referenceColumn.getIndexesOfValue(referenceColumnValue);
        CSVRecord record = getColumnByHeaderLabel(columnLabel);
        return record.meanValues(indexes);
    }




    public BigDecimal medianColumn(int columnNumber){
        CSVRecord record = getColumn(columnNumber);
        return record.medianValues();
    }

    public BigDecimal medianColumn(String columnLabel){
        CSVRecord record = getColumnByHeaderLabel(columnLabel);
        return record.medianValues();
    }


    public BigDecimal medianColumnByMatching(int columnNumber, String referenceColumnLabel, String referenceColumnValue) {
        CSVRecord referenceColumn = getColumnByHeaderLabel(referenceColumnLabel);
        List<Integer> indexes = referenceColumn.getIndexesOfValue(referenceColumnValue);
        CSVRecord record = getColumn(columnNumber);
        return record.medianValues(indexes);
    }

    public BigDecimal medianColumnByMatching(String columnLabel, String referenceColumnLabel, String referenceColumnValue) {
        CSVRecord referenceColumn = getColumnByHeaderLabel(referenceColumnLabel);
        List<Integer> indexes = referenceColumn.getIndexesOfValue(referenceColumnValue);
        CSVRecord record = getColumnByHeaderLabel(columnLabel);
        return record.medianValues(indexes);
    }





    public BigDecimal varianceColumn(int columnNumber){
        CSVRecord record = getColumn(columnNumber);
        return record.varianceValues();
    }


    public BigDecimal varianceColumn(String columnLabel){
        CSVRecord record = getColumnByHeaderLabel(columnLabel);
        return record.varianceValues();
    }


    public BigDecimal varianceColumnByMatching(int columnNumber, String referenceColumnLabel, String referenceColumnValue) {
        CSVRecord referenceColumn = getColumnByHeaderLabel(referenceColumnLabel);
        List<Integer> indexes = referenceColumn.getIndexesOfValue(referenceColumnValue);
        CSVRecord record = getColumn(columnNumber);
        return record.varianceValues(indexes);
    }


    public BigDecimal varianceColumnByMatching(String columnLabel, String referenceColumnLabel, String referenceColumnValue) {
        CSVRecord referenceColumn = getColumnByHeaderLabel(referenceColumnLabel);
        List<Integer> indexes = referenceColumn.getIndexesOfValue(referenceColumnValue);
        CSVRecord record = getColumnByHeaderLabel(columnLabel);
        return record.varianceValues(indexes);
    }




    public BigDecimal stdDevColumn(int columnNumber){
        CSVRecord record = getColumn(columnNumber);
        return record.stdDevValues();
    }


    public BigDecimal stdDevColumn(String columnLabel){
        CSVRecord record = getColumnByHeaderLabel(columnLabel);
        return record.stdDevValues();
    }


    public BigDecimal stdDevColumnByMatching(int columnNumber, String referenceColumnLabel, String referenceColumnValue) {
        CSVRecord referenceColumn = getColumnByHeaderLabel(referenceColumnLabel);
        List<Integer> indexes = referenceColumn.getIndexesOfValue(referenceColumnValue);
        CSVRecord record = getColumn(columnNumber);
        return record.stdDevValues(indexes);
    }


    public BigDecimal stdDevColumnByMatching(String columnLabel, String referenceColumnLabel, String referenceColumnValue) {
        CSVRecord referenceColumn = getColumnByHeaderLabel(referenceColumnLabel);
        List<Integer> indexes = referenceColumn.getIndexesOfValue(referenceColumnValue);
        CSVRecord record = getColumnByHeaderLabel(columnLabel);
        return record.stdDevValues(indexes);
    }



    /**
     * Normalize the CSV column values
     *
     * @param columnPosition
     * @param replace IF the old values of the column will be replaced by  normalized Values
     * @return
     */
    public List<String> normalizeColumn(int columnPosition, boolean replace){
        CSVRecord record = getColumn(columnPosition);
        List<String> normalizedValues = record.normalizeValues();
        if(replace) {
            if(containsColumnsHeaders) // add header at first position
                normalizedValues.add(0, columnsHeader.getValues().get(columnPosition));
            replaceColumn(columnPosition, normalizedValues);
        }
        return normalizedValues;
    }


    /**
     * Normalize the CSV column values
     *
     * @param columnLabel
     * @param replace IF the old values of the column will be replaced by  normalized Values
     * @return
     */
    public List<String> normalizeColumn(String columnLabel, boolean replace){
        int columnPosition = getColumnPositionByHeaderLabel(columnLabel);
        return normalizeColumn(columnPosition, replace);
    }


    /**
     * Replace a column of CSV file
     * @param columnPosition
     * @param values
     */
    public void replaceColumn(int columnPosition, List<String> values){
        removeColumn(columnPosition);
        addColumn(values, columnPosition);
    }

    /**
     * Replace a column of CSV file
     * @param columnLabel
     * @param values
     */
    public void replaceColumn(String columnLabel, List<String> values){
        int columnPosition =  getColumnPositionByHeaderLabel(columnLabel);
        replaceColumn(columnPosition, values);
    }







    ////////////////////////////// operation over rows ////////////////////////////////////


    public BigDecimal countRowValues(int rowNumber, String matchingValue){
        CSVRecord record = getRow(rowNumber);
        return record.countValues(matchingValue);
    }

    public BigDecimal sumRow(int rowNumber){
        CSVRecord record = getRow(rowNumber);
        return record.sumValues();
    }

    public BigDecimal sumRowByMatching(int rowNumber, int referenceRowNumber, String referenceRowValue) {
        CSVRecord referenceRow = getRow(referenceRowNumber);
        List<Integer> indexes = referenceRow.getIndexesOfValue(referenceRowValue);
        CSVRecord record = getRow(rowNumber);
        return record.sumValues(indexes);
    }

    public BigDecimal sumRowByMatching(String rowLabel, String referenceRowLabel, String referenceRowValue) {
        CSVRecord referenceRow = getRowByHeaderLabel(referenceRowLabel);
        List<Integer> indexes = referenceRow.getIndexesOfValue(referenceRowValue);
        CSVRecord record = getRowByHeaderLabel(rowLabel);
        return record.sumValues(indexes);
    }


    public BigDecimal meanRow(int rowNumber){
        CSVRecord record = getRow(rowNumber);
        return record.meanValues();
    }

    public BigDecimal meanRowByMatching(int rowNumber, int referenceRowNumber, String referenceRowValue) {
        CSVRecord referenceRow = getRow(referenceRowNumber);
        List<Integer> indexes = referenceRow.getIndexesOfValue(referenceRowValue);
        CSVRecord record = getRow(rowNumber);
        return record.meanValues(indexes);
    }

    public BigDecimal medianRow(int rowNumber){
        CSVRecord record = getRow(rowNumber);
        return record.medianValues();
    }

    public BigDecimal medianRowByMatching(int rowNumber, int referenceRowNumber, String referenceRowValue) {
        CSVRecord referenceRow = getRow(referenceRowNumber);
        List<Integer> indexes = referenceRow.getIndexesOfValue(referenceRowValue);
        CSVRecord record = getRow(rowNumber);
        return record.medianValues(indexes);
    }

    public BigDecimal varianceRow(int rowNumber){
        CSVRecord record = getRow(rowNumber);
        return record.varianceValues();
    }

    public BigDecimal varianceRowByMatching(int rowNumber, int referenceRowNumber, String referenceRowValue) {
        CSVRecord referenceRow = getRow(referenceRowNumber);
        List<Integer> indexes = referenceRow.getIndexesOfValue(referenceRowValue);
        CSVRecord record = getRow(rowNumber);
        return record.varianceValues(indexes);
    }

    public BigDecimal stdDevRow(int rowNumber){
        CSVRecord record = getRow(rowNumber);
        return record.stdDevValues();
    }

    public BigDecimal stdDevRowByMatching(int rowNumber, int referenceRowNumber, String referenceRowValue) {
        CSVRecord referenceRow = getRow(referenceRowNumber);
        List<Integer> indexes = referenceRow.getIndexesOfValue(referenceRowValue);
        CSVRecord record = getRow(rowNumber);
        return record.stdDevValues(indexes);
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
            if(containsRowsHeaders) // add header at first position
                normalizedValues.add(0, rowsHeader.getValues().get(rowNumber));
            replaceRow(rowNumber, normalizedValues);
        }
        return normalizedValues;
    }

    /* Normalize the CSV row values
     *
     * @param rowLabel
     * @param replace IF the old values of the column will be replaced by  normalized Values
     * @return
      */
    public List<String> normalizeRow(String rowLabel, boolean replace){
        int rowPosition = getColumnPositionByHeaderLabel(rowLabel);
        return normalizeRow(rowPosition, replace);
    }


    /**
     * Replace a row of CSV file
     * @param rowNumber
     * @param values
     */
    public void replaceRow(int rowNumber, List<String> values){
        removeRow(rowNumber);
        addRow(values, rowNumber);
    }

    /**
     * Replace a column of CSV file
     * @param rowLabel
     * @param values
     */
    public void replaceRow(String rowLabel, List<String> values){
        int rowPosition =  getRowPositionByHeaderLabel(rowLabel);
        replaceRow(rowPosition, values);
    }


    /////////////////////////////////////////////////////////////

    /**
     * Print the CSV
     */
    public void print(){

        System.out.println("Cross Header");
        System.out.println("'"+crossHeader+"'");

        System.out.println("Columns Headers");
        System.out.println(columnsHeader.getValues());

        System.out.println("Rows Headers");
        System.out.println(rowsHeader.getValues());

        System.out.println("Rows Values");
        for (CSVRecord row : rows){
            System.out.println("["+row.getType()+"]"+"("+row.getPosition()+")"+row.getValues());
        }

        System.out.println("Columns Values ");
        for (CSVRecord column : columns) {
            System.out.println("["+column.getType()+"]"+"("+column.getPosition()+")");
            for (String value : column.getValues()) {
                System.out.println(value + " ");
            }
        }

    }


    /////////////////////////////////////////////////////////////

    /**
     * Return the column correspondent to the header label
     * @param columnLabel
     * @return
     */
    private CSVRecord getColumnByHeaderLabel(String columnLabel){
        int columnPosition = 0;
        boolean find = false;
        for(String headerLabel : columnsHeader.getValues()){
            if(headerLabel.equals(columnLabel)) {
                find = true;
                break;
            }

            columnPosition++;
        }

        if(! find) columnPosition = -1;

        if(columnPosition < 0 || columnPosition >= columns.size())
            throw new IllegalArgumentException("Column \""+columnLabel+"\" does not exits");

        return columns.get(columnPosition);
    }


    /**
     * Return the column correspondent to the header label
     * @param rowLabel
     * @return
     */
    private CSVRecord getRowByHeaderLabel(String rowLabel){
        int rowPosition = 0;
        boolean find = false;
        for(String headerLabel : rowsHeader.getValues()){
            if(headerLabel.equals(rowLabel)) {
                find = true;
                break;
            }

            rowPosition++;
        }

        if(! find)
            throw new IllegalArgumentException("There is not row: "+rowLabel);

        if(rowPosition < 0 || rowPosition >= rows.size())
            throw new IllegalArgumentException("Row \""+rowLabel+"\" does not exits");

        return rows.get(rowPosition);
    }

    private int getColumnPositionByHeaderLabel(String columnLabel) {
        int columnPosition = 0;
        boolean find = false;
        for (String headerLabel : columnsHeader.getValues()) {
            if (headerLabel.equals(columnLabel)) {
                find = true;
                break;
            }

            columnPosition++;
        }
        if(! find)
            throw new IllegalArgumentException("There is not column: "+columnLabel);

        return columnPosition;
    }


    private int getRowPositionByHeaderLabel(String rowLabel) {
        int rowPosition = 0;
        boolean find = false;
        for (String headerLabel : rowsHeader.getValues()) {
            if (headerLabel.equals(rowLabel)) {
                find = true;
                break;
            }

            rowPosition++;
        }
        if(! find)
            throw new IllegalArgumentException("There is not row: "+rowLabel);

        return rowPosition;
    }


    private CSVRecord getColumn(int columnPosition){
        if(isNotInitializeRowAndColumns())
            throw new IllegalArgumentException("Data set has no data or data was not loaded ");

        if(columnPosition< 0 || columnPosition >= columns.size())
            throw new IllegalArgumentException("Column with Position \""+columnPosition+"\" does not exits");

        return columns.get(columnPosition);
    }

    private CSVRecord getRow(int rowPosition){
        if(isNotInitializeRowAndColumns())
            throw new IllegalArgumentException("Data set has no data or data was not loaded ");

        if(rowPosition< 0 || rowPosition >= rows.size())
            throw new IllegalArgumentException("Row with Position \""+rowPosition+"\" does not exits");

        return rows.get(rowPosition);
    }

    private boolean containsColumn(int position) {
        for (CSVRecord record : columns){
            if( record.getPosition() == position )
                return true;
        }
        return false;
    }


    private boolean containsRow(int position) {
        for (CSVRecord record : rows){
            if( record.getPosition() == position )
                return true;
        }
        return false;
    }

    private void initializeColumnHeader() {
        if(columnsHeader == null)
            setColumnsHeaders(Arrays.asList(new String[]{}) );
    }

    private void initializeRowsHeader() {
        if(rowsHeader == null)
            setRowsHeaders(Arrays.asList(new String[]{}) );
    }

    private void initializeRowAndColumns() {
        if (rows == null || columns == null) {
            rows = new ArrayList<>();
            columns = new ArrayList<>();
        }
    }

    //initializes the list of data
    private void initializeData() {
        if(columnsHeader == null)
            setColumnsHeaders(Arrays.asList(new String[]{}) );
        if(rowsHeader == null)
            setRowsHeaders(Arrays.asList( new String[]{}) );
        if (rows == null || columns == null) {
            rows = new ArrayList<>();
            columns = new ArrayList<>();
        }
    }

    private boolean isNotInitializeRowAndColumns() {
        if (rows == null || columns == null) {
            return true;
        }
        return false;
    }

    private void validatedRowSize(List<String> rowValues) {

        if(columnsHeader == null){
            throw new IllegalArgumentException("Invalid CSV file. No header found");
        }

        // the values need be the same size os headers values
        // header1, header2, header3
        // value1, value2, value3
        if ( containsColumnsHeaders && rowValues.size() != columnsHeader.getValues().size() ) {
            throw new IllegalArgumentException("Invalid number of row elements: "+rowValues.size()+". Not same size of column headers elements: "  + columnsHeader.getValues().size());
        }

    }

    private void validatedColumnSize(List<String> columnValues) {

        if(containsColumnsHeaders && columnsHeader == null){
            throw new IllegalArgumentException("Invalid CSV file. No Column header found");
        }

        if(columns.size() > 1){
            // a new column needs to have the same size of first column
            if (columnValues.size() != columns.get(0).getValues().size() ) {
                throw new IllegalArgumentException("Invalid number of columns elements: "+columnValues.size()+". Columns should have: "  + columns.get(0).getValues().size()+" elements. ");
            }
        }

        if(containsColumnsHeaders && columns.size() != columnsHeader.getValues().size()){
            throw new IllegalArgumentException("Invalid number of columns elements "+columns.size()+". Columns should have same size of header labels: "  + columnsHeader.getValues().size()+" size. ");
        }

    }

}
