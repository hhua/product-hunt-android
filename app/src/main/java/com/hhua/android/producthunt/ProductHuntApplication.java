package com.hhua.android.producthunt;


import android.content.Context;

import com.parse.Parse;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     TwitterClient client = TwitterApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class ProductHuntApplication extends com.activeandroid.app.Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        ProductHuntApplication.context = this;
        Parse.initialize(this, "3RObJtm1duandsuMRXWIa8dd1g7gMvhs2o8REccS", "hOUzaf5p3eR6AchyngeCXt2FIC8ThjF26cSjgMtr");

//        Map<String, String> dimensions = new HashMap<String, String>();
//// What type of news is this?
//        dimensions.put("category", "politics");
//// Is it a weekday or the weekend?
//        dimensions.put("dayType", "weekday");
//// Send the dimensions to Parse along with the 'read' event
//
//        ParseAnalytics.trackEventInBackground("read", dimensions);
    }


    public static ProductHuntClient getRestClient() {
        return (ProductHuntClient) ProductHuntClient.getInstance(ProductHuntClient.class, ProductHuntApplication.context);
    }

}
