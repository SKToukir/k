package com.vumobile.clubzed.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vumobile.clubzed.AppConstant;
import com.vumobile.clubzed.Picture_Sticker_Related.PictureDetailsActivity;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.Soap.CallSoap;
import com.vumobile.clubzed.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by it-6 on 1/26/2016.
 */
public class Download_Class {
    String finalUrl;
    public static String testUrl;
    public static Context applicationContext;
    public static String rsltNumber = "";
    public static boolean active_activity = false;
    String redirectURL = null;
    public static String rslt = "";
    String PIN = null;
    private ProgressDialog pDialog = new ProgressDialog(applicationContext);
    private ProgressDialog mPDialog = new ProgressDialog(applicationContext);
    public String CarryScore = "999999999";
    String chargeType = null;
    String message = null;


    PictureDetailsActivity pictureDetailsActivity;
    public static String contentCode = "", categoryCode = "", contentName = "", sContentType = "",
            sPhysicalFileName = "", ZedID = "", contentImg = "", model = "", manufacture = "";
    public static int dimHeight, dimWidth;


    String customURL = "http://wap.shabox.mobi/clubz/Pages/ContentDownloadApp.aspx?";

    private Intent service = new Intent(applicationContext, DownloadService.class);


    public void detectMSISDN() {

        mPDialog = new ProgressDialog(applicationContext);
        // Showing progress dialog before making http request
        mPDialog.setMessage("Loading...");
        mPDialog.show();


        rsltNumber = "START";

        try {
            Thread.sleep(1000);

            Caller c = new Caller();
            // c.ad= c.ad;
            c.join();
            c.start();

            while (rsltNumber == "START") {
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                }
            }

            AppConstant.mno = rsltNumber;

        } catch (Exception ex) {

        }


        // new serverAccessAsynTask().execute("");
        // if MSISDN "ERROR" then show Enter phone no. pop-up
        if (rsltNumber.equalsIgnoreCase("ERROR")) {
            hidePDialog();
            hidemPDialog();
            wifiMSISDNDialog();

        }

        // APN Condition
        else {


            if (IfExist(rsltNumber).equals("1"))
            {
                //------------implemented for banglalink Campaign at 10.1.2016---------------
                if (rsltNumber.startsWith("019") || rsltNumber.startsWith("88019"))
                {
                    //getJsonObjectforCampain(AppConstant.mno);
                    if (CarryScore.matches("99999"))
                    {
                        Toast.makeText(applicationContext, "You have reached your today goal.", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        urlJsonRequest();
                    }
                }
                else
                {
                    urlJsonRequest();
                }
            }
            else
            {

                if (rsltNumber.startsWith("018") || rsltNumber.startsWith("88018"))
                {

                    robiConfirmDialog();
                }
                else if (rsltNumber.startsWith("015")
                        || rsltNumber.startsWith("88015")
                        || rsltNumber.startsWith("016")
                        || rsltNumber.startsWith("88016")
                        || rsltNumber.startsWith("017")
                        || rsltNumber.startsWith("88017"))
                {

                    otheOperatorConfirm();

                }
                else if (rsltNumber.startsWith("019")
                        || rsltNumber.startsWith("88019"))
                {
                    blConfirm();
                }
                else
                {
                    urlJsonRequest();
                }
            }
            hidePDialog();


        }

    }

    //im here*****************************************************************************************************************
    // Make Volley Json Request for get redirect URL
//Download method
    public void urlJsonRequest() {

        hidePDialog();
        Log.i("GameZid", ZedID);

        String mCustomURL = customURL + "content_code=" + contentCode
                + "&CategoryCode=" + categoryCode + "&ContentTitle="
                + contentName + "&sContentType="
                + sContentType + "&sPhysicalFileName="
                + sPhysicalFileName.replace(" ", "%20") + "&sPreviewUrl="
                + contentName.replace(" ", "_") + ".jpg" + "&ZedID="
                + ZedID + "&Mno=" + rsltNumber + "&source=CZ_GGL_AND_APP"
                + "&manufacturer" + manufacture.replace(" ", "%20") + "&model="
                + model.replace(" ", "%20") + "&dimension=" + dimHeight + "x"
                + dimWidth + "&suptype=" + chargeType;


        Log.d("mcustomURL", mCustomURL);
        finalUrl = mCustomURL.replaceAll(" ","%20");
        Log.d("ios",finalUrl);


        JsonObjectRequest mainObj = new JsonObjectRequest(Request.Method.GET, finalUrl, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {


                Log.i(">>Response<<", response.toString());

                try {
//                    String url = "http://wap.shabox.mobi/CMS/Content/Graphics/FullVideo/D480x320/"+sPhysicalFileName+".3gp";
                    //redirectURL = testUrl;
                    redirectURL = response.getString("url").replace(" ", "%20");
                    message = response.getString("message");

                    Log.i("URL", redirectURL);
                    //Log.d("ulrs",testUrl);
                    Log.i(">>>Message<<<", message);
                    // Toast.makeText(getApplicationContext(),redirectURL,Toast.LENGTH_LONG).show();

                    if (redirectURL.equalsIgnoreCase("null")) {
                        hidePDialog();
                        hidemPDialog();
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show();

                    } else {
                        // String url = redirectURL;
                        hidemPDialog();

                        Toast.makeText(applicationContext,
                                message, Toast.LENGTH_SHORT).show();
                        Log.d("Lk",redirectURL);

                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {

                            e.printStackTrace();
                        }

                        // new DownloadFileAsync().execute(redirectURL);


                        hidemPDialog();
                        service.putExtra("url", redirectURL);
                        applicationContext.startService(service);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                CallSoap.sendMSDNtoSOAP();
                            }
                        }).start();


                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                    hidemPDialog();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {

                //
                hidePDialog();
                Toast.makeText(applicationContext, arg0.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        AppController.getInstance().addToRequestQueue(mainObj);
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallSoap.sendMSDNtoSOAP();
            }
        }).start();


    }









    private void wifiMSISDNDialog() {

        final Dialog dialogMNO = new Dialog(applicationContext,
                R.style.MyDialog);
        dialogMNO.setContentView(R.layout.msisdn_form);
        dialogMNO.setCancelable(true);
        final EditText edtMno = (EditText) dialogMNO.findViewById(R.id.edtMno);
        final Button btnOk = (Button) dialogMNO.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                rsltNumber = edtMno.getText().toString();

                if (rsltNumber.startsWith("01")) {
                    rsltNumber = "88" + rsltNumber;
                    dialogMNO.dismiss();
                } else if (rsltNumber.equals("") || rsltNumber.equals(" ")
                        || rsltNumber.equals(null) || rsltNumber.isEmpty()
                        || rsltNumber.length() > 13 || rsltNumber.length() < 11) {
                    Toast.makeText(applicationContext,
                            "Please Enter Correct Mobile Number",
                            Toast.LENGTH_LONG).show();
                }

                if (rsltNumber.startsWith("018")
                        || rsltNumber.startsWith("019")
                        || rsltNumber.startsWith("88018")
                        || rsltNumber.startsWith("88019")) {
                    dialogMNO.dismiss();
                    Toast.makeText(applicationContext, "Please use operator's APN", Toast.LENGTH_SHORT).show();
                } else {
                    dialogMNO.dismiss();
                    if (IfExist(rsltNumber).equals("1")) {

                        final Dialog dialogPIN = new Dialog(applicationContext, R.style.MyDialog);
                        dialogPIN.setContentView(R.layout.pincode_form);
                        dialogPIN.setCancelable(true);
                        final TextView tvClickHere = (TextView) dialogPIN
                                .findViewById(R.id.btn_click_here);
                        final EditText edtPIN = (EditText) dialogPIN
                                .findViewById(R.id.txtPinCode);
                        final Button btnOkPIN = (Button) dialogPIN
                                .findViewById(R.id.btnOkPIN);

                        btnOkPIN.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {


                                PIN = edtPIN.getText().toString();
                                if (PIN.equals("") || PIN.equals(" ")
                                        || PIN.equals(null)) {
                                    Toast.makeText(applicationContext,
                                            "Please Enter Any PIN",
                                            Toast.LENGTH_LONG).show();
                                }
                                // Toast.makeText(ContentDownloadActivity.this,
                                // PIN, 1000).show();

                                try {
                                    String pinStatus = PinChk(rsltNumber, PIN);
                                    if (pinStatus.equalsIgnoreCase("1")) {
                                        dialogPIN.dismiss();
                                        // ibtnDownload.setBackgroundResource();
                                        urlJsonRequest();
                                    } else {

                                        Toast.makeText(applicationContext,
                                                "Invalid PIN", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {

                                }
                            }
                        });

                        tvClickHere.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                // stub
                                forgetPin(rsltNumber);

                                Toast.makeText(applicationContext,
                                        "You Will Receive SMS Shortly",
                                        Toast.LENGTH_LONG).show();

                            }
                        });

                        dialogPIN.show();
                    } else {
                        if (rsltNumber.equals("") || rsltNumber.equals(" ")
                                || rsltNumber.equals(null)
                                || rsltNumber.isEmpty()
                                || rsltNumber.length() > 13
                                || rsltNumber.length() < 11) {
                            Toast.makeText(applicationContext,
                                    "Please Enter Correct Mobile Number",
                                    Toast.LENGTH_LONG).show();
                        } else {

                            Log.i(">>Else NO<<", rsltNumber);

                            sendSMS(rsltNumber);

                            final Dialog dialogPIN = new Dialog(applicationContext, R.style.MyDialog);
                            dialogPIN.setContentView(R.layout.pincode_form);
                            dialogPIN.setCancelable(true);
                            final TextView tvClickHere = (TextView) dialogPIN
                                    .findViewById(R.id.btn_click_here);
                            final EditText edtPIN = (EditText) dialogPIN
                                    .findViewById(R.id.txtPinCode);
                            final Button btnOkPIN = (Button) dialogPIN
                                    .findViewById(R.id.btnOkPIN);

                            btnOkPIN.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {


                                    PIN = edtPIN.getText().toString();
                                    if (PIN.equals("") || PIN.equals(" ")
                                            || PIN.equals(null)) {
                                        Toast.makeText(applicationContext,
                                                "Please Enter Any PIN",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    // Toast.makeText(ContentDownloadActivity.this,
                                    // PIN, 1000).show();

                                    try {
                                        String pinStatus = PinChk(rsltNumber,
                                                PIN);
                                        if (pinStatus.equalsIgnoreCase("1")) {
                                            dialogPIN.dismiss();
                                            // ibtnDownload.setBackgroundResource();
                                            urlJsonRequest();
                                        } else {

                                            Toast.makeText(
                                                    applicationContext,
                                                    "Invalid PIN", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {

                                    }
                                }
                            });

                            tvClickHere
                                    .setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            // stub
                                            forgetPin(rsltNumber);

                                            Toast.makeText(
                                                    applicationContext,
                                                    "You Will Receive SMS Shortly",
                                                    Toast.LENGTH_LONG).show();

                                        }
                                    });

                            dialogPIN.show();

                            Toast.makeText(applicationContext,
                                    "You Will Receive SMS With PIN Shortly",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }
        });

