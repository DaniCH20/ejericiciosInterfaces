package com.example.buttonandtextview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button myButton=null;
    private TextView myTextView=null;
    private EditText myEditText=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myButton= (Button) findViewById(R.id.button);
        myButton.setOnClickListener(this);
        myTextView= (TextView) findViewById(R.id.textView);
        myEditText= (EditText) findViewById(R.id.editText);
    }
    public void onClick (View v){

        String textViewText=myTextView.getText().toString();
        String button=myButton.getText().toString();
        String textPulsa=getString(R.string.text_pulsa);
        String textNo=getString(R.string.text_No);
        String textSi=getString(R.string.text_Si);
        int number=Integer.parseInt(myEditText.getText().toString());
        String buttComprobar=getString(R.string.button_Comprobar);
        String buttReiniciar=getString(R.string.button_Reiniciar);

        if(textViewText.equalsIgnoreCase(textPulsa)){
            if(myButton.equals(buttComprobar)) {
                if (esPrimo(number)) {
                    myTextView.setText(textSi);

                } else {
                    myTextView.setText(textNo);

                }
            }else{
                myTextView.setText(textPulsa);

            }
        }else{
            myTextView.setText(textPulsa);
        }
    }
    public static Boolean esPrimo(long n) {
        if (n < 2) {
            return false;
        } else {
            for (long i = n / 2; i >= 2; i--) {
                if (n % i == 0) {
                    return false;
                }
            }
        }
        return true;
    }

}