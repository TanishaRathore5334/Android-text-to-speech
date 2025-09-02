package net.gotev.speechdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import net.gotev.speech.Speech;
import net.gotev.speech.SpeechDelegate;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Runtime permission check
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

        // Speech init
        Speech.init(this, getPackageName());

        // UI elements
        ImageButton button = findViewById(R.id.button);
        TextView textView = findViewById(R.id.text);

        // Mic button click listener
        button.setOnClickListener(v -> {
            if (Speech.getInstance().isListening()) {
                Speech.getInstance().stopListening();
            } else {
                Speech.getInstance().startListening(new SpeechDelegate() {
                    @Override
                    public void onStartOfSpeech() {
                        textView.setText("Listening...");
                    }

                    @Override
                    public void onSpeechRmsChanged(float value) {}

                    @Override
                    public void onSpeechPartialResults(List<String> results) {}

                    @Override
                    public void onSpeechResult(String result) {
                        textView.setText(result); // jo bola uska text yaha show hoga
                    }
                });
            }
        });
    }
}
