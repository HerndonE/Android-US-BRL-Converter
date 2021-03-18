package eherndon.csumb.currencyconverteroffline;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class usToBRLON extends AppCompatActivity {

    public EditText eText;
    TextView result;
    TextView footer;
    public String jsonResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.us_to_br_online);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        eText = (EditText) findViewById(R.id.currencyInput);
        result = (TextView) findViewById(R.id.amountConverted);
        footer = (TextView) findViewById(R.id.footer_textOne);
        eText.setHintTextColor(Color.BLACK);
        footer.setText("US to Brazil");

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTON", "I am pressed");
                //Log.d("INPUT","" + eText.getText().toString());

                //result.setText(convertCurrency());
                apiCall();
                // result.setText(apiCall());

            }
        });


        Button Sbutton = (Button) findViewById(R.id.switchCur);
        Sbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), brToUSON.class));

            }
        });

    }

    public String apiCall() {
        String URL = "https://api.exchangeratesapi.io/latest?base=USD";
        final String[] returnedRate = {
                ""
        };
        final String[] theResult = {
                ""
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener < JSONObject > () {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());
                        //result.setText("$:" + response.toString() );
                        jsonResult = response.toString();

                        try {
                            Log.e("Currency Rate ", response.getJSONObject("rates").getString("BRL"));
                            returnedRate[0] = response.getJSONObject("rates").getString("BRL");
                            Log.e("returned rate", returnedRate[0]);
                            Log.e("I AM", returnedRate[0].getClass().getName());

                            String tempString = returnedRate[0];
                            Log.e("temp string", tempString);
                            double toBeConverted = 0;
                            double currencyRate = Double.parseDouble(tempString);
                            Log.e("temp double", "" + currencyRate);

                            if(eText.getText().toString().isEmpty()){
                                Context context = getApplicationContext();
                                CharSequence text = "Enter a value";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                return;
                            }

                            toBeConverted = Integer.parseInt(eText.getText().toString()) * currencyRate;
                            theResult[0] = String.format("%.2f", toBeConverted);
                            Log.e("RESULT", "$" + theResult[0]);
                            result.setText("$" + theResult[0]);
                            Log.e("I AM RESULT TYPE1", theResult[0].getClass().getName());




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                }
        );
        requestQueue.add(objectRequest);


        Log.e("I AM RESULT TYPE2", theResult[0].getClass().getName());

        return theResult[0];
    }

}