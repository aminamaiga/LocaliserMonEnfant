package fr.umontpellier.localisermonenfant.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import fr.umontpellier.localisermonenfant.R;
import fr.umontpellier.localisermonenfant.adapters.ChildsAdapter;
import fr.umontpellier.localisermonenfant.adapters.ZoneAdapter;
import fr.umontpellier.localisermonenfant.models.Child;
import fr.umontpellier.localisermonenfant.models.Zone;
import fr.umontpellier.localisermonenfant.utils.ItemClickSupport;
import fr.umontpellier.localisermonenfant.utils.PrefUserInfos;

import static fr.umontpellier.localisermonenfant.networks.businesses.BusinessServiceChild.USERDATA;

public class ZoneParameterFragment extends Fragment {

    private ZoneParameterViewModel mViewModel;
    RecyclerView recyclerView;
    Context context;
    String TAG = "ZoneParameterFragment";
    ZoneAdapter zoneAdapter;
    private static final String ZONE_LEVEL = "ZONE_LEVEL";
    int zoneLevel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static ZoneParameterFragment newInstance() {
        return new ZoneParameterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            zoneLevel = bundle.getInt(ZONE_LEVEL, 0); // Key, default value
        }
        View view = inflater.inflate(R.layout.zone_parameter_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewZone);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String parentId = null;
        PrefUserInfos prefUserInfos = new PrefUserInfos(context);
        JSONObject userObject = prefUserInfos.loadMap(USERDATA);
        try {
            String userId = userObject.getString("userId");
            parentId = userId.replaceAll("\"", "");
            Log.i(TAG, "register: userObject " + userId.replaceAll("\"", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewModel = new ViewModelProvider(this, new ViewModelFactoryParameter(context, parentId, zoneLevel)).get(ZoneParameterViewModel.class);
        mViewModel.getZoneMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Zone>>() {
            @Override
            public void onChanged(List<Zone> zones) {
                Log.d(TAG, "onChanged: ");
                zoneAdapter = new ZoneAdapter(zones);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(zoneAdapter);
                zoneAdapter.notifyDataSetChanged();
                Log.d(TAG, "onChanged: " + zones.size());
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.child_fragment)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Zone zone = zoneAdapter.getZone(position);
                    }
                });
    }
}