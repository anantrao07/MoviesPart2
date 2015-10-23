package app.com.moviez.anant.moviez.data;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

/**
 * Created by anant on 2015-10-18.
 */
public class TestDb extends AndroidTestCase {

    public static final String LOG_CAT = TestDb.class.getName();

    //since we want each tast case to start on a clean slate we use the below method

    public void testCreateDb() throws Throwable {


        mContext.deleteDatabase(MovieDbHelper.DATABSE_NAME);

        SQLiteDatabase db = new MovieDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();


/*
        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());


*/

       /* public void setUp(){
            deleteDatabse();
        }


        public void testMovieDetailTable(){

    }

  /*  long movieDetailRowId = insertMovieDetail();

    // Make sure we have a valid row ID.
            assertFalse("Error: Location Not Inserted Correctly", movieDetailRowId == -1L);


    // First step: Get reference to writable database
    // If there's an error in those massive SQL table creation Strings,
    // errors will be thrown here when you try to get a writable database.

        MovieDbHelper dbHelper = new MovieDbHelper(mContext);

    SQLiteDatabase db = dbHelper.getWritableDatabase();

    ContentValues movieValues =  TestUtilities.createMovieValues(movieDetailRowId);

    long rowId =
            db.insert(MovieContract.MovieEntry.TABLE_NAME,null,movieValues);
    assertTrue(movieDetailRowId != 1);



    // Fourth Step: Query the database and receive a Cursor back
          // A cursor is your primary interface to the query results.

    Cursor movieCursor = db.query(MovieContract.MovieEntry,null,
            null,
            null,
            null,
            null);

    assertTrue( "Error: No Records returned from location query", movieCursor.moveToFirst() );

    // Fifth Step: Validate the location Query
          TestUtilities.validateCurrentRecord("testInsertReadDb weatherEntry failed to validate",
                           movieCursor, movieValues);

    // Move the cursor to demonstrate that there is only one record in the database

    assertFalse( "Error: More than one record returned from weather query",weatherCursor.moveToNext() );


    /*
        This function gets called before each test is executed to delete the database.  This makes
        sure that we always have a clean test.
     */


        // Sixth Step: Close cursor and database*/
        //  dbHelper.close();


    }
}
