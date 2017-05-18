package com.example.a15039840.demodatabasecrud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by 15039840 on 18/5/2017.
 */

public class EditActivity extends AppCompatActivity {

    TextView tvID;
    EditText etContent;
    Button btnUpdate, btnDelete;
    Note data;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        //initialize the variables with UI here
        tvID = (TextView)findViewById(R.id.tvID);
        etContent = (EditText)findViewById(R.id.etContent);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        i = getIntent();
        data = (Note) i.getSerializableExtra("data");

        tvID.setText("ID: " + data.getId());
        etContent.setText(data.getNoteContent());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                data.setNoteContent(etContent.getText().toString());
                dbh.updateNote(data);
                dbh.close();
                setResult(RESULT_OK,i);
                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                dbh.deleteNote(data.getId());
                dbh.close();
                setResult(RESULT_OK,i);
                finish();
            }
        });




    }

}

