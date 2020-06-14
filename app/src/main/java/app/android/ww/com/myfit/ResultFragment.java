package app.android.ww.com.myfit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {
    ExerciseRecord exerciseRecord;

    TextView tvExerciseTime;
    TextView tvExerciseDistance;
    TextView tvExerciseCalorie;
    TextView tvExerciseStep;
    TextView tvExerciseDate;

    ImageView imageView;

    StorageReference storageRef;


    public ResultFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result, container, false);

            exerciseRecord = LoginUtil.RECORD;
            tvExerciseTime = v.findViewById(R.id.tvExerciseTime);
            tvExerciseDistance = v.findViewById(R.id.tvExerciseDistance);
            tvExerciseCalorie = v.findViewById(R.id.tvExerciseCalorie);
            tvExerciseStep = v.findViewById(R.id.tvExerciseStep);
            tvExerciseDate = v.findViewById(R.id.tvExerciseDate);

            System.out.println("운동시간 :" + exerciseRecord.exerciseTime);
            System.out.println("운동거리 :" + exerciseRecord.execiseDistance);
            System.out.println("소모칼로리 :" + exerciseRecord.exerciseCalorie);
            System.out.println("걸음수 :" + exerciseRecord.exerciseStep);
            System.out.println("운동날짜 :" + exerciseRecord.exerciseDate);

            tvExerciseTime.setText("운동 시간 : " +
                    String.format("%d", exerciseRecord.exerciseTime));
            tvExerciseDistance.setText("운동 거리 : " +
                    String.format("%.2f", exerciseRecord.execiseDistance));
            tvExerciseCalorie.setText("소모 칼로리 : " +
                    String.format("%d", exerciseRecord.exerciseCalorie));
            tvExerciseStep.setText("걸음수 :" +
                    String.format("%d", exerciseRecord.exerciseStep));
            tvExerciseDate.setText("운동날짜 : " +
                    exerciseRecord.exerciseDate);
//        roadImage();

            return v;
        }
    }
//    public void roadImage(){
//
//        try {
//            // Storage 에서 다운받아 저장시킬 임시파일
//            final File imageFile = File.createTempFile("images", "png");
//            storageRef.getFile(imageFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    // Success Case
//                    Bitmap bitmapImage = BitmapFactory.decodeFile(imageFile.getPath());
//                    imageView.setImageBitmap(bitmapImage);
//                    Toast.makeText(getActivity().getApplicationContext(), "Success !!", Toast.LENGTH_LONG).show();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(getActivity().getApplicationContext(), "Fail !!", Toast.LENGTH_LONG).show();
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
