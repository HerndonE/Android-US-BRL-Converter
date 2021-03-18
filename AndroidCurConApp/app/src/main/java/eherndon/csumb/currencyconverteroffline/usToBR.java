package eherndon.csumb.currencyconverteroffline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class usToBR extends AppCompatActivity {

    public EditText eText;
    TextView result;
    TextView footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.us_to_br);
        eText = (EditText) findViewById(R.id.currencyInput);
        result = (TextView) findViewById(R.id.amountConverted);
        footer = (TextView) findViewById(R.id.footer_textOne);
        eText.setHintTextColor(Color.BLACK);
        footer.setText("US to Brazil");

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("BUTTON","I am pressed");
                //Log.d("INPUT","" + eText.getText().toString());
                result.setText("$" + convertCurrency());
            }
        });

        Button Sbutton = (Button) findViewById(R.id.switchCur);
        Sbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });
    }

    public String convertCurrency() {
        double toBeConverted = 0;
        String theResult = "";

        if(eText.getText().toString().isEmpty()){
            Context context = getApplicationContext();
            CharSequence text = "Enter a value";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return theResult;
        }

        toBeConverted = Integer.parseInt(eText.getText().toString()) * 5.58;
        theResult = String.format("%.2f", toBeConverted);
        return theResult;
    }
}