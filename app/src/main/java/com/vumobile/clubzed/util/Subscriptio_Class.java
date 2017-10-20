package com.vumobile.clubzed.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.vumobile.clubzed.AppConstant;
import com.vumobile.clubzed.Picture_Sticker_Related.PictureDetailsActivity;
import com.vumobile.clubzed.Picture_Sticker_Related.StickerPreview;
import com.vumobile.clubzed.R;
import com.vumobile.clubzed.Soap.CallSoap;
import com.vumobile.clubzed.SongRelated.BanglaTopHits;
import com.vumobile.clubzed.SongRelated.PlaySongActivity;
import com.vumobile.clubzed.VideoRelated.VideoPreViewActivity;

/**
 * Created by it-6 on 2/28/2016.
 */
public class Subscriptio_Class {

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
    public static String type="";
    public BanglaTopHits banglaTopHits=new BanglaTopHits();
    public static boolean alreadySubscribed=false;

    public WebView subscriptionWebView=new WebView(applicationContext);

    public static String contentCode = "", categoryCode = "", contentName = "", sContentType = "",
            sPhysicalFileName = "", ZedID = "", contentImg = "", model = "", manufacture = "";
    public static int dimHeight, dimWidth;
    // Context hello=applicationContext.getApplicationContext();



    public void detectMSISDN(){




        if (alreadySubscribed==true){

            if (type.matches("song")) {
                Intent playSongIntent=new Intent(applicationContext,PlaySongActivity.class);
                applicationContext.startActivity(playSongIntent);
            } else if(type.matches("video")) {
                Intent playSongIntent=new Intent(applicationContext,VideoPreViewActivity.class);
                applicationContext.startActivity(playSongIntent);
            }
            else if(type.matches("game")) {
                Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                applicationContext.startActivity(playSongIntent);
            }
            else if(type.matches("pic")) {
                Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                applicationContext.startActivity(playSongIntent);
            }
            else if(type.matches("mpic")) {
                Intent playSongIntent=new Intent(applicationContext,StickerPreview.class);
                applicationContext.startActivity(playSongIntent);
            }


        }

        else {
            mPDialog = new ProgressDialog(applicationContext);
            // Showing progress dialog before making http request
            mPDialog.setMessage("Loading...");
            // mPDialog.show();


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


                if (IfExist(rsltNumber).equals("1")) {
                    //------------implemented for banglalink Campaign at 10.1.2016---------------
                    if (rsltNumber.startsWith("019") || rsltNumber.startsWith("88019")) {
                        //getJsonObjectforCampain(AppConstant.mno);
                        if (CarryScore.matches("99999")) {
                            Toast.makeText(applicationContext, "You have reached your today goal.", Toast.LENGTH_LONG).show();

                        } else {

                            alreadySubscribed=true;
                            //Toast.makeText(applicationContext,"You Subscribed Successfully",Toast.LENGTH_LONG).show();
                            if (type.matches("song")) {
                                Intent playSongIntent=new Intent(applicationContext,PlaySongActivity.class);
                                applicationContext.startActivity(playSongIntent);
                            } else if(type.matches("video")) {
                                Intent playSongIntent=new Intent(applicationContext,VideoPreViewActivity.class);
                                applicationContext.startActivity(playSongIntent);
                            }else if(type.matches("game")) {
                                Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                                applicationContext.startActivity(playSongIntent);
                            }
                            else if(type.matches("pic")) {
                                Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                                applicationContext.startActivity(playSongIntent);
                            }
                            else if(type.matches("mpic")) {
                                Intent playSongIntent=new Intent(applicationContext,StickerPreview.class);
                                applicationContext.startActivity(playSongIntent);
                            }

                            //urlJsonRequest();


                        }
                    } else {
                        alreadySubscribed=true;
                        // Toast.makeText(applicationContext,"You Subscribed Successfully",Toast.LENGTH_LONG).show();
                        if (type.matches("song")) {
                            Intent playSongIntent=new Intent(applicationContext,PlaySongActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        } else if(type.matches("video")) {
                            Intent playSongIntent=new Intent(applicationContext,VideoPreViewActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        else if(type.matches("game")) {
                            Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        else if(type.matches("pic")) {
                            Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        else if(type.matches("mpic")) {
                            Intent playSongIntent=new Intent(applicationContext,StickerPreview.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        //urlJsonRequest();
                        //urlJsonRequest();
                    }
                } else {
                    if (rsltNumber.startsWith("018") || rsltNumber.startsWith("88018"))
                    {
                        robiNewSubscription();
                        //robiConfirmDialog();
                    }
                    else if (rsltNumber.startsWith("015")
                            || rsltNumber.startsWith("88015")
                            || rsltNumber.startsWith("016")
                            || rsltNumber.startsWith("88016")
                            || rsltNumber.startsWith("017")
                            || rsltNumber.startsWith("88017")) {

                        otheOperatorConfirm();

                    } else if (rsltNumber.startsWith("019")
                            || rsltNumber.startsWith("88019")) {
                        blConfirm();
                    } else {
                        alreadySubscribed=true;
                        //Toast.makeText(applicationContext,"You Subscribed Successfully",Toast.LENGTH_LONG).show();
                        if (type.matches("song")) {
                            Intent playSongIntent=new Intent(applicationContext,PlaySongActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        } else if(type.matches("video")) {
                            Intent playSongIntent=new Intent(applicationContext,VideoPreViewActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        else if(type.matches("game")) {
                            Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        else if(type.matches("pic")) {
                            Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        else if(type.matches("mpic")) {
                            Intent playSongIntent=new Intent(applicationContext,StickerPreview.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        //urlJsonRequest();//urlJsonRequest();
                        //urlJsonRequest();
                    }
                }
                hidePDialog();


            }
        }

    }

    private void robiNewSubscription() {
        boolean isSubscribe = checkRobiSubscription();

        if (isSubscribe){
            if (type.matches("song")) {
                Intent playSongIntent=new Intent(applicationContext,PlaySongActivity.class);
                applicationContext.startActivity(playSongIntent);
            } else if(type.matches("video")) {
                Intent playSongIntent=new Intent(applicationContext,VideoPreViewActivity.class);
                applicationContext.startActivity(playSongIntent);
            }
            else if(type.matches("game")) {
                Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                applicationContext.startActivity(playSongIntent);
            }
            else if(type.matches("pic")) {
                Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                applicationContext.startActivity(playSongIntent);
            }
            else if(type.matches("mpic")) {
                Intent playSongIntent=new Intent(applicationContext,StickerPreview.class);
                applicationContext.startActivity(playSongIntent);
            }
        }else {
            showRobiSubscriptionDialog();
        }
    }

    private void showRobiSubscriptionDialog() {
        
    }

    private boolean checkRobiSubscription() {
        return false;
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
                                        alreadySubscribed=true;
                                        if (type.matches("song")) {
                                            Intent playSongIntent=new Intent(applicationContext,PlaySongActivity.class);
                                            applicationContext.startActivity(playSongIntent);
                                        } else if(type.matches("video")) {
                                            Intent playSongIntent=new Intent(applicationContext,VideoPreViewActivity.class);
                                            applicationContext.startActivity(playSongIntent);
                                        }
                                        else if(type.matches("game")) {
                                            Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                                            applicationContext.startActivity(playSongIntent);
                                        }
                                        else if(type.matches("pic")) {
                                            Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                                            applicationContext.startActivity(playSongIntent);
                                        }
                                        else if(type.matches("mpic")) {
                                            Intent playSongIntent=new Intent(applicationContext,StickerPreview.class);
                                            applicationContext.startActivity(playSongIntent);
                                        }
                                        //Toast.makeText(applicationContext,"You Subscribed Successfully",Toast.LENGTH_LONG).show();//urlJsonRequest();
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
                                            alreadySubscribed=true;
                                            // ibtnDownload.setBackgroundResource();
                                            if (type.matches("song")) {
                                                Intent playSongIntent=new Intent(applicationContext,PlaySongActivity.class);
                                                applicationContext.startActivity(playSongIntent);
                                            } else if(type.matches("video")) {
                                                Intent playSongIntent=new Intent(applicationContext,VideoPreViewActivity.class);
                                                applicationContext.startActivity(playSongIntent);
                                            }
                                            else if(type.matches("game")) {
                                                Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                                                applicationContext.startActivity(playSongIntent);
                                            }
                                            else if(type.matches("pic")) {
                                                Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                                                applicationContext.startActivity(playSongIntent);
                                            }
                                            else if(type.matches("mpic")) {
                                                Intent playSongIntent=new Intent(applicationContext,StickerPreview.class);
                                                applicationContext.startActivity(playSongIntent);
                                            }
                                            // Toast.makeText(applicationContext,"You Subscribed Successfully",Toast.LENGTH_LONG).show();//urlJsonRequest();
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



        // use this condition to solve crash.Beacuse sometimes app can not show dialog
        if(!((Activity) applicationContext).isFinishing())
        {
            //show dialog
            dialogMNO.show();
        }
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

        //final Dialog dialogPackageRobi = new Dialog(applicationContext, R.style.MyDialog);
        //dialogPackageRobi.setContentView(R.layout.robi_activation_type);  //TODO
        //dialogPackageRobi.setContentView(R.layout.robi_daily_charge_dialog);

//        final ImageButton ibtnDaily = (ImageButton) dialogPackageRobi
//                .findViewById(R.id.ibtnDailyCharge);
        //final ImageView ibtnWeekly = (ImageView) dialogPackageRobi.findViewById(R.id.ibtnWeeklyCharge);
        //final ImageButton ibtnCancel = (ImageButton) dialogPackageRobi.findViewById(R.id.ibtnCancel);

//        ibtnDaily.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                dialogPackageRobi.dismiss();

        chargeType = "CZD";

        final Dialog dialogRobiConfirm = new Dialog(applicationContext, R.style.MyDialog);
        dialogRobiConfirm.setContentView(R.layout.robi_daily_charge_dialog);

        final ImageButton btnOkConfirm = (ImageButton) dialogRobiConfirm.findViewById(R.id.btnOk);
        final ImageButton btnCancelConfirm = (ImageButton) dialogRobiConfirm.findViewById(R.id.btnCancel);

        btnOkConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // dialogRobiConfirm.dismiss();

                Toast.makeText(applicationContext,"Clicked",Toast.LENGTH_SHORT).show();
                dialogRobiConfirm.setContentView(R.layout.robi_suru_korun_dialog);
//                        final Dialog dialogRobiSuruKorun = new Dialog(applicationContext, R.style.MyDialog);
//                        dialogRobiSuruKorun.setContentView(R.layout.robi_suru_korun_dialog);
                ImageButton btnSurukorun = (ImageButton) dialogRobiConfirm.findViewById(R.id.btnOk);
                ImageButton btnCancelation = (ImageButton) dialogRobiConfirm.findViewById(R.id.btnCancel);

                btnSurukorun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogRobiConfirm.dismiss();

                        alreadySubscribed=true;

                        if (type.matches("song")) {
                            Intent playSongIntent = new Intent(applicationContext, PlaySongActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        } else if (type.matches("video")) {
                            Intent playSongIntent = new Intent(applicationContext, VideoPreViewActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        } else if (type.matches("pic")) {
                            Intent playSongIntent = new Intent(applicationContext, PictureDetailsActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        } else if (type.matches("game")) {
                            Intent playSongIntent = new Intent(applicationContext, PictureDetailsActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        } else if (type.matches("mpic")) {
                            Intent playSongIntent = new Intent(applicationContext, StickerPreview.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                    }
                });

                btnCancelation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogRobiConfirm.dismiss();            ///////////////////////////////////////////////Nibondhon Batil
                        robiDeactivate(rsltNumber, chargeType);
                        Toast.makeText(applicationContext, "Un-subscribed", Toast.LENGTH_LONG).show();
//                                Intent homeIntent = new Intent(applicationContext, MainActivity.class);
//                                //startActivity(homeIntent);
                        //context.finish();
                    }
                });

                // ////////////////////////////////////////////////////////////////////////////

                robiSubscription(rsltNumber, chargeType);
                // Toast.makeText(applicationContext, "Subscribed", Toast.LENGTH_LONG).show();


            }
        });

        btnCancelConfirm
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        dialogRobiConfirm.dismiss();
                    }
                });

        dialogRobiConfirm.show();
//            }
//        });



//        ibtnWeekly.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                // ////////////////////////////////////////////////////////////////////////////
//                dialogPackageRobi.dismiss();
//                chargeType = "CZW";
//                final Dialog dialogRobiConfirm = new Dialog(
//                        applicationContext, R.style.MyDialog);
//                dialogRobiConfirm
//                        .setContentView(R.layout.robi_weekly_charge_dialog);
//
//                final ImageButton btnOkConfirm = (ImageButton) dialogRobiConfirm
//                        .findViewById(R.id.btnOk);
//                final ImageButton btnCancelConfirm = (ImageButton) dialogRobiConfirm
//                        .findViewById(R.id.btnCancel);
//
//                btnOkConfirm.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//
//                        dialogRobiConfirm.dismiss();
//                        robiSubscription(rsltNumber, chargeType);
//                        Toast.makeText(applicationContext, "Subscribed", Toast.LENGTH_LONG).show();
//                        // ////////////////////////////////////////////////////////////////////////////
//                        final Dialog dialogRobi = new Dialog(
//                                applicationContext, R.style.MyDialog);
//                        dialogRobi.setContentView(R.layout.robi_weekly_download_dialog);
//
//                        final ImageButton btnDownload = (ImageButton) dialogRobi
//                                .findViewById(R.id.btnDownload);
//                        final ImageButton btnCancel = (ImageButton) dialogRobi
//                                .findViewById(R.id.btnCancel);
//
//                        btnDownload.setOnClickListener(new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//
//                                dialogRobi.dismiss();
//                                alreadySubscribed=true;
//                                if (type.matches("song")) {
//                                    Intent playSongIntent=new Intent(applicationContext,PlaySongActivity.class);
//                                    applicationContext.startActivity(playSongIntent);
//                                } else if(type.matches("video")) {
//                                    Intent playSongIntent=new Intent(applicationContext,VideoPreViewActivity.class);
//                                    applicationContext.startActivity(playSongIntent);
//                                }
//                                else if(type.matches("game")) {
//                                    Intent playSongIntent=new Intent(applicationContext,StickerPreview.class);
//                                    applicationContext.startActivity(playSongIntent);
//                                }
//                                else if(type.matches("pic")) {
//                                    Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
//                                    applicationContext.startActivity(playSongIntent);
//                                }
//                                else if(type.matches("mpic")) {
//                                    Intent playSongIntent=new Intent(applicationContext,StickerPreview.class);
//                                    applicationContext.startActivity(playSongIntent);
//                                }
//                                Toast.makeText(applicationContext,
//                                        "You Will Receive SMS Shortly",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                        btnCancel.setOnClickListener(new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//
//                                dialogRobi.dismiss();
//
//                                robiDeactivate(rsltNumber, chargeType);
//                                Toast.makeText(applicationContext, "Un-subscribed", Toast.LENGTH_LONG).show();
//                                /*Intent homeIntent = new Intent(applicationContext, HomeActivity.class);
//
//                                startActivity(homeIntent);
//                                finish();*/
//                            }
//                        });
//
//                        dialogRobi.show();
//
//
//
//                    }
//                });
//
//                btnCancelConfirm
//                        .setOnClickListener(new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//
//                                dialogRobiConfirm.dismiss();
//                               /* Intent homeIntent = new Intent(applicationContext, HomeActivity.class);
//
//                                startActivity(homeIntent);
//                                finish();*/
//                            }
//                        });
//
//                dialogRobiConfirm.show();
//            }
//        });
//
//
//
//        ibtnCancel.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                dialogPackageRobi.dismiss();
//
//              /*  Intent homeIntent = new Intent(ContentDownloadActivity.this, HomeActivity.class);
//
//                startActivity(homeIntent);
//                finish();*/
//            }
//        });
//
//        dialogPackageRobi.show();
    }


    private void otheOperatorConfirm() {

        hidePDialog();
        hidemPDialog();
        final Dialog dialogOther = new Dialog(applicationContext, R.style.MyDialog);
        dialogOther.setContentView(R.layout.subscrib_dialog);
        TextView confirmationDialogText = (TextView) (dialogOther.findViewById(R.id.txtSuggestionLebel));

        if (rsltNumber.startsWith("88017") || rsltNumber.startsWith("017")) {
            confirmationDialogText.setText("ClubZ is subscription based entertainment service for Grameenphone.\n" +
                    "ClubZ subscribers will get Weekly 4 free downloads from the APP.\n" +
                    "\n \nTo get FREE content activate ClubZ first. Click Confirm button below! You will be charged Tk15(+VAT+SD&SC)/Weekly with Auto-renewal.\n" +
                    "\nTo DeActive type STOP CZ and send SMS to 6624.");

        } else if (rsltNumber.startsWith("88016") || rsltNumber.startsWith("016")) {
            confirmationDialogText.setText("ClubZ is subscription based entertainment service for Airtel.\n" +
                    "ClubZ subscribers will get Weekly 4 free downloads from the APP.\n" +
                    "\n" +
                    " \nTo get FREE content activate ClubZ first. Click Confirm button below! You will be charged Tk15(+VAT+SD&SC)/Weekly with Auto-renewal.\n" +
                    "\nTo DeActive type STOP CZ and send SMS to 6624.");
        } else if (rsltNumber.startsWith("88015") || rsltNumber.startsWith("015")) {
            confirmationDialogText.setText("ClubZ is subscription based entertainment service for Teletalk.\n" +
                    "ClubZ subscribers will get Weekly 4 free downloads from the APP.\n" +
                    "\n" +
                    "To get FREE content activate ClubZ first. Click Confirm button below! You will be charged Tk2(+VAT+SD&SC)/Daily with Auto-renewal.\n" +
                    "\n" +
                    "To DeActive type STOP CZ and send SMS to 6624.");
        }

        final Button btnOk = (Button) dialogOther.findViewById(R.id.btnOk);


        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialogOther.cancel();
                final Dialog mDialog = new Dialog(applicationContext,R.style.MyDialog);
                mDialog.setContentView(R.layout.subscrib_scnd_class);
                Button nishchit_korun = (Button) mDialog.findViewById(R.id.btnOk_nishchit_korun);

                nishchit_korun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.cancel();
                        subscriptionWebView.loadUrl("http://wap.shabox.mobi/cznewui/pages/clubzsubscription.aspx?content_code=123456789&CategoryCode=123456789&Mno=" + rsltNumber);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                CallSoap.sendMSDNtoSOAP();
                            }
                        }).start();
                        alreadySubscribed=true;
                        if (type.matches("song")) {
                            Intent playSongIntent=new Intent(applicationContext,PlaySongActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        } else if(type.matches("video")) {
                            Intent playSongIntent=new Intent(applicationContext,VideoPreViewActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        else if(type.matches("game")) {
                            Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        else if(type.matches("pic")) {
                            Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        else if(type.matches("mpic")) {
                            Intent playSongIntent=new Intent(applicationContext,StickerPreview.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        //Toast.makeText(applicationContext, "You Subscribed Successfully", Toast.LENGTH_LONG).show();//urlJsonRequest();
                    }
                });
                mDialog.show();

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
                final Dialog blmDialog = new Dialog(applicationContext,R.style.MyDialog);
                blmDialog.setContentView(R.layout.subscrib_scnd_class);
                Button btnBlNishcitKorun = (Button) blmDialog.findViewById(R.id.btnOk_nishchit_korun);

                btnBlNishcitKorun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        blmDialog.cancel();
                        subscriptionWebView.loadUrl("http://wap.shabox.mobi/cznewui/pages/clubzsubscription.aspx?content_code=123456789&CategoryCode=123456789&Mno=" + rsltNumber);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                CallSoap.sendMSDNtoSOAP();
                            }
                        }).start();
                        alreadySubscribed=true;
                        if (type.matches("song")) {
                            Intent playSongIntent=new Intent(applicationContext,PlaySongActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        } else if(type.matches("video")) {
                            Intent playSongIntent=new Intent(applicationContext,VideoPreViewActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        else if(type.matches("game")) {
                            Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        else if(type.matches("pic")) {
                            Intent playSongIntent=new Intent(applicationContext,PictureDetailsActivity.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        else if(type.matches("mpic")) {
                            Intent playSongIntent=new Intent(applicationContext,StickerPreview.class);
                            applicationContext.startActivity(playSongIntent);
                        }
                        //Toast.makeText(applicationContext, "You Subscribed Successfully", Toast.LENGTH_LONG).show();//urlJsonRequest();

                    }
                });
                blmDialog.show();


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

