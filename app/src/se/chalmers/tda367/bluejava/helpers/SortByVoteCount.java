package se.chalmers.tda367.bluejava.helpers;

import se.chalmers.tda367.bluejava.interfaces.SortMethod;
import se.chalmers.tda367.bluejava.models.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class for sorting results by Vote Count.
 */
public class SortByVoteCount implements SortMethod {

    /** Sorts the results in ascending order by vote count. */
    @Override
    public List<Movie> sort(List<Movie> list) {

        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException();
        }

        // @see SortByTitle for more info about the following code.
        Map<String, Movie> map = new TreeMap<String, Movie>();

        for (Movie movie : list) {

            String voteCount = movie.getVoteCount();

            map.put(voteCount, movie);

        }

        return new ArrayList<Movie>(map.values());
    }
}
