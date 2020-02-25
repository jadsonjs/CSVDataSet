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

##### Contribution guidelines

Be free to implement new algorithms or correct bugs and submit pull requests. Since, you write a correlated Unit Test that prove that your implementation is correct.


##### Who do I talk to?

    Jadson Santos - jadsonjs@gmail.com
