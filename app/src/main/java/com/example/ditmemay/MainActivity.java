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
import java.util.Collections;
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
    @Override
    public int compare(countScore a, countScore b) {
        return a.score < b.score ? 1 : -1;
    }
}
public class MainActivity extends AppCompatActivity {
    TextView textView;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    static String[] diseases_symptoms = {"Anxiety", "Bloating", "Chest pain", "Coughing", "Dyspena", "Drowsiness/Confusion" , "Fast heartbeat", "Fatigue", "Fever", "Headache", "Heartburn", "Hypoglycemia"
            ,"Joint pain", "Insomnia", "Lightheadedness", "Loss of appetite", "Memory loss", "Mood swings", "Muscle pain",
    "Nausea", "Nose bleed", "Poor concentration", "Rapid heartbeat", "Runny nose", "Sneezing", "Sore throat", "Stomachache", "Sweating and shivering", "Swelling", "Unexplained weight loss", "Urine Discoloration",
    "Vision problems", "Vomitting", "Watery Eyes", "Reading difficulty" };
  //  Collections.sort(disease_symptoms);
    //static String[] url = {"a", "b", "c"};
    static String[] url1 = {"https://www.webmd.com/alzheimers/default.htm",
            "https://www.webmd.com/asthma/default.htm",
            "https://www.webmd.com/cold-and-flu/common_cold_overview",
            "https://www.webmd.com/depression/default.htm",
            "https://www.webmd.com/diabetes/default.htm",
            "https://www.webmd.com/digestive-disorders/digestive-diseases-diarrhea",
            "https://www.webmd.com/hepatitis/digestive-diseases-hepatitis-b",
            "https://www.webmd.com/hypertension-high-blood-pressure/default.htm",
            "https://www.mayoclinic.org/diseases-conditions/flu/symptoms-causes/syc-20351719",
            "https://www.webmd.com/a-to-z-guides/malaria-symptoms",
            "https://www.webmd.com/children/vaccines/what-is-measles",
            "https://www.webmd.com/digestive-disorders/peptic-ulcer-overview",
          "https://www.webmd.com/lung/understanding-pneumonia-basics#:~:text=Pneumonia%20is%20a%20lung%20infection,oxygen%20to%20reach%20your%20bloodstream.",
            "https://www.webmd.com/lung/understanding-tuberculosis-basics",
    };
    static String[] url = {"https://www.webmd.com/alzheimers/default.htm",
            "https://www.webmd.com/asthma/default.htm",
            "https://www.webmd.com/cold-and-flu/common_cold_overview",
            "https://www.webmd.com/depression/default.htm",
            "https://www.webmd.com/diabetes/default.htm",
            "https://www.webmd.com/digestive-disorders/digestive-diseases-diarrhea",
            "https://www.webmd.com/hepatitis/digestive-diseases-hepatitis-b",
            "https://www.webmd.com/hypertension-high-blood-pressure/default.htm",
            "https://www.mayoclinic.org/diseases-conditions/flu/symptoms-causes/syc-20351719",
            "https://www.webmd.com/a-to-z-guides/malaria-symptoms",
            "https://www.webmd.com/children/vaccines/what-is-measles",
            "https://www.webmd.com/digestive-disorders/peptic-ulcer-overview",
            "https://www.webmd.com/lung/understanding-pneumonia-basics#:~:text=Pneumonia%20is%20a%20lung%20infection,oxygen%20to%20reach%20your%20bloodstream.",
            "https://www.webmd.com/lung/understanding-tuberculosis-basics",
    };
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

        String[] diseases = {"Alzheimer's", "Asthma", "Common Cold", "Depression", "Diabetes", "Diarrhea", "Hepatitis B",
        "Hypertension", "Influenza", "Malaria", "Measles", "Peptic Ulcer", "Pneumonia", "Tuberculosis"};

        LinkedList<String>[] symptoms_of_disease = new LinkedList[diseases.length];

        String[] d1 = {"Memory loss" , "Poor concentration", "Mood swings", "Reading difficulty", "Anxiety"};
        String[] d2 = {"Dyspnea", "Fatigue", "Drowsiness/Confusion", "Coughing", "Fast heartbeat"};
        String[] d3 = {"Coughing", "Sore throat", "Fever", "Sneezing", "Watery eyes"};
        String[] d4 = {"Anxiety", "Mood swings", "Poor concentration", "Fatigue"};
        String[] d5 = {"Vision problems", "Fatigue", "Unexplained weight loss", "Lightheadedness", "Fast heartbeat"};
        String[] d6 = {"Nausea", "Fever", "Bloating", "Fatigue"};
        String[] d7 = {"Fever", "Fatigue", "Nausea", "Joint pain", "Loss of appetite"};
        String[] d8 = {"Vision problems", "Fatigue", "Dyspnea", "Drowsiness/Confusion","Insomnia"};
        String[] d9 = {"Coughing", "Fatigue", "Fever", "Headache", "Nausea", "Vomitting"};
        String[] d10 = {"Headache", "Muscle pain", "Fatigue", "Nausea","Vomitting", "Coughing"};
        String[] d11 = {"Watery eyes", "Coughing", "Runny nose", "Fatigue"};
        String[] d12 = {"Heartburn", "Nausea", "Vomitting", "Fatigue", "Bloating", "Unexplained weight loss"};
        String[] d13 = {"Dyspnea", "Sweating and shivering", "Fever", "Watery eyes", "Rapid heartbeat"};
        String[] d14 = {"Unexplained weight loss", "Fatigue", "Loss of appetite", "Swelling"};




        for (i = 0; i < diseases.length; i++) symptoms_of_disease[i] = new LinkedList<String>();

        symptoms_of_disease[0] = new LinkedList<>(Arrays.asList(d1));
        symptoms_of_disease[1] = new LinkedList<>(Arrays.asList(d2));
        symptoms_of_disease[2] = new LinkedList<>(Arrays.asList(d3));
        symptoms_of_disease[3] = new LinkedList<>(Arrays.asList(d4));
        symptoms_of_disease[4] = new LinkedList<>(Arrays.asList(d5));
        symptoms_of_disease[5] = new LinkedList<>(Arrays.asList(d6));
        symptoms_of_disease[6] = new LinkedList<>(Arrays.asList(d7));
        symptoms_of_disease[7] = new LinkedList<>(Arrays.asList(d8));
        symptoms_of_disease[8] = new LinkedList<>(Arrays.asList(d9));
        symptoms_of_disease[9] = new LinkedList<>(Arrays.asList(d10));
        symptoms_of_disease[10] = new LinkedList<>(Arrays.asList(d11));
        symptoms_of_disease[11] = new LinkedList<>(Arrays.asList(d12));
        symptoms_of_disease[12] = new LinkedList<>(Arrays.asList(d13));
        symptoms_of_disease[13] = new LinkedList<>(Arrays.asList(d14));

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
//        for (i = 0; i < diseases.length; i++)  System.out.println(ll.get(i).score);

        Collections.sort(ll,new CustomComparator());
//        System.out.println(ll.get(0).name);
        for( i = 0; i < ll.size(); i++) {
            url[i] = ll.get(i).Link;

        }
        return ll;
    }
}