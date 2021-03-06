package com.ifreedomer.cplus.activity;

import android.os.Bundle;

import com.ifreedomer.cplus.R;
import com.ifreedomer.cplus.ui.register.RegisterFragment;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RegisterFragment.newInstance())
                    .commitNow();
        }
    }
}
