package com.vumobile.clubzed.Soap;

import android.util.Log;

import com.vumobile.clubzed.util.Download_Class;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

//import org.ksoap2.serialization.PropertyInfo;

public class CallSoap {


    // Operation name with name space
    public final String SOAP_ACTION = "http://tempuri.org/MSISDN";
    public final String SOAP_ACTION_FOR_USER = "http://tempuri.org/StatusChk";
    public final String SOAP_ACTION_FOR_NEW_USER_SMS = "http://tempuri.org/newUserSMS";
    public final String SOAP_ACTION_FOR_SUBSCRIPTION = "http://tempuri.org/Subscription";
    public final String SOAP_ACTION_FOR_ROBI_DEACTIVATE = "http://tempuri.org/RobiDeactivate";

    // Operation name declare
    public final String OPERATION_NAME = "MSISDN";
    public final String OPERATION_NAME_FOR_USER = "StatusChk";
    public final String OPERATION_NAME_FOR_NEW_USER_SMS = "newUserSMS";
    public final String OPERATION_NAME_FOR_SUBSCRIPTION = "Subscription";
    public final String OPERATION_NAME_FOR_ROBI_DEACTIVATE = "RobiDeactivate";

    // Name space
    public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    public final String WSDL_TARGET_NAMESPACE_FOR_USER = "http://tempuri.org/";
    public final String WSDL_TARGET_NAMESPACE_FOR_NEW_USER_SMS = "http://tempuri.org/";
    public final String WSDL_TARGET_NAMESPACE_FOR_SUBSCRIPTION = "http://tempuri.org/";
    public final String WSDL_TARGET_NAMESPACE_FOR_ROBI_DEACTIVATE = "http://tempuri.org/";

    // Soap Address
    public final String SOAP_ADDRESS = "http://wap.shabox.mobi/czapp/Service1.asmx";
    public final String SOAP_ADDRESS_FOR_USER = "http://wap.shabox.mobi/CZStatus/";
    public final String SOAP_ADDRESS_FOR_NEW_USER_SMS = "http://wap.shabox.mobi/CZStatus/";
    public final String SOAP_ADDRESS_FOR_SUBSCRIPTION = "http://wap.shabox.mobi/CZStatus/";
    public final String SOAP_ADDRESS_FOR_ROBI_DEACTIVATE = "http://wap.shabox.mobi/CZStatus/";

    public CallSoap() {

    }

    // MSISDN Detection web service
    public String Call() {

        Log.d("Tracker", "This is Callsoap");
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,
                OPERATION_NAME);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        Object response = null;

        try {
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
            sendMSDNtoSOAP();
        } catch (Exception exception) {
            // response=exception.toString();
            response = "ERROR";
        }

