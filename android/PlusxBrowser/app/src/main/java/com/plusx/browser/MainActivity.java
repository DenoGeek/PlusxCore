package com.plusx.browser;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.plusx.core.activities.PlusxCompatActivity;

public class MainActivity extends PlusxCompatActivity {

    private AppCompatButton appCompatButton;
    private AppCompatEditText appCompatEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plusxWebView = findViewById(R.id.plus_web);
        appCompatButton = findViewById(R.id.go);
        appCompatEditText = findViewById(R.id.url);
        plusxWebView.loadUrl("http://mysite-that-has-plusxjs.com/receipt.html");

        /* call this to bind the device to your app */

        tryBinding();

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusxWebView.loadUrl(appCompatEditText.getText().toString());
            }
        });
    }
}
