package com.jdj.pluginkeygen;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.ClipboardManager;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    EditText productId;
    EditText productKey;
    TextView TextToShow;
    TextView BmsDcs;

    Button genKey;
    Button copyText;
    Button pasteId;
    Button copyId;
    String BD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BmsDcs = findViewById(R.id.bmsDcs);
        productId = findViewById(R.id.productId);
        productKey = findViewById(R.id.productKey);
        TextToShow = findViewById(R.id.TextToShow);
        genKey = findViewById(R.id.genKey);
        pasteId = findViewById(R.id.pasteId);
        copyText = findViewById(R.id.copyText);
        copyId = findViewById(R.id.copyId);
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);

        genKey.setEnabled(false);
        copyText.setEnabled(false);
        copyId.setEnabled(false);

        productId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String id = productId.getText().toString();
                if (id.contains("JDCS-")) {
                    BmsDcs.setText("DCS");
                    BD = "DCS: <VA_INSTALL_DIR>\\Apps\\JanJan_DCS\\profile" + "\n";
                }else if (id.contains("JD16-")){
                        BmsDcs.setText("DCS");
                        BD = "DCS: <VA_INSTALL_DIR>\\Apps\\JanJan_DCS\\profile" + "\n";
                }else if (id.contains("JD18-")){
                    BmsDcs.setText("DCS");
                    BD = "DCS: <VA_INSTALL_DIR>\\Apps\\JanJan_DCS_FA18\\profile" + "\n";
                } else if (id.contains("JBMS-")){
                    BmsDcs.setText("BMS");
                    BD = "BMS :<VA_INSTALL_DIR>\\Apps\\JanJanBMS_VaPlugin\\profile" + "\n";
                } else if (id.contains("JB16-")){
                    BmsDcs.setText("BMS");
                    BD = "BMS :<VA_INSTALL_DIR>\\Apps\\JanJanBMS_VaPlugin\\profile" + "\n";
                } else if (id.contains("JB15-")){
                    BmsDcs.setText("BMS");
                    BD = "BMS :<VA_INSTALL_DIR>\\Apps\\JanJanBMS_F15\\profile" + "\n";
                } else{
                    BmsDcs.setText("DCS/BMS");
                    BD = "DCS: <VA_INSTALL_DIR>\\Apps\\JanJan_DCS\\profile" + "\n" +
                            "BMS :<VA_INSTALL_DIR>\\Apps\\JanJanBMS_VaPlugin\\profile" + "\n";
                }
                if(productId.getText().toString().length()>=44){
                    genKey.setEnabled(true);
                    copyText.setEnabled(true);
                    copyId.setEnabled(true);
                }
            }
        });

        productId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //return false;
            }
        });

        //xxJBMS-BE07-801B-13D4-8C11-51D7-AD44-943B-6F45yy
        genKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = productId.getText().toString();
                //Key should be 44 characters length
                //If starts with JDCS or JBMS just pick 44 chars.
                String keyString = id;
                    //extract ID from text
                if (id.indexOf("JDCS-")>-1){
                    keyString = id.substring(id.indexOf("JDCS-"), id.length());
                    keyString = keyString.substring(0,44);
                    productId.setText(keyString);
                    id=keyString.substring(5, keyString.length());;
                } else if(id.indexOf("JBMS-")>-1) {
                    keyString = id.substring(id.indexOf("JBMS-"), id.length());
                    keyString = keyString.substring(0, 44);
                    productId.setText(keyString);
                    id = keyString.substring(5, keyString.length());
                    ;
                }

                String key = null;
                try {
                    key = Licensing.GenerateLicenseKey(id);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                productKey.setText(key);
                productKey.setEnabled(false);
                String message = "Congratulations on installing JanJan Plugin for " + BmsDcs.getText().toString() + ".\n" +
                        "JanJan Plugin can do a LOT more than just pressing buttons and sending radio messages\n" +
                        "Please go over the installation process and the plugin setup\n" +
                        "Make sure you check out the new *Dynamic checks* section\n" +
                        "and use the new *Phrase Filter by...*command.\n" +
                        "The above is especially useful if you have 2 monitors and even more if you use VR.\n" +
                        "Make sure your **profile** is the latest well. I suggest you import it anyway.\n" +
                        "In VA import JanJan profile from:\n" + BD +
                        "See the plugin Doc(open from Config/ Help tab) for more information \n" +
                        "If you have any question, after reading the doc, do not hesitate to ask\n" +
                        "\nThis is your registration key. \n" +
                        "\n**" + key + "**\n";
                TextToShow.setText(message);
            }
        });

        copyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipData clip = ClipData.newPlainText("copy text", TextToShow.getText().toString());
                clipboard.setPrimaryClip(clip);
            }
        });

        pasteId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pasteData = "";
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
                String id = item.getText().toString();
                if (id != null) {
                    productId.setText(id);
                }
            }
        });


        copyId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipData clip = ClipData.newPlainText("copy id", productKey.getText().toString());
                clipboard.setPrimaryClip(clip);
            }
        });
    }
}