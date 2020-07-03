package com.caltech.autoattend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SubjectList extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SubjectListRecyclerViewAdapter mAdapter;
    SubjectListViewModel mViewModel;
    LiveData<List<Subject>> allSubject;
    ArrayList<Subject> nonDuplicateSubjectList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.subject_list_fragment, container, false);
        mViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(SubjectListViewModel.class);

        mViewModel.getAllSubject().observe(getActivity(), subjects -> {
            nonDuplicateSubjectList.clear();

            for (Subject subject : subjects) {
                boolean isElementExists = false;
                if (nonDuplicateSubjectList.isEmpty()) {
                    nonDuplicateSubjectList.add(subject);
                } else {
                    for (int i = 0; i < nonDuplicateSubjectList.size(); i++) {
                        if (!nonDuplicateSubjectList.get(i).sub_name.equals(subject.sub_name)) {
                            if (i == nonDuplicateSubjectList.size() - 1 && !isElementExists) {
                                nonDuplicateSubjectList.add(subject);
                            }
                        } else {
                            isElementExists = true;
                        }
                    }
                }

            }

            if (mAdapter == null) {
                mAdapter = new SubjectListRecyclerViewAdapter(nonDuplicateSubjectList);
                recyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.updateData(nonDuplicateSubjectList);
            }

        });
        recyclerView = view.findViewById(R.id.subject_itemList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}