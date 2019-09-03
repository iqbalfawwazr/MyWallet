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

public class InsertKey extends Activity{
    Button btOpens,  btnKeys;
    EditText nilaikey;
    TextView insertkey_response;
    Spinner spBauds;
    CheckBox cbAutoscrolls;
    ImageView imageback;
    boolean canexit=false;
    Physicaloid mPhysicaloid; // initialising library

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_key);

        imageback = (ImageView) findViewById(R.id.imageback);
        btOpens  = (Button) findViewById(R.id.btOpens);
        btnKeys = (Button) findViewById(R.id.buttonkeys);
        nilaikey = (EditText) findViewById(R.id.nilaikey);
        insertkey_response  = (TextView) findViewById(R.id.insertkey_response);
        spBauds = (Spinner) findViewById(R.id.spinners);
        cbAutoscrolls = (CheckBox)findViewById(R.id.autoscrolls);
        setEnabledUi(false);
        mPhysicaloid = new Physicaloid(this);

        btOpens.setOnClickListener(new View.OnClickListener()
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
                    String kirim = getText(R.string.insert_key).toString(); //Mengirim case 4 ke while loop, seharusnya gabung connect btOpens.
                    if(kirim.length()>0) {
                        byte[] buf = kirim.getBytes();
                        mPhysicaloid.write(buf, buf.length);
                    }


                    if(mPhysicaloid.open()) { // Pesan ini diberikan wallet
                        byte[] bufs = new byte[256];
                        mPhysicaloid.read(bufs, bufs.length);
                        String str = new String(bufs);
                        insertkey_response.setText(null);
                        insertkey_response.setText(str); // ini pesannya contoh " Wallet sudah siap" .
                       // mPhysicaloid.close();
                    }

                    setEnabledUi(true);
                    nilaikey.setVisibility(View.VISIBLE); // Saat inilah baru bisa memasukkan key
                    if(cbAutoscrolls.isChecked())
                    {
                        insertkey_response.setMovementMethod(new ScrollingMovementMethod());
                    }
                    mPhysicaloid.addReadListener(new ReadLisener() {
                        @Override
                        public void onRead(int size) {
                            byte[] buf = new byte[size];
                            mPhysicaloid.read(buf, size);
                            tvAppend(insertkey_response, Html.fromHtml( new String(buf) ));
                        }
                    });
                } else {
                    insertkey_response.setText("GOBLOK!!! Sambungin dulu lah walletnya!");
                    Toast toast = Toast.makeText(getApplicationContext(),"Not Connect",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        btnKeys.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String kirim = nilaikey.getText().toString(); //Mengirim case 4 ke while loop, seharusnya gabung connect btOpens.
                if(kirim.length()>0) {
                    byte[] buf = kirim.getBytes();
                    mPhysicaloid.write(buf, buf.length);}
            }
        });

        imageback.setOnClickListener(new View.OnClickListener()
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
            btOpens.setEnabled(false);
            spBauds.setEnabled(false);
            cbAutoscrolls.setEnabled(false);
            insertkey_response.setEnabled(true);
        } else {
            btOpens.setEnabled(true);
            spBauds.setEnabled(true);
            cbAutoscrolls.setEnabled(true);
            insertkey_response.setEnabled(false);
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
