package com.example.notebook2.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;

import com.example.notebook2.MainActivity;
import com.example.notebook2.R;
import com.example.notebook2.databinding.FragmentHomeBinding;
import com.example.notebook2.models.Student;
import com.example.notebook2.room.AppDb;
import com.example.notebook2.room.StudentDao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private AppDb appDb;
    private StudentDao studentDao;
    private ActivityResultLauncher<String> content_l;
    private Bitmap bitmap_imafeStudent;
    private boolean isImgSelect = false;
    NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnLoad.setOnClickListener(v1 -> {

            HomeFragment.this.content_l.launch("image/*");
        });

        content_l = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        try{
                            bitmap_imafeStudent = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),result);

                            binding.kotik.setImageBitmap(bitmap_imafeStudent);
                            isImgSelect = true;
                        } catch (IOException e){
                            e.printStackTrace();
                            isImgSelect = false;
                        }
                    }
                });
         return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnAdd.setOnClickListener( v2 ->{

            String nameSur = binding.nameSurForm.getText().toString();
            String num = binding.numForm.getText().toString();

            if(nameSur.isEmpty() || nameSur.isEmpty()){
                Toast.makeText(requireActivity(), "Заполните поле контакты", Toast.LENGTH_SHORT).show();
                isImgSelect = false;
            }else{
                if(isImgSelect){

                        ByteArrayOutputStream baos_imageStudent = new ByteArrayOutputStream();
                        bitmap_imafeStudent.compress(Bitmap.CompressFormat.PNG, 100, baos_imageStudent);

                        byte[] imgStudent = baos_imageStudent.toByteArray();

                        Student student = new Student(nameSur, num, imgStudent);

//                    this.appDb = Room.databaseBuilder(binding.getRoot().getContext(), AppDb.class, "database")
//                            .fallbackToDestructiveMigration()
//                            .allowMainThreadQueries()
//                            .build();
                    try {

                        this.appDb = Room.databaseBuilder(binding.getRoot().getContext(), AppDb.class, "database")
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries().build();

                    } catch (Exception e) {
                        Toast.makeText(requireActivity(), "afro фото", Toast.LENGTH_SHORT).show();
                    }
                        studentDao = appDb.studentDao();


                        studentDao.insert(student);

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
                        navController.navigate(R.id.action_navigation_home_to_navigation_dashboard);


                }else{
                    Toast.makeText(requireActivity(), "Загрузите фото", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}