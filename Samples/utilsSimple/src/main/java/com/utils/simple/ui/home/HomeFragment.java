package com.utils.simple.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.utils.simple.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recycler_list);

        RecyclerView.LayoutManager layoutManager;

        layoutManager = new GridLayoutManager(getContext(), 2);
//        layoutManager = new LinearLayoutManager(getContext());

//        DividerItemDecoration dividerItemDecoration_Vertical = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
//        dividerItemDecoration_Vertical.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.bg_shape_divider)));
//
//        recyclerView.addItemDecoration(dividerItemDecoration_Vertical);

        recyclerView.addItemDecoration(new GridDividerItemDecoration(7,2));

        recyclerView.setLayoutManager(layoutManager);

        HomeCompatAdapter compatAdapter = new HomeCompatAdapter(getContext());

        recyclerView.setAdapter(compatAdapter);

        homeViewModel.getCompatList().observe(this, compatBeans -> {

            compatAdapter.setCompatBeanList(compatBeans);

            compatAdapter.notifyDataSetChanged();
        });


        return root;
    }
}