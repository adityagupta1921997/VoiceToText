package com.apkglobal.voicetotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView text;
    ImageButton btn_convert;
    private final int REQ_CODE_SPEECH_OUTPUT=143;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_convert=(ImageButton)findViewById(R.id.btnSpeak);
        text=(TextView)findViewById(R.id.text);
        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnToOpenMic();
            }
        });
    }

    private void btnToOpenMic() {

        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hiii Speak Now...........");
        try
        {
            startActivityForResult(intent,REQ_CODE_SPEECH_OUTPUT);
        }
        catch(ActivityNotFoundException tim)
        {
            Toast.makeText(this, "Application Not Responding", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case REQ_CODE_SPEECH_OUTPUT:
            {
                if (resultCode==RESULT_OK&&null!=data)
                {
                    ArrayList<String> voiceInText=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text.setText(voiceInText.get(0));
                }
                break;
            }
        }
        if(text.equals("Share this app"))
        {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "REPLACE WITH YOUR TEXT");

            startActivity(sharingIntent);
        }
        else if (text.equals("Open browser"))
        {
            Intent feedback=new Intent(Intent.ACTION_VIEW);
            feedback.setData(Uri.parse("https://play.google.com"));
            startActivity(feedback);
        }
    }
}
