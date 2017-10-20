package com.vumobile.clubzed.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vumobile.clubzed.AppConstant;
import com.vumobile.clubzed.MainActivity;
import com.vumobile.clubzed.MyDownLoadsActivity;
import com.vumobile.clubzed.R;


public class HelpActivity extends Activity implements View.OnClickListener {

    private Intent intent;
    private ImageView btnHome, btnHelp, btnFaq, btnMyDownloads,btnBuddy, btnBdTube, btnBanglaBeats, btnAmarSticker, btnRateMe;
	TextView helpTextView,portalAddress,partThree,helpLine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout. activity_help);

        btnHome = (ImageView) findViewById(R.id.btnHomeHelp);
        btnHelp = (ImageView) findViewById(R.id.btnHelpHelp);
        btnFaq = (ImageView) findViewById(R.id.btnHelpSameHelp);
        btnMyDownloads = (ImageView) findViewById(R.id.btnMyDownloadsHelp);

        btnHome.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        btnFaq.setOnClickListener(this);
        btnMyDownloads.setOnClickListener(this);

        btnBuddy = (ImageView) findViewById(R.id.btnBuddyLinkHelp);
        btnBdTube = (ImageView) findViewById(R.id.btnBdTubeLinkHelp);
        btnBanglaBeats = (ImageView) findViewById(R.id.btnBanglaBeatsLinkHelp);
        btnAmarSticker = (ImageView) findViewById(R.id.btnAmarStickerLinkHelp);
        btnRateMe = (ImageView) findViewById(R.id.btnRateMeLinkHelp);

        btnBuddy.setOnClickListener(this);
        btnBdTube.setOnClickListener(this);
        btnBanglaBeats.setOnClickListener(this);
        btnAmarSticker.setOnClickListener(this);
        btnRateMe.setOnClickListener(this);

        Log.d("Tracker", "This is HelpActivity");
        helpTextView= (TextView) findViewById(R.id.helpTextViewOne);
        portalAddress=(TextView) findViewById(R.id.helpTextPortal);
        partThree=(TextView) findViewById(R.id.helpTextThree);
        helpLine=(TextView) findViewById(R.id.helpTextHelpLine);
        portalAddress.setMovementMethod(LinkMovementMethod.getInstance());
        helpLine.setMovementMethod(LinkMovementMethod.getInstance());





        String rsltNumber= AppConstant.mno;
        Log.d("Operator","result no  :" + rsltNumber);



        if (rsltNumber.startsWith("017")||rsltNumber.startsWith("88017")){
            helpTextView.setText("\nService Name: ClubZ\n" +
                    "Service Type: Entertainment\n" +
                    "\n" +
                    "What is ClubZ?\n" +
                    "ClubZ is a  entertainment service App.\n"+  "\n" +
                            "What is ClubZ Portal Address?"
                    );
            String text1 = "<a href='http://wap.shabox.mobi/ClubZ'>http://wap.shabox.mobi/ClubZ</a>";
            portalAddress.setText(Html.fromHtml(text1));


            partThree.setText( "\nWhy should I subscribe in ClubZ APP?\n" +
                    "From ClubZ app you can enjoy exclusive latest Song, Video Clips, Full Videos, amazing Wallpapers, most exciting Games, a vast pool of Bangla\n" +
                    "Stickers and many more entertainment base services which will full-fill your thirst of entertainment.\n" +
                    "\n" +
                    "How much I have to pay for all these service?\n" +
                    "Weekly Charging Taka 15(+VAT+SD&SC)\n" +
                    "\n" +
                    "Weekly 4-free contents\n" +
                    "  •  To Activate: Type START CZ and send SMS to 6624\n" +
                    "  •  To Deactivate: Type STOP CZ and send SMS to 6624\n" +
                    "\n" +
                    "What is the helpline/hotline number for all Operators?\n" +
                    "8801717053061\n" +
                    "\n" +
                    "What is the time to get the helpline support?\n" +
                    "08:00 AM – 07:00 PM\n" +
                    "\n" +
                    "Is this site providing any email support?\n" +
                    "Yes\n" +
                    "\n" +
                    "What is the support email address?\n");



        }
        else if (rsltNumber.startsWith("016")||rsltNumber.startsWith("88016")){
            helpTextView.setText("\nService Name: ClubZ\n" +
                            "Service Type: Entertainment\n" +
                            "\n" +
                            "What is ClubZ?\n" +
                            "ClubZ is a  entertainment service App.\n"+  "\n" +
                            "What is ClubZ Portal Address?\n"
            );
            String text1 = "<a href='http://wap.shabox.mobi/ClubZ'>http://wap.shabox.mobi/ClubZ</a>";
            portalAddress.setText(Html.fromHtml(text1));

            partThree.setText("\nWhy should I subscribe in ClubZ APP?\n" +
                    "From ClubZ app you can enjoy exclusive latest Song, Video Clips, Full Videos, amazing Wallpapers, most exciting Games, a vast pool of Bangla\n" +
                    "Stickers and many more entertainment base services which will full-fill your thirst of entertainment.\n" +
                    "\n" +
                    "How much I have to pay for all these service?\n" +
                    "\n" +
//                    "Weekly Charging Taka 15(+VAT+SD&SC)\n" +
//                    "Weekly 4-free contents\n" +
                    "Daily Charging Taka 2 + (VAT+SD+SC)\n"+
                    "Daily 3 Free contents.\n"+



                    "Airtel Users - Weekly Subscription\n" +
                    "\n" +
                    "  •   To Activate: Type START CZ and send SMS to 6624\n" +
                    "  •   To Deactivate: Type STOP CZ and send SMS to 6624\n" +
                    "\n" +
                    "What is the helpline/hotline number for all Operators?\n" +
                    "8801674985965\n" +
                    "\n" +
                    "What is the time to get the helpline support?\n" +
                    "08:00 AM – 07:00 PM\n" +
                    "\n" +
                    "Is this site providing any email support?\n" +
                    "Yes\n" +
                    "\n" +
                    "What is the support email address?\n"  );


        }

        else if (rsltNumber.startsWith("015")||rsltNumber.startsWith("88015")){
            helpTextView.setText("\nService Name: ClubZ\n" +
                            "Service Type: Entertainment\n" +
                            "\n" +
                            "What is ClubZ?\n" +
                            "ClubZ is a  entertainment service App.\n"+  "\n" +
                            "What is ClubZ Portal Address?\n"
            );
            String text1 = "<a href='http://wap.shabox.mobi/ClubZ'>http://wap.shabox.mobi/ClubZ</a>";
            portalAddress.setText(Html.fromHtml(text1));

            partThree.setText(
                    "\n" +
                            "\nWhy should I subscribe in ClubZ APP?\n" +
                            "From ClubZ app you can enjoy exclusive latest Song, Video Clips, Full Videos, amazing Wallpapers, most exciting Games, a vast pool of Bangla\n" +
                            "Stickers and many more entertainment base services which will full-fill your thirst of entertainment.\n" +
                            "\n" +
                            "How much I have to pay for all these service?\n" +
                            "\n" +
                            "Daily Charging Taka 2(+VAT+SD&SC\n" +
                            "Weekly 4-free contents\n" +
                            "\n" +
                            "Teletalk Users - Daily Subscription\n" +
                            "\n" +
                            "  •   To Activate: Type START CZ and send SMS to 6624\n" +
                            "  •   To Deactivate: Type STOP CZ and send SMS to 6624\n" +
                            "\n" +
                            "What is the helpline/hotline number for all Operators?\n" +
                            "8801534524714\n" +
                            "\n" +
                            "What is the time to get the helpline support?\n" +
                            "08:00 AM – 07:00 PM\n" +
                            "\n" +
                            "Is this site providing any email support?\n" +
                            "Yes\n" +
                            "\n" +
                            "What is the support email address?\n");



        }


        else if (rsltNumber.startsWith("018")||rsltNumber.startsWith("88018")){
            helpTextView.setText("\nService Name: ClubZ\n" +
                            "Service Type: Entertainment\n" +
                            "\n" +
                            "What is ClubZ?\n" +
                            "ClubZ is a  entertainment service App.\n"+  "\n" +
                            "What is ClubZ Portal Address?"
            );
            String text1 = "<a href='http://wap.shabox.mobi/ClubZ'>http://wap.shabox.mobi/ClubZ</a>";
            portalAddress.setText(Html.fromHtml(text1));

            partThree.setText("\n" +
                    "\nWhy should I subscribe in ClubZ app?\n" +
                    "From ClubZ app, you can enjoy exclusive latest Song, Video Clips, Full Videos, amazing Wallpapers, most exciting Games, a vast pool of Bangla\n" +
                    "Stickers and many more entertainment base services which will full-fill your thirst of entertainment.\n" +
                    "\n" +
                    "How much I have to pay for all these service?\n" +
                    "\n" +
                    "Daily Charging Taka 2(+VAT+SD&SC)\n" +

                    "Daily 3-free contents.\n" +
                    "\n" +
                    "Robi Users - Daily Subscription\n" +
                    "\n" +
                    "  •   To Activate: Type START CZD and send SMS to 6624\n" +
                    "  •   To Deactivate: Type STOP CZD and send SMS to 6624\n" +
                    "\n" +

                    "What is the helpline/hotline number for all Operators?\n" +
                    " 8801814426426\n" +
                    "\n" +
                    "What is the time to get the helpline support?\n" +
                    "08:00 AM – 07:00 PM\n" +
                    "\n" +
                    "Is this site providing any email support?\n" +
                    "Yes\n" +
                    "\n" +
                    "What is the support email address?\n" );


        }


        else if (rsltNumber.startsWith("019")||rsltNumber.startsWith("88019")){
            helpTextView.setText("\nService Name: ClubZ\n" +
                            "Service Type: Entertainment\n" +
                            "\n" +
                            "What is ClubZ?\n" +
                            "ClubZ is a  entertainment service App.\n"+  "\n" +
                            "What is ClubZ Portal Address?\n"
            );


            String text1 = "<a href='http://wap.shabox.mobi/ClubZ'>http://wap.shabox.mobi/ClubZ</a>";
            portalAddress.setText(Html.fromHtml(text1));


            partThree.setText("\nWhy should I subscribe in ClubZ APP?\n" +
                    "From ClubZ app you can enjoy exclusive latest Song, Video Clips, Full Videos, amazing Wallpapers, most exciting Games, a vast pool of Bangla\n" +
                    "Stickers and many more entertainment base services which will full-fill your thirst of entertainment.\n" +
                    "\n" +
                    "How much I have to pay for all these service?\n" +
                    "\n" +
                    "Daily Charging Taka 2(+VAT+SD&SC)\n" +
                    "Daily 5-free contents\n" +
                    "\n" +
                    "Banglalink Users - Daily Subscription\n" +
                    "\n" +
                    "  •   To Activate: Type START CZ and send SMS to 6624\n" +
                    "  •   To Deactivate: Type STOP CZ and send SMS to 6624\n" +
                    "\n" +
                    "What is the helpline/hotline number for all Operators?\n" +
                    "8801992303765\n" +
                    "\n" +
                    "What is the time to get the helpline support?\n" +
                    "08:00 AM – 07:00 PM\n" +
                    "\n" +
                    "Is this site providing any email support?\n" +
                    "Yes\n" +
                    "\n" +
                    "What is the support email address?\n");



        }

        else {
            helpTextView.setText("\nService Name: ClubZ\n" +
                    "Service Type: Entertainment\n" +
                    "\n" +
                    "What is ClubZ?\n" +
                    "ClubZ is a  entertainment service App.\n" +
                    "\n" +
                    "What is ClubZ Portal Address?");

            String text1 = "<a href='http://wap.shabox.mobi/ClubZ'>http://wap.shabox.mobi/ClubZ</a>";
            portalAddress.setText(Html.fromHtml(text1));

            partThree.setText("Why should I subscribe in ClubZ APP?\n" +
                    "From ClubZ app you can enjoy exclusive latest Song, Video Clips, Full Videos, amazing Wallpapers, most exciting Games, a vast pool of Bangla\n" +
                    "Stickers and many more entertainment base services which will full-fill your thirst of entertainment.\n" +
                    "\n" +
                    "  •  To Activate: Type START CZ and send SMS to 6624\n" +
                    "  •  To Deactivate: Type STOP CZ and send SMS to 6624\n" +
                    "\n" +
                    "What is the helpline/hotline number for all Operators?\n" +
                    "8801717053061\n" +
                    "\n" +
                    "What is the time to get the helpline support?\n" +
                    "08:00 AM – 07:00 PM\n" +
                    "\n" +
                    "Is this site providing any email support?\n" +
                    "Yes\n" +
                    "\n" +
                    "What is the support email address?");

        }


		

	}


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnHelpHelp :
                intent = new Intent(HelpActivity.this, FAQActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.btnHomeHelp :
                intent = new Intent(HelpActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.btnMyDownloadsHelp :
                intent = new Intent(HelpActivity.this,MyDownLoadsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.btnBuddyLinkHelp :
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.shaboxbuddy")));
                break;
            case R.id.btnBdTubeLinkHelp:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=bdtube.vumobile.com.bdtube")));
                break;
            case R.id.btnBanglaBeatsLinkHelp:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=app.vumobile.banglabeats.global&hl=en")));
                break;
            case R.id.btnAmarStickerLinkHelp:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=com.vumobile.amarsticker")));
                break;
            case R.id.btnRateMeLinkHelp:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("https://play.google.com/store/apps/details?id=rate.vumobile.com.rateme&hl=en")));
                break;

            case R.id.btnHelpSameHelp:
                intent = new Intent(HelpActivity.this,HelpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

        }
    }
}
