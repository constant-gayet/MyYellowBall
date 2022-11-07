package uk.ac.abertay.cmp309.myyellowballv1;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AtpRankingsDataService atpRankingsDataService;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context context;
    private ListView list_atp_players;

    ArrayList<AtpRankingsModel> players_list;
    AtpRankingsAdapter adapter;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        players_list = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.getATPRankings();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        list_atp_players = view.findViewById(R.id.list_atp_players);
        return view;
    }

    public void getATPRankings(){
        String key ="";
        try {
            ApplicationInfo app = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
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
                        try {
                            //Here I get the whole rankings table
                            JSONArray JSON_players_array = response.getJSONArray("rankings").getJSONObject(0).getJSONArray("competitor_rankings");

                            for(int i =0; i< JSON_players_array.length(); i++){
                                //Now I parse the table i got above to get only the data I need according to my model
                                JSONObject player = JSON_players_array.getJSONObject(i);
                                int rank = player.getInt("rank");
                                int points = player.getInt("points");
                                String name = player.getJSONObject("competitor").getString("name");
                                String country_code = "RUS";
                                if (player.getJSONObject("competitor").has("country_code")) {
                                    country_code = player.getJSONObject("competitor").getString("country_code");
                                }
                                players_list.add(new AtpRankingsModel(rank,points,name,country_code));
                            }
                            /* Initialise the adapter with the list of players built above */
                            adapter = new AtpRankingsAdapter(getActivity(), players_list);
                            /* Attach the adapter to our list view. */
                            list_atp_players.setAdapter(adapter);
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