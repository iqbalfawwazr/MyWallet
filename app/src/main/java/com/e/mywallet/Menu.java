package com.e.mywallet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Menu extends AppCompatActivity {

    Button btnCek,btnSend, btnReceive, btnCheck,btnKey;
    TextView tampil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent newint = getIntent();
        setContentView(R.layout.activity_menu);
        btnSend = (Button)findViewById(R.id.buttonsend);
        btnReceive = (Button)findViewById(R.id.buttonreceive);
        btnCheck = (Button)findViewById(R.id.buttoncheck);
        btnKey = (Button) findViewById(R.id.buttonkey);
        tampil = (TextView) findViewById(R.id.text_view_id);

        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Menu.this, Send.class));
            }
        });

        btnReceive.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Menu.this, Receive.class));
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Menu.this,Check.class));
            }
        });

        btnKey.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Menu.this, Key.class));;
            }
        });

    }

}
