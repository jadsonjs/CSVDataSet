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

   CSVDataSet has a binary distribution on **libs/CSVDataSet.X.Y.jar** directory.
   
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
dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3"}) );
dataSet.addColumn(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
dataSet.addColumn(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
dataSet.addColumn(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );
dataSet.storeData();
```


```
# Load CSV from file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
```

```
# Load CSV from file and Calculating the sum of a column 0 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.sumColumn(0)
```

```
# Load CSV from file and Calculating the sum of a row 0 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.sumRow(0)
```


```
# Load CSV from file and Calculating the mean of a column 10 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.meanColumn(10)
```

```
# Load CSV from file and Calculating the mean of a row 10 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.meanRow(10)
```



```
# Load CSV from file and Calculating the mean of a column 20 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.mediaColumn(20)
```

```
# Load CSV from file and Calculating the mean of a row 15 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.mediaRow(15)
```


```
# Load CSV from file and Calculating the standard deviation of a column 100 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.stdDevColumn(100)
```

```
# Load CSV from file and Calculating the standard deviation of a row 100 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.stdDevRow(100)
```



```
# Load CSV from file and Normalizing the values column 1 and update the values a of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.normalizeColumn(1, true)
```

```
# Load CSV from file and Normalizing the  values row 1 and update the values a of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.normalizeRow(1, true)
```


```
# Load CSV from file and Get the values of a column 5 as a list of double values
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.getColumnValuesAsDouble(5)
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

for (int qtdColumn =0 ;  qtdColumn < dataSet.getColumnsCount() ; qtdColumn++){
    dataSet.normalizeColumn(qtdColumn, true);
}

dataSet.storeData();

```


### How to run tests

 Run ```gradlew test``` command.
 
  
### Contribution guidelines

Be free to implement new algorithms or correct bugs and submit pull requests. Since, you write a correlated Unit Test that prove that your implementation is correct.

 
