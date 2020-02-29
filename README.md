# CSVDataSet
CSVDataSet is a library to manipulate a DataSet stored in CSV files for Java language. 

#### Version: 

1.0

#### Authors:

    Jadson Santos - jadsonjs@gmail.com
    
    
### Dependencies
    
    Junit 5.6.0
    
### How do I get set up?

#### From the source code:

   Clone the project -> Import it as a gradle project on your IDE.

#### From the binary:

   CSVDataSet has a binary distribution on **libs/csvdataset-X.Y.jar** directory.
   
   Include it on your classpath.    
    

### How to use

```
# Create a CSV  by row and save to file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );
dataSet.storeData();
```

```
# Create a CSV by column and save to file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.addColumn(  Arrays.asList(new String[]{"1", "2", "3", "4"})   , "Column1"  );
dataSet.addColumn(  Arrays.asList(new String[]{"5", "6", "7", "8"})   , "Column2"  );
dataSet.addColumn(  Arrays.asList(new String[]{"9", "10", "11", "12"}), "Column3"  );
dataSet.storeData();
```


```
# Load CSV from file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
```

```
# Delete the CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.deleteFile();
```

```
# Load CSV from file and Calculating the sum of a column 0 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
BigDecimal sum = dataSet.sumColumn(0)
```


```
# Load CSV from file and calculating the sum of a column "Column1" of CSV dataset
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
BigDecimal sum = dataSet.sumColumn("Column1")
```



```
# Load CSV from file and Calculating the sum of a row 0 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
BigDecimal sum = dataSet.sumRow(0)
```


```
# Load CSV from file and Calculating the mean of a column 10 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
BigDecimal mean = dataSet.meanColumn(10)
```

```
# Load CSV from file and Calculating the mean of a row 10 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
BigDecimal mean = dataSet.meanRow(10)
```



```
# Load CSV from file and Calculating the mean of a column 20 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
BigDecimal median = dataSet.medianColumn(20)
```

```
# Load CSV from file and Calculating the mean of a row 15 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
BigDecimal median = dataSet.medianRow(15)
```


```
# Load CSV from file and Calculating the standard deviation of a column 100 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
BigDecimal stdDev = dataSet.stdDevColumn(100)
```

```
# Load CSV from file and Calculating the standard deviation of a row 100 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
BigDecimal stdDev = dataSet.stdDevRow(100)
```



```
# Load CSV from file and normalizing the values column 1 
# and update the values a of CSV file and save to file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
List<String> newColumnValues = dataSet.normalizeColumn(1, true)
dataSet.storeData();
```


```
# Load CSV from file and normalizing the values column "Column1"
# and update the values a of CSV file and save to file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
List<String> newColumnValues = dataSet.normalizeColumn("Column1", true)
dataSet.storeData();
```


```
# Load CSV from file and normalizing the  values row 1 
# and update the values a of CSV file and save to file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
List<String> newRowValues = dataSet.normalizeRow(1, true)
dataSet.storeData();
```

```
# Load CSV from file and just normalizing the  values row 1 
# but not update the values a of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
List<String> newRowValues = dataSet.normalizeRow(1, false)
```


```
# Load CSV from file and Get the values of a column 5
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
List<String> values = dataSet.getColumnValues(5)
```


```
# Load CSV from file and Get the values of a column 5 as a list of double values
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
List<Double> doubleValues = dataSet.getColumnValuesAsDouble(5)
```


```
# Load CSV from file and Get the values of a column "Column1" as a list of double values
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
List<Double> doubleValues = dataSet.getColumnValuesAsDouble("Column1")
```

```
# Load CSV from file and Get the values of a column 5 as a list of integer values
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
List<Integer> integerValues = dataSet.getColumnValuesAsInteger(5)
```


```
# Load CSV from file and Get the values of a column 5 as a list of BigDecimal values
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
List<BigDecimal> bigDecimalValues = dataSet.getColumnValuesAsBigDecimal(5)
```



```
# Creating a CSV in memory, Normalizing all values and save to file.

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3"}) );
dataSet.addRow(  Arrays.asList(new String[]{ "100", "100", "100"}) );
dataSet.addRow(  Arrays.asList(new String[]{ "50",   "50",  "50"}) );
dataSet.addRow(  Arrays.asList(new String[]{ "11",   "11",  "11"}) );
dataSet.addRow(  Arrays.asList(new String[]{ "5",     "5",   "5"}) );
dataSet.addRow(  Arrays.asList(new String[]{ "40",   "40",  "40"}) );
dataSet.addRow(  Arrays.asList(new String[]{ "140", "140", "140"}) );
dataSet.addRow(  Arrays.asList(new String[]{ "200", "200", "200"}) );

for (int positionColumn =0 ;  positionColumn < dataSet.getColumnsCount() ; positionColumn++){
    dataSet.normalizeColumn(positionColumn, true);
}

dataSet.storeData();

```


```
# Creating a CSV in memory, add and remove rows and columns, sum a column by label and save to file.

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3"}) );
dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3"})  );
dataSet.addRow(  Arrays.asList(new String[]{"4", "5", "6"}) );
dataSet.addRow(  Arrays.asList(new String[]{"7", "8", "9"}) );

dataSet.addColumn(  Arrays.asList(new String[]{"2.5", "5.5", "8.5"}), "Column2.1",  2  );
dataSet.removeColumn("Column1");

dataSet.addRow(Arrays.asList(new String[]{"100", "200", "300"}), 0);
dataSet.removeRow(0);

BigDecimal sum = dataSet.sumColumn("Column2.1") )

dataSet.storeData();
```


### How to run tests

 Run ```gradlew test``` command.
 
  
### Contribution guidelines

Be free to implement new algorithms or correct bugs and submit pull requests. Since, you write a correlated Unit Test that prove that your implementation is correct.

 
