package com.plusx.core.utils;

import android.content.Context;
import android.os.RemoteException;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.smartdevice.aidl.IZKCService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PrintWebInterface {
    Context mContext;
    private IZKCService mIzkcService;
    private static final String mIzkcService_CUT_OFF_RULE = "--------------------------------\n";
    // 品名占位长度
    private static final int GOODS_NAME_LENGTH = 10;
    // 单价占位长度
    private static final int GOODS_UNIT_PRICE_LENGTH = 4;
    // 价格占位长度
    private static final int GOODS_PRICE_LENGTH = 4;
    // 数量占位长度
    private static final int GOODS_AMOUNT = 6;
    private String receipt_head;

    // Instantiate the interface and set the context
    public PrintWebInterface(Context c) {
        mContext = c;
        this.receipt_head = "Sale receipt";
    }

    // Show a toast from the web page
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void printDummyReceipt(){

        List<ReceiptItem> items = new ArrayList<>();
        ReceiptItem r = new ReceiptItem();
        r.product_name = "Prod name";
        r.qty = 6;
        r.unit_price = 900;

        items.add(r );
        printDoc(receipt_head,items,5400,5500);

    }

    @JavascriptInterface
    public void updateBusinessName(String name){
        this.receipt_head = name;
    }


    @JavascriptInterface
    public String printPlusxReceipt(String json,float total,float tendered_in){
        List<ReceiptItem> printables = new ArrayList<>();

        try {
            JSONArray items = new JSONArray(json);
            for(int i=0;i<items.length();i++){
                ReceiptItem receiptItem = new ReceiptItem();
                JSONObject item = items.getJSONObject(i);
                receiptItem.product_name = item.getString("product");
                receiptItem.unit_price = item.getDouble("unit_price");
                receiptItem.qty = item.getInt("qty");
                receiptItem.sub_total = item.getDouble("sub_total");
                printables.add(receiptItem);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return "Invalid json structure"+e.getMessage();
        }

        return printDoc(receipt_head,printables,total,tendered_in);

    }



    public String printDoc(String businessname, List<ReceiptItem> receiptItems,float total,float tendered_in){
        if(mIzkcService!=null){
            try {
                String status =  mIzkcService.getPrinterStatus();
                /*
                Create a list of products, traverse them and shit
                 */
                mIzkcService.printGBKText("\n");
                mIzkcService.printGBKText(""+businessname + "\n\n");

                mIzkcService.printGBKText(mIzkcService_CUT_OFF_RULE);
                mIzkcService.printGBKText("Product" + "       " + "unit" + "  " + "Qty" + "  " + "Price" + "  " + "\n");
                mIzkcService.printGBKText(mIzkcService_CUT_OFF_RULE);

                for (int i = 0; i < receiptItems.size(); i++) {

                    String space0 = "";
                    String space1 = "";
                    String space2 = "";
                    String space3 = "";

                    String name = receiptItems.get(i).product_name;
                    String unit_price = ""+receiptItems.get(i).unit_price;
                    String amount = ""+receiptItems.get(i).qty;
                    String price = ""+receiptItems.get(i).unit_price*receiptItems.get(i).qty;

                    int name_length = name.length();
                    int unit_price_length = unit_price.length();
                    int amount_length = amount.length();
                    int price_length = price.length();

                    int space_length0 = GOODS_NAME_LENGTH - name_length;
                    int space_length1 = GOODS_UNIT_PRICE_LENGTH - unit_price_length;
                    int space_length2 = GOODS_AMOUNT - amount_length;
                    int space_length3 = GOODS_PRICE_LENGTH - price_length;

                    String name1 = "";
                    String name2 = "";

                    if (name_length > GOODS_NAME_LENGTH) {
                        name1 = name.substring(0, 10);
                        name2 = name.substring(10, name_length);

                        for (int j = 0; j < space_length1; j++) {
                            space1 += " ";
                        }
                        for (int j = 0; j < space_length2 - 1; j++) {
                            space2 += " ";
                        }

                        mIzkcService
                                .printGBKText(name1 + "  " + unit_price
                                        + space1 + " " + amount + space2
                                        + price + "\n");

                        mIzkcService.printGBKText(name2 + "\n");

                    } else {
                        for (int j = 0; j < space_length0; j++) {
                            space0 += "  ";
                        }
                        for (int j = 0; j < space_length1; j++) {
                            space1 += " ";
                        }
                        for (int j = 0; j < space_length2 - 1; j++) {
                            space2 += " ";
                        }

                        mIzkcService.printGBKText(name + space0 + "  "
                                + unit_price + space1 + " " + amount + space2
                                + price + "\n");
                    }
                }

                mIzkcService.printGBKText(mIzkcService_CUT_OFF_RULE);

                mIzkcService.printGBKText("Total: "+total+"\n");
                mIzkcService.printGBKText("Tax: "+0.00+"\n");
                mIzkcService.printGBKText("Tendered in: "+tendered_in+"\n");
                if((tendered_in - total)>0)
                    mIzkcService.printGBKText("Balance: "+(tendered_in - total)+"\n");

                mIzkcService.printGBKText(mIzkcService_CUT_OFF_RULE);

                mIzkcService.printGBKText("\nPowered by getplusx.com" + "\n\n\n\n");


            } catch (RemoteException e) {
                e.printStackTrace();
                Toast.makeText(mContext,"Error:"+e.getMessage(),Toast.LENGTH_SHORT).show();

            }

            return "Print success";
        }else{
            return "Device interface not yet active";
        }
    }

    public void updatePrintService(IZKCService mIzkcService) {
        this.mIzkcService = mIzkcService;
    }


    private class ReceiptItem{
        public String product_name;
        public double unit_price;
        public int qty;
        public double sub_total;
    }

}