        return response.toString();
    }

    // User Status Check Web Service
    public String Call(String a) {

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE_FOR_USER,
                OPERATION_NAME_FOR_USER);
        request.addProperty("msisdn", a);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(
                SOAP_ADDRESS_FOR_USER);
        Object response = null;

        try {
            httpTransport.call(SOAP_ACTION_FOR_USER, envelope);
            response = envelope.getResponse();
            sendMSDNtoSOAP();
        } catch (Exception exception) {
            // response=exception.toString();
            response = "ERROR";
        }

        return response.toString();
    }


    public String Call_ROBI(String msisdn, String chargeType, String query) {
        Object response = null;

        if (query.equals("subscription")) {
            SoapObject request = new SoapObject(
                    WSDL_TARGET_NAMESPACE_FOR_SUBSCRIPTION,
                    OPERATION_NAME_FOR_SUBSCRIPTION);
            request.addProperty("msisdn", msisdn);
            request.addProperty("charginType", chargeType);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(
                    SOAP_ADDRESS_FOR_SUBSCRIPTION);

            try {
                httpTransport.call(SOAP_ACTION_FOR_SUBSCRIPTION, envelope);
                response = envelope.getResponse();
            } catch (Exception exception) {
                // response=exception.toString();
                response = "ERROR";
            }
        } else if (query.equals("robideactivate")) {
            SoapObject request = new SoapObject(
                    WSDL_TARGET_NAMESPACE_FOR_ROBI_DEACTIVATE,
                    OPERATION_NAME_FOR_ROBI_DEACTIVATE);
            request.addProperty("msisdn", msisdn);
            request.addProperty("charginType", chargeType);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(
                    SOAP_ADDRESS_FOR_ROBI_DEACTIVATE);

            try {
                httpTransport.call(SOAP_ACTION_FOR_ROBI_DEACTIVATE, envelope);
                response = envelope.getResponse();
            } catch (Exception exception) {
                // response=exception.toString();
                response = "ERROR";
            }
        }

        return response.toString();

    }


    // Web services forgot pin, get robi limit, pincode check etc.
    public String Call_WS(String msisdn, String q, String pin) {
        Object response = null;
        //Faisal
        if (q.equals("new_user_sms")) {
            SoapObject request = new SoapObject(
                    WSDL_TARGET_NAMESPACE_FOR_NEW_USER_SMS,
                    OPERATION_NAME_FOR_NEW_USER_SMS);
            request.addProperty("msisdn", msisdn);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(
                    SOAP_ADDRESS_FOR_NEW_USER_SMS);

            try {
                httpTransport.call(SOAP_ACTION_FOR_NEW_USER_SMS, envelope);
                response = envelope.getResponse();
            } catch (Exception exception) {
                // response=exception.toString();
                response = "ERROR";
            }
        } else if (q.equals("forget_pin_code")) {

            SoapObject request = new SoapObject(
                    WSDL_TARGET_NAMESPACE_FOR_NEW_USER_SMS, "ForgetPin");
            request.addProperty("msisdn", msisdn);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(
                    SOAP_ADDRESS_FOR_USER);
            Log.i("FORGET_PIN", "" + msisdn);

            try {
                httpTransport.call("http://tempuri.org/ForgetPin", envelope);
                response = envelope.getResponse();
                Log.i("FORGET_PIN_RESPONSE ", "" + response);
            } catch (Exception exception) {
                // response=exception.toString();
                response = "ERROR PINCODE FORGET PIN";
            }
        } else if (q.equals("pincode_check")) {

            SoapObject request = new SoapObject(
                    WSDL_TARGET_NAMESPACE_FOR_NEW_USER_SMS, "chkPin");
            request.addProperty("msisdn", msisdn);
            request.addProperty("pincode", pin);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(
                    SOAP_ADDRESS_FOR_USER);
            Log.i("PIN_CODE_CHECK", "" + msisdn);

            try {
                httpTransport.call("http://tempuri.org/chkPin", envelope);
                response = envelope.getResponse();
            } catch (Exception exception) {
                response = "ERROR PINCODE  CHECKING";
            }
        } else if (q.equals("get_robi_message")) {

            SoapObject request = new SoapObject(
                    WSDL_TARGET_NAMESPACE_FOR_NEW_USER_SMS,
                    "ChkDwonloadCountforROBI");
            request.addProperty("msisdn", msisdn);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(
                    SOAP_ADDRESS_FOR_USER);
            Log.i("GET_ROBI_APP", "" + msisdn);

            try {
                httpTransport.call(
                        "http://tempuri.org/ChkDwonloadCountforROBI", envelope);
                response = envelope.getResponse();
            } catch (Exception exception) {
                // response=exception.toString();
                response = "ERROR PINCODE  CHECKING";
            }

        }

        return response.toString();

    }

    //================ Set the subcription source======================
    public static void sendMSDNtoSOAP() {
        Object response = null;
        String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

        String SOAP_ADDRESS = "http://wap.shabox.mobi/sourcecz/service1.asmx";//"http://wap.shabox.mobi/SBAppService/SBService.asmx";
        String SOAP_ACTION = "http://tempuri.org/GetClubZSource";//"http://tempuri.org/SubscriberStatus";
        Log.d("Tracker", "CallSoap GET_SUBSCRIBER_STATUS");
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, "GetClubZSource");//"SubscriberStatus");
        request.addProperty("MSISDN", Download_Class.rsltNumber);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);


        try {
            Log.i("SOAP SUBSCRIBER RESULT", "" + response);
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();

            //http://tempuri.org/SubscriberStatus
            //response = envelope.getResponse();
            Log.i("SOAP SUBSCRIBER RESULT", "" + response);
            Log.d("Tracker", "Call Soap Subscripber result");
        } catch (IOException e) {
            e.printStackTrace();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }
}
