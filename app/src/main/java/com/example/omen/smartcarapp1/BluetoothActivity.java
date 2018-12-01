package com.example.omen.smartcarapp1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBack;
    Switch switchBT;
    Button btnStatus, btnDiagnostics, btnSearch, btnLedOff;
    static Button btnStatus2, btnReceive;


    TextView navHeaderName, navHeaderCar;
    DrawerLayout mDrawerLayout;
    SharedPreferences sPref;
    SharedPreferences.Editor editor;


    //private static TextView mBluetoothStatus;
    //private static TextView mReadBuffer;
    //private Button mListPairedDevicesBtn;
    //private Button mDiscoverBtn;
    private BluetoothAdapter mBTAdapter;
    private Set<BluetoothDevice> mPairedDevices;
    private ArrayAdapter<String> mBTArrayAdapter;
    private ListView mDevicesListView;
    //private CheckBox mLED1;
    //private static TextView txtArduino;
    private static StringBuilder sb = new StringBuilder();

    private static Handler mHandler; // Our main handler that will receive callback notifications
    private ConnectedThread mConnectedThread; // bluetooth background worker thread to send and receive data
    private BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier


    // #defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    private final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);


        //mBluetoothStatus = (TextView)findViewById(R.id.bluetoothStatus);
        //mReadBuffer = (TextView)findViewById(R.id.readBuffer);
        //mScanBtn = (Button)findViewById(R.id.scan);
        //mOffBtn = (Button)findViewById(R.id.off);
        //mDiscoverBtn = (Button)findViewById(R.id.discover);
        //mListPairedDevicesBtn = (Button)findViewById(R.id.PairedBtn);
        //mLED1 = (CheckBox)findViewById(R.id.checkboxLED1);
        //txtArduino = (TextView)findViewById(R.id.txtArduino);
        switchBT = (Switch) findViewById(R.id.switchBT);
        btnStatus = (Button) findViewById(R.id.btnStatus);
        btnReceive = (Button) findViewById(R.id.btnReceive);
        btnDiagnostics = (Button) findViewById(R.id.btnDiagnostics);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnStatus2 = (Button) findViewById(R.id.btnStatus2);
        btnLedOff = (Button)findViewById(R.id.btnLedOff);

        switchBT.setOnClickListener(this);
        btnDiagnostics.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnLedOff.setOnClickListener(this);

        mBTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();

        mDevicesListView = (ListView) findViewById(R.id.devicesListView);
        if (mDevicesListView == null) {
            Log.d("myLogs", "1");
        }
        if (mBTArrayAdapter == null) {
            Log.d("myLogs", "2");
        }
        mDevicesListView.setAdapter(mBTArrayAdapter);
        mDevicesListView.setOnItemClickListener(mDeviceClickListener);

        mHandler = new MyVeryOwnHandler();

        if (mBTArrayAdapter == null) {
            //Device does not support Bluetooth
            btnStatus.setText("Status: Bluetooth not found");
            Toast.makeText(getApplicationContext(), "Bluetooth device not found", Toast.LENGTH_SHORT).show();

        } else {
            btnDiagnostics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mConnectedThread != null) {
                        String on = "11";
                        mConnectedThread.write(on);
                    }
                }
            });
            btnLedOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mConnectedThread != null) {
                        String off = "00";
                        mConnectedThread.write(off);
                    }
                }
            });
        }
        switchBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothOnOff(v);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discover(v);
                listPairedDevices(v);
            }
        });


        btnBack = (ImageButton) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(this);


        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navHeaderName = (TextView) headerView.findViewById(R.id.navHeaderName);
        navHeaderCar = (TextView) headerView.findViewById(R.id.navHeaderCar);
        navigationView.setItemIconTintList(null);

        sPref = getSharedPreferences("my_account", MODE_PRIVATE);
        editor = sPref.edit();
        navHeaderName.setText(sPref.getString("name", "error"));
        navHeaderCar.setText(sPref.getString("carModel", "error"));


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_auto_health:

                                break;
                        }

                        return true;
                    }
                }
        );

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Intent intent;
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.nav_main_page:
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_bluetooth:
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.nav_auto_health:
                                intent = new Intent(getApplicationContext(), AutoHealthActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_remote_control:
                                intent = new Intent(getApplicationContext(), RemoteControlActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_car_location:
                                intent = new Intent(getApplicationContext(), CarLocationActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_safety_score:
                                intent = new Intent(getApplicationContext(), SafetyScoreActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_driving_history:
                                intent = new Intent(getApplicationContext(), DrivingHistoryActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_alerts:
                                intent = new Intent(getApplicationContext(), AlertsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_my_account:
                                intent = new Intent(getApplicationContext(), MyAccountActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_help_support:
                                intent = new Intent(getApplicationContext(), HelpAndSupportActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_about:
                                intent = new Intent(getApplicationContext(), AboutActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_signout:

                                break;
                            case R.id.nav_dev:
                                intent = new Intent(getApplicationContext(), DeveloperActivity.class);
                                startActivity(intent);
                                break;
                        }


                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                }
        );


    }//end of onCreate()

    private void bluetoothOnOff(View v) {
        if (switchBT.isChecked()) {
            if (!mBTAdapter.isEnabled()) {
                Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
                btnStatus.setText("Bluetooth enabled");
                Toast.makeText(getApplicationContext(), "Bluetooth turned on", Toast.LENGTH_SHORT).show();

            }else if(mBTAdapter.isEnabled()){
                btnStatus.setText("Bluetooth enabled");
                Toast.makeText(getApplicationContext(),"Bluetooth already enabled",Toast.LENGTH_SHORT).show();
            }
        } else if (!switchBT.isChecked()) {
            mBTAdapter.disable();
            btnStatus.setText("Bluetooth disabled");
            Toast.makeText(getApplicationContext(), "Bluetooth turned off", Toast.LENGTH_SHORT).show();
        }
    }

    //enter here after user selects "yes" of "no" to enabling radio
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            //make sure the request was successful
            if (resultCode == RESULT_OK) {
                //the user picked a contact
                //the intent's data Uri identifies which contact was selected
                btnStatus.setText("enabled");
            } else {
                btnStatus.setText("disabled");
            }
        }
    }

    private void discover(View v) {
        mBTArrayAdapter.clear();
        Log.d("myLogs","clear");

        if (mBTAdapter.isDiscovering()) {
            mBTAdapter.cancelDiscovery();
            Toast.makeText(getApplicationContext(), "Discovery stopped", Toast.LENGTH_SHORT).show();
        }
        if (mBTAdapter.isEnabled()) {
            mBTArrayAdapter.clear();
            mBTAdapter.startDiscovery();
            Toast.makeText(getApplicationContext(), "discovery started", Toast.LENGTH_SHORT).show();
            registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();

        }

    }

    final BroadcastReceiver blReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //add the name to the list
                mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                mBTArrayAdapter.notifyDataSetChanged();
            }
        }
    };

    private void listPairedDevices(View v) {
        mPairedDevices = mBTAdapter.getBondedDevices();
        if (mBTAdapter.isEnabled()) {
            //put it's one to the adapter
            for (BluetoothDevice device : mPairedDevices) {
                mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
            Toast.makeText(getApplicationContext(), "show paired devices", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();

        }
    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            if (!mBTAdapter.isEnabled()) {
                Toast.makeText(getBaseContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
                return;
            }
            btnStatus.setText("Connecting...");
            //Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            final String address = info.substring(info.length() - 17);
            final String name = info.substring(0, info.length() - 17);

            //Spawn a new thread to avoid blocking the GUI one
            new Thread() {
                public void run() {
                    boolean fail = false;

                    BluetoothDevice device = mBTAdapter.getRemoteDevice(address);

                    try {
                        mBTSocket = createBluetoothSocket(device);
                    } catch (IOException e) {
                        fail = true;
                        Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }

                    //Establish the Bluetooth socket connection
                    try {
                        mBTSocket.connect();
                    } catch (IOException e) {
                        try {
                            fail = true;
                            mBTSocket.close();
                            mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                    .sendToTarget();
                        } catch (IOException e2) {
                            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (fail == false) {
                        mConnectedThread = new ConnectedThread(mBTSocket);
                        mConnectedThread.start();

                        mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, name)
                                .sendToTarget();

                    }
                }
            }.start();
        }
    };

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connection with BT device using UUID
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            //Get the input and output streams, using temp objects because
            //member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {

            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
//here changed
        public void run() {

            byte[] buffer = new byte[1024]; //buffer store for the stream
            int bytes; //bytes returned from read()
            //Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    //Read from the InputStream
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        SystemClock.sleep(100); //pause and wait fot rest of data
                        bytes = mmInStream.available(); //how many bytes are ready to be read?
                        bytes = mmInStream.read(buffer, 0, bytes); //record how many bytes we actually read
                        mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                                .sendToTarget(); //Send the obtained bytes to the UI activity
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        //Call this from the main activity to send data to the remote device
        public void write(String input) {
            Log.d("myLogs", input);
            byte[] bytes = input.getBytes(Charset.forName("UTF-8"));
            String str = new String(bytes, StandardCharsets.UTF_8);
            Log.d("myLogs", str); //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
            }

        }

        //Call this from the main activity to shutdown the connection
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {

            }
        }


    }

    private static class MyVeryOwnHandler extends Handler {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == MESSAGE_READ) {
                String readMessage = null;
                byte[] readBuf = (byte[]) msg.obj;

                try {
                    readMessage = new String((readBuf), "UTF-8");
                    readMessage = change(readMessage);
                    btnReceive.setText(readMessage);
                    Log.d("myLogs", readMessage + " length: " + readMessage.length());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if (msg.what == CONNECTING_STATUS) {
                if (msg.arg1 == 1) {
                    btnStatus2.setText("Connected to device: " + (String) (msg.obj));
                } else {
                    btnStatus2.setText("Connection failed");
                }
            }
        }
    }

    public static String change(String s) {
        int endIndex, endIndex2, len;
        endIndex = s.indexOf("*");
        endIndex2 = s.indexOf("**");
        String str = s.substring(endIndex + 1, endIndex2);
        len = Integer.parseInt(str);
        s = s.substring(0, len);

        return s;
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnBack:
                finish();
                break;
        }
    }
}