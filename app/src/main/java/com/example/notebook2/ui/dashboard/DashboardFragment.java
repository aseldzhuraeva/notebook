package com.example.notebook2.ui.dashboard;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.notebook2.databinding.FragmentDashboardBinding;
import com.example.notebook2.room.AppDb;
import com.example.notebook2.room.StudentDao;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private AppDb appDb;
    private StudentDao studentDao;
    private  TelephoneAdapter telephoneAdapter;
    RecyclerView rv_main_catalog;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rv_main_catalog = binding.rvNoteBook;
        telephoneAdapter = new TelephoneAdapter();
        rv_main_catalog.setAdapter(telephoneAdapter);

        appDb = Room.databaseBuilder(binding.getRoot().getContext(), AppDb.class, "database")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        studentDao = appDb.studentDao();
        telephoneAdapter.setList(studentDao.getAll());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}