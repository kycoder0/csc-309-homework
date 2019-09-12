package com.example.brunospizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class credit_card extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        final EditText editText = (EditText)findViewById(R.id.et_date);

//        editText.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                if(s.length() == 2) {
//                    editText.setText(editText.getText()+"/");
//                    editText.setSelection(editText.length());
//                }
//
//            }
//        });



        Button btn_pay = (Button)findViewById(R.id.btn_submit_payment);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, "Payment confirmed!", duration);
                toast.show();;

                Intent intent = new Intent(CreditCardInfo.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
    static final String SAVE_CARD_NUM = "cardNum";
    static final String SAVE_NAME = "name";
    static final String SAVE_DATE = "date";
    static final String SAVE_CCV = "ccv";
    @Override
    public void onSaveInstanceState( Bundle savedInstanceState ) {
        super.onSaveInstanceState( savedInstanceState );

        TextView tv_card_num = findViewById(R.id.et_card_num);
        TextView tv_name = findViewById(R.id.et_name_on_card);
        TextView tv_date = findViewById(R.id.et_date);
        TextView tv_ccv = findViewById(R.id.et_ccv);

        // save the current letters on the board


        savedInstanceState.putString(SAVE_CARD_NUM, tv_card_num.getText().toString());
        savedInstanceState.putString(SAVE_NAME, tv_name.getText().toString());
        savedInstanceState.putString(SAVE_DATE, tv_date.getText().toString());
        savedInstanceState.putString(SAVE_CCV, tv_ccv.getText().toString());

    }

    @Override
    public void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );

        TextView tv_card_num = findViewById(R.id.et_card_num);
        TextView tv_name = findViewById(R.id.et_name_on_card);
        TextView tv_date = findViewById(R.id.et_date);
        TextView tv_ccv = findViewById(R.id.et_ccv);

        tv_card_num.setText(savedInstanceState.getString(SAVE_CARD_NUM));
        tv_name.setText(savedInstanceState.getString(SAVE_NAME));
        tv_date.setText(savedInstanceState.getString(SAVE_DATE));
        tv_ccv.setText(savedInstanceState.getString(SAVE_CCV));
    }
}