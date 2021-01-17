package com.example.sqlliteexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter customerArrayAdapter;
    Button viewAll, add;
    EditText name, age;
    Switch aSwitch;
    ListView listView;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewAll = findViewById(R.id.viewAll);
        add = findViewById(R.id.add);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        aSwitch = findViewById(R.id.switchScroll);
        listView = findViewById(R.id.listview);
        databaseHelper = new DatabaseHelper(MainActivity.this);

        showCustomerOnClick();

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper = new DatabaseHelper(MainActivity.this);

                showCustomerOnClick();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerModel customerModel;
                try {
                    customerModel = new CustomerModel(-1, name.getText().toString(), Integer.parseInt(age.getText().toString()), aSwitch.isChecked());
                    String res = customerModel.toString();

                    Toast.makeText(MainActivity.this,res,Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(MainActivity.this,"Error Creating customer", Toast.LENGTH_LONG).show();

                    customerModel = new CustomerModel(-1, "error", 0, false);

                }

                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                boolean success = databaseHelper.addOne(customerModel);

                if(!success)    Toast.makeText(MainActivity.this, "Data is not saved", Toast.LENGTH_SHORT).show();
                if(success)    Toast.makeText(MainActivity.this, "Data is saved", Toast.LENGTH_SHORT).show();

                showCustomerOnClick();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
//                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                databaseHelper.deleteCustomer(clickedCustomer);
                showCustomerOnClick();
                Toast.makeText(MainActivity.this, "Deleted customer.....\n"+ clickedCustomer, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCustomerOnClick() {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getAllData());
        listView.setAdapter(customerArrayAdapter);
    }
}