package com.example.Medinity;

import static com.example.Medinity.MainActivity.Calcpercentage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.Medinity.R;
import com.example.Medinity.databinding.FragmentSecondBinding;

import java.util.ArrayList;
import java.util.*;
public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        // Acessing the array of symptoms and user input symptoms from the first fragment
        ListView list_of_dis= binding.listBenhId;
        LinkedList<countScore> list_benh = Calcpercentage();
        ArrayList<String> arr = new ArrayList<String>();

        // If this particular disease matches x number of symptoms with the user inputs, then it might be a possible disease the user has
        // put the disease in the possible list

        for(int i=0; i< list_benh.size();i++) {
            if(list_benh.get(i).score >= 1) arr.add(list_benh.get(i).name);
        }


        // Using an Array Adapater to output the diseases on the second screen
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(), R.layout.text_color_layout,arr);
        list_of_dis.setAdapter(myAdapter);
        // make this so that when the user clicks on the disease name, an external link leading to a website is created and
        // open the website on the user's screen
        list_of_dis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(MainActivity.url[position])));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}