package se.chalmers.tda367.bluejava.helpers;

import se.chalmers.tda367.bluejava.interfaces.SortMethod;
import se.chalmers.tda367.bluejava.models.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class for sorting a list of Movie-objects by title.
 */
public class SortByTitle implements SortMethod {

    /**
     * The method sorts the objects in ascending alphabetical
     * order by the movies title.
     * @param list The list to be sorted.
     */
    @Override
    public List<Movie> sort(List<Movie> list) {

        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException();
        }

        Map<String, Movie> map = new TreeMap<String, Movie>();

        for (Movie movie : list) {

            String title = movie.getTitle();

            /*
            * Using the title as key will naturally sort
            * the later used collection by title when
            * we call map.values later.
            */
            map.put(title, movie);
        }

        /* Convert the Map to a List (Collection) */
        return new ArrayList<Movie>(map.values());

    }

}
