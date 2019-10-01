#Features

```bash
1.Coding Solution using Java8 features including lambdas aand streams.
2.Parallel processing using parallel streams.
3.Proper use of logging for information and severity level.
4.Use of spring boot for adding more features in the future such as monitoring using actuator.
4.All features tested as per requirement.
5.Detailed instruction in Readme for database setup and connectivity.
```

## Installation


Download hsqldb from [Sourceforge](https://sourceforge.net/projects/hsqldb/) and unzip to C:\hsqldb-2.5.0 on windows.

###Creating the DB and starting HSQL

To create a database for HSQLDB, first create a properties file named server.properties which defines a new database named demodb. Take a look at the following database server properties.

```bash
server.database.0 = file:hsqldb/demodb
server.dbname.0 = solutiondb
```

Now place this server.properties file into HSQLDB home directory C:\hsqldb-2.5.0\hsqldb

On windows go to command prompt inside the folder C:\hsqldb-2.5.0\hsqldb
And execute the following command


```bash
$java -classpath lib/hsqldb.jar org.hsqldb.server.Server
```
Once you are done creating a database, you have to start the database by using the following command.

```bash
$java -classpath lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/demodb --dbname.0 solutiondb
```

Start the HSQL Database manager from home directory C:\hsqldb-2.5.0\hsqldb as below

```bash
$java -cp lib/hsqldb.jar org.hsqldb.util.DatabaseManagerSwing
```

Connect to the database using information as in the image

Once connected to database solutiondb, create table using following cmd

```bash

CREATE TABLE events (
   eventid VARCHAR (15),
   duration INT  NOT NULL,
   type VARCHAR (15),
   host VARCHAR (15), 
   alert BOOLEAN					   
); 

```
