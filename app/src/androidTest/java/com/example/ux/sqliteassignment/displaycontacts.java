package com.example.ux.sqliteassignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Ravi on 6/23/2016.
 */
public class displaycontacts extends Activity {
    int id_to_update = 0;
    EditText username;
    EditText Age;
    ImageView imageView;
    private dbhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_name);
        username = (EditText) findViewById(R.id.usrname);
        Age = (EditText) findViewById(R.id.Age1);
        imageView = (ImageView) findViewById(R.id.image2);

        db = new dbhelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int values = extras.getInt("id");
            if (values > 0) {
                Cursor cursor = db.numofround(values);
                id_to_update = values;
                cursor.moveToFirst();
                String nam = cursor.getString(cursor.getColumnIndex(dbhelper.db_name));
                String ag = cursor.getString(cursor.getColumnIndex(dbhelper.dbage));
                String img = cursor.getString(cursor.getColumnIndex(dbhelper.dbimage));

                if (!cursor.isClosed()) {
                    cursor.close();
                }
                Button btn = (Button) findViewById(R.id.btnsave);
                btn.setVisibility(View.INVISIBLE);

                username.setText((CharSequence) nam);
                username.setFocusable(false);
                username.setClickable(false);

                Age.setText((CharSequence) ag);
                Age.setFocusable(false);
                Age.setClickable(false);

                byte[] blob = cursor.getBlob(cursor.getColumnIndex(dbhelper.dbimage));
                ByteArrayInputStream inputStream = new ByteArrayInputStream(blob);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int values = bundle.getInt("id");
            if (values > 0) {
                getMenuInflater().inflate(R.menu.menu_sqlite, menu);

            } else {
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.Eidt:
                Button b = (Button) findViewById(R.id.btnsave1);
                b.setVisibility(View.VISIBLE);
                username.setEnabled(true);
                username.setFocusableInTouchMode(true);
                username.setClickable(true);

                Age.setEnabled(true);
                Age.setFocusableInTouchMode(true);
                Age.setClickable(true);

                imageView.setEnabled(true);
                imageView.setFocusableInTouchMode(true);
                imageView.setClickable(true);

                return true;
            case R.id.delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.deletemethod(id_to_update);

                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        int Value = extras.getInt("id");

        
        if (extras != null) {
            if (Value > 0) {
                if (db.updatemethod(id_to_update, username.getText().toString(), Age.getText().toString(),imageView)) {
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (db.insertmethod(username.getText().toString(), Age.getText().toString(),)) {
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}