package radio.android.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SMSPortalFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static final String TAG = "SMSPortalFragment";

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0 ;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;

    Button sendButton;
    EditText fullName;
    EditText textMessage;
    String phoneNo = "2010";
    String name;
    String message;
    String sendMessage;

    private View mLayout;

    public SMSPortalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View smsportal = inflater.inflate(R.layout.fragment_smsportal, container, false);
        mLayout = getActivity().findViewById(R.id.fragment);
        fullName = (EditText) smsportal.findViewById(R.id.fullName);
        textMessage = (EditText) smsportal.findViewById(R.id.textMessage);
        sendButton = (Button) smsportal.findViewById(R.id.sendButon);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });

        return smsportal;
    }

    private void checkPermission() {
        // BEGIN_INCLUDE(sendSMSMessage)
        // Check if the SMS permission has been granted

        if (Build.VERSION.SDK_INT >= 24) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    // Permission is already available, send SMS message
                    Snackbar.make(mLayout, "SMS permission is available.", Snackbar.LENGTH_SHORT).show();
                    sendSMSMessage();
                } else {
                    // Permission is missing and must be requested.
                    requestSMSPermission();
                }
            } else {
                requestREADPermission();
            }
        } else{
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                // Permission is already available, send SMS message
                Snackbar.make(mLayout, "SMS permission is available.", Snackbar.LENGTH_SHORT).show();
                sendSMSMessage();
            } else {
                // Permission is missing and must be requested.
                requestSMSPermission();
            }
        }
        // END_INCLUDE(sendSMSMessage)
    }

    private void requestREADPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with cda button to request the missing permission.
            Snackbar.make(mLayout, "Read Phone State access is required for SMS to work properly", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                }
            }).show();
        } else {
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
    }

    private void requestSMSPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.SEND_SMS)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with cda button to request the missing permission.
            Snackbar.make(mLayout, "SMS access is required to send messages", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                }
            }).show();

        } else {
            Snackbar.make(mLayout, "SMS could not be sent.", Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }

    private void sendSMSMessage() {
        name = fullName.getText().toString();
        message = textMessage.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(message)){
            sendMessage = "RS " + name + " " + message;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, sendMessage, null, null);
            Snackbar.make(mLayout, "SMS has been sent.", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(mLayout, "Please enter your Name and Message.", Snackbar.LENGTH_SHORT).show();
        }
    }
}
