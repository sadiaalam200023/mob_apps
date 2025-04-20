package com.example.assignment1draft;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.assignment1draft.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {




    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private TextView countTextView;
    private int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        countTextView = findViewById(R.id.show_count);

    }



    public void Toast(View view) {
        String msg="Hello Toast";
        Toast toast = Toast.makeText(this, msg,Toast.LENGTH_SHORT);
        toast.show();
        a = 0;
        countTextView.setText(String.valueOf(a));
    }

    public void Count(View view) {
        a++;
        countTextView.setText(String.valueOf(a));


    }
}