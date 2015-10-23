package app.com.moviez.anant.moviez.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

/**
 * Created by anant on 2015-10-19.
 */
public class TestUtilities extends AndroidTestCase {

    static final String TEST_LOCATION = "99705";
    static final String TEST_DATE = "2015-10-02";  // December 20th, 2014

                static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
               assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
                validateCurrentRecord(error, valueCursor, expectedValues);
                valueCursor.close();
            }

                static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
                Set< Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
                for (Map.Entry<String, Object> entry : valueSet) {
                        String columnName = entry.getKey();
                        int idx = valueCursor.getColumnIndex(columnName);
                        assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
                        String expectedValue = entry.getValue().toString();
                        assertEquals("Value '" + entry.getValue().toString() +
                                        "' did not match the expected value '" +
                                        expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
                    }
           }


    static ContentValues createMovieValues(long movieRowId) {

    ContentValues movieValues = new ContentValues();
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIEDETAIL_KEY,movieRowId);
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID  ,76341);
        movieValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE,"Mad Max: Fury Road");
        movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,"An apocalyptic story set in the furthest reaches of our planet, ");
        movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE,"2015-10-02");
        movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,7.6);
        movieValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH,"/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg");




        return movieValues;


    }
}
