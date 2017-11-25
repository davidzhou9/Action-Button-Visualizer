package com.example.david.action_button_visualizer;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.renderscript.ScriptGroup;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<NewsArticle> articles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        articles = readFromDB();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Comment this
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
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (NewsArticle article : articles) {
            String title = article.getName();
            String url = article.getLink();
            LatLng loc = article.getLoc();
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(resizeMapIcons(article.getCategory(), 120, 120));



            mMap.addMarker(new MarkerOptions()
                    .position(loc)
                    .icon(bitmap)
                    .title(title))
                    .setSnippet(url);
        }

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(final Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.windowlayout, null);


                TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);

                TextView btn = (TextView) v.findViewById(R.id.tv_link);

                tvTitle.setText(marker.getTitle());
                tvTitle.setTextSize(20);


                // Returning the view containing InfoWindow contents
                return v;
            }
        });

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
     * Comment this
     * @param iconName
     * @param width
     * @param height
     * @return Bitmap
     */
    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
}
