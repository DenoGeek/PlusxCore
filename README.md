## PlusX device development sdks

This repository contains development tools for the web and android for integrating to the plus-x machine.

**Apendix A - Android**
The sdk is packaged with print service and custom webview that can understand the plusx javascript library.
Important classes:

 1. PlusxCompatActivity
 2. PlusxActivity
 3. PlusxWebView

The activities can be extended to siplify printing configuration while the PlusxWebview is an android webview that understands plusx.js
**usage**

    public class MainActivity extends PlusxCompatActivity {  
  
    @Override  
	  protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	  setContentView(R.layout.activity_main);  
	  
	  plusxWebView = findViewById(R.id.plus_web);  
	  plusxWebView.loadUrl("http://mysite-that-has-plusxjs.com/receipt.html"); 
	   
	  /* call this to bind the device to your app */
	  
	  tryBinding();  
	  }  
	}

**Apendix B-Web**
