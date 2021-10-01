package com.example.quako;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquakes>> {

    private final String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.e("onStartLoading()", "Called");
        forceLoad();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public List<Earthquakes> loadInBackground() {

        Log.e("loadInBackground()", "Called");
        List<Earthquakes> earthquakes = new ArrayList<>();


        if (mUrl == null) {
            return null;
        }

        else if (!isConnected()) {
            earthquakes.add(new Earthquakes("No Internet", "No Internet", 0.00, null, "No Internet"));
            return earthquakes;
        }


        // Perform the network request, parse the response, and extract a list of earthquakes.
        earthquakes = QueryUtils.fetchEarthquakeData(mUrl);

        return earthquakes;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = cm.getActiveNetwork();
        NetworkCapabilities cap = cm.getNetworkCapabilities(network);

        return cap != null && cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }
}
