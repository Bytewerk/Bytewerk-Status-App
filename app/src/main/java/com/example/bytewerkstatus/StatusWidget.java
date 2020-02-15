package com.example.bytewerkstatus;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



/**
 * Implementation of App Widget functionality.
 */
public class StatusWidget<appWidgetId> extends AppWidgetProvider {

    static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.status_widget);
// ...

// Instantiate the RequestQueue.
       RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://stats.bytewerk.org/status.txt";
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        if (response.equals("open")) {
                            views.setViewVisibility(R.id.status_online, 1);
                        } else {
                            views.setViewVisibility(R.id.status_offline, 1);
                        }
                        //views.setTextViewText(R.id.text, "Yeah!" + response);
                        appWidgetManager.updateAppWidget(appWidgetId, views);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                views.setViewVisibility(R.id.status_nointernet, 1);
                appWidgetManager.updateAppWidget(appWidgetId, views);

            }
        });
        queue.add(stringRequest);


    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}




