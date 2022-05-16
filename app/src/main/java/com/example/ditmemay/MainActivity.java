package com.example.ditmemay;

import android.os.Bundle;
import android.content.Intent;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.widget.Button;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ditmemay.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.*;
import android.widget.TextView;
import android.widget.Toast;
class countScore {
    LinkedList<String> symptoms;
    String name;
    int score;

    public countScore(String name, LinkedList<String> symptoms) {
        this.name = name;
        this.symptoms = symptoms;
        this.score = 0;
    }

    public int compareTo(countScore a) {
        return this.score - a.score;
    }
}

class CustomComparator implements Comparator<countScore> {
    @Override
    public int compare(countScore a, countScore b) {
        return a.score < b.score ? 1 : -1;
    }
}
public class MainActivity extends AppCompatActivity {
    TextView textView;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    static String[] diseases_symptoms = {"dau dau", "so mui", "dau bung", "bu cu", "cu bu", "sugar baby"};
    static LinkedList<String> user_symptoms = new LinkedList<String>();
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        textView = findViewById(R.id.textView);
        selectedLanguage = new boolean[diseases_symptoms.length];
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select your symptoms...");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(diseases_symptoms, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {

                            langList.add(i);
                            user_symptoms.add(diseases_symptoms[i]);
                            // Sort array list

                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(diseases_symptoms[langList.get(j)]);
                            // check condition
                            if (j != langList.size() - 1) {
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
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    // LinkedList<String>
    // String[]
    public static LinkedList<countScore> Calcpercentage() {
        int i = 0, j, k;
        LinkedList<String> User_Symptoms_List = MainActivity.user_symptoms;

        String[] diseases = {"Common Cold", "Influenza", "Diarrhea", "Diabetes", "Asthma", "Alzheimer's", "Hypertension"};

        //  List<String> dl = Arrays.asList(diseases);
        //  LinkedList<String> Disease_List = new LinkedList<String>(dl);
        LinkedList<String>[] symptoms_of_disease = new LinkedList[diseases.length];

        String[] headache_symptoms = {"dau dau"};
        String[] Stomache_symptoms = {"dau dau", "so mui", "dau bung", "bu cu"};
        String[] Alabatrap_symptoms = {"dau dau", "so mui", "sugar baby"};


        for (i = 0; i < diseases.length; i++) symptoms_of_disease[i] = new LinkedList<String>();

        symptoms_of_disease[0] = new LinkedList<>(Arrays.asList(headache_symptoms));
        symptoms_of_disease[1] = new LinkedList<>(Arrays.asList(Stomache_symptoms));
        symptoms_of_disease[2] = new LinkedList<>(Arrays.asList(Alabatrap_symptoms));

        LinkedList<countScore> ll = new LinkedList<countScore>();
        for (i = 0; i < symptoms_of_disease.length; i++) {
            countScore tmp = new countScore(diseases[i], symptoms_of_disease[i]);
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
//        for (i = 0; i < diseases.length; i++)  System.out.println(ll.get(i).score);

        Collections.sort(ll,new CustomComparator());
//        System.out.println(ll.get(0).name);
        return ll;
    }
}