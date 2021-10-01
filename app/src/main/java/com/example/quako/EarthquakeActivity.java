package com.example.quako;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquakes>> {

    private static final int EARTHQUAKE_LOADER_ID = 1;
    private static final String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=100";
    private EarthquakesAdapter quakesAdapter;
    private TextView emptyTextView;
    private ImageView earthImageView;
    private Button retry;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        ListView earthquakeListView = findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        quakesAdapter = new EarthquakesAdapter(this, new ArrayList<>());

        emptyTextView = findViewById(R.id.empty_view);
        earthImageView = findViewById(R.id.earthImageView);
        retry = findViewById(R.id.retry);

        RelativeLayout emptyView = findViewById(R.id.empty_layout_1);
        earthquakeListView.setEmptyView(emptyView);

        earthquakeListView.setAdapter(quakesAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquakes earthquake = quakesAdapter.getItem(position);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(earthquake.getUrl()));
                startActivity(intent);
            }
        });

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = LoaderManager.getInstance(this);

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

    }


    @NonNull
    @Override
    public Loader<List<Earthquakes>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.e("onCreateLoader()", "Called");

        return new EarthquakeLoader(this, url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Earthquakes>> loader, List<Earthquakes> data) {
        Log.e("onLoadFinished()", "Called");
        quakesAdapter.clear();

        if (data != null && !data.isEmpty()) {
            if (data.get(0).getOffset().equals("No Internet")) {
                data.remove(0);
                emptyTextView.setText(R.string.no_internet_connection);
                ProgressBar progressBar = findViewById(R.id.loading_spinner_1);
                progressBar.setVisibility(View.GONE);
                retry.setVisibility(View.VISIBLE);
                retry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                });
            } else {
                quakesAdapter.addAll(data);
            }
        } else {
            earthImageView.animate().alpha(1).setDuration(2000);
            emptyTextView.setText("Thank God!! No Earthquakes yet");
            ProgressBar progressBar = findViewById(R.id.loading_spinner_1);
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Earthquakes>> loader) {
        Log.e("onLoaderReset()", "Called");
        quakesAdapter.clear();
    }
}