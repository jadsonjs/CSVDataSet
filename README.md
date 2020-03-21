# CSVDataSet

CSVDataSet is a library to manipulate a DataSet stored in CSV files for Java language. 

#### Versions: 

 - 1.3 - Replace Column and Row operation
 - 1.4 - Skip quotes and count values algorithm.
 - 2.0 - Refactoring of the library, adding rows labels

#### Authors:

    Jadson Santos - jadsonjs@gmail.com
    
    
### Dependencies
    
    Java 11
    Gradle 5.2.1
    Junit 5.6.0
    
### How do I get set up?

#### From the source code:

   Clone the project -> Import it as a gradle project on your IDE.

#### From the binary:

   CSVDataSet has a binary distribution on **libs/csvdataset-X.Y.jar** directory.
   
   Include it on your classpath.    
    

### How to use

You can build your dataset from rows or columns.  We can have the first column as column headers and the first row as rows headers in the dataset.

Headers are labels that are not process when you calculate some algorithm over the dataset.


```
# You can create a CSV  by row and save to file

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

dataSet.addRow(  Arrays.asList( new String[]{ " ",  "Column0", "Column1", "Column2", "Column3"})    );
dataSet.addRow(  Arrays.asList( new String[]  {"Row0",   "1",       "2",       "3",        "4"} )   );
dataSet.addRow(  Arrays.asList( new String[]  {"Row1",   "5",       "6",       "7",        "8"} )   );
dataSet.addRow(  Arrays.asList( new String[]  {"Row2",   "9",       "10",     "11",       "12"} )   );

dataSet.storeData();

#this code you save a temp.csv file in current directory with the content:
#  "", Column1,Column2,Column3,Column4
#  Row0,1,2,3,4                
#  Row1,5,6,7,8
#  Row2,9,10,11,12

```


```
# You can create a CSV  by row just with column headers and save to file

CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

dataSet.addRow(  Arrays.asList( new String[]{ "Column0", "Column1", "Column2", "Column3"})    );
dataSet.addRow(  Arrays.asList( new String[]  { "1",       "2",       "3",        "4"} )   );
dataSet.addRow(  Arrays.asList( new String[]  { "5",       "6",       "7",        "8"} )   );
dataSet.addRow(  Arrays.asList( new String[]  { "9",       "10",     "11",       "12"} )   );

dataSet.storeData();

#this code you save a temp.csv file in current directory with the content:
#  Column1,Column2,Column3,Column4
#  1,2,3,4                
#  5,6,7,8
#  9,10,11,12

```


```
# You can create a CSV  by row just with rows headers and save to file

CSVDataSet dataSet = new CSVDataSet( "temp.csv", false, true );

dataSet.addRow(  Arrays.asList( new String[]  {"Row0",   "1",       "2",       "3",        "4"} )   );
dataSet.addRow(  Arrays.asList( new String[]  {"Row1",   "5",       "6",       "7",        "8"} )   );
dataSet.addRow(  Arrays.asList( new String[]  {"Row2",   "9",       "10",     "11",       "12"} )   );

dataSet.storeData();

#this code you save a temp.csv file in current directory with the content:
#  Row0,1,2,3,4                
#  Row1,5,6,7,8
#  Row2,9,10,11,12

```


```
# You can create a CSV  by row without headers and save to file

CSVDataSet dataSet = new CSVDataSet( "temp.csv", false, false );

dataSet.addRow(  Arrays.asList( new String[]  {"1",       "2",       "3",        "4"} )   );
dataSet.addRow(  Arrays.asList( new String[]  {"5",       "6",       "7",        "8"} )   );
dataSet.addRow(  Arrays.asList( new String[]  {"9",       "10",     "11",       "12"} )   );

dataSet.storeData();

#this code you save a temp.csv file in current directory with the content:
#  1,2,3,4                
#  5,6,7,8
#  9,10,11,12

```


```
# You can also create a CSV  by columns. It is a way a little confused to visualize, but generated the same result

 CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

dataSet.addColumn(  Arrays.asList(new String[]{ " ", "Row0", "Row1", "Row2", "Row3"}));
dataSet.addColumn(  Arrays.asList(new String[]{ "Column0", "1", "2", "3", "4"})      );
dataSet.addColumn(  Arrays.asList(new String[]{ "Column1", "5", "6", "7", "8"})      );
dataSet.addColumn(  Arrays.asList(new String[]{ "Column2", "9", "10", "11", "12"})   );
 
dataSet.storeData();

#this code you save a temp.csv file in current directory with the content:
#  "", Column1,Column2,Column3,Column4
#  Row0,1,2,3,4                
#  Row1,5,6,7,8
#  Row2,9,10,11,12

```



```
# You can load data form a CSV File

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

dataSet.loadData();

```

```
# Delete the CSV file

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.deleteFile();
```



```
# After create a dataset, you can access columns and rows by label or positions

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

dataSet.addRow(  Arrays.asList( new String[]{ " ",  "Column0", "Column1", "Column2", "Column3"})    );
dataSet.addRow(  Arrays.asList( new String[]  {"Row0",   "1",       "2",       "3",        "4"} )   );
dataSet.addRow(  Arrays.asList( new String[]  {"Row1",   "5",       "6",       "7",        "8"} )   );
dataSet.addRow(  Arrays.asList( new String[]  {"Row2",   "9",       "10",     "11",       "12"} )   );


System.out.println( dataSet.getColumnValues("Column0") )  // prints "1", "5", "9"
System.out.println( dataSet.getRowValues("Row1") )  // prints "5", "6", "7", "8"


System.out.println( dataSet.getColumnValues(0) )  // prints "1", "5", "9"
System.out.println( dataSet.getRowValues(1) )  // prints "5", "6", "7", "8"


```


```
# You can remove specific columns and rows

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

dataSet.addRow(  Arrays.asList( new String[]{ " ",  "Column0", "Column1", "Column2", "Column3"})    );
dataSet.addRow(  Arrays.asList( new String[]  {"Row0",   "1",       "2",       "3",        "4"} )   );
dataSet.addRow(  Arrays.asList( new String[]  {"Row1",   "5",       "6",       "7",        "8"} )   );
dataSet.addRow(  Arrays.asList( new String[]  {"Row2",   "9",       "10",     "11",       "12"} )   );

dataSet.removeColumn(1);
dataSet.removeRow(0);

```






```
# Load CSV from file and calculating the sum of a column position "0" of CSV file (the second column after the rows headers)

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
BigDecimal sum = dataSet.sumColumn(0);

```


```
# Columns can be accessed by Position or by the Header Label
# Create CSV  and calculating the sum of a column with label "Column1" of CSV

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

dataSet.addRow(  Arrays.asList( new String[]{ " ",  "Column0", "Column1", "Column2", "Column3"})    );
dataSet.addRow(  Arrays.asList( new String[]  {"Row0",   "1",       "2",       "3",        "4"} )   );
dataSet.addRow(  Arrays.asList( new String[]  {"Row1",   "5",       "6",       "7",        "8"} )   );
dataSet.addRow(  Arrays.asList( new String[]  {"Row2",   "9",       "10",     "11",       "12"} )   );

BigDecimal sum = dataSet.sumColumn("Column1"); // return sum == 18
BigDecimal sum2 = dataSet.sumColumn(1);        // return sum == 18


```



```
# Load CSV from file and Calculating the mean of a column position 10 of CSV file

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
BigDecimal mean = dataSet.meanColumn(10);

```


```
# Load CSV from file and Calculating the mean of a row position 10 of CSV file

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
BigDecimal mean = dataSet.meanRow(10);

```



```
# Load CSV from file and Calculating the mean of a column 20 of CSV file

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
BigDecimal median = dataSet.medianColumn(20);

```

```
# Load CSV from file and Calculating the mean of a row 15 of CSV file

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
BigDecimal median = dataSet.medianRow(15);

```


```
# Load CSV from file and Calculating the standard deviation of a column 100 of CSV file

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
BigDecimal stdDev = dataSet.stdDevColumn(100);

```



```
# Load CSV from file and Calculating the standard deviation of a row 100 of CSV file

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
BigDecimal stdDev = dataSet.stdDevRow(100);

```



```
# Load CSV from file and normalizing the values column 1 
# and update the values a of CSV file and save to file

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
List<String> newColumnValues = dataSet.normalizeColumn(1, true);
dataSet.storeData();

```


```
# Load CSV from file and normalizing the values column "Column1"
# and update the values a of CSV file and save to file

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
List<String> newColumnValues = dataSet.normalizeColumn("Column1", true);
dataSet.storeData();

```


```
# Load CSV from file and normalizing the  values row 1 
# and update the values a of CSV file and save to file

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
List<String> newRowValues = dataSet.normalizeRow(1, true);
dataSet.storeData();

```


```
# Load CSV from file and just normalizing the  values row 1 
# but not update the values a of CSV file

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
List<String> newRowValues = dataSet.normalizeRow(1, false);

```


```
# Load CSV from file and Get the values of a column 5

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
List<String> values = dataSet.getColumnValues(5);

```


```
# Load CSV from file and Get the values of a column by label

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
List<String> values = dataSet.getColumnValues("Column2");

```



```
# Load CSV from file and Get the values of a column 5 as a list of double values

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
List<Double> doubleValues = dataSet.getColumnValuesAsDouble(5);

```



```
# Load CSV from file and Get the values of a column 5 as a list of integer values

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
List<Integer> integerValues = dataSet.getColumnValuesAsInteger(5);

```



```
# Load CSV from file and Get the values of a column 5 as a list of BigDecimal values

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );
dataSet.loadData();
List<BigDecimal> bigDecimalValues = dataSet.getColumnValuesAsBigDecimal(5);

```



```
# Creating a CSV in memory, Normalizing all values and after save to file.

CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false );

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

CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false  );
dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3"}) );
dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3"})  );
dataSet.addRow(  Arrays.asList(new String[]{"4", "5", "6"}) );
dataSet.addRow(  Arrays.asList(new String[]{"7", "8", "9"}) );

dataSet.addColumn(  Arrays.asList(new String[]{"2.5", "5.5", "8.5"}), "Column2.5",  2  );
dataSet.removeColumn("Column1");

dataSet.addRow( Arrays.asList(new String[]{"100", "200", "300"}) );
dataSet.removeRow(0);

BigDecimal sum = dataSet.sumColumn("Column2.5") )

dataSet.storeData();

```


```
# we can sum/mean/median/stdDev the values of column "Column3" just where "Column5" value == true

CSVDataSet dataSet = new CSVDataSet( "temp.csv", true, false  );

dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4", "Column5"}) );
dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4", "true"})   );
dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8", "false"})    );
dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12", "true"}) );

//  sumCol == 14
BigDecimal sumCol = dataSet.sumColumnByMatching("Column3", "Column5", "true");

```


### How to run tests

 Run ```gradlew test``` command.
 
  
### Contribution guidelines

Be free to implement new algorithms or correct bugs and submit pull requests. Since, you write a correlated Unit Test that prove that your implementation is correct.

 
