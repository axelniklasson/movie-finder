package se.chalmers.tda367.bluejava.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import se.chalmers.tda367.bluejava.R;
import se.chalmers.tda367.bluejava.activities.DisplayPersonActivity;
import se.chalmers.tda367.bluejava.apis.MovieApi;
import se.chalmers.tda367.bluejava.models.BlueJava;
import se.chalmers.tda367.bluejava.models.CrewMember;

import java.util.List;

/**
 * Used to create a list item in the crew view.
 */
public class CrewTabArrayAdapter extends BaseAdapter {

    private final List<CrewMember> crew;
    private final Context context;
    private final Activity activity;

    public CrewTabArrayAdapter(Context context, Activity activity, List<CrewMember> crew) {
        this.context = context;
        this.activity = activity;
        this.crew = crew;
    }

    @Override
    public int getCount() {
        return crew.size();
    }

    @Override
    public Object getItem(int position) {
        return crew.get(position);
    }

    @Override
    public long getItemId(int position) {
        return crew.get(position).getID();
    }

    /**
     * Converts a view that is located in another parent view.
     *
     * @param position    The position of the view in the parent.
     * @param convertView The view which is to be converted.
     * @param parent      The parent view.
     * @return The view which is to be used.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final CrewMember crewMember = (CrewMember) getItem(position);

        if (convertView == null) {

            final LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.crew_tab_list_item, null);
        }

        /* Find the right view pieces to change. */
        ImageView coverImageView = (ImageView) convertView.findViewById(R.id.image);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.name);
        TextView jobTextView = (TextView) convertView.findViewById(R.id.job);

        /* Load the picture into the image view. */
        MovieApi movieApi = new MovieApi();
        String url = movieApi.getCoverURL(crewMember.getProfilePath());
        Picasso.with(context).load(url).into(coverImageView);

        /* Set the correct text to the text fields. */
        nameTextView.setText(crewMember.getName());
        jobTextView.setText(crewMember.getJOB());

        /* Show person details when clicked. */
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DisplayPersonActivity.class);

                intent.putExtra(BlueJava.EXTRA_PERSON, crewMember);
                activity.startActivity(intent);
            }
        });

        return convertView;
    }
}
