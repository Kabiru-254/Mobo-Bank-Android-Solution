package com.example.trial;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnBalance;
    ListView balanceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBalance = findViewById(R.id.btnBalance);
        balanceList = findViewById(R.id.LVBalance);

        btnBalance.setOnClickListener(view -> {
        // start request queue

            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            String url = "https://api.npoint.io/26165f9b1c74652ca71d";

            Hashtable<String, String> request_params = new Hashtable<>();
            request_params.put("transaction_reference", "MP90023");
            request_params.put("transaction_code", "BIMM");
            request_params.put("amount", "0");
            request_params.put("narration", "Payment for order 8923");
            request_params.put("phone_number", "254706215505");
            JSONObject params_Json = new JSONObject(request_params);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                    url,
                    params_Json,
                    response -> {

                        try {
                            String amount = response.getString("amount");
                            String narration = response.getString("narration");
                            String account_name = response.getString("account_name");
                            String phone_number = response.getString("phone_number");
                            String actual_balance = response.getString("actual_balance");
                            String trans_code = response.getString("transaction_code");
                            String available_balance = response.getString("available_balance");
                            String trans_ref = response.getString("transaction_reference");

                            ArrayList<String> bank_details_array = new ArrayList<>();

//                            bank_details_array.add(amount);
//                            bank_details_array.add(narration);
                            bank_details_array.add("Hello " + account_name);
//                            bank_details_array.add(phone_number);
                            bank_details_array.add("Actual Balance: " + actual_balance);
//                            bank_details_array.add(trans_code);
                            bank_details_array.add("Available Balance: " + available_balance);
//                            bank_details_array.add(trans_ref);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, bank_details_array);
                            balanceList.setAdapter(arrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    },
                    error -> Toast.makeText(MainActivity.this, "Failed!" + error, Toast.LENGTH_SHORT).show());

            queue.add(request);
        });
    }
}