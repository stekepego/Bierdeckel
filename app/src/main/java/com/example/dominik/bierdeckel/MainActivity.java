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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Food> foodMenu;
    private ListView listView1;
    private FoodItemAdapter foodAdapter;
    float priceSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        foodMenu = new ArrayList<Food>();
        foodMenu = initFoodList();

        foodAdapter = new FoodItemAdapter(this, R.layout.food_item, foodMenu);

        listView1 = (ListView)findViewById(R.id.foodView);
        listView1.setAdapter(foodAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFoodInputDialog();
            }
        });

        this.refreshPriceSumView();
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
                foodAdapter.notifyDataSetChanged();
                refreshPriceSumView();
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

    public List<Food> initFoodList()
    {
        List<Food> foodList = new ArrayList<>();

        Food bier = new Food("Bier", 2.2f, "", 0);
        foodList.add(bier);

        Food schinken = new Food("Schinken", 1.03f, "", 1);
        foodList.add(schinken);

        Food kase = new Food("Käse", 3.55f, "", 2);
        foodList.add(kase);

        return foodList;
    }

    public void refreshPriceSumView()
    {
        TextView priceSumView = (TextView) findViewById(R.id.priceSum);
        priceSum = getPriceSum(foodMenu);
        String priceSumText = " = " + String.format("%.2f", priceSum) + "€";
        priceSumView.setText(priceSumText);
    }

    public float getPriceSum(List<Food> foodList)
    {
        priceSum = 0.0f;

        for (int i = 0; i < foodList.size(); i++)
        {
            priceSum += foodList.get(i).price * foodList.get(i).count;
        }

        return priceSum;
    }
}
