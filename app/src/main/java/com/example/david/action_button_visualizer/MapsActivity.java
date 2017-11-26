package com.example.david.action_button_visualizer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Class description: MapsActivity class handles the Google maps UI and relevant news markers
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<NewsArticle> articles;
    private ProgressDialog pDialog;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ARTICLES = "articles";
    private static final String TAG_title = "title";
    private static final String TAG_URL = "url";
    private static final String TAG_CATEGORY = "category";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_LONGITUDE = "longitude";

    // url to get all articles
    private static String file_all_articles = "articleJSON.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set the views
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        try {
            JSONObject temp = readJSONFromFile(file_all_articles);
            articles = getArticlesFromJSON(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Read news article information from database, store into list, and return the list
     * @return ArrayList<NewsArticle></NewsArticle>
     */
    private List<NewsArticle> readFromDB(){
        List<NewsArticle> temp = new ArrayList<NewsArticle>();
        try {
            AssetManager am = this.getAssets();
            InputStream is = am.open("articleTable.txt");
            Scanner scan = new Scanner(is);
            while (scan.hasNextLine()) {
                String title = scan.nextLine();
                String url = scan.nextLine();
                String category = scan.nextLine();
                String[] coordinates = scan.nextLine().split(" ");
                double latitude = Double.parseDouble(coordinates[0]);
                double longitude = Double.parseDouble(coordinates[1]);
                temp.add(new NewsArticle(title, url, category, latitude, longitude));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // add the NewsArticles to the map as markers
        for (NewsArticle article : articles) {
            String title = article.getName();
            String url = article.getLink();
            LatLng loc = article.getLoc();

            // assigns bitmap based on news category (i.e. sustainability, disaster, etc.)
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(resizeMapIcons(article.getCategory(), 120, 120));


            // add the marker with relevant params
            mMap.addMarker(new MarkerOptions()
                    .position(loc)
                    .icon(bitmap)
                    .title(title))
                    .setSnippet(url);
        }

        // use InfoWindow to add custom view to map Markers
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(final Marker marker) {


                View v = getLayoutInflater().inflate(R.layout.windowlayout, null);
                TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);

                // set the article title
                tvTitle.setText(marker.getTitle());
                tvTitle.setTextSize(20);


                // Returning the view containing InfoWindow contents
                return v;
            }
        });


        // when InfoWindow is clicked, open the link in browser
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String url = marker.getSnippet();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }

    /**
     * Method to resize bitmap icons to desired size
     * @param iconName
     * @param width
     * @param height
     * @return Bitmap
     */
    private Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    public JSONObject readJSONFromFile(String file) throws IOException, JSONException {

        AssetManager am = this.getAssets();
        InputStream is = am.open(file);
        Scanner scan = new Scanner(is);
        String jsonText = "";
        while (scan.hasNextLine()) {
            jsonText += scan.nextLine();
        }
        JSONObject json = new JSONObject(jsonText);
        return json;
    }

    public List<NewsArticle> getArticlesFromJSON(JSONObject json) throws JSONException {
        int success = json.getInt(TAG_SUCCESS);
        JSONArray temp = new JSONArray();
        List<NewsArticle> listOfArticles = new ArrayList<>();

        if (success == 1) {
            // Getting Array of Products
            temp = json.getJSONArray(TAG_ARTICLES);

            // looping through All Products
            for (int i = 0; i < temp.length(); i++) {
                JSONObject c = temp.getJSONObject(i);

                // Storing each json item in variable
                String title = c.getString(TAG_title);
                String url = c.getString(TAG_URL);
                String category = c.getString(TAG_CATEGORY);
                double latitude = c.getDouble(TAG_LATITUDE);
                double longitude = c.getDouble(TAG_LONGITUDE);

                listOfArticles.add(new NewsArticle(title, url, category, latitude, longitude));
            }
        }
        return listOfArticles;
    }
}
