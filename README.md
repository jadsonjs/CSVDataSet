# CSVDataSet
CSVDataSet is a library to manipulate a DataSet stored in CSV files for Java language. 

##### Version: 

0.1

##### Authors:

    Jadson Santos

##### Use Examples

```
# Save CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4"}) );
dataSet.addRow(  Arrays.asList(new String[]{"1", "2", "3", "4"})   );
dataSet.addRow(  Arrays.asList(new String[]{"5", "6", "7", "8"})    );
dataSet.addRow(  Arrays.asList(new String[]{"9", "10", "11", "12"}) );
dataSet.storeData();
```


```
# Load CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
```

```
# Calculating the sum of a column 0 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.sumColumn(0)
```

```
# Calculating the sum of a row 0 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.sumRow(0)
```


```
# Calculating the mean of a column 10 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.meanColumn(10)
```

```
# Calculating the mean of a row 10 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.meanRow(10)
```



```
# Calculating the mean of a column 20 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.mediaColumn(20)
```

```
# Calculating the mean of a row 15 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.mediaRow(15)
```


```
# Calculating the standard deviation of a column 100 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.stdDevColumn(100)
```

```
# Calculating the standard deviation of a row 100 of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.stdDevRow(100)
```



```
# Normalizing the values column 1 and update the values a of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.normalizeColumnColumn(1, true)
```

```
# Normalizing the  values row 1 and update the values a of CSV file
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.normalizeRow(1, true)
```


```
# Get the values of a column 5 as a list of double values
CSVDataSet dataSet = CSVDataSet.getInstance( "temp.csv" );
dataSet.loadData();
dataSet.getColumnValuesAsDouble(5)
```

```
# Normalizing all values of a data set

CSVDataSet dataSet = new CSVDataSet( "temp.csv" );

dataSet.clearData();
dataSet.setHeaders( Arrays.asList(new String[]{"Column1", "Column2", "Column3", "Column4", "Column5", "Column6", "Column7"}) );
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

##### Contribution guidelines

Be free to implement new algorithms or correct bugs and submit pull requests. Since, you write a correlated Unit Test that prove that your implementation is correct.


##### Who do I talk to?

    Jadson Santos - jadsonjs@gmail.com
