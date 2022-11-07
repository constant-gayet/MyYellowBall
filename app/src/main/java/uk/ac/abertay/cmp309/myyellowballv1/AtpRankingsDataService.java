package uk.ac.abertay.cmp309.myyellowballv1;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AtpRankingsDataService {

    Context context;

    public AtpRankingsDataService(Context context) {
        this.context = context;
    }

    public void getATPRankings(){
        String key ="";
        try {
            ApplicationInfo app = context.getPackageManager().getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
            Bundle bundle = app.metaData;
            key = bundle.getString("tennisAPIKey");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        String url = "https://api.sportradar.com/tennis/trial/v3/en/rankings.json?api_key=" + key;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(context, "Response: " + response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            JSONArray competitor_rankings_list = response.getJSONArray("competitor_rankings");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    };
}
