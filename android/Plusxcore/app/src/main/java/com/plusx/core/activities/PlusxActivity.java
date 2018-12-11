package com.plusx.core.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.plusx.core.widgets.PlusxWebView;
import com.smartdevice.aidl.IZKCService;

public class PlusxActivity extends Activity {

    public IZKCService printService;

    public PlusxWebView plusxWebView;

    public ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            printService = null;
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("client", "onServiceConnected");
            printService = IZKCService.Stub.asInterface(service);
            plusxWebView.getPrintWebInterface().updatePrintService(printService);

        }
    };

    public void tryBinding(){
        Intent intent = new Intent("com.zkc.aidl.all");
        intent.setPackage("com.smartdevice.aidl");
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
    }
}
