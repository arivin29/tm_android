package retrofit.etos.it.sket.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import retrofit.etos.it.sket.R;

/**
 * Created by IT on 07/09/2016.
 */
public class ConnectBtAdapter extends AsyncTask<Void, Void, Void>
{
    private boolean ConnectStatus = true;
    private ProgressDialog progres;
    Context context;
    BluetoothSocket btSocket=null;
    BluetoothAdapter myBluetoothAdapter =null;
    private boolean isBtConnected = false;
    String address;
    String nama;
    TextView pilhanBt;
    int allow=0;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public ConnectBtAdapter(Context context, String address, String nama, TextView pilhanBt, int allow)
    {
        this.context =context;
        this.address = address;
        this.nama = nama;
        this.pilhanBt =pilhanBt;
        this.allow =allow;
    }

    @Override
    protected  void onPreExecute()
    {
        progres = ProgressDialog.show(context,"Connecting....", "Mohon tunggu!!" + address);

    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.e("Btsoket","ada");
        try {
            if(btSocket==null || !isBtConnected && allow > 0)
            {
                myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                BluetoothDevice dispositivo = myBluetoothAdapter.getRemoteDevice(address);//connects to the device's address and checks if it's available
                btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                Log.e("Mac", address);

                btSocket.connect();//start connection

            }
            else
            {
                Log.e("Btsoket","ada");
            }
        }catch (IOException e)
        {
            //ConnectStatus = false;//if the try failed, you can check the exception here
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
    {
        super.onPostExecute(result);

        if (!ConnectStatus)
        {
            Toast.makeText(context,"Connection Failed. Is it a SPP Bluetooth? Try again.",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(context,"Connected.",Toast.LENGTH_LONG).show();

            pilhanBt.setText("" + nama + "/n Connecting..");
            pilhanBt.setText("" + nama + "/n " + address);
            if(allow > 0)
            {
                isBtConnected = true;
            }
            else
            {
                isBtConnected = false;
            }

        }
        progres.dismiss();
    }

    public BluetoothSocket Koenksi()
    {
        return btSocket;
    }


}