        dialogMNO.show();
    }


    public String sendSMS(String mno) {
        try {
            Thread.sleep(1000);
            rslt = "START";
            CallerAll cws = new CallerAll();
            cws.a = mno;
            cws.b = "new_user_sms";
            cws.ad = cws.ad;
            cws.join();
            cws.start();

            while (rslt == "START") {
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                }
            }

            Log.i("SEND_SMS", "" + rslt);

        } catch (Exception ex) {
            Log.i("EXCEPTION EXIST USER", "" + ex.toString());
            rslt = "Exception";

        }

        return rslt;
    }


    //---------------Method for checking the MSISDN exist or not--------------

    public String IfExist(String mno) {
        try {
            Thread.sleep(1000);
            rslt = "START";
            CallerCheckExistUser cd = new CallerCheckExistUser();
            cd.a = mno;
            cd.ad = cd.ad;
            cd.join();
            cd.start();

            while (rslt == "START") {
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                }
            }

            Log.i("IsEXIST", "" + rslt);

        } catch (Exception ex) {
            Log.i("EXCEPTION EXIST USER", "" + ex.toString());
            rslt = "Exception";
        }

        return rslt;
    }

    //--------Method for retreve the user pin if user forget the pin-------------

    public String forgetPin(String mno) {
        try {
            Thread.sleep(1000);
            rslt = "START";
            CallerAll cws = new CallerAll();
            cws.a = mno;
            cws.b = "forget_pin_code";
            cws.pin = "";
            cws.ad = cws.ad;
            cws.join();
            cws.start();

            while (rslt == "START") {
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                }
            }

            Log.i("FORGET PIN RESULT ", "" + rslt);

        } catch (Exception ex) {
            Log.i("EXCEPTION EXIST USER", "" + ex.toString());
            rslt = "Exception";

        }

        return rslt;
    }


    //---------------Method for varify the MSISDN and pin-----------------
    // This Method For Verify MSISDN & PIN
    public String PinChk(String mno, String pin) {
        try {
            Thread.sleep(1000);
            rslt = "START";
            CallerAll cws = new CallerAll();
            cws.a = mno;
            cws.b = "pincode_check";
            cws.pin = pin;
            cws.ad = cws.ad;
            cws.join();
            cws.start();

            while (rslt == "START") {
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                }
            }

            Log.i("PIN_CHECK RESULT", "" + rslt);

        } catch (Exception ex) {
            Log.i("EXCEPTION EXIST USER", "" + ex.toString());
            rslt = "Exception";

        }

        return rslt;
    }


    //------------------Robi Confirm Dialog--------------------
    private void robiConfirmDialog() {


        hidePDialog();
        hidemPDialog();

        final Dialog dialogPackageRobi = new Dialog(
                applicationContext, R.style.MyDialog);
        //dialogPackageRobi.setContentView(R.layout.robi_activation_type);     //TODO
        dialogPackageRobi.setContentView(R.layout.robi_daily_charge_dialog);

        final ImageButton ibtnDaily = (ImageButton) dialogPackageRobi
                .findViewById(R.id.ibtnDailyCharge);
        final ImageView ibtnWeekly = (ImageView) dialogPackageRobi
                .findViewById(R.id.ibtnWeeklyCharge);
        final ImageButton ibtnCancel = (ImageButton) dialogPackageRobi
                .findViewById(R.id.ibtnCancel);

        ibtnDaily.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialogPackageRobi.dismiss();

                chargeType = "CZD";

                final Dialog dialogRobiConfirm = new Dialog(
                        applicationContext, R.style.MyDialog);
                dialogRobiConfirm
                        .setContentView(R.layout.robi_daily_charge_dialog);

                final ImageButton btnOkConfirm = (ImageButton) dialogRobiConfirm
                        .findViewById(R.id.btnOk);
                final ImageButton btnCancelConfirm = (ImageButton) dialogRobiConfirm
                        .findViewById(R.id.btnCancel);

                btnOkConfirm.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        // ////////////////////////////////////////////////////////////////////////////
                        dialogRobiConfirm.dismiss();
                        robiSubscription(rsltNumber, chargeType);
                        Toast.makeText(applicationContext, "Subscribed", Toast.LENGTH_LONG).show();
                        final Dialog dialogRobi = new Dialog(
                                applicationContext, R.style.MyDialog);
                        dialogRobi.setContentView(R.layout.robi_daily_download_dialog);

                        final ImageButton btnDownload = (ImageButton) dialogRobi
                                .findViewById(R.id.btnDownload);
                        final ImageButton btnCancel = (ImageButton) dialogRobi
                                .findViewById(R.id.btnCancel);

                        btnDownload.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                dialogRobi.dismiss();
                                urlJsonRequest();
                                Toast.makeText(applicationContext,
                                        "You Will Receive SMS Shortly",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                        btnCancel.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                dialogRobi.dismiss();
                                robiDeactivate(rsltNumber, chargeType);
                                Toast.makeText(applicationContext, "Un-subscribed", Toast.LENGTH_LONG).show();
                                return;
                            }
                        });

                        dialogRobi.show();
                    }
                });

                btnCancelConfirm
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                dialogRobiConfirm.dismiss();
                                return;
                                /*Intent homeIntent = new Intent(ContentDownloadActivity.this, HomeActivity.class);

                                startActivity(homeIntent);
                                finish();*/
                            }
                        });

                dialogRobiConfirm.show();
            }
        });



        ibtnWeekly.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // ////////////////////////////////////////////////////////////////////////////
                dialogPackageRobi.dismiss();
                chargeType = "CZW";
                final Dialog dialogRobiConfirm = new Dialog(
                        applicationContext, R.style.MyDialog);
                dialogRobiConfirm
                        .setContentView(R.layout.robi_weekly_charge_dialog);

                final ImageButton btnOkConfirm = (ImageButton) dialogRobiConfirm
                        .findViewById(R.id.btnOk);
                final ImageButton btnCancelConfirm = (ImageButton) dialogRobiConfirm
                        .findViewById(R.id.btnCancel);

                btnOkConfirm.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        dialogRobiConfirm.dismiss();
                        robiSubscription(rsltNumber, chargeType);
                        Toast.makeText(applicationContext, "Subscribed", Toast.LENGTH_LONG).show();
                        // ////////////////////////////////////////////////////////////////////////////
                        final Dialog dialogRobi = new Dialog(
                                applicationContext, R.style.MyDialog);
                        dialogRobi.setContentView(R.layout.robi_weekly_download_dialog);

                        final ImageButton btnDownload = (ImageButton) dialogRobi
                                .findViewById(R.id.btnDownload);
                        final ImageButton btnCancel = (ImageButton) dialogRobi
                                .findViewById(R.id.btnCancel);

                        btnDownload.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                dialogRobi.dismiss();
                                urlJsonRequest();
                                Toast.makeText(applicationContext,
                                        "You Will Receive SMS Shortly",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                        btnCancel.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                dialogRobi.dismiss();

                                robiDeactivate(rsltNumber, chargeType);
                                Toast.makeText(applicationContext, "Un-subscribed", Toast.LENGTH_LONG).show();
                                return;
                            }
                        });

                        dialogRobi.show();



                    }
                });

                btnCancelConfirm
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                dialogRobiConfirm.dismiss();
                                return;
                            }
                        });

                dialogRobiConfirm.show();
            }
        });



        ibtnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialogPackageRobi.dismiss();

                return;
            }
        });

        dialogPackageRobi.show();
    }


    private void otheOperatorConfirm() {

        hidePDialog();
        hidemPDialog();
        final Dialog dialogOther = new Dialog(applicationContext, R.style.MyDialog);
        dialogOther.setContentView(R.layout.subscrib_dialog);
        TextView confirmationDialogText = (TextView) (dialogOther.findViewById(R.id.txtSuggestionLebel));

        if (rsltNumber.startsWith("88017") || rsltNumber.startsWith("017")) {
            confirmationDialogText.setText(
                    "You are not a registered user. To download free content every day at Tk2+ (VAT+SD&SC)/day click Confirm button.\n" +
                            "\nTo DeActive type STOP CZ and send SMS to 6624.");

        } else if (rsltNumber.startsWith("88016") || rsltNumber.startsWith("016")) {
            confirmationDialogText.setText(
                    //" \nTo get FREE content activate ClubZ first. Click Confirm button below! You will be charged Tk15(+SD&VAT)/Weekly with Auto-renewal.\n" +
                    "\nYou are not a registered user. To download free content every day at Tk2+ (VAT+SD&SC)/day click CONFIRM button.\n"+
                            "\nTo DeActive type STOP CZ and send SMS to 6624.");
        } else if (rsltNumber.startsWith("88015") || rsltNumber.startsWith("015")) {
            confirmationDialogText.setText(
                    "You are not a registered user. To download free content every day at Tk2+ (VAT+SD&SC)/day click CONFIRM button.\n"+
                            "To DeActive type STOP CZ and send SMS to 6624.");
        }

        final Button btnOk = (Button) dialogOther.findViewById(R.id.btnOk);


        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialogOther.cancel();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        applicationContext);
                builder1.setTitle("Confirm !");
                builder1.setMessage("Would You Like To Confirm !");
                builder1.setCancelable(true);
                builder1.setIcon(R.drawable.clubz);
                builder1.setPositiveButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });



                builder1.setNegativeButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                urlJsonRequest();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

        dialogOther.show();

    }


    private void blConfirm() {

        hidePDialog();
        hidemPDialog();
        final Dialog dialogOther = new Dialog(applicationContext,
                R.style.MyDialog);
        dialogOther.setContentView(R.layout.bl_dialog);

        final Button btnOk = (Button) dialogOther.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialogOther.dismiss();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        applicationContext);
                builder1.setTitle("Confirm !");
                builder1.setMessage("Would You Like To Confirm !");
                builder1.setCancelable(true);
                builder1.setIcon(R.drawable.clubz);

                builder1.setNegativeButton ("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                urlJsonRequest();
                            }
                        });
                builder1.setPositiveButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });






                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

        dialogOther.show();

    }


    // This Method For Robi subscription
    public String robiSubscription(String msisdn, String chargeType) {
        try {
            Thread.sleep(1000);
            rslt = "START";
            CallRobi cr = new CallRobi();
            cr.msisdn = msisdn;
            cr.query = "subscription";
            cr.chargeType = chargeType;
            cr.ad = cr.ad;
            cr.join();
            cr.start();

            while (rslt == "START") {
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                }
            }
            Log.i("MSISDN", "" + rsltNumber);
            Log.i("Charge Type", "" + chargeType);
            Log.i("RESPONSE", "" + rslt);

        } catch (Exception ex) {
            Log.i("EXCEPTION EXIST USER", "" + ex.toString());
            rslt = "Exception";

        }

        return rslt;
    }

    // This Method For Robi Deactivation
    public String robiDeactivate(String msisdn, String chargeType) {
        try {
            Thread.sleep(1000);
            rslt = "START";
            CallRobi cr = new CallRobi();
            cr.msisdn = msisdn;
            cr.query = "robideactivate";
            cr.chargeType = chargeType;
            cr.ad = cr.ad;
            cr.join();
            cr.start();

            while (rslt == "START") {
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {
                }
            }

            Log.i("PIN_CHECK RESULT", "" + rslt);

        } catch (Exception ex) {
            Log.i("EXCEPTION EXIST USER", "" + ex.toString());
            rslt = "Exception";

        }

        return rslt;
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private void hidemPDialog() {
        if (mPDialog != null) {
            mPDialog.dismiss();
            mPDialog = null;
        }
    }


}
