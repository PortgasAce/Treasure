package com.portgas.treasure;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.portgas.treasure.main.MainActivity;
import com.portgas.treasure.portgastreasure.R;

public class SplashActivity extends AppCompatActivity {

  private Handler handler = new Handler();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        openMainActivity();
      }
    }, 2000);
  }


  private void openMainActivity() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
    finish();
  }

}