package com.madongqiang.gifdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FingerRecognitionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtTip;
    private Button btnStart;
    private FingerprintManager manager;

    private static final int PERMISSION_REQUEST_CODE = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_recognition);

        txtTip = findViewById(R.id.txt_tip);
        btnStart = findViewById(R.id.btn_start);

        manager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                Log.e("马东强","clicked");
                if (manager.hasEnrolledFingerprints()) {
                    Log.e("马东强","该设备有指纹识别硬件");
                    authenticate();
                } else {
                    Log.e("马东强","该设备没有有指纹识别硬件");
                }
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) !=
//                        PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.USE_FINGERPRINT}, PERMISSION_REQUEST_CODE);
//                } else {
//                    authenticate();
//                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("马东强","grantResults:" + grantResults.length);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            authenticate();
        }
    }

    private void authenticate() {
//        FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject()
//        manager.authenticate(null, null, 0, new FingerprintManager.AuthenticationCallback() {
//            @Override
//            public void onAuthenticationError(int errorCode, CharSequence errString) {
//                Log.e("马东强","onAuthenticationError:" + errString);
//            }
//
//            @Override
//            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
//                Log.e("马东强","onAuthenticationHelp:" + helpString);
//            }
//
//            @Override
//            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
//                Log.e("马东强","onAuthenticationSucceeded:" + result.getCryptoObject().getSignature());
//            }
//
//            @Override
//            public void onAuthenticationFailed() {
//                Log.e("马东强","onAuthenticationFailed");
//            }
//        }, null);
    }
}
