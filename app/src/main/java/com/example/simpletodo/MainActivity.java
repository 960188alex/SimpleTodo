package com.example.simpletodo;

import org.apache.commons.io.FileUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<String> items;

    // from xml
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItem;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItem = findViewById(R.id.rvItem);

        etItem.setText("this is from Java");

        loadItems();

        ItemAdapter.OnLongClickListener onLongClickListener = new ItemAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // delete from model
                items.remove(position);
                // notify delete
                itemAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was deleted", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };

        // construct adaptor
        itemAdapter = new ItemAdapter(items,onLongClickListener);
        rvItem.setAdapter(itemAdapter);
        rvItem.setLayoutManager(new LinearLayoutManager(this));

        // add button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                items.add(todoItem);
                itemAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                // pop up message
                Toast.makeText(getApplicationContext(),"Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });

    }

    // save load function
    private File getDataFile(){
        return new File(getFilesDir(),"data.txt");
    }

    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity","Error reading items", e);
            items = new ArrayList<>();
        }
    }

    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity","Error writing items", e);

        }
    }

}