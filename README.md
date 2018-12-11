## PlusX device development sdks

This repository contains development tools for the web and android for integrating to the plus-x machine.

**Apendix A - Android**
The sdk is packaged with print service and custom webview that can understand the plusx javascript library.
Important classes:

 1. PlusxCompatActivity
 2. PlusxActivity
 3. PlusxWebView

The activities can be extended to siplify printing configuration while the PlusxWebview is an android webview that understands plusx.js

**installation**
just import the aar under the android folder of the repo to your app
[Google documentation on importing aar files](https://developer.android.com/studio/projects/android-library)

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
plusx.js contains predefined functions to assist any site loaded into PlusxWebView to print

 - addItem(item)
 - plusxPrint(total,tendered)
 - printDummyPusx()
 - setBusinessName(string)

**add item** takes in a Receipt javascript obect with the structure below

    {"product":"Fresh baked avocado","unit_price":300,"qty":5,"sub_total":1500}
    
**plusxPrint(total,tendered)**- takes in two floats fot the total to be paid for the entire sale and what the customer paid respectively
**printDummyPusx()** - prints out a dummy receipt
**setBusinessName()** - takes in content to replace at the top of the receipt

**sample usage**

    <script  src="plusx.js"></script>
	<script  type="text/javascript">
		setBusinessName("Jujamica\nThe only place in kenya with mica")
		
		function  addToCart(){
		/*
		use any logic to mainatin your cart but ad it to plusx sdk
		general structure var item = {"product":"Fresh baked avocado","unit_price":300,"qty":5,"sub_total":1500}
		*/
		var  item = {"product":"Fresh baked avocado","unit_price":300,"qty":5,"sub_total":1500}
		addItem(item);
		}
		
		function  printOut(){
			return  plusxPrint(5000,6000);
		}
		</script>

