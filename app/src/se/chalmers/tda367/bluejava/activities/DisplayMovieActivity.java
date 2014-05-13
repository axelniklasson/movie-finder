package se.chalmers.tda367.bluejava.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;
import se.chalmers.tda367.bluejava.adapters.CastTabArrayAdapter;
import se.chalmers.tda367.bluejava.adapters.DisplayResultsArrayAdapter;
import se.chalmers.tda367.bluejava.models.Actor;
import se.chalmers.tda367.bluejava.models.Movie;
import se.chalmers.tda367.bluejava.adapters.MovieTabsAdapter;
import se.chalmers.tda367.bluejava.R;

import java.util.List;

public class DisplayMovieActivity extends FragmentActivity implements ActionBar.TabListener {

    private static final String TAG = "DisplayMovieActivity";

    private Movie movie;

    private ViewPager viewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.display_movie_activity);

        Intent intent = getIntent();

        movie = intent.getParcelableExtra("movie");

        setupTabs(savedInstanceState);
    }

    private void setupTabs(Bundle savedInstanceState) {

        final ActionBar actionBar = getActionBar();

        if (actionBar == null) {
            return;
        }

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        viewPager = (ViewPager) findViewById(R.id.pager);
        MovieTabsAdapter movieTabsAdapter = new MovieTabsAdapter(getSupportFragmentManager(), this, movie);
        viewPager.setAdapter(movieTabsAdapter);

        String[] tabs = { "Details", "Cast", "Crew" };

        for (String tab : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab).setTabListener(this));
        }

        if (savedInstanceState != null) {
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }

        /**
          * Make respective tab selected when swiping the viewpager
          */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}