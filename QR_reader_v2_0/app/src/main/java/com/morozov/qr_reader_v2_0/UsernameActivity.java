package com.morozov.qr_reader_v2_0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UsernameActivity extends AppCompatActivity {
    private Button button_enter;
    private EditText editText_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        initElems();
        button_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText_username.getText().toString().isEmpty()){
                    Intent intent = new Intent();
                    intent.putExtra("USER_NAME", editText_username.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void initElems(){
        button_enter = (Button)findViewById(R.id.button_enter);
        editText_username = (EditText)findViewById(R.id.editText_username);
    }
}
