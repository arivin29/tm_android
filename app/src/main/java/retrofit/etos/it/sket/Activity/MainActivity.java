package retrofit.etos.it.sket.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import retrofit.etos.it.sket.Adapter.IkanAdaptor;
import retrofit.etos.it.sket.Api.IkanInterface;
import retrofit.etos.it.sket.Api.KapalInterface;
import retrofit.etos.it.sket.ApiCLient;
import retrofit.etos.it.sket.Data.Db_kapals;
import retrofit.etos.it.sket.Data.Db_timbang;
import retrofit.etos.it.sket.Help.SocketKirim;
import retrofit.etos.it.sket.Model.Ikan;
import retrofit.etos.it.sket.Model.IkanRespon;
import retrofit.etos.it.sket.Model.Kapal;
import retrofit.etos.it.sket.Model.Timbang;
import retrofit.etos.it.sket.R;
import retrofit.etos.it.sket.Service.BluetoothSPP;
import retrofit.etos.it.sket.Service.BluetoothState;
import retrofit.etos.it.sket.Service.DeviceList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Kapal kapal;
    Timbang timbang;

    private Toolbar toolbar;
    public  static ListView listView, listView_timbang;


    public String address =null;
    public String nama =null;
    BluetoothSPP bt;
    TextView textStatus, textRead;
    EditText etMessage,text_barcode,pilihanIkan;
    Button btn_simpan;
    //barcode
    ImageView id_barcode;
    TextView nama_kapal, alat_tangkap, no_izin;
    String hasil_baca_barcode;

    //form
    EditText faktor_a, faktor_b, input_harga, tujuan_ikan;
    Double ValueTimbang =new Double(0);
    int StatusBt =0;

    SocketKirim socketKirim;

    //database
    Db_kapals db_kapal;
    Db_timbang db_timbang;
    String keyUnik;
    public  static int id_ikan=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xSocket.connect();
