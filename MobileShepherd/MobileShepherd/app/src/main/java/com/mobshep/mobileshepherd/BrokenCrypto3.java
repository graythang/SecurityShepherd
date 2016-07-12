package com.mobshep.mobileshepherd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class BrokenCrypto3 extends MainActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broken_layout3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        generateKey();
        generateDB(this);

        String destinationDir = this.getFilesDir().getParentFile().getPath() + "/crypto/";

        String destinationPath = destinationDir + "key.txt";

        File f = new File(destinationPath);

        if (!f.exists()) {
            File directory = new File(destinationDir);
            directory.mkdirs();

            try {
                copyDatabase(getBaseContext().getAssets().open("key.txt"),
                        new FileOutputStream(destinationPath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void copyDatabase(InputStream iStream, OutputStream oStream)
            throws IOException {
        byte[] buffer = new byte[1024];
        int i;
        while ((i = iStream.read(buffer)) > 0) {
            oStream.write(buffer, 0, i);
        }
        iStream.close();
        oStream.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement



        if (id == R.id.action_exit)
        {
            finish();
        }

        if (id == R.id.action_info) {


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title
            alertDialogBuilder.setTitle("Information");

            // set dialog message
            alertDialogBuilder
                    .setMessage("This App has encrypted it's data using SQLCipher! This will stop hackers from stealing data on this App.")
                    .setCancelable(false)
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }



    public void generateDB(Context context) {
        try {
            SQLiteDatabase.loadLibs(context);

            String dbPath = context.getDatabasePath("key4.db").getPath();

            File dbPathFile = new File(dbPath);
            if (!dbPathFile.exists())
                dbPathFile.getParentFile().mkdirs();

            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbPath,
                    "Pa88w0rd1234", null);

            db.execSQL("DROP TABLE IF EXISTS key4");
            db.execSQL("CREATE TABLE key4(key VARCHAR)");

            db.execSQL("INSERT INTO key4 VALUES('The Key is ShaveTheSkies.')");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Toast error = Toast.makeText(BrokenCrypto3.this,
                    "An error occurred.", Toast.LENGTH_LONG);
            error.show();

        }

    }

    public void generateKey()
    {
        String destinationDir = this.getFilesDir().getParentFile().getPath() + "/crypto/encrypt/";

        String destinationPath = destinationDir + "key";


        File f = new File(destinationPath);

        if (!f.exists()) {
            File directory = new File(destinationDir);
            directory.mkdirs();

            try {
                copyKey(getBaseContext().getAssets().open("key"),
                        new FileOutputStream(destinationPath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void copyKey(InputStream iStream, OutputStream oStream)
            throws IOException {
        byte[] buffer = new byte[1024];
        int i;
        while ((i = iStream.read(buffer)) > 0) {
            oStream.write(buffer, 0, i);
        }
        iStream.close();
        oStream.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
