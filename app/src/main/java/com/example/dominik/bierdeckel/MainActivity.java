package com.example.dominik.bierdeckel;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Food> foodMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFoodInputDialog();
            }
        });

        foodMenu = new ArrayList<Food>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openFoodInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Neues Essen/Trinken:");

        final LinearLayout inputList = new LinearLayout(this);
        inputList.setOrientation(LinearLayout.VERTICAL);

        // Set up the input
        final EditText nameInput = new EditText(this);
        nameInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        nameInput.setHint("Name");
        inputList.addView(nameInput);

        final EditText descriptionInput = new EditText(this);
        descriptionInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        descriptionInput.setHint("Beschreibung (optional)");
        inputList.addView(descriptionInput);

        final EditText priceInput = new EditText(this);
        priceInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        priceInput.setHint("Preis");
        inputList.addView(priceInput);

        final EditText countInput = new EditText(this);
        countInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        countInput.setHint("Anzahl (optional)");
        inputList.addView(countInput);

        builder.setView(inputList);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String foodName = nameInput.getText().toString();
                String description = descriptionInput.getText().toString();
                float price = 0.0f;
                int count = 0;

                if (!priceInput.getText().toString().equals("")) {
                    price = Float.parseFloat(priceInput.getText().toString());
                }
                if (!countInput.getText().toString().equals("")) {
                    count = Integer.parseInt(countInput.getText().toString());
                }

                Food food = new Food(foodName, price, description, count);

                foodMenu.add(food);
                for (int i = 0; i < foodMenu.size(); i++) {
                    System.out.println(foodMenu.get(i).foodName);
                    System.out.println(foodMenu.get(i).description);
                    System.out.println(foodMenu.get(i).price);
                    System.out.println(foodMenu.get(i).count);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }
}