//        mSocket.connect();

        toolbar  = (Toolbar)findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);


        //ambil setingan
        Intent newIntent = getIntent();
        address = newIntent.getStringExtra("alamat");
        nama = newIntent.getStringExtra("nama");

        BacaBtTm(address,nama);
        kapal = new Kapal();
        db_kapal = new Db_kapals(this);
        db_timbang = new Db_timbang(this);
        BacaBarcode();


        //list ikan
        listView = (ListView) findViewById(R.id.list_ikan);
        listView_timbang = (ListView) findViewById(R.id.list_hasil_timbang);
        getDataIkan();

        // getDataTimbang();


        confirmSimpanDataTimbang();

    }

    private Socket  xSocket;
    {
        try {
            xSocket = IO.socket("http://192.168.1.16:5000");

        } catch (URISyntaxException e) {}
    }

    private void SimpanTimbang()
    {

        faktor_a = (EditText) findViewById(R.id.input_faktor_a);
        faktor_b = (EditText) findViewById(R.id.input_faktor_b);
        input_harga = (EditText) findViewById(R.id.input_harga);
        pilihanIkan = (EditText) findViewById(R.id.pilihIkan);
        tujuan_ikan = (EditText) findViewById(R.id.input_tujuan);

        String s_faktor_a = faktor_a.getText().toString();
        String s_faktor_b = faktor_b.getText().toString();
        String s_input_harga = input_harga.getText().toString();
        String s_pilihanIkan = pilihanIkan.getText().toString();
        String s_tujuan_ikan = tujuan_ikan.getText().toString();

        if(s_pilihanIkan.length() > 0 && s_faktor_a.length() > 0 && s_faktor_b != null && s_input_harga !=null && id_ikan > 0)
        {
            if(kapal.getIdKapal() > 0)
            {
                StatusBt=1;
                if(StatusBt > 0 )
                {
                    Kapal kapal_db = db_kapal.getKapal(keyUnik);

                    timbang = new Timbang();
                    Log.e("sss",s_faktor_b);
                    timbang.setBerat(ValueTimbang);
                    timbang.setFaktor_a(s_faktor_a);
                    timbang.setFaktor_b(s_faktor_b);
                    timbang.setHarga(Integer.parseInt(s_input_harga));
                    timbang.setId_ikan(id_ikan);
                    timbang.setNama_ikan(s_pilihanIkan);
                    timbang.setId_kapal(kapal_db.getIdKapal());
                    timbang.setId_user(1);
                    timbang.setSatuan("kg");
                    timbang.setUpi(kapal_db.getNoIzin());
                    timbang.setKeyUnik(keyUnik);

                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    String date = df.format(Calendar.getInstance().getTime());
                    timbang.setTanggal_timbang(date);
                    timbang.setKode_timbang(nama);
                    timbang.setStatus_timbang(0);

                    //kirim ka lcd
                    socketKirim = new SocketKirim();
                    socketKirim.kirimHasilTimbang(getApplicationContext(),mSocket,db_timbang,timbang,listView_timbang);

                    //kosongan form
                    faktor_a.setText("");
                    faktor_b.setText("");
                    input_harga.setText("");
                    pilihanIkan.setText("");
                    tujuan_ikan.setText("");
                    id_ikan=0;

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Timbangan tidak konek",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Data kapal Kosong, baca barcode",Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Data detail timbang Kosong!!",Toast.LENGTH_SHORT).show();
        }

    }

    private void confirmSimpanDataTimbang(){
        btn_simpan = (Button) findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialogDataTimbang();
            }
        });
    }

    private  void confirmDialogDataTimbang()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apa Kamu Yakin Untuk Simpan Data Timbang?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        SimpanTimbang();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void getDataKapal()
    {
        class getData extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Proses Data...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Log.e("data","berhasil di ambil" + s);
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    KapalInterface kapalInterface = ApiCLient.getClient().create(KapalInterface.class);
                    Call<Kapal> call = kapalInterface.getData(hasil_baca_barcode);
                    call.enqueue(new Callback<Kapal>() {
                        @Override
                        public void onResponse(Call<Kapal> call, Response<Kapal> response) {
                            kapal = response.body();
                            if(kapal.getIdKapal() > 0)
                            {
                                keyUnik = UUID.randomUUID().toString();
                                kapal.setKeyUnik(keyUnik);

                                nama_kapal = (TextView) findViewById(R.id.nama_kapal);
                                no_izin = (TextView)findViewById(R.id.no_izin_kapal);
                                alat_tangkap = (TextView)findViewById(R.id.alat_tangkap);

                                //tuka text
                                nama_kapal.setText(kapal.getNamaKapal().toUpperCase());
                                no_izin.setText(kapal.getNoIzin().toUpperCase());
                                alat_tangkap.setText(kapal.getAlatTangkap().toUpperCase());

                                //kirim ka soket
                                SocketKirim socketKirim = new SocketKirim();
                                socketKirim.kirimHasilKapal(getApplicationContext(),mSocket,db_kapal,kapal);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Data kapal Tidak ditemukan",Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<Kapal> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Data Tidak ditemukan",Toast.LENGTH_SHORT).show();
                            Log.e("eroor load data", t.getMessage());
                        }
                    });


                }
                catch (Exception e)
                {

                }
                return null;
            }
        }
        getData ge = new getData();
        ge.execute();
    }

    private void confirmAmbilDataKapal(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apa Kamu Yakin Untuk Menganti Data ini?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        getDataKapal();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



    /**--------------------------------------------------*/
    //DATA IKAN
    /**--------------------------------------------------*/
    private void getDataIkan()
    {
        Log.d("mulai", "Cari data");
        pilihanIkan = (EditText)findViewById(R.id.pilihIkan);


        IkanInterface ikanInterface = ApiCLient.getClient().create(IkanInterface.class);
        Call<IkanRespon> call = ikanInterface.getData();
        call.enqueue(new Callback<IkanRespon>() {
            @Override
            public void onResponse(Call<IkanRespon> call, Response<IkanRespon> response) {
                Log.d("mulai", "dapat data");
                List<Ikan> ikan = response.body().getListIkan();
                final IkanAdaptor ikanAdaptor = new IkanAdaptor(getApplicationContext(),ikan,pilihanIkan);
                listView.setAdapter(ikanAdaptor);


                EditText cariIkan = (EditText) findViewById(R.id.cari_ikan);
                cariIkan.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        Log.e("pesan", charSequence + "sds");
                        ikanAdaptor.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }

            @Override
            public void onFailure(Call<IkanRespon> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"APlikasi gagal load Data, cek koneksi internet",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.pengaturan)
        {
            Intent i = new Intent(MainActivity.this, Pengaturan.class);
            startActivity(i);
        }

        if(id == R.id.action_user)
        {
            try {
                mSocket.emit("send", db_kapal.toJSONallKapal());
                mSocket.emit("send", db_timbang.toJSONallTimbang());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(id == R.id.trash_db)
        {
            db_kapal.clearTable();
            db_timbang.clearTable();
            Toast.makeText(getApplicationContext(),"database berhasil di kosongkan",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void BacaBtTm(String address, final String name) {
        bt = new BluetoothSPP(this);

        textRead = (TextView)findViewById(R.id.hasil_timbang);
        textStatus = (TextView)findViewById(R.id.statusBt);

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                textRead.setText(message + "");

                JSONObject json = new JSONObject();
                try {
                    json.put("bt", message);
                    json.put("nama_timbangan", name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                StatusBt=1;
                Double Aa= Double.parseDouble(message.toString());
                System.out.println("aaaa" + Aa);
                ValueTimbang = Aa;
                mSocket.emit("send",json);
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceDisconnected() {
                textStatus.setText("BT Not connect");
                StatusBt=0;
                Toast.makeText(getApplicationContext(),"Timbangan Tidak Konek",Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() {
                textStatus.setText("BT Connection failed");
                StatusBt=0;
                Toast.makeText(getApplicationContext(),"Timbangan Tidak Konek, periksa Bluetooth",Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnected(String name, String address) {
                textStatus.setText("Connected " + name);
                StatusBt=1;
//                menu.clear();
//                getMenuInflater().inflate(R.menu.menu_disconnection, menu);
            }
        });

        bt.setAutoConnectionListener(new BluetoothSPP.AutoConnectionListener() {
            public void onNewConnection(String name, String address) {
                Log.e("Check", "New Connection - " + name + " - " + address);
            }

            public void onAutoConnectionStarted() {
                Log.e("Check", "Auto menu_connection started");
            }
        });

        if(address !=null)
        {
            Log.e("status",address);
            bt.disconnect();
            bt.setupService();
            bt.startService(BluetoothState.DEVICE_ANDROID);
            bt.connect(address);
        }
        textStatus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                    Log.i("MainActivity","Gagal Connnect");
                } else {
                    bt.setDeviceTarget(BluetoothState.DEVICE_OTHER);

                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }

            }
        });

    }

    public void onDestroy() {
        super.onDestroy();
        bt.stopService();
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if(!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
//                DashboardActivity.setup();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
//                DashboardActivity.setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else
        {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                //String scanFormat = scanningResult.getFormatName();
                text_barcode.setText(scanContent);
                hasil_baca_barcode = scanContent;
                confirmAmbilDataKapal();

            }else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }


    /*----------------------------------------------*/
    //    Barcode
    /*----------------------------------------------*/

    private void BacaBarcode()
    {
        text_barcode = (EditText) findViewById(R.id.input_id_barcode);
        id_barcode = (ImageView) findViewById(R.id.icon_barcode);
        id_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.initiateScan();
            }
        });
    }

    /*-----------------------------------------------------------*/
    // SOKET BT
    /*-----------------------------------------------------------*/

    private com.github.nkzawa.socketio.client.Socket mSocket;
    {
        try {
            Log.e("lcd server", Pengaturan.ipMonitor);
            mSocket = IO.socket("http://192.168.1.16:5000");
//            mSocket = IO.socket("http://"+ Pengaturan.ipMonitor.toString() +":"+ Pengaturan.monitorPort);
            Log.e("CONNECTED", "SUCCESS");
        } catch (URISyntaxException e) {
            Log.e("CONNECTED", "SUCCESS" + e.getMessage());
            throw new RuntimeException(e);
        }
    }



}
