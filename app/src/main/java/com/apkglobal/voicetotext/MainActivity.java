package com.apkglobal.voicetotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView text;
    ImageButton btn_convert;
    Button btn_speakup;
    TextToSpeech t1;
    String voice_text;
    private final int REQ_CODE_SPEECH_OUTPUT = 143;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_speakup = (Button) findViewById(R.id.btn_speakup);
        btn_convert = (ImageButton) findViewById(R.id.btnSpeak);
        text = (TextView) findViewById(R.id.text);
        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnToOpenMic();
            }
        });
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        btn_speakup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tospeak=voice_text;
                Toast.makeText(getApplicationContext(), tospeak, Toast.LENGTH_SHORT).show();
                t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }


    private void btnToOpenMic() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hiii Speak Now...........");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_OUTPUT);
        } catch (ActivityNotFoundException tim) {
            Toast.makeText(this, "Application Not Responding", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_OUTPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> voiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text.setText(voiceInText.get(0));
                }
                break;
            }
        }
        voice_text = text.getText().toString();
        if (voice_text.equals("share this app")) {

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "REPLACE WITH YOUR TEXT");

            startActivity(sharingIntent);
        } else if (voice_text.equals("open browser")) {
            Intent feedback = new Intent(Intent.ACTION_VIEW);
            feedback.setData(Uri.parse("https://play.google.com"));
            startActivity(feedback);
        } else if (voice_text.equals("call my favourite number")) {
            Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+91)8057856164"));
            startActivity(dial);
        } else if (voice_text.contains("camera")) {
            Intent image = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(image);
        } else if (voice_text.equals("show my contacts")) {
            Intent contacts = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("content://contacts/people/"));
            startActivity(contacts);
        }

    }
}
//last commit

