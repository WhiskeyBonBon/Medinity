package com.example.Medinity;
import android.content.Intent;
import android.os.Bundle;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.Medinity.R;
import com.example.Medinity.databinding.ActivityMainBinding;
import com.example.Medinity.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.*;

import android.widget.Toast;
import java.util.Collections;

// The countScore class is used to contain the info of a disease
// List of symptoms, the Link to the website about it,
// score is for how many of the disease symptoms that matches the user input symptoms
class countScore {
    LinkedList<String> symptoms;
    String name;
    int score;
    String Link;
    //, String link
    public countScore(String name, LinkedList<String> symptoms, String link) {
        this.name = name;
        this.symptoms = symptoms;
        this.score = 0;
        this.Link = link;
    }

    public int compareTo(countScore a) {
        return this.score - a.score;
    }
}

class CustomComparator implements Comparator<countScore> {
    // used to compare two CocuntScore objects for the sorting process
    @Override
    public int compare(countScore a, countScore b) {
        return a.score < b.score ? 1 : -1;
    }
}


public class MainActivity extends AppCompatActivity{
    TextView textView;
    boolean[] selectedDisease;

    // to contain user input symptoms
    ArrayList<Integer> symlist = new ArrayList<>();
    public static int numLines = 0;
    public static String[] diseases_symptoms;
    public static LinkedList<String>[] symptoms_of_disease;

    // to contain the urls of the diseases
    public static String[] url;

    // to contain diseases names
    public static String[] diseases;



    static LinkedList<String> user_symptoms = new LinkedList<String>();
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    public static String csvfileString;
    // Method to automatically read data from a csv file
    public void readData() {
        try {
            // Reading the symptoms list and the links from the Excel file
            InputStream is = getResources().openRawResource(R.raw.testoop);

            BufferedReader count = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            // Set to contain all the symptoms, needs to be unique for the drop down list
            Set<String> set_diseases_symptoms = new HashSet<String>();

            String line = "";

            while (count.readLine() != null) numLines++;
            count.close();
            symptoms_of_disease = new LinkedList[numLines];
            url = new String[numLines];
            diseases = new String[numLines];

            is = getResources().openRawResource(R.raw.testoop);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            int z = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String[] arr = new String[values.length - 2];
                for (int j = 2; j < values.length; j++) {
                    arr[j - 2] = values[j];
                    set_diseases_symptoms.add(values[j]);
                }
                diseases[z] = values[0];
                url[z] = values[1];
                symptoms_of_disease[z] = new LinkedList<String>(Arrays.asList(arr));
                z++;
            }

            diseases_symptoms = new String[set_diseases_symptoms.size()];
           // Arrays.sort(diseases_symptoms);
            set_diseases_symptoms.toArray(diseases_symptoms);
            // Sorting the symptoms list for the drop down list
            Arrays.sort(diseases_symptoms);

            br.close();
        } catch (IOException e1) {
            Log.e("MainActivity", "Error", e1);
            e1.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.readData();
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        textView = findViewById(R.id.textView);
        // seleced diseases by the user
        selectedDisease = new boolean[diseases_symptoms.length];
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select your symptoms...");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(diseases_symptoms, selectedDisease, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {

                            symlist.add(i);
                            user_symptoms.add(diseases_symptoms[i]);
                            // Sort array list

                            Collections.sort(symlist);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            symlist.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < symlist.size(); j++) {
                            // concat array value
                            stringBuilder.append(diseases_symptoms[symlist.get(j)]);
                            // check condition
                            if (j != symlist.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        textView.setText(stringBuilder.toString());
                        Toast toast = Toast.makeText(getApplicationContext(), "Please click on next button  for our diagnosis", Toast.LENGTH_SHORT);
                        toast.show();
                        textView.setVisibility(View.INVISIBLE);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedDisease.length; j++) {
                            // remove all selection
                            selectedDisease[j] = false;
                            // clear language list
                            symlist.clear();
                            // clear text view value
                            textView.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

// To access the user symptoms list and sorting them in order for the second fragment
    public static LinkedList<countScore> Calcpercentage() {
        int i = 0, j, k;
        LinkedList<String> User_Symptoms_List = MainActivity.user_symptoms;

        // , MainActivity.url[i]
        LinkedList<countScore> ll = new LinkedList<countScore>();
        for (i = 0; i < symptoms_of_disease.length; i++) {
            countScore tmp = new countScore(diseases[i], symptoms_of_disease[i], url[i]);
            int score = 0;
            for (j = 0; j < User_Symptoms_List.size(); j++) {
                for (k = 0; k < symptoms_of_disease[i].size(); k++) {
                    if (User_Symptoms_List.get(j) == symptoms_of_disease[i].get(k)) {
                        score++;
                    }
                }
            }
            tmp.score = score;

            ll.add(tmp);
        }


        Collections.sort(ll,new CustomComparator());
        // Contains the urls of the diseases (for each one)
        for( i = 0; i < ll.size(); i++) {
            url[i] = ll.get(i).Link;

        }
        return ll;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, ThirdFragment.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}