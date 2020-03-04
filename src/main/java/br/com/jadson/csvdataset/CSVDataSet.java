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

    /** The values of first line of CSV */
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
     * Save a CSV data in memory to file
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

    /**
     * Clear all CSV data
     */
    public void clearData() {
        this.header = null;
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
    public void setHeaders(List<String> headers) {
        header = new CSVRecord(CSVRecord.CSVRecordType.ROW, 0, new ArrayList<>( headers) );
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

        // in row N of CSV file I have all this values
        rows.add( new CSVRecord(CSVRecord.CSVRecordType.ROW, rows.size(), new ArrayList<String>( rowValues) ) );

        // add each value for all columns in the end
        int columnPosition = 0;
        for (String columnValue : rowValues) {
            if( ! containsColumn(columnPosition) )
                columns.add(new CSVRecord(CSVRecord.CSVRecordType.COLUMN, columnPosition));
            columns.get(columnPosition).addValue(columnValue);
            columnPosition++;
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

        for (int index = position; index < rows.size() ; index++){
            CSVRecord record = rows.get(index);
            record.incrementPosition();
        }

        // in row "position" of CSV file I have all this values
        rows.add(position, new CSVRecord(CSVRecord.CSVRecordType.ROW, position, new ArrayList<String>( rowValues ) ) );

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
    public void addColumn(List<String> columnValues, String headerValue) {

        initializeHeader();
        initializeRowAndColumns();

        // in column N of CSV file I have all this values
        columns.add( new CSVRecord(CSVRecord.CSVRecordType.COLUMN, columns.size(), new ArrayList<String>( columnValues) ) );
        header.getValues().add(headerValue);

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
    public void addColumn(List<String> columnValues, String headerValue, int position) {

        initializeHeader();
        initializeRowAndColumns();

        for (int index = position; index < columns.size() ; index++){
            CSVRecord record = columns.get(index);
            record.incrementPosition();
        }

        // in column "position" of CSV file I have all this values
        columns.add(position, new CSVRecord(CSVRecord.CSVRecordType.COLUMN, position, new ArrayList(columnValues) ) );

        header.getValues().add(position, headerValue);

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

        header.getValues().remove(position);
    }

    /**
     * Remove a column by label
     * @param columnLabel
     */
    public void removeColumn(String columnLabel) {
        removeColumn(getColumnPositionByHeaderLabel(columnLabel));
    }



    public int getRowCount(){ return rows != null ? rows.size() : 0 ; }
    public int getColumnsCount(){ return columns != null ? columns.size() : 0 ; }

    public List<String> getRowValues(int rowPosition)                { return getRow(rowPosition).getValues(); }
    public List<BigDecimal> getRowValuesAsBigDecimal(int rowPosition){ return getRow(rowPosition).getValuesAsBigDecimal(); }
    public List<Double> getRowValuesAsDouble(int rowPosition)        { return getRow(rowPosition).getValuesAsDouble(); }
    public List<Integer> getRowValuesAsInteger(int rowPosition)      { return getRow(rowPosition).getValuesAsInteger(); }
    public List<Boolean> getRowValuesAsBoolean(int rowPosition)      { return getRow(rowPosition).getValuesAsBoolean(); }

    public List<String> getColumnValues(int columnPosition)                { return getColumn(columnPosition).getValues(); }
    public List<BigDecimal> getColumnValuesAsBigDecimal(int columnPosition){ return getColumn(columnPosition).getValuesAsBigDecimal(); }
    public List<Double> getColumnValuesAsDouble(int columnPosition)        { return getColumn(columnPosition).getValuesAsDouble(); }
    public List<Integer> getColumnValuesAsInteger(int columnPosition)      { return getColumn(columnPosition).getValuesAsInteger(); }
    public List<Boolean> getColumnValuesAsBoolean(int columnPosition)      { return getColumn(columnPosition).getValuesAsBoolean(); }


    public List<String> getColumnValues(String columnLabel)                { return getColumnByHeaderLabel(columnLabel).getValues(); }
    public List<BigDecimal> getColumnValuesAsBigDecimal(String columnLabel){ return getColumnByHeaderLabel(columnLabel).getValuesAsBigDecimal(); }
    public List<Double> getColumnValuesAsDouble(String columnLabel)        { return getColumnByHeaderLabel(columnLabel).getValuesAsDouble(); }
    public List<Integer> getColumnValuesAsInteger(String columnLabel)      { return getColumnByHeaderLabel(columnLabel).getValuesAsInteger(); }
    public List<Boolean> getColumnValuesAsBoolean(String columnLabel)      { return getColumnByHeaderLabel(columnLabel).getValuesAsBoolean(); }



    ////////////////////   Algorithm over numeric data   ///////////////////////




    //////////////////////////  operation over column  //////////////////////////////////////


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
        CSVRecord record = getColumnByHeaderLabel(columnLabel);
        int columnPosition = getColumnPositionByHeaderLabel(columnLabel);
        List<String> normalizedValues = record.normalizeValues();
        if(replace) {
            replaceColumn(columnPosition, normalizedValues);
        }
        return normalizedValues;
    }

    /**
     * Replace a column of CSV file
     * @param columnPosition
     * @param values
     */
    public void replaceColumn(int columnPosition, List<String> values){
        String headerValue = header.getValues().get(columnPosition);
        removeColumn(columnPosition);
        addColumn(values, headerValue, columnPosition);
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
            replaceRow(rowNumber, normalizedValues);
        }
        return normalizedValues;
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


    /////////////////////////////////////////////////////////////

    /**
     * Print the CSV
     */
    public void print(){

        System.out.println("Headers");
        System.out.println(header.getValues());

        System.out.println("Row");
        for (CSVRecord row : rows){
            System.out.println("["+row.getType()+"]"+"("+row.getPosition()+")"+row.getValues());
        }

        System.out.println("Columns");
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
        for(String headerLabel : header.getValues()){
            if(headerLabel.equals(columnLabel)) {
                find = true;
                break;
            }

            columnPosition++;
        }

        if(! find) columnPosition = -1;

        if(columnPosition < 0 || columnPosition >= columns.size())
            throw new IllegalArgumentException("Column "+columnLabel+" not exits");

        return columns.get(columnPosition);
    }

    private int getColumnPositionByHeaderLabel(String columnLabel) {
        int columnPosition = 0;
        boolean find = false;
        for (String headerLabel : header.getValues()) {
            if (headerLabel.equals(columnLabel)) {
                find = true;
                break;
            }

            columnPosition++;
        }
        if(! find) columnPosition = -1;
        return columnPosition;
    }


    private CSVRecord getColumn(int columnPosition){
        if(columnPosition< 0 || columnPosition >= columns.size())
            throw new IllegalArgumentException("Column Position "+columnPosition+" not exits");
        return columns.get(columnPosition);
    }

    private CSVRecord getRow(int rowPosition){
        if(rowPosition< 0 || rowPosition >= rows.size())
            throw new IllegalArgumentException("Row Position "+rowPosition+" not exits");
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

    private void initializeHeader() {
        if(header == null)
            setHeaders(Arrays.asList(new String[]{}) );
    }

    private void initializeRowAndColumns() {
        if (rows == null || columns == null) {
            rows = new ArrayList<>();
            columns = new ArrayList<>();

        }
    }

    private void validatedRowSize(List<String> rowValues) {

        if(header == null){
            throw new IllegalArgumentException("Invalid CSV file. No header found");
        }

        // the values need be the same size os headers values
        // header1, header2, header3
        // value1, value2, value3
        if (rowValues.size() != header.getValues().size() ) {
            throw new IllegalArgumentException("Invalid number of row elements. Not same size of headers elements: "  + header.getValues().size());
        }

    }

    private void validatedColumnSize(List<String> columnValues) {

        if(header == null){
            throw new IllegalArgumentException("Invalid CSV file. No header found");
        }

        if(columns.size() > 1){
            // a new column needs to have the same size of first column
            if (columnValues.size() != columns.get(0).getValues().size() ) {
                throw new IllegalArgumentException("Invalid number of columns elements. Columns should have: "  + columns.get(0).getValues().size()+" size. ");
            }
        }

        if(columns.size() != header.getValues().size()){
            throw new IllegalArgumentException("Invalid number of columns elements. Columns should have same size of header labels: "  + header.getValues().size()+" size. ");
        }

    }


}
