package com.example.bazel;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import android.widget.TextView;
import eflect.AndroidEflect;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import java.io.FileOutputStream;
import android.content.Context;
import android.util.Log;
import android.os.Debug;

public class EflectWrapper extends Fragment {
    private static AndroidEflect android_eflect;

    public EflectWrapper() {
        super(R.layout.eflect_wrapper);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /*
        Initialize Android Eflect
         */
        if (isAdded()) {
            android_eflect = AndroidEflect.getInstance(getActivity());
        } else {
            Log.v("Eflect", "Activity not attacehd!\n");
        }

        /*
        Set up status text view
         */
        TextView statusTextView = view.findViewById(R.id.statusTextView);
        statusTextView.setText("Press start to start eflect!");

        /*
        Set up Start and Stop buttons.
        TODO: Implement logic to prevent double start or double stop.
         */
        Button startButton = getView().findViewById(R.id.eflectStartButton);
        startButton.setOnClickListener(v -> statusTextView.setText(startEflect()));

        Button stopButton = getView().findViewById(R.id.eflectStopButton);
        stopButton.setOnClickListener(v -> {
            try (FileOutputStream output = getActivity().openFileOutput("eflect-data.pb", Context.MODE_PRIVATE)) {
                stopEflect(output);
                statusTextView.setText("Eflect stopped!\n");
            } catch (Exception e) {
                Log.e("Eflect", null, e);
            }
        });
    }

    /*
    Wrappers around start and stop methods of Eflect
     */
    public String startEflect() {
        android_eflect.start();

        // TODO: Use a variable for the path
        Debug.startMethodTracing("/data/user/0/com.example.bazel/files/android-data.trace");
        return "Eflect started!";
    }

    public String stopEflect(FileOutputStream output) {
        android_eflect.stop();
        Debug.stopMethodTracing();
        android_eflect.dump(output);
        return "Eflect stopped!";
    }
}