package fr.umontpellier.localisermonenfant.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.umontpellier.localisermonenfant.R;
import fr.umontpellier.localisermonenfant.models.Child;
import fr.umontpellier.localisermonenfant.models.Trajet;
import fr.umontpellier.localisermonenfant.networks.businesses.BussnessServiceTrajet;
import fr.umontpellier.localisermonenfant.services.LocationUpdateService;
import fr.umontpellier.localisermonenfant.utils.CheckIfServiceIsRunning;

public class MainActivityChild extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS = 100;
    boolean boolean_permission;
    TextView tv_latitude, tv_longitude, tv_address, tv_area, tv_locality;
    Double latitude, longitude;
    Geocoder geocoder;
    Intent intent;
    Child child;
    String TAG = "MainActivityChild";
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_child);
        context = this;
        intent = getIntent();
        if (intent != null) {
            child = (Child) intent.getSerializableExtra("Child");
        }
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_latitude = (TextView) findViewById(R.id.tv_latitude);
        tv_longitude = (TextView) findViewById(R.id.tv_longitude);
        tv_area = (TextView) findViewById(R.id.tv_area);
        tv_locality = (TextView) findViewById(R.id.tv_locality);
        geocoder = new Geocoder(this, Locale.getDefault());
        fn_permission();
    }

    private void fn_permission() {
        ActivityCompat.requestPermissions(MainActivityChild.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS);

        if ((ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivityChild.this, android.Manifest.permission.ACCESS_FINE_LOCATION))) {
            } else {
                ActivityCompat.requestPermissions(MainActivityChild.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION
                        }, REQUEST_PERMISSIONS);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Merci d'activer la GPS", Toast.LENGTH_SHORT).show();
        }
        if (!new CheckIfServiceIsRunning(getApplicationContext()).isMyServiceRunning(LocationUpdateService.class)) {
            Intent intent = new Intent(getApplicationContext(), LocationUpdateService.class);
            startService(intent);
            Toast.makeText(getApplicationContext(), "service est en cours", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    boolean_permission = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Merci d'authoriser la géolocalisation", Toast.LENGTH_LONG).show();
                    fn_permission();
                }
            }
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            latitude = Double.valueOf(intent.getStringExtra("latutide"));
            longitude = Double.valueOf(intent.getStringExtra("longitude"));

            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null) {
                    String cityName = addresses.get(0).getAddressLine(0);
                    String stateName = addresses.get(0).getAddressLine(1);
                    String countryName = addresses.get(0).getAddressLine(2);
                    String postalCode = addresses.get(0).getPostalCode();
                    String locality = postalCode + " " + addresses.get(0).getLocality();

                    tv_area.setText(addresses.get(0).getAdminArea());
                    tv_locality.setText(stateName);
                    tv_address.setText(cityName);
                    tv_locality.setText(locality);

                    Trajet trajet = new Trajet
                            (latitude, longitude, countryName + cityName, locality, cityName, countryName,
                                    postalCode, 0, new Date(), child.get_id(), child.getFirstname(), child.getLastname(), child.getParent());
                    Log.i(TAG, "onReceive: " + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault()).format(trajet.getDate()));
                    BussnessServiceTrajet bussnessServiceTrajet = new BussnessServiceTrajet(context);
                    bussnessServiceTrajet.initializeAuthentificatedCommunication();
                   bussnessServiceTrajet.save(trajet);
                } else {
                    Toast.makeText(getApplicationContext(), "Aucune adresse trouvée", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }


            tv_latitude.setText(latitude + "");
            tv_longitude.setText(longitude + "");
            Log.d("localisation", "" + latitude);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(LocationUpdateService.str_receiver));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}