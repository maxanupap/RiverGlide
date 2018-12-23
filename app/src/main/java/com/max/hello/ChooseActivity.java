package com.max.hello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Declaring an SpinnerA
    private Spinner spinnerst;

    //An ArrayList for Spinner Items
    private ArrayList<String> stopboat;

    //JSON Array
    private JSONArray result;
    private TextView txlong, txid, txlat, txstop;
    private String log, lat, id, stop, line, type ;

    @Override     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerline);
        Spinner spinnertype = (Spinner) findViewById(R.id.spinnertype);
        final Button bttomap = (Button) findViewById(R.id.button1);
        txlat = (TextView) findViewById(R.id.txtlat);
        txlong = (TextView) findViewById(R.id.txtlon);
        txid = (TextView) findViewById(R.id.txtid);
        txstop = (TextView) findViewById(R.id.txtstop);


        stopboat = new ArrayList<String>();
        spinnerst = (Spinner) findViewById(R.id.spinnerstop);
        spinnerst.setOnItemSelectedListener(this);

        getData();

        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<>();
        categories.add("เรือด่วนธงส้ม(Orange)");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinnertype.setOnItemSelectedListener(this);
        List<String> categoriestype = new ArrayList<>();
        categoriestype.add("นนทบุรี(Nonthaburi)");
        categoriestype.add("วัดราชสิงขร(Wat Rajsingkorn)");
        ArrayAdapter<String> dataAdaptertype = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriestype);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertype.setAdapter(dataAdaptertype);

        bttomap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Response.Listener <String> responseListener = new Response.Listener <String>() {
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String name = jsonResponse.getString("name");
                            String slat = jsonResponse.getString("lat");
                            String slon = jsonResponse.getString("lon");

                            Intent intent = new Intent(ChooseActivity.this, MapsActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("slat", slat);
                            intent.putExtra("slon", slon);
                            intent.putExtra("lat", lat);
                            intent.putExtra("log", log);
                            intent.putExtra("id", id);
                            intent.putExtra("stop", stop);
                            intent.putExtra("type", type);
                            intent.putExtra("line", line);

                            ChooseActivity.this.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ExlocationRequest ExlocationRequest = new ExlocationRequest(line, type, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ChooseActivity.this);
                queue.add(ExlocationRequest);

                Toast.makeText(getBaseContext(), lat + "\n" + log + "\n" + "Line : "+ line +"Type : "+ type , Toast.LENGTH_SHORT).show();

            }
    });
    }


    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(StopRequest.DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //การแยกสตริง
                            j = new JSONObject(response);

                            //การเก็บสตริงอาร์เรย์ของสตริง JSON ไปยังอาร์เรย์ JSON ของเรา
                            result = j.getJSONArray(StopRequest.JSON_ARRAY);

                            //วิธีเรียก getStopboat เพื่อรับนักเรียนจากอาร์เรย์ JSON
                            getStopboat(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

//Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

//Adding request to the queue
        requestQueue.add(stringRequest);     }

    private void getStopboat(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                stopboat.add(json.getString(StopRequest.TAG_NAMETHAI));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinnerst.setAdapter(new ArrayAdapter<String>(ChooseActivity.this, android.R.layout.simple_spinner_dropdown_item, stopboat));
    }


    //Doing the same with this method as we did with getName()
    private String getLat(int position) {
        lat = "";
        try {
            JSONObject json = result.getJSONObject(position);
            lat = json.getString(StopRequest.TAG_LAT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lat;
    }

    //Doing the same with this method as we did with getName()
    private String getLong(int position) {
        log = "";
        try {
            JSONObject json = result.getJSONObject(position);
            log = json.getString(StopRequest.TAG_LONG);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return log;
    }

    private String getId(int position) {
        id = "";
        try {
            JSONObject json = result.getJSONObject(position);
            id = json.getString(StopRequest.TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

    private String getStop(int position) {
        stop = "";
        try {
            JSONObject json = result.getJSONObject(position);
            stop = json.getString(StopRequest.TAG_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stop;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.spinnerstop) {
            stop = parent.getItemAtPosition(position).toString();
            txlat.setText(getLat(position));
            txlong.setText(getLong(position));
            txid.setText(getId(position));
            txstop.setText(getStop(position));
        } else if (spinner.getId() == R.id.spinnerline) {
            line = parent.getItemAtPosition(position).toString();
            if (line == "เรือด่วนธงส้ม(Orange)") {
                line = "0";
            } else if (line == "เรือด่วนธงเหลือง(Yellow)") {
                line = "1";
            }
        } else if (spinner.getId() == R.id.spinnertype) {
                type = parent.getItemAtPosition(position).toString();
                if (type == "นนทบุรี(Nonthaburi)") {
                    type = "0";
                } else {
                    type = "1";
                }

        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


}