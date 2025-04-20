package com.example.todolist;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.navigation.ui.AppBarConfiguration;


import com.example.todolist.databinding.ActivityMainBinding;
import android.widget.ArrayAdapter;
import java.util.ArrayList;


import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private EditText taskedit;
    private Button addtask, refresh, update, deletebtn;
    private ListView todolistView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> taskList;
    private int selected = -1;


    private Databasehelper dbHelper;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;



    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing all our variables.
        addtask = findViewById(R.id.add);
        update = findViewById(R.id.update);
        taskedit = findViewById(R.id.task);
        refresh = findViewById(R.id.refresh);
        deletebtn = findViewById(R.id.deletebtn);
        todolistView = findViewById(R.id.todolistView);
        dbHelper = new Databasehelper(MainActivity.this);
        taskList = dbHelper.getallTasks();
        Log.d("Mainactivity","size"+taskList.size());

        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, taskList);
        todolistView.setAdapter(arrayAdapter);


        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!taskedit.getText().toString().isEmpty()) {
                    if (dbHelper.addNewTask(taskedit.getText().toString())) {

                        Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();

                        taskList.clear();
                        taskList.addAll(dbHelper.getallTasks());
                        arrayAdapter.notifyDataSetChanged();} else {
                        Toast.makeText(MainActivity.this, "Not Added", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    taskedit.setError("Enter task");
                }
            }
        });
        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskList.clear();
                taskList.addAll(dbHelper.getallTasks());
                Log.d("Mainactivity","tasks after refresh"+taskList.toString());
                arrayAdapter.notifyDataSetChanged();

            }

        });
        todolistView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTask = taskList.get(position);  // Get the selected task description
            selected = dbHelper.getTaskId(selectedTask);  // Get the actual task ID from the database
            taskedit.setText(selectedTask);
            Toast.makeText(MainActivity.this, "Selected Task ID: " + selected, Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.deletebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected != -1) { // Check if a task is selected
                    if (dbHelper.deleteTask(selected)) {
                        Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                        selected = -1;
                        taskedit.setText("");

                    } else {
                        Toast.makeText(MainActivity.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Select a task first", Toast.LENGTH_SHORT).show();
                }
            }


            }

        );


    } }







