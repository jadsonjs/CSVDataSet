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
 * CSVRecord
 * 25/02/20
 */
package br.com.jadson.csvdataset;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>Class represents CSV row or column.</p>
 *
 * <p>Contains a list of string values, that can be numeric or not. IF theses values were numeric. Some operation can be calculated.</p>
 *
 * Jadson Santos - jadsonjs@gmail.com
 */
class CSVRecord {

    public enum CSVRecordType{ ROW, COLUMN }

    /**
     * The id of a row or column
     */
    private int recordNumber = 0;

    /**
     * If this record is a row or column of CSV file
     */
    private CSVRecordType type;


    /**
     * List of values of specific row or column
     */
    private List<String> values;


    public CSVRecord(CSVRecordType type, int recordNumber) {
        this.type = type;
        this.recordNumber = recordNumber;
        this.values = new ArrayList<>();
    }

    public CSVRecord(CSVRecordType type, int recordNumber, List<String> values){
        this(type, recordNumber);
        this.values = values;
    }

    public void addValue(String value) {
        if(values == null)
            throw new IllegalArgumentException("CSVRecord not initialized properly. ");
        this.values.add(value);
    }

    public void addValue(String value, int position) {
        if(values == null)
            throw new IllegalArgumentException("CSVRecord not initialized properly. ");
        this.values.add(position, value);
    }

    public BigDecimal sumValues() {

        validatedValues();

        BigDecimal total = BigDecimal.ZERO;
        for(String value : values){
            total = total.add(convertToBD(value));
        }
        return total;
    }

    /**
     * Calculate the average of values of specific record
     *
     * @return
     */
    public BigDecimal meanValues() {

        validatedValues();

        BigDecimal sum = sumValues();
        return sum.divide(new BigDecimal( values.size() ), 5, RoundingMode.HALF_UP );
    }

    /**
     * Calculate the median of values of specific record.
     *
     * @return
     */
    public BigDecimal medianValues() {

        validatedValues();

        List<BigDecimal> tempList = new LinkedList<BigDecimal>();
        for (String value : values) {
            tempList.add(convertToBD(value) );
        }

        Collections.sort(tempList);

        BigDecimal median = BigDecimal.ZERO;
        // if old, it the the average of the 2 in the middle
        if (tempList.size() % 2 == 0)
            median = ( ( tempList.get(tempList.size()/2) )
                    .add(   tempList.get(tempList.size()/2-1)  )
            ).divide(new BigDecimal(2), 5, RoundingMode.HALF_UP);
        else {
            // If even, it is the middle element
            median = tempList.get(tempList.size() / 2);
        }

        return median;
    }


    /**
     * Calculate the variance of specific record
     *
     * @return
     */
    public BigDecimal varianceValues() {

        validatedValues();

        BigDecimal variance = BigDecimal.ZERO;

        BigDecimal mean = meanValues();

        //  (  SUM (X - MEAN) ^ 2 ) / N-1
        for(String num: values) {
            BigDecimal sub = convertToBD(num).subtract(mean);
            variance = variance.add( sub.pow(2) );
        }
        variance = variance.divide( new BigDecimal( values.size()), 5, RoundingMode.HALF_UP );

        return variance;
    }


    /**
     * Calculate the Standard Deviation of values of specific record
     *
     * @return
     */
    public BigDecimal stdDevValues() {

        validatedValues();

        BigDecimal variance = varianceValues();

        return variance.sqrt(new MathContext(5));
    }

    /**
     * <p>Normalize using Min-Max:</p>
     *
     * <p>(X - Xmin) / (Xmax - Xmin) </p>
     */
    public List<String> normalizeValues() {

        validatedValues();

        List<String> normalizedValues = new ArrayList<>();

        BigDecimal Xmin = null;
        BigDecimal Xmax = null;

        for(String value: values) {
            BigDecimal X = convertToBD(value);
            if(Xmin == null || X.compareTo(Xmin) <= 0)
                Xmin = X;
            if(Xmax == null || X.compareTo(Xmax) >= 0)
                Xmax = X;
        }

        for(String value : values) {
            if(Xmin.compareTo(Xmax) != 0) {
                BigDecimal X = convertToBD(value);
                normalizedValues.add((X.subtract(Xmin)).divide(Xmax.subtract(Xmin), 5, RoundingMode.HALF_UP).toString());
            }else{
                normalizedValues.add( "1.00000" );
            }
        }

        return normalizedValues;
    }


    /**
     * Convert String to BigDecimal
     * @param value
     * @return
     */
    private BigDecimal convertToBD(String value) {
        try {
            return new BigDecimal(value);
        }catch(NumberFormatException nfe){
            throw new NumberFormatException("CSV File has a no numeric value");
        }
    }

    /**
     * Convert String to Double
     * @param value
     * @return
     */
    private Double convertToDouble(String value) {
        try {
            return Double.valueOf(value);
        }catch(NumberFormatException nfe){
            throw new NumberFormatException("CSV File has a no numeric value");
        }
    }

    /**
     * Convert String to Integer
     * @param value
     * @return
     */
    private Integer convertToInteger(String value) {
        try {
            return Integer.valueOf(value);
        }catch(NumberFormatException nfe){
            throw new NumberFormatException("CSV File has a no numeric value");
        }
    }

    private void validatedValues() {
        if ( values== null || values.size() == 0)
            throw new ArithmeticException("CSV " + type + " has no elements");
    }

    public CSVRecordType getType() {
        return type;
    }

    public long getRecordNumber() {
        return recordNumber;
    }

    public List<String> getValues() {
        return values;
    }

    public List<BigDecimal> getValuesAsBigDecimal() {
        List<BigDecimal> r = new ArrayList<>();
        for (String v : values) {
            r.add(convertToBD(v));
        }
        return r;
    }

    public List<Double> getValuesAsDouble() {
        List<Double> r = new ArrayList<>();
        for (String v : values) {
            r.add(convertToDouble(v));
        }
        return r;
    }

    public List<Integer> getValuesAsInteger() {
        List<Integer> r = new ArrayList<>();
        for (String v : values) {
            r.add(convertToInteger(v));
        }
        return r;
    }


    @Override
    public String toString() {
        return "CSVRecord{" + "type=" + type + "}";
    }

}
