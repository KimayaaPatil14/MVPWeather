package com.example.mvpweather;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout homeRL;
    private ProgressBar loadingPB;
    private TextView cityNameTV, temperatureTV, conditionTV;
    private RecyclerView weatherRV;
    private TextInputEditText cityEdt;
    private ImageView backIV, iconIV, searchIV;
    private ArrayList<MVPWeather> mvpWeatherArrayList;
    private MVPWeatherAdapter mvpWeatherAdapter;
    private LocationManager locationManager;
    private int PERMISSION_CODE = 1;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        homeRL = findViewById(R.id.idRLHome);
        loadingPB = findViewById(R.id.idPBLoading);
        cityNameTV = findViewById(R.id.idTVCityName);
        temperatureTV = findViewById(R.id.idTVTemperature);
        conditionTV = findViewById(R.id.idTVcondition);
        weatherRV = findViewById(R.id.idRvWeather);
        cityEdt = findViewById(R.id.idEdtCity);
        backIV = findViewById(R.id.idIVBack);
        iconIV = findViewById(R.id.idIVIcon);
        searchIV = findViewById(R.id.idIVSearch);
        mvpWeatherArrayList = new ArrayList<>();
        mvpWeatherAdapter = new MVPWeatherAdapter(context:this, mvpWeatherArrayList);
        weatherRV.setAdapter(mvpWeatherAdapter);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCOmpat.checkSelfPermission(context:
        this, Manifest.permission.ACCESS_FINE_LOCATION)!=
        PackageManager.PERMISSION_GRANTED && ActivvityCompat.checkSelfPermission(context:
        this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermission(activity:
        MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE)
        ;

    }

    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        cityName = getCityName(location.getLongitude(), location.getLatitude());
        getWeatherInfo(cityName);

        searchIV.setOnClickListener()new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityEdt.getText().toString();
                if(city.isempty()) {
                    Toast.makeText(context:MainActivity.this, text:"Please enter city Name", Toast.LENGTH_SHORT).show();
                }else {
                    cityNameTV.setText(cityName);
                    getWeatherInfo(city);
                }
            }
        });
}

@Override
public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
super.onRequestPermissionResults(requestCOde, permission, grantResults);
    if(requestCode==PERMISSION_CODE){
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context:this, text:"Permissions Granted...", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context:this, text:"Please provide the Permissions...", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    }

private String getCityName(double longitude, double latitude) {
    String cityName = "NOt found";
    Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
    try {
        List<Address> addresses = gcd.getFromLocation(latitude, longitude, maxResults:10);

        for (Address adr : addresses) {
            if (adr != null) {
                String city = adr.getLocality();
                if (city != null && !city.equals("")) {
                    cityName = city;
                } else {
                    Log.d(tag:"TAG", msg:"CITY NOT FOUND");
                    Toast.makeText(context:this, text:"User City Not Found...", Toast.LENGTH_SHORT).
                    show();
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return cityName;
}




        private void getWeatherInfo(String cityName){
         String url="http://api.weatherapi.com/v1/forecast.json?key=cdddf45475474acfb98151429242409&q=" + cityName + "&days=1&aqi=no&alerts=no"
         cityNameTV.setText(cityName);
         RequestQueue requestQueue = Volley.newRequestQueue(context:MainActivity.this);

         JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonRequest:nutll, new Response.Listener<JSONObject>)
            @Override
                    public void onResponse(JSONObject response) {
             loadingPB.setVisibility(View.GONE);
             homeRL.setVisibility(View.VISIBLE);
             mvpWeatherArrayList.clear();

             try {
                 String temperature = response.getJSONObject("current").getString(name:"temp_c");
                 temperatureTV.setText(temperature+"°C");
                 int isDay = response.getJSONObject("current").getInt(name:"is_day");
                 String condition = response.getJSONObject("current").getJSONObject("condition").getString(name:"text");
                 String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString(name:"icon");
                 Picasso.get().load("http:".concat(conditionIcon)).into(iconIV);
                 conditionTV.setText(condition);
                 if(isDay===1) {
                     Picasso.get().load(path:"").into(backIV);
                 }else {
                     Picasso.get().load(path:"").into(backIV);
                 }

                 JSONObject forecastObj = response.getJSONObject("forecast");
                 JSONObject forcastO = forecastObj.getJSONArray(name:"forecastday").getJSONObject(index:0);
                 JSONObject hourArray = forcastO.getJSONArray(name:"hour");

                 for(int i = 0; i < hourArray.length(); i++) {
                     JSONObject hourObj = hourArray.getJSONObject(i);
                     String time = hourObj.getString(name:"time");
                     String temper = hourObj.getString(name:"temp_c");
                     String img = hourObj.getJSONObject("condition").getString(name:"icon");
                     String wind = hourObj.getString(name:"wind_kph");
                     mvpWeatherArrayList.add(new MVPWeather(time, temper, img, wind));
                 }
                 mvpWeatherAdapter.notifyDataSetChanged();

                 }catch (JSONException e){
                 e.printStackTrace();
             }
                 }

            }, new Response.ErrorListener() {
        @Override
                public void onErrorResponse(VolleyError error) {
            Toast.makeText(context:MainActivity.this, text:"Please enetr valid city name...", Toast.LENGTH_SHORT).show();
        }
    });
requestQueue.add(jsonObjectRequest);
     }
}