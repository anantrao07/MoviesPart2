package app.com.moviez.anant.moviez.data;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by anant on 2015-10-17.
 */
public class MovieContract {


    public static final String CONTENT_AUTHORITY = "com.moviez.anant.moviez.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";

    public  static final String  PATH_DETAIL = "details";


    public static final class DetailsEntry implements BaseColumns{


        public  static final String TABLE_NAME = "details";


        public static final String COLUMN_MOVIE_ID = "movie_id";



        public static final String COLUMN_AUTHOR = "author";


        public static final String COLUMN_CONTENT = "content";


        public static final String COLUMN_URL = "url";


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DETAIL).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DETAIL;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DETAIL;

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }



    }

    public static final class MovieEntry implements BaseColumns{

        public static final String TABLE_NAME = "movie";


        // Column with the foreign key into the location table.
        public static final String COLUMN_MOVIEDETAIL_KEY = "moviedetail_id";

        public static final String COLUMN_MOVIE_ID = "movie_id";


        // movie id as returned by API, to identify the icon to be used



        public static final String COLUMN_ORIGINAL_TITLE = "original_title";

        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";

        public static final String COLUMN_OVERVIEW = "overview";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;



        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


        public static Uri buildMovieDetail(String detailSetting) {
            return null;
        }





    }
}
