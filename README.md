# Quiz-IQ-App

## Android SQLite

![Image description](https://cdn57.androidauthority.net/wp-content/uploads/2016/10/Creating-a-simple-SQLite-database-in-your-Android-app.jpg)


Android SQLite is a very lightweight database which comes with Android OS. Android SQLite combines a clean SQL interface with a very small memory footprint and decent speed. For Android, SQLite is “baked into” the Android runtime, so every Android application can create its own SQLite databases.

Android SQLite native API is not JDBC, as JDBC might be too much overhead for a memory-limited smartphone. Once a database is created successfully its located in data/data//databases/ accessible from Android Device Monitor.

SQLite is a typical relational database, containing tables (which consists of rows and columns), indexes etc. We can create our own tables to hold the data accordingly. This structure is referred to as a schema.

## Android SQLite SQLiteOpenHelper

Android has features available to handle changing database schemas, which mostly depend on using the SQLiteOpenHelper class.

SQLiteOpenHelper is designed to get rid of two very common problems.

1. When the application runs the first time – At this point, we do not yet have a database. So we will have to create the tables, indexes, starter data, and so on.

2. When the application is upgraded to a newer schema – Our database will still be on the old schema from the older edition of the app. We will have option to alter the database schema to match the needs of the rest of the app.

SQLiteOpenHelper wraps up these logic to create and upgrade a database as per our specifications. For that we’ll need to create a custom subclass of SQLiteOpenHelper implementing at least the following three methods.
