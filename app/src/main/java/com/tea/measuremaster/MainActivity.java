package com.tea.measuremaster;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button exportButton;
    private Button openButton;
    private TextView textView;

    private AlertDialog alertDialog;
    private AlertDialog mDialog;

    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    private int REQUEST_PERMISSION_CODE = 1000;


    private String filePath = "/sdcard/AndroidExcelDemo";

    private void requestPermission() {
        if (Build.VERSION.SDK_INT > 23) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    permissions[0])
                    == PackageManager.PERMISSION_GRANTED) {
                //授予权限
                Log.i("requestPermission:", "用户之前已经授予了权限！");
            } else {
                //未获得权限
                Log.i("requestPermission:", "未获得权限，现在申请！");
                requestPermissions(permissions
                        , REQUEST_PERMISSION_CODE);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();


        exportButton = findViewById(R.id.export_button);
        exportButton.setOnClickListener(this);
    }



    private void showDialogTipUserRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    @Override
    public void onClick(View view) {

    }

    private void openDir() {

        File file = new File(Environment.getExternalStorageDirectory().getPath());
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setDataAndType(Uri.fromFile(file), "file/*");
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "没有正确打开文件管理器", Toast.LENGTH_SHORT).show();
        }
    }



}
