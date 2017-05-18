package com.example.a15039840.demodatabasecrud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnAdd, btnEdit, btnRetrieve;
    TextView tvDBContent;
    EditText etContent;
    ArrayList<String> al;
    ListView lv;
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the variables with UI here
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        btnRetrieve = (Button)findViewById(R.id.btnRetrieve);
        tvDBContent = (TextView) findViewById(R.id.tvDBContent);
        etContent = (EditText) findViewById(R.id.etContent);
        lv = (ListView) findViewById(R.id.lv);


        al = new ArrayList<String>();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etContent.getText().toString();
                DBHelper dbh = new DBHelper(MainActivity.this);
                long row_affected = dbh.insertNote(data);
                dbh.close();

                if (row_affected != -1){
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                al.clear();
                al.addAll(dbh.getAllNotes());
                dbh.close();

                String txt = "";
                for (int i = 0; i< al.size(); i++){
                    String tmp = al.get(i);
                    txt += tmp + "\n";
                }
                tvDBContent.setText(txt);
                aa.notifyDataSetChanged();

            }
        });
        lv = (ListView) findViewById(R.id.lv);
        aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);

                String data = al.get(0);
                String id = data.split(",")[0].split(":")[1];
                String content = data.split(",")[1].trim();

                Note target = new Note(Integer.parseInt(id), content);
                i.putExtra("data", target);
                //startActivity(i);
                startActivityForResult(i, 9);
            }
        });


    }

//    no need click on Retrieve button, the data will refreshh!
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9){
            btnRetrieve.performClick();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(MainActivity.this);
        al.clear();
        al.addAll(dbh.getAllNotes());
        dbh.close();

        String txt = "";
        for (int i = 0; i< al.size(); i++){
            String tmp = al.get(i);
            txt += tmp + "\n";
        }
        tvDBContent.setText(txt);
    }
}
