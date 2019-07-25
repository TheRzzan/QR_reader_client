package com.morozov.qr_reader.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.morozov.qr_reader.R;
import com.morozov.qr_reader.activities.MainActivity;
import com.morozov.qr_reader.interfaces.DataLoader;
import com.morozov.qr_reader.interfaces.OnDataLoadedListener;
import com.morozov.qr_reader.utility.HttpAsyncRequest;
import com.morozov.qr_reader.utility.MySharedPreferences;
import com.morozov.qr_reader.utility.Ticket;

public class UserInfoFragment extends Fragment implements OnDataLoadedListener{
    private static final String LOG_TAG = UserInfoFragment.class.getSimpleName();

    private static final String ARG_POSITION = "position";
    private int position;

    private static final int REQUEST_CODE_QR_SCAN = 101;
    private Ticket ticket;

    public static UserInfoFragment newInstance(int position) {
        UserInfoFragment f = new UserInfoFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, null);

        FloatingActionButton fabScan = view.findViewById(R.id.fab_scann);
        fabScan.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return;
            }
            Intent i = new Intent(getContext(), QrCodeActivity.class);
            startActivityForResult( i,REQUEST_CODE_QR_SCAN);
        });

        displayTicket(view);

        return view;
    }

    private void displayTicket(View view) {
        EditText editText_result = view.findViewById(R.id.editText_result);
        String result = "";

        if (ticket != null) {
            result += getString(R.string.pref_name_title) + ": " + ticket.getName() + '\n';
            if (MySharedPreferences.getPreference(getContext(), getString(R.string.pref_age_key)))
                result += getString(R.string.pref_age_title) + ": " + ticket.getAge() + '\n';
            if (MySharedPreferences.getPreference(getContext(), getString(R.string.pref_id_key)))
                result += getString(R.string.pref_id_title) + ": " + ticket.getId() + '\n';
            if (MySharedPreferences.getPreference(getContext(), getString(R.string.pref_code_ticket_key)))
                result += getString(R.string.pref_code_ticket_title) + ": " + ticket.getCodeTicket() + '\n';
            if (MySharedPreferences.getPreference(getContext(), getString(R.string.pref_date_key)))
                result += getString(R.string.pref_date_title) + ": " + ticket.getDate() + '\n';
            if (MySharedPreferences.getPreference(getContext(), getString(R.string.pref_promo_key)))
                result += getString(R.string.pref_promo_title) + ": " + ticket.getPromo() + '\n';
            if (MySharedPreferences.getPreference(getContext(), getString(R.string.pref_uni_key)))
                result += getString(R.string.pref_uni_title) + ": " + ticket.getUni() + '\n';
            if (MySharedPreferences.getPreference(getContext(), getString(R.string.pref_event_key)))
                result += getString(R.string.pref_event_title) + ": " + ticket.getEvent() + '\n';
            if (MySharedPreferences.getPreference(getContext(), getString(R.string.pref_date_event_key)))
                result += getString(R.string.pref_date_event_title) + ": " + ticket.getDateEvent() + '\n';
            if (MySharedPreferences.getPreference(getContext(), getString(R.string.pref_mob_phone_key)))
                result += getString(R.string.pref_mob_phone_title) + ": " + ticket.getMobPhone() + '\n';
        } else
            result += getString(R.string.no_info);

        editText_result.setText(result);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            Log.d(LOG_TAG,"COULD NOT GET A GOOD RESULT.");
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
            }
            return;
        }

        if(requestCode == REQUEST_CODE_QR_SCAN){
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            DataLoader dataLoader = new HttpAsyncRequest();
            dataLoader.loadData(result, UserInfoFragment.this);
        }
    }

    @Override
    public void onDataLoaded(Ticket ticket) {
        this.ticket = ticket;
        Toast.makeText(getContext(), "Data loaded", Toast.LENGTH_SHORT).show();
        displayTicket(getView());
    }
}
