package fr.umontpellier.localisermonenfant.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import fr.umontpellier.localisermonenfant.databinding.FragmentAddChildBinding;
import fr.umontpellier.localisermonenfant.R;
import fr.umontpellier.localisermonenfant.models.Child;
import fr.umontpellier.localisermonenfant.viewmodel.ChildRegisterViewModel;

public class AddChildFragment extends Fragment {
    ChildRegisterViewModel childRegisterViewModel;
    FragmentAddChildBinding binding;

    public AddChildFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        childRegisterViewModel = ViewModelProviders.of(this).get(ChildRegisterViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_child, container, false);
        binding.setLifecycleOwner(getActivity());
        binding.setRegisterChildViewModel(childRegisterViewModel);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        childRegisterViewModel.getChild().observe(getViewLifecycleOwner(), new Observer<Child>() {
            @Override
            public void onChanged(Child child) {
                if (TextUtils.isEmpty(Objects.requireNonNull(child).getFirstname())) {
                    binding.editTextFirstName.setError("Prenom est vide.");
                    binding.editTextFirstName.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(child).getLastname())) {
                    binding.editTextName.setError("Nom est vide.");
                    binding.editTextName.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(child).getCode().toString())) {
                    binding.editTextCode.setError("Entrer un code");
                    binding.editTextCode.requestFocus();
                } else if (child.getCode() < 1000) {
                    binding.editTextCode.setError("Entrer un code de 4 chiffres");
                    binding.editTextCode.requestFocus();
                } else {
                    binding.editTextFirstName.setText(child.getFirstname());
                    binding.editTextName.setText(child.getLastname());
                    binding.editTextCode.setText(child.getCode().toString());
                }
            }
        });
    }
}