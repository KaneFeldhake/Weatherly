package com.example.weatherly;


import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;

import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.apache.commons.text.WordUtils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeatherScreen extends AppCompatActivity {


    // Current Weather Views
    TextView usersCityName;
    TextView date;
    TextView currentTemp;
    TextView highT;
    TextView lowT;
    ImageView statusIcon;
    TextView des;

    // Additional Info Views
    TextView hum;
    TextView windy;
    TextView feelsLikeTemp;
    TextView uvIndex;

    // 7-day Forecast Views (Insert Day of Week below)
    TextView dayName1;
    TextView dayName2;
    TextView dayName3;
    TextView dayName4;
    TextView dayName5;
    TextView dayName6;
    TextView dayName7;
    ImageView statusIconDay0;
    ImageView statusIconDay1;
    ImageView statusIconDay2;
    ImageView statusIconDay3;
    ImageView statusIconDay4;
    ImageView statusIconDay5;
    ImageView statusIconDay6;
    ImageView statusIconDay7;
    TextView day0des;
    TextView day1des;
    TextView day2des;
    TextView day3des;
    TextView day4des;
    TextView day5des;
    TextView day6des;
    TextView day7des;
    TextView day0lowTemp;
    TextView day1lowTemp;
    TextView day2lowTemp;
    TextView day3lowTemp;
    TextView day4lowTemp;
    TextView day5lowTemp;
    TextView day6lowTemp;
    TextView day7lowTemp;
    TextView day0highTemp;
    TextView day1highTemp;
    TextView day2highTemp;
    TextView day3highTemp;
    TextView day4highTemp;
    TextView day5highTemp;
    TextView day6highTemp;
    TextView day7highTemp;




    // Fields and Helper Objects.
    private final String url = "http://api.openweathermap.org/data/2.5/onecall";
    private final String appid = "56bbfdea12d9852e39188f010ca00106";
    private double lati;
    private double longi;
    public String userCit;
    DecimalFormat df = new DecimalFormat("#");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_screen);

        // Current Weather View Mappings
        usersCityName = findViewById(R.id.userLocation);
        currentTemp = findViewById(R.id.currtemp);
        highT = findViewById(R.id.hightemp);
        lowT = findViewById(R.id.lowtemp);
        statusIcon = findViewById(R.id.img_weatherIcon);
        des = findViewById(R.id.weatherdescription);

        // Additional Info View Mappings
        hum = findViewById(R.id.humid);
        windy = findViewById(R.id.wind);
        feelsLikeTemp = findViewById(R.id.feelslike);
        uvIndex = findViewById(R.id.uv);

        // 7-day Forecast View Mappings
        dayName1 = findViewById(R.id.day1Label);
        dayName2 = findViewById(R.id.day2Label);
        dayName3 = findViewById(R.id.day3Label);
        dayName4 = findViewById(R.id.day4Label);
        dayName5 = findViewById(R.id.day5Label);
        dayName6 = findViewById(R.id.day6Label);
        dayName7 = findViewById(R.id.day7Label);
        statusIconDay0 = findViewById(R.id.day0Icon);
        statusIconDay1 = findViewById(R.id.day1Icon);
        statusIconDay2 = findViewById(R.id.day2Icon);
        statusIconDay3 = findViewById(R.id.day3Icon);
        statusIconDay4 = findViewById(R.id.day4Icon);
        statusIconDay5 = findViewById(R.id.day5Icon);
        statusIconDay6 = findViewById(R.id.day6Icon);
        statusIconDay7 = findViewById(R.id.day7Icon);
        day0des = findViewById(R.id.day0Descript);
        day1des = findViewById(R.id.day1Descript);
        day2des = findViewById(R.id.day2Descript);
        day3des = findViewById(R.id.day3Descript);
        day4des = findViewById(R.id.day4Descript);
        day5des = findViewById(R.id.day5Descript);
        day6des = findViewById(R.id.day6Descript);
        day7des = findViewById(R.id.day7Descript);
        day0lowTemp = findViewById(R.id.day0low);
        day1lowTemp = findViewById(R.id.day1low);
        day2lowTemp = findViewById(R.id.day2low);
        day3lowTemp = findViewById(R.id.day3low);
        day4lowTemp = findViewById(R.id.day4low);
        day5lowTemp = findViewById(R.id.day5low);
        day6lowTemp = findViewById(R.id.day6low);
        day7lowTemp = findViewById(R.id.day7low);
        day0highTemp = findViewById(R.id.day0high);
        day1highTemp = findViewById(R.id.day1high);
        day2highTemp = findViewById(R.id.day2high);
        day3highTemp = findViewById(R.id.day3high);
        day4highTemp = findViewById(R.id.day4high);
        day5highTemp = findViewById(R.id.day5high);
        day6highTemp = findViewById(R.id.day6high);
        day7highTemp = findViewById(R.id.day7high);


        //Map and display the current date.
        date = findViewById(R.id.theDate);



        // Get Long and Lat from previous screen.
        Intent intent = getIntent();
        lati = intent.getDoubleExtra("SENDLATVALUE", 0);
        longi = intent.getDoubleExtra("SENDLONGVALUE", 0);
        userCit = intent.getStringExtra("SENDCITYNAME");



        //Output weather details.
        getWeatherDetails();

    }


    /**
     * The getWeatherDetails method makes an API request to get
     *  all the needed Weather data.
     */
    public void getWeatherDetails()
    {
        // Create the URL to make the API call.
        String tempUrl = "";
        tempUrl = url + "?lat=" + lati + "&lon=" + longi + "&units=imperial" + "&appid=" + appid;

        // Make request.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                String output = "";

                // Request and fill information.
                try {

                    /*---------------------------
                       Request  JSON Information
                     ----------------------------*/

                    // Current days Weather Information
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONObject currentObject = jsonResponse.getJSONObject("current");
                    JSONArray jsonArrayWeather = currentObject.getJSONArray("weather");
                    JSONObject weatherDetails0 = jsonArrayWeather.getJSONObject(0);
                    int weatherIdStatus = weatherDetails0.getInt("id");;
                    String description = weatherDetails0.getString("description");
                    double temp = currentObject.getDouble("temp");
                    double feelsLike = currentObject.getDouble("feels_like");
                    double uvi = currentObject.getDouble("uvi");
                    int humidity = currentObject.getInt("humidity");
                    double windSpeed = currentObject.getDouble("wind_speed");


                    // High and Low Temps + 7 day forecast
                    JSONArray dailyArray = jsonResponse.getJSONArray("daily");
                    JSONObject daily0Object = dailyArray.getJSONObject(0);
                    JSONObject temp0Object = daily0Object.getJSONObject("temp");

                    // Current day weather
                    double highTempDay0 = temp0Object.getDouble("max");
                    double lowTempDay0 = temp0Object.getDouble("min");

                    // Day 1 weather (tomorrow)
                    JSONObject daily1Object = dailyArray.getJSONObject(1);
                    JSONObject temp1Object = daily1Object.getJSONObject("temp");
                    double highTempDay1 = temp1Object.getDouble("max");
                    double lowTempDay1 = temp1Object.getDouble("min");
                    JSONArray weatherCondition1 = daily1Object.getJSONArray("weather");
                    JSONObject weatherSpecific1 = weatherCondition1.getJSONObject(0);
                    String description1 = weatherSpecific1.getString("description");
                    int weatherIdStatus1 = weatherSpecific1.getInt("id");

                    // Day 2 weather
                    JSONObject daily2Object = dailyArray.getJSONObject(2);
                    JSONObject temp2Object = daily2Object.getJSONObject("temp");
                    double highTempDay2 = temp2Object.getDouble("max");
                    double lowTempDay2 = temp2Object.getDouble("min");
                    JSONArray weatherCondition2 = daily2Object.getJSONArray("weather");
                    JSONObject weatherSpecific2 = weatherCondition2.getJSONObject(0);
                    String description2 = weatherSpecific2.getString("description");
                    int weatherIdStatus2 = weatherSpecific2.getInt("id");

                    // Day 3 weather
                    JSONObject daily3Object = dailyArray.getJSONObject(3);
                    JSONObject temp3Object = daily3Object.getJSONObject("temp");
                    double highTempDay3 = temp3Object.getDouble("max");
                    double lowTempDay3 = temp3Object.getDouble("min");
                    JSONArray weatherCondition3 = daily3Object.getJSONArray("weather");
                    JSONObject weatherSpecific3 = weatherCondition3.getJSONObject(0);
                    String description3 = weatherSpecific3.getString("description");
                    int weatherIdStatus3 = weatherSpecific3.getInt("id");

                    // Day 4 weather
                    JSONObject daily4Object = dailyArray.getJSONObject(4);
                    JSONObject temp4Object = daily4Object.getJSONObject("temp");
                    double highTempDay4 = temp4Object.getDouble("max");
                    double lowTempDay4 = temp4Object.getDouble("min");
                    JSONArray weatherCondition4 = daily4Object.getJSONArray("weather");
                    JSONObject weatherSpecific4 = weatherCondition4.getJSONObject(0);
                    String description4 = weatherSpecific4.getString("description");
                    int weatherIdStatus4 = weatherSpecific4.getInt("id");

                    // Day 5 weather
                    JSONObject daily5Object = dailyArray.getJSONObject(5);
                    JSONObject temp5Object = daily5Object.getJSONObject("temp");
                    double highTempDay5 = temp5Object.getDouble("max");
                    double lowTempDay5 = temp5Object.getDouble("min");
                    JSONArray weatherCondition5 = daily5Object.getJSONArray("weather");
                    JSONObject weatherSpecific5 = weatherCondition5.getJSONObject(0);
                    String description5 = weatherSpecific5.getString("description");
                    int weatherIdStatus5 = weatherSpecific5.getInt("id");

                    // Day 6 weather
                    JSONObject daily6Object = dailyArray.getJSONObject(6);
                    JSONObject temp6Object = daily6Object.getJSONObject("temp");
                    double highTempDay6 = temp6Object.getDouble("max");
                    double lowTempDay6 = temp6Object.getDouble("min");
                    JSONArray weatherCondition6 = daily6Object.getJSONArray("weather");
                    JSONObject weatherSpecific6 = weatherCondition6.getJSONObject(0);
                    String description6 = weatherSpecific6.getString("description");
                    int weatherIdStatus6 = weatherSpecific6.getInt("id");

                    // Day 7 weather
                    JSONObject daily7Object = dailyArray.getJSONObject(7);
                    JSONObject temp7Object = daily7Object.getJSONObject("temp");
                    double highTempDay7 = temp7Object.getDouble("max");
                    double lowTempDay7 = temp7Object.getDouble("min");
                    JSONArray weatherCondition7 = daily7Object.getJSONArray("weather");
                    JSONObject weatherSpecific7 = weatherCondition7.getJSONObject(0);
                    String description7 = weatherSpecific7.getString("description");
                    int weatherIdStatus7 = weatherSpecific7.getInt("id");


                    /*---------------------------
                      OUTPUT WEATHER INFORMATION
                     ----------------------------*/

                    // Upper area info
                    usersCityName.setText(userCit);
                    displayMainDate(date);
                    currentTemp.setText(df.format(temp) + "\u00B0");
                    highT.setText(df.format(highTempDay0) + "\u00B0");
                    lowT.setText(df.format(lowTempDay0) + "\u00B0");
                    des.setText(WordUtils.capitalize(description));

                    // Get and set image of weather for current day
                    String stringImgId = updateWeatherIcon(weatherIdStatus);
                    int imageid = getResources().getIdentifier(stringImgId, "drawable", getPackageName());
                    statusIcon.setImageResource(imageid);

                    // Extra info area
                    hum.setText(humidity + "%");
                    windy.setText(df.format(windSpeed) + "\nmph");
                    feelsLikeTemp.setText(df.format(feelsLike) + "\u00B0");
                    uvIndex.setText(df.format(uvi));

                    // 7-Day Forecast Area
                    // Display Future Dates
                    displayLaterDates(dayName1, 1);
                    displayLaterDates(dayName2, 2);
                    displayLaterDates(dayName3, 3);
                    displayLaterDates(dayName4, 4);
                    displayLaterDates(dayName5, 5);
                    displayLaterDates(dayName6, 6);
                    displayLaterDates(dayName7, 7);

                    // Display Future Weather Symbols
                    statusIconDay0.setImageResource(imageid);
                    String stringImgId1 = updateWeatherIcon(weatherIdStatus1);
                    int imageid1 = getResources().getIdentifier(stringImgId1, "drawable", getPackageName());
                    statusIconDay1.setImageResource(imageid1);

                    String stringImgId2 = updateWeatherIcon(weatherIdStatus2);
                    int imageid2 = getResources().getIdentifier(stringImgId2, "drawable", getPackageName());
                    statusIconDay2.setImageResource(imageid2);

                    String stringImgId3 = updateWeatherIcon(weatherIdStatus3);
                    int imageid3 = getResources().getIdentifier(stringImgId3, "drawable", getPackageName());
                    statusIconDay3.setImageResource(imageid3);
                    String stringImgId4 = updateWeatherIcon(weatherIdStatus4);
                    int imageid4 = getResources().getIdentifier(stringImgId4, "drawable", getPackageName());
                    statusIconDay4.setImageResource(imageid4);
                    String stringImgId5 = updateWeatherIcon(weatherIdStatus5);
                    int imageid5 = getResources().getIdentifier(stringImgId5, "drawable", getPackageName());
                    statusIconDay5.setImageResource(imageid5);
                    String stringImgId6 = updateWeatherIcon(weatherIdStatus6);
                    int imageid6 = getResources().getIdentifier(stringImgId6, "drawable", getPackageName());
                    statusIconDay6.setImageResource(imageid6);
                    String stringImgId7 = updateWeatherIcon(weatherIdStatus7);
                    int imageid7 = getResources().getIdentifier(stringImgId7, "drawable", getPackageName());
                    statusIconDay7.setImageResource(imageid7);

                    // Display Future Descriptions
                    day0des.setText(WordUtils.capitalize(description));
                    day1des.setText(WordUtils.capitalize(description1));
                    day2des.setText(WordUtils.capitalize(description2));
                    day3des.setText(WordUtils.capitalize(description3));
                    day4des.setText(WordUtils.capitalize(description4));
                    day5des.setText(WordUtils.capitalize(description5));
                    day6des.setText(WordUtils.capitalize(description6));
                    day7des.setText(WordUtils.capitalize(description7));

                    // Display Future High/Low Temps
                    day0lowTemp.setText(df.format(lowTempDay0) + "\u00B0");
                    day1lowTemp.setText(df.format(lowTempDay1) + "\u00B0");
                    day2lowTemp.setText(df.format(lowTempDay2) + "\u00B0");
                    day3lowTemp.setText(df.format(lowTempDay3) + "\u00B0");
                    day4lowTemp.setText(df.format(lowTempDay4) + "\u00B0");
                    day5lowTemp.setText(df.format(lowTempDay5) + "\u00B0");
                    day6lowTemp.setText(df.format(lowTempDay6) + "\u00B0");
                    day7lowTemp.setText(df.format(lowTempDay7) + "\u00B0");
                    day0highTemp.setText(df.format(highTempDay0) + "\u00B0");
                    day1highTemp.setText(df.format(highTempDay1) + "\u00B0");
                    day2highTemp.setText(df.format(highTempDay2) + "\u00B0");
                    day3highTemp.setText(df.format(highTempDay3) + "\u00B0");
                    day4highTemp.setText(df.format(highTempDay4) + "\u00B0");
                    day5highTemp.setText(df.format(highTempDay5) + "\u00B0");
                    day6highTemp.setText(df.format(highTempDay6) + "\u00B0");
                    day7highTemp.setText(df.format(highTempDay7) + "\u00B0");





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WeatherScreen.this, error.toString().trim(), Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }




    /*
    -------------------------Helper Methods Below-----------------------------------
     */

    /**
     * The updateWeatherIcon method returns the image name
     *  based on the current weather conditions.
     * @param condition ID given from API that tells what IMAGE to use.
     * @return The name of the image to use.
     */
    private static String updateWeatherIcon(int condition)
    {
        if (condition < 300)
        {
            return "thunderstorm";
        }
        else if (condition >=300 && condition < 500)
        {
            return "drizzle";
        }
        else if (condition >=500 && condition < 600)
        {
            return "rain";
        }
        else if (condition >=600 && condition < 700)
        {
            return "snow";
        }
        else if (condition >=701 && condition < 800)
        {
            return "fog";
        }
        else if (condition == 800)
        {
            return "clearsunny";
        }
        else if (condition > 800)
        {
            return "cloudy";
        }
        return "imageNotFound";
    }


    /**
     * The displayDate method simply sets the "date" TextView
     *  to the current date.
     * @param date TextView to display date onto.
     */
    protected void displayMainDate(TextView date)
    {
        Date getDate = new Date();
        DateFormat df = new SimpleDateFormat("MMMM, dd");
        String currentDate = df.format(getDate);
        date.setText(currentDate);
    }

    /**
     * The displayLaterDates method acts just like the above method,
     *  except it gets the abbreviated day of week for the 7 days ahead.
     * @param date Textview to display day of week onto.
     * @param daysAhead How many days ahead we want.
     */
    protected void displayLaterDates(TextView date, int daysAhead)
    {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, daysAhead);
        dt = c.getTime();
        String dayOfWeekText = new SimpleDateFormat("E").format(dt);
        date.setText(dayOfWeekText);

    }

}