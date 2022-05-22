package com.example.bazel;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;



public class MainActivity extends FragmentActivity {
  private final String YOUTUBE_API = "AIzaSyB7hEaX6OrwwAPWJwX91jHDJFsSkxhshrE";
  private final String YOUTUBE_VIDEO_ID = "7bOptq-NPJQ";

  private ActivityResultLauncher<String> requestPermissionLauncher =
          registerForActivityResult(
                  new RequestPermission(),
                  isGranted -> {
                    if (isGranted) {
                      Log.v("Eflect", "Permission Granted by User!");
                    } else {
                      Log.v("Eflect", "Permission Rejected by User!");
                    }
                  });

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    /*
    Button clickMeButton = findViewById(R.id.clickMeButton);
    TextView helloBazelTextView = findViewById(R.id.helloBazelTextView);

    Greeter greeter = new Greeter();

    // Bazel supports Java 8 language features like lambdas!
    clickMeButton.setOnClickListener(v -> helloBazelTextView.setText(greeter.sayHello()));

     */

    /*
    Get necessary permissions
     */
    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    for (String permission : PERMISSIONS) {
      if (ContextCompat.checkSelfPermission(this, permission)
              == PackageManager.PERMISSION_GRANTED) {
        Log.v("Eflect", "Permission already granted");
      } else {
        Log.v("Eflect", "Asking for permission");
        requestPermissionLauncher.launch(permission);
      }
    }

    /*
    Add Youtube Fragment
    */
    YouTubePlayer.OnInitializedListener onInitializedListener = new YouTubePlayer.OnInitializedListener() {
      @Override
      public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(YOUTUBE_VIDEO_ID);
      }

      @Override
      public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
      }
    };
    YouTubePlayerFragment youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_wrapper_fragment);
    youTubePlayerFragment.initialize(YOUTUBE_API, onInitializedListener);

    /*
    Add Eflect Wrapper fragment
    */
    if (savedInstanceState == null) {
      getSupportFragmentManager()
              .beginTransaction()
              .setReorderingAllowed(true)
              .add(R.id.eflect_wrapper_fragment, EflectWrapper.class, null)
              .commit();
    }
  }
}
