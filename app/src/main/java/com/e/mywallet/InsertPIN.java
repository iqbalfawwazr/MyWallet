package com.e.mywallet;

import com.physicaloid.lib.Physicaloid;
import com.physicaloid.lib.usb.driver.uart.ReadLisener;
//import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InsertPIN extends Activity{
    Button btOpenz,  btnKeyz;
    EditText nilaiPin;
    TextView insertpin_response;
    Spinner spBauds;
    CheckBox cbAutoscrolls;
    ImageView imagebackPin;
    boolean canexit=false;
    Physicaloid mPhysicaloid; // initialising library

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_pin);

        imagebackPin = (ImageView) findViewById(R.id.imagebackPin);
        btOpenz  = (Button) findViewById(R.id.btOpenz);
        btnKeyz = (Button) findViewById(R.id.buttonkeyz);
        nilaiPin = (EditText) findViewById(R.id.nilaiPin);
        insertpin_response  = (TextView) findViewById(R.id.insertpin_response);
        spBauds = (Spinner) findViewById(R.id.spinnerz);
        cbAutoscrolls = (CheckBox)findViewById(R.id.autoscrollz);
        setEnabledUi(false);
        mPhysicaloid = new Physicaloid(this);

        btOpenz.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String baudtext = spBauds.getSelectedItem().toString();
                switch (baudtext) {
                    case "300 baud":
                        mPhysicaloid.setBaudrate(300);
                        break;
                    case "1200 baud":
                        mPhysicaloid.setBaudrate(1200);
                        break;
                    case "2400 baud":
                        mPhysicaloid.setBaudrate(2400);
                        break;
                    case "4800 baud":
                        mPhysicaloid.setBaudrate(4800);
                        break;
                    case "9600 baud":
                        mPhysicaloid.setBaudrate(9600);
                        break;
                    case "19200 baud":
                        mPhysicaloid.setBaudrate(19200);
                        break;
                    case "38400 baud":
                        mPhysicaloid.setBaudrate(38400);
                        break;
                    case "576600 baud":
                        mPhysicaloid.setBaudrate(576600);
                        break;
                    case "744880 baud":
                        mPhysicaloid.setBaudrate(744880);
                        break;
                    case "115200 baud":
                        mPhysicaloid.setBaudrate(115200);
                        break;
                    case "230400 baud":
                        mPhysicaloid.setBaudrate(230400);
                        break;
                    case "250000 baud":
                        mPhysicaloid.setBaudrate(250000);
                        break;
                    default:
                        mPhysicaloid.setBaudrate(9600);
                }

                if(mPhysicaloid.open()) {
                    setEnabledUi(true);
                    String kirim = getText(R.string.insert_pin).toString(); //Mengirim case 4 ke while loop, seharusnya gabung connect btOpens.
                    if(kirim.length()>0) {
                        byte[] buf = kirim.getBytes();
                        mPhysicaloid.write(buf, buf.length);
                    }


                    if(mPhysicaloid.open()) { // Pesan ini diberikan wallet
                        byte[] bufs = new byte[256];
                        mPhysicaloid.read(bufs, bufs.length);
                        String str = new String(bufs);
                        insertpin_response.setText(null);
                        insertpin_response.setText(str); // ini pesannya contoh " Wallet sudah siap" .
                        // mPhysicaloid.close();
                    }

                    setEnabledUi(true);
                    nilaiPin.setVisibility(View.VISIBLE); // Saat inilah baru bisa memasukkan key
                    if(cbAutoscrolls.isChecked())
                    {
                        insertpin_response.setMovementMethod(new ScrollingMovementMethod());
                    }
                    mPhysicaloid.addReadListener(new ReadLisener() {
                        @Override
                        public void onRead(int size) {
                            byte[] buf = new byte[size];
                            mPhysicaloid.read(buf, size);
                            tvAppend(insertpin_response, Html.fromHtml( new String(buf) ));
                        }
                    });
                } else {
                    insertpin_response.setText("GOBLOK!!! Sambungin dulu lah walletnya!");
                    Toast toast = Toast.makeText(getApplicationContext(),"Not Connect",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        btnKeyz.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String kirim = nilaiPin.getText().toString(); //Mengirim case 4 ke while loop, seharusnya gabung connect btOpens.
                if(kirim.length()>0) {
                    byte[] buf = kirim.getBytes();
                    mPhysicaloid.write(buf, buf.length);}
            }
        });

        imagebackPin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String kirim = "moemoe";
                if(kirim.length()>0) {
                    byte[] buf = kirim.getBytes();
                    mPhysicaloid.write(buf, buf.length);}
                canexit=true;
                onBackPressed();
            }
        });
    }



    public void onBackPressed(){
        if (canexit) {
            super.onBackPressed();
            mPhysicaloid.close();
        }
    }

    private void setEnabledUi(boolean on) {
        if(on) {
            btOpenz.setEnabled(false);
            spBauds.setEnabled(false);
            cbAutoscrolls.setEnabled(false);
            insertpin_response.setEnabled(true);
        } else {
            btOpenz.setEnabled(true);
            spBauds.setEnabled(true);
            cbAutoscrolls.setEnabled(true);
            insertpin_response.setEnabled(false);
        }
    }

    // public void openDialog() {
    //   ExampleDialog exampleDialog = new ExampleDialog();
    // exampleDialog.show(getSupportFragmentManager(), "example dialog");
    //}

    Handler mHandler = new Handler();
    private void tvAppend(TextView tv, CharSequence text) {
        final TextView ftv = tv;
        final CharSequence ftext = text;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ftv.append(ftext);
            }
        });
    }
}
