package com.example.androidapp.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidapp.R;
import com.example.androidapp.adapter.FlowerAdapter;
import com.example.androidapp.api.ApiFlowerService;
import com.example.androidapp.model.Flower;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private FlowerAdapter flowerAdapter;
    private List<Flower> flowerList;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        CallApiGetFlower();

        return view;
    }

    private void CallApiGetFlower() {
        ApiFlowerService.apiFlowerService.getListFlowers()
                .enqueue(new Callback<List<Flower>>() {
                    @Override
                    public void onResponse(Call<List<Flower>> call, Response<List<Flower>> response) {

                        flowerList = response.body();
                        Collections.sort(flowerList, new Comparator<Flower>() {
                            @Override
                            public int compare(Flower flower1, Flower flower2) {
                                return Double.compare(flower2.getAvgScore(), flower1.getAvgScore());
                            }
                        });
                        flowerAdapter = new FlowerAdapter(flowerList, getContext());
                        recyclerView.setAdapter(flowerAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Flower>> call, Throwable t) {
                        Toast.makeText(requireContext(), "Call API ERROR", Toast.LENGTH_LONG).show();
                    }
                });

    }
}