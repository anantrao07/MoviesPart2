package app.com.moviez.anant.moviez;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment  {


    public final String trailer_results = "results";
    protected final String trailer_id = "id";
    protected final String trailer_key = "key";
    protected final String trailer_name = "name";
    protected final String review_results = "results";
    protected final String review_author = "author";
    protected final String review_content = "content";
    static final String TRAILERKEY = "app.com.moviez.anant.moviez";

    protected ArrayList<TrailerDetail> trailerdetailsarray = new ArrayList<TrailerDetail>();
    protected ArrayList<ReviewsPoJo> reviewsdetailsarray = new ArrayList<ReviewsPoJo>();
    protected int movieId;
    ImageView poster_view;
    RatingBar user_rating_bar;
    ListView trailerlistview;
    ListView reviewslistview;

    TrailerAdapter trailerViewAdapter;
    ReviewAdapter reviewViewAdapter;
    Intent intent;
    TextView abt_movie;

    Intent mShareIntent;
    ShareActionProvider mShareActionProvider;
    public DetailActivityFragment() {
    }

    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link //#onAttach(Activity)} and before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * <p/>
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mShareIntent = new Intent();
        mShareIntent.setAction(Intent.ACTION_SEND);
        mShareIntent.setType("text/plain");
        mShareIntent.putExtra(Intent.EXTRA_TEXT, "From me to you, this text is new.");

    }


    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {@link #setHasOptionsMenu}.  See
     * {@link //Activity#onCreateOptionsMenu(Menu) Activity.onCreateOptionsMenu}
     * for more information.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_fragment_menu,menu);

        MenuItem shareItem = menu.findItem(R.id.menu_item_share);


        // Get its ShareActionProvider
      //  mShareActionProvider = (ShareActionProvider) shareItem.getActionProvider();


        // Connect the dots: give the ShareActionProvider its Share Intent
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(mShareIntent);
        }

        // Return true so Android will know we want to display the menu

    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootview =  inflater.inflate(R.layout.fragment_detail, container, false);


       // ListView trailerlistview = (ListView)findViewById(R.id.trailer_name);
       // trailerlistview.setAdapter(trailerViewAdapter);
        TrailView tv = new TrailView();

        intent = getActivity().getIntent();
        if(intent != null && intent.hasExtra(MainActivityFragment.MOVIE_TITLE)){
            //setting title
            String title = intent.getStringExtra(MainActivityFragment.MOVIE_TITLE);
            //setting Movie Synopsis
            String about  = intent.getStringExtra(MainActivityFragment.MOVIE_ABOUT);
            //Setting release date
            String release_date = intent.getStringExtra(MainActivityFragment.MOVIE_RELEASE_DATE);
            //Setting Rating
            String userratingstring  = intent.getStringExtra(MainActivityFragment.MOVIE_RATING);
            //Settimg Poster
            String poster_url = intent.getStringExtra(MainActivityFragment.MOVIE_POSTER);
            //Parsing the rating in float
            float ratingfloat = Float.parseFloat(userratingstring);

            String movie_idString  = intent.getStringExtra((MainActivityFragment.MOVIE_ID));
            movieId = Integer.parseInt(movie_idString);

            user_rating_bar = ((RatingBar)rootview.findViewById(R.id.ratingBar));

            abt_movie = ((TextView) rootview.findViewById(R.id.aboutmovie));
         //   abt_movie.addTextChangedListener(this);

            ((TextView) rootview.findViewById(R.id.title_textview)).setText(title);

             trailerlistview = (ListView)rootview.findViewById(R.id.trailer_listView);
             reviewslistview = (ListView)rootview.findViewById((R.id.review_listview));


            user_rating_bar.setRating(ratingfloat);

            poster_view = (ImageView)rootview.findViewById(R.id.movieposter);
            Picasso.with(getActivity()).load(poster_url).into(poster_view);
            abt_movie.setText(about);
           // ToggleButton showMoreToggle = (ToggleButton)rootview.findViewById(R.id.toggleButton);

           // abt_movie.setMovementMethod(new ScrollingMovementMethod());

            ((TextView)rootview.findViewById(R.id.releasedate_display)).setText("Release Date " + release_date);


        }
        Log.v("****Movie id**** ", Integer.toString(movieId));

        tv.execute("http://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key="+ getString(R.string.api_key),
                "http://api.themoviedb.org/3/movie/" + movieId + "/reviews?api_key="+getString(R.string.api_key));


        trailerViewAdapter = new TrailerAdapter(getActivity(),trailerdetailsarray);
       trailerlistview.setAdapter(trailerViewAdapter);


        reviewViewAdapter = new ReviewAdapter(getActivity(),reviewsdetailsarray);
        reviewslistview.setAdapter(reviewViewAdapter);



        trailerlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrailerDetail keyBasket = trailerViewAdapter.getItem(position);

                String urilink = "https://www.youtube.com/watch?v=" + keyBasket.getKey();
                Intent trailerintent = new Intent(Intent.ACTION_VIEW);
                trailerintent.setData(Uri.parse(urilink));
                Log.v("Movie key", keyBasket.getKey());
                startActivity(trailerintent);

            }
        });


        //to set the scroll view of the trailer list view
        trailerListScroll();

        //to set the scroll view of the reviews list view
        reviewListScroll();



        return rootview;
    }

    public void trailerListScroll() {
        trailerlistview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;


            }
        });
    }


    public void reviewListScroll(){
        reviewslistview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;


            }
        });
    }


    protected  class TrailView extends AsyncTask<String , Void , Wrapper> {

        JSONObject jsonObj,jsonreviewsobj;
        JSONArray jsonArray,jsonArrayreview;

        HttpURLConnection trailerUrl,reviewUrl ;
        String moviedetailjsnstr , reviewDetailJsonStr;
        BufferedReader moviedetailreader , reviewDetailReader;

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param list The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */



        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Wrapper doInBackground(String... params) {


            try {
                URL treviews = new URL(params[0]);
                URL review = new URL(params[1]);

                //trailer
                trailerUrl = (HttpURLConnection) treviews.openConnection();
                trailerUrl.setRequestMethod("GET");
                trailerUrl.connect();

                reviewUrl = (HttpURLConnection) review.openConnection();
                reviewUrl.setRequestMethod("GET");
                reviewUrl.connect();

                InputStream isr = trailerUrl.getInputStream();
                StringBuffer sb = new StringBuffer();
                StringBuffer rsb = new StringBuffer();

                InputStream risr = reviewUrl.getInputStream();


                if (isr == null) {
                    moviedetailjsnstr = null;

                }

                if(risr == null){
                    reviewDetailJsonStr = null;
                }



                moviedetailreader = new BufferedReader(new InputStreamReader(isr));

                reviewDetailReader = new BufferedReader((new InputStreamReader(risr)));

                String lineCheck;
                String reviewLineCheck;

                while ((lineCheck = moviedetailreader.readLine()) != null) {
                sb.append(lineCheck + "\n");

            }
                while ((reviewLineCheck = reviewDetailReader.readLine()) != null) {
                    rsb.append(reviewLineCheck + "\n");

                }
            if (sb.length() == 0) {
                moviedetailjsnstr = null;


            }
            else{
                moviedetailjsnstr  = sb.toString();
                Log.v("****traielr list***" , moviedetailjsnstr);
            }
                if (rsb.length() == 0) {
                    reviewDetailJsonStr = null;
                }
                else{
                    reviewDetailJsonStr  = rsb.toString();
                    Log.v("****reviews list***" , reviewDetailJsonStr);
                }
            try{
                return new Wrapper(gettrailers(moviedetailjsnstr),getReviews(reviewDetailJsonStr));



            }catch (JSONException je){
                je.printStackTrace();
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException io) {

            io.printStackTrace();
        }
         /*   try{
                Log.v("Reviewjson" , reviewDetailJsonStr);
                return getReviews(reviewDetailJsonStr);



            }catch (JSONException je){
                je.printStackTrace();
            }



        catch (Exception io) {

            io.printStackTrace();
        }
        finally {
                trailerUrl.disconnect();
                reviewUrl.disconnect();
                if (moviedetailreader != null || reviewDetailJsonStr !=null) {
                    try {
                        moviedetailreader.close();
                        reviewDetailReader.close();


                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }*/
            return null;
        }



        public ArrayList<TrailerDetail> gettrailers(String moviejsonfeed) throws JSONException {

            // Log.e("MOVIEJASONSTR", "its empty");

            jsonObj = new JSONObject(moviejsonfeed);


            //parse the entire json string
            jsonArray = jsonObj.getJSONArray(trailer_results);

            for (int i = 0; i < jsonArray.length(); i++) {
                int id;
                String trailerId;
                String trailerSite;
                String trailerName;
                String trailerKey;

                //  float int_user_rating;

                JSONObject int_jsonObj = jsonArray.getJSONObject(i);
//parse json objects into its values
                trailerId = int_jsonObj.getString(trailer_id);
                trailerKey = int_jsonObj.getString(trailer_key);
                trailerName = int_jsonObj.getString(trailer_name);



                //create a mMovieDetail object that we will populate the json data
                TrailerDetail td = new TrailerDetail();
                td.setKey(trailerKey);
                td.setName(trailerName);
                td.setTrailer_id(trailerId);
                trailerdetailsarray.add(td);

                //Log.i("Parsedmsg", aboutmoiejsonstr);

                //Log.i("allMovies", allMovies.get(i).toString());

            }
            return trailerdetailsarray;


        }
        public ArrayList<ReviewsPoJo> getReviews(String reviejsonfeed) throws JSONException{


            jsonreviewsobj = new JSONObject(reviejsonfeed);
            jsonArrayreview = jsonreviewsobj.getJSONArray(review_results);

            for(int i = 0 ; i<jsonArrayreview.length();i++){
                String reviewAuthor;
                String reviewContent;
                String reviewId;
                 JSONObject reviewjsonobj = jsonArrayreview.getJSONObject(i);
                reviewAuthor = reviewjsonobj.getString(review_author);


                reviewContent = reviewjsonobj.getString(review_content);
                Log.v("----author---",reviewAuthor);
                Log.v("-----content-----",reviewContent);
                ReviewsPoJo rj = new ReviewsPoJo();
                rj.setReview_author(reviewAuthor);
                rj.setReview_content(reviewContent);
                reviewsdetailsarray.add(rj);


            }
            return reviewsdetailsarray;
        }
        @Override
        protected void onPostExecute(Wrapper list) {
            super.onPostExecute(list);


            trailerViewAdapter.notifyDataSetChanged();
            reviewViewAdapter.notifyDataSetChanged();


        }


    }
}

