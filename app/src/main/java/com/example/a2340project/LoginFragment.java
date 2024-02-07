//package com.example.a2340project;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.example.a2340project.databinding.ClassesFragmentBinding;
//
//public class LoginFragment extends Fragment {
//    private ClassesFragmentBinding binding;
//    private LinearLayoutManager layoutManager;
//
//    @Override
//    public View onCreateView(
//            LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState
//    ) {
//
//        binding = ClassesFragmentBinding.inflate(inflater, container, false);
//
//        layoutManager = new LinearLayoutManager(getActivity());
//
//
//
//        ClassListAdapter classListAdapter = new ClassListAdapter(courses, getActivity().getSupportFragmentManager());
//        binding.courseListView.setAdapter(classListAdapter);
//        binding.courseListView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        MainActivity.updateMenu(getParentFragmentManager(), TaskbarMenuState.CLASS_LIST);
//
//        return binding.getRoot();
//
//    }
//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
///*
//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
//*/
//    }
//
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}
