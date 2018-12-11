package com.plusx.core.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.plusx.core.utils.PrintWebInterface;


public class PlusxWebView extends WebView {

    private PrintWebInterface printWebInterface;
    private Context context;


    public PlusxWebView(Context context) {
        super(context);
        this.context = context;
        setUpWebview();
    }

    public PlusxWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setUpWebview();

    }

    public void setUpWebview(){
        printWebInterface = new PrintWebInterface(context);
        this.getSettings().setJavaScriptEnabled(true);
        this.addJavascriptInterface(printWebInterface, "AndroidInterface");
    }

    public PrintWebInterface getPrintWebInterface() {
        return printWebInterface;
    }
}
