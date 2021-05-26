package fr.umontpellier.localisermonenfant.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import fr.umontpellier.localisermonenfant.R;
import fr.umontpellier.localisermonenfant.adapters.ChildsAdapter;
import fr.umontpellier.localisermonenfant.models.Child;
import fr.umontpellier.localisermonenfant.utils.ItemClickSupport;

public class ChildFragment extends Fragment {

    private ChildViewModel mViewModel;
    private MapViewModel mapViewModel;
    RecyclerView recyclerView;
    Context context;
    String TAG = "ChildFragment";
    ChildsAdapter childsAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static ChildFragment newInstance() {
        return new ChildFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.child_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        this.configureOnClickRecyclerView();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(context)).get(ChildViewModel.class);
        mViewModel.getChild().observe(getViewLifecycleOwner(), new Observer<List<Child>>() {
            @Override
            public void onChanged(List<Child> childs) {
                Log.d(TAG, "onChanged: ");
                childsAdapter = new ChildsAdapter(childs);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(childsAdapter);
                childsAdapter.notifyDataSetChanged();
                Log.d(TAG, "onChanged: " + childs.size());
            }
        });

        mapViewModel = new ViewModelProvider(requireActivity(), new ViewModelFactoryMap(context)).get(MapViewModel.class);
        mapViewModel.getChildId().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mapViewModel.getLastTrajetByChildId(s);
            }
        });
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.child_fragment)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Child child = childsAdapter.getChild(position);
                        mapViewModel.childId.setValue(child.get_id());
                        mapViewModel.clickedChild.setValue(child);
                        mapViewModel.postion.setValue(position);
                    }
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }
}