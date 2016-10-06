package retrofit.etos.it.sket.Activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit.etos.it.sket.ApiCLient;
import retrofit.etos.it.sket.Data.Db_config;
import retrofit.etos.it.sket.Data.Db_kapals;
import retrofit.etos.it.sket.Data.Db_timbang;
import retrofit.etos.it.sket.R;

public class Pengaturan extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    public static String name = null;
    public static String address = null;
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    private WifiManager wifiManager;
    ListView devicelist;
    public static String url ="http://arivin.xyz/TM/public/";
    public static String ipMonitor = "192.168.1.10";
    public static String monitorPort="5000";

    private EditText textUrl;
    private EditText textIpMonitor;
    private EditText textMonitorPort, btnPaired;
    TextView nama_bluetoth;

    Db_config db_config;
    Db_kapals db_kapals;
    Db_timbang db_timbang;

    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        textUrl = (EditText) findViewById(R.id.input_url);
        textIpMonitor = (EditText) findViewById(R.id.input_ip_monito);
        textMonitorPort = (EditText) findViewById(R.id.input_monitor_port);
        btnPaired = (EditText) findViewById(R.id.mac_bluetoth);
        nama_bluetoth = (TextView) findViewById(R.id.nama_bluetoth);

        if(ApiCLient.LOGIN != false)
        {
            Intent intent = new Intent(Pengaturan.this, Login.class);
            startActivity(intent);
        }


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        ImageButton buttonBack = (ImageButton) findViewById(R.id.action_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        db_config = new Db_config(this);
        myBluetooth();

        Button simpan = (Button) findViewById(R.id.simpan_config);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //simpan config ka db
                db_config.editConfig("bt_mac",btnPaired.getText().toString());
                db_config.editConfig("bt_nama",nama_bluetoth.getText().toString());
                db_config.editConfig("server_url",textUrl.getText().toString());
                db_config.editConfig("lcd_ip",textIpMonitor.getText().toString());
                db_config.editConfig("lcd_port",textMonitorPort.getText().toString());

                url =db_config.getConfig("server_ulr");
                ipMonitor = db_config.getConfig("lcd_ip");
                Log.e("ip monitor", db_config.getConfig("lcd_ip"));
                monitorPort="5000";

                PindahKaMain(address,name);
            }
        });

        db_kapals = new Db_kapals(this);
        db_timbang = new Db_timbang(this);
        Button Reset = (Button) findViewById(R.id.Reset);
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_kapals.Reset();
                db_timbang.Reset();
                Toast.makeText(getApplicationContext(),"Database berhasil di reset",Toast.LENGTH_SHORT).show();
            }
        });

        Onload();
    }

    private void Onload()
    {
        btnPaired.setText(db_config.getConfig("bt_mac"));
        nama_bluetoth.setText(db_config.getConfig("bt_nama"));
        textUrl.setText(db_config.getConfig("server_url"));
        textIpMonitor.setText(db_config.getConfig("lcd_ip"));
        textMonitorPort.setText(db_config.getConfig("lcd_port"));

    }

    private void myBluetooth()
    {

        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if(myBluetooth == null)
        {
            //Show a mensag. that the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();

            //finish apk
            finish();
        }
        else if(!myBluetooth.isEnabled())
        {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon,1);
        }

        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                pairedDevicesList();
            }
        });


    }

    private void pairedDevicesList()
    {
        pairedDevices = myBluetooth.getBondedDevices();
        List<String> list = new ArrayList<String>();

        if (pairedDevices.size()>0)
        {
            for(BluetoothDevice bt : pairedDevices)
            {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final CharSequence[] ListBt = list.toArray(new String[list.size()]);
        AlertDialog.Builder diaBuilder = new AlertDialog.Builder(this);
        diaBuilder.setTitle("Pilih Bluetooth");
        diaBuilder.setItems(ListBt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String selected = ListBt[i].toString();
                address = selected.substring(selected.length() - 17);
                name = selected.substring(0, selected.length() - 17);

                if(name != null)
                {
                    EditText mac_bluetoth = (EditText) findViewById(R.id.mac_bluetoth);
                    TextView nama_bluetoth = (TextView) findViewById(R.id.nama_bluetoth);
                    mac_bluetoth.setText(address);
                    nama_bluetoth.setText(name );
                }


            }
        });

        AlertDialog alertDialog =diaBuilder.create();
        alertDialog.show();

//        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
//        devicelist.setAdapter(adapter);
//        devicelist.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked

    }

    private void PindahKaMain(String address,String nama)
    {
        url = textUrl.getText().toString();
        ipMonitor = textIpMonitor.getText().toString();
        monitorPort = textMonitorPort.getText().toString();

        Intent intent = new Intent(Pengaturan.this, MainActivity.class);
        intent.putExtra("alamat", address); //this will be received at ledControl (class) Activity
        intent.putExtra("nama", name); //this will be received at ledControl (class) Activity
        startActivity(intent);
    }




    public static String getWifiName(Context context) {
        String ssid = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            return null;
        }

        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null) {
                ssid = connectionInfo.getSSID();
            }
        }

        return ssid;
    }

    //================== map ====================
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;

    LatLng latLng;
    GoogleMap mGoogleMap;
    Marker currLocationMarker;
    private GoogleMap mMap;

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currLocationMarker = mGoogleMap.addMarker(markerOptions);
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "onConnectionSuspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        if (currLocationMarker != null) {
            currLocationMarker.remove();
        }
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        currLocationMarker = mGoogleMap.addMarker(markerOptions);

        //Toast.makeText(this, "Location Changed", Toast.LENGTH_SHORT).show();

        //zoom to current position:
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(14).build();

        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "onConnectionFailed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("sadasdas","ASdasd");
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);

        buildGoogleApiClient();
        Log.e("sadasdas","2");

        mGoogleApiClient.connect();
    }

    protected synchronized void buildGoogleApiClient() {
        Toast.makeText(this,"buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Log.e("sadasdas","build");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
