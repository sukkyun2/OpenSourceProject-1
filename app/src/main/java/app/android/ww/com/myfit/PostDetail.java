package app.android.ww.com.myfit;

import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;

public class PostDetail extends AppCompatActivity {

    ExerciseRecord exerciseRecord;

    TextView tvId;
    TextView tvExerciseTime;
    TextView tvExerciseDistance;
    TextView tvExerciseCalorie;
    TextView tvExerciseStep;
    TextView tvExerciseDate;

    final ImageView imageView = findViewById(R.id.imageView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        showRecord();
        setImageView();

    }

    private void showRecord() {
        exerciseRecord = (ExerciseRecord) getIntent().getSerializableExtra("RECORD");
        tvId=findViewById(R.id.tvId);
        tvExerciseTime = findViewById(R.id.tvExerciseTime);
        tvExerciseDistance = findViewById(R.id.tvExerciseDistance);
        tvExerciseCalorie = findViewById(R.id.tvExerciseCalorie);
        tvExerciseStep = findViewById(R.id.tvExerciseStep);
        tvExerciseDate = findViewById(R.id.tvExerciseDate);

        System.out.println("운동시간 :" + exerciseRecord.exerciseTime);
        System.out.println("운동거리 :" + exerciseRecord.execiseDistance);
        System.out.println("소모칼로리 :" + exerciseRecord.exerciseCalorie);
        System.out.println("걸음수 :" + exerciseRecord.exerciseStep);
        System.out.println("운동날짜 :" + exerciseRecord.exerciseDate);

        tvId.setText(exerciseRecord.userId+"님의 운동기록");
        tvExerciseTime.setText(
                String.format("%d", exerciseRecord.exerciseTime));
        tvExerciseDistance.setText(
                String.format("%.2f", exerciseRecord.execiseDistance));
        tvExerciseCalorie.setText(
                String.format("%d", exerciseRecord.exerciseCalorie));
        tvExerciseStep.setText(
                String.format("%d", exerciseRecord.exerciseStep));
        tvExerciseDate.setText(
                exerciseRecord.exerciseDate);
    }

    private void setImageView(){
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference().child(exerciseRecord.userId+"_"+exerciseRecord.exerciseDate+".png");
        System.out.println(exerciseRecord.userId+"_"+exerciseRecord.exerciseDate+".png");
        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // Glide
                    Glide.with(PostDetail.this)
                            .load(task.getResult())
                            .override(1000, 800)
                            .into(imageView);
                } else {
                    System.out.println("error!");
                }
            }
        });
    }
}
