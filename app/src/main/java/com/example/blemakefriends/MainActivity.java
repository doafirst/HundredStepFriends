package com.example.blemakefriends;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "HrudredStep";
    private Button facebookButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        facebookButton = findViewById(R.id.FaceBookButton);
        facebookButton.setOnClickListener(view -> facebookClick());
    }

    public void facebookClick(){
        Log.d(TAG,"This is button!!!");
    }
}