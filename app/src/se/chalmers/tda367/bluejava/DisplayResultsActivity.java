package se.chalmers.tda367.bluejava;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.*;

import java.util.List;


public class DisplayResultsActivity extends ListActivity implements AdapterView.OnItemSelectedListener {

    private AndroidHttpClient httpClient;

    private HttpHandler httpHandler;

    private MovieApi movieApi;

    private List<Movie> movies;

    private SortMethod sortMethod;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_results_activity);

        // Following block is used to populate the sort-spinner with choices
        Spinner spinner = (Spinner) findViewById(R.id.sort_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Set listener to the spinner
        spinner.setOnItemSelectedListener(this);

		// Get the action bar
		ActionBar actionBar = getActionBar();

		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);

        /*
         * Used to create appropriate URLs for our http requests
         */
        movieApi = new MovieApi();

        /*
         * Handling all http requests and communication with our APIs
         * Once done with a request, it'll call handleSearchResults
         * and pass a string containing the result
         */
        httpClient = HttpHandler.getAndroidHttpClient(this);
        httpHandler = new HttpHandler(httpClient);

        /* Set default sort method to sort by title in ascending order. */
        sortMethod = new SortByNothing();

		handleIntent(getIntent());
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);

		// Associate the searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

		return super.onCreateOptionsMenu(menu);
	}

    public void handleSearchResults(String json) {

        if (json == null) {
            return;
        }

        /*
         * Take the string and make a lot of movies from it
         */
        movies = Movie.jsonToListOfMovies(json);

        /*
         * Give the user som feedback on their search
         */
        String toastMessage = (movies != null)
                ? "Yeey! I found " + movies.size() + " movies."
                : "Sorry! You must be a united fan.";
        showToast(toastMessage);

        displayMovies(movies);
    }

    /**
     * Take our list with movies and display them on our listview
     * Read more:
     * https://github.com/thecodepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
     */
    public void displayMovies(List<Movie> movies) {
        if (movies == null) {
            showToast("No Hits\n" + "Search again");
            // This doesn't work anymore when spinner was added instead of textfield
/*            TextView textView = (TextView) findViewById(R.id.sort_label);
            textView.setText("Sök igen");*/
        } else {
            DisplayResultsArrayAdapter arrayAdapter = new DisplayResultsArrayAdapter(this,
                    R.layout.display_results_list_item, movies, movieApi);
            setListAdapter(arrayAdapter);
        }
    }

    public void findMovies(String type, String title) {
        if (type.equals("discover")) {
			httpHandler.get(movieApi.createDiscoverMovieQuery(title), this);
		} else {
			httpHandler.get(movieApi.createMovieQuery(title), this);
		}

    }

    private void showToast(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
    }

    public void sortMovies(View view) {
        if(sortMethod instanceof SortByNothing) {
            showToast("Choose sort method");
        } else {
            showToast("Sorting");
            displayMovies(sortMethod.sort(movies));
        }
    }

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	/**
	 * Handling intent data
	 */
	private void handleIntent(Intent intent) {
		String type, query;

		// Check if query comes from search field in activity bar
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			query = intent.getStringExtra(SearchManager.QUERY);
			type = "search";
		}

		// Otherwise it comes from navigation drawer browsing
		else {
			query = "latest"; // intent.getStringExtra("EXTRA_MESSAGE");
			type = "discover";
		}
		
		findMovies(type, query);
	}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (pos == 1) {
            sortMethod = new SortByTitle();
        } else if (pos == 2) {
            sortMethod = new SortByYear();
        } else if (pos == 3) {
            sortMethod = new SortByRating();
        } else if (pos == 4) {
            sortMethod = new SortByVoteCount();
        } else if (pos == 5) {
            sortMethod = new SortByPopularity();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        ;
    }
}
