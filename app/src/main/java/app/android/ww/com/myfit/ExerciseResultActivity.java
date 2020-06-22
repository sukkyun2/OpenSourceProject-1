package app.android.ww.com.myfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 프로그램명 : ExerciseResultActivity
 * 작성자 : 홍석균
 * 작성일 : 2020.06.05
 * 프로그램 설명 :
 * 운동하기(ExerciseActivity)의 결과화면을 출력하는 화면입니다.
 * 메모를 입력받고 운동기록과 메모를 FireBase RealTime DataBase에 저장합니다.
 **/

public class ExerciseResultActivity extends AppCompatActivity {

    //firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;

    //map image
    ImageView imageView;

    //Record
    private ExerciseRecord exerciseRecord;

    TextView tvExerciseTime;
    TextView tvExerciseDistance;
    TextView tvExerciseCalorie;
    TextView tvExerciseStep;
    TextView tvExerciseDate;

    String comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_result);


        connect_db();
        showRecord();
        setImageView();
        writeComment();
//        submitPost();



    }

    private void setImageView(){
        imageView=findViewById(R.id.imageView);

        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");

        Bitmap bitMap=BitmapFactory.decodeFile("/sdcard/"+LoginUtil.USERID+"_"+format.format(now)+".png");
        System.out.println("bitMap.getWidth() :" + bitMap.getWidth() + "bitMap.getHeight() :"+bitMap.getHeight());

        imageView.setImageBitmap(imgResize(bitMap));
    }

    private void showRecord(){
        exerciseRecord = LoginUtil.RECORD;
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

    private void connect_db(){
        firebaseDatabase=FirebaseDatabase.getInstance();
        mDatabase=firebaseDatabase.getReference();
    }

    public Bitmap imgResize(Bitmap bitmap) {
        int x=360,y=440; //바꿀 이미지 사이즈
        Bitmap output = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawBitmap(bitmap, 0, 0, null);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Rect src = new Rect(0, 0, w, h);
        Rect dst = new Rect(0, 0, x, y);//이 크기로 변경됨
        canvas.drawBitmap(bitmap, src, dst, null);
        return output;
    }

    private void submitPost() {

        final String userId = LoginUtil.USERID;

        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        writeNewRecord(userId);

                        Toast.makeText(getApplicationContext(),"기록이 완료되었습니다", Toast.LENGTH_LONG).show();

//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(intent);
//                        finish();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
    }

    private void writeNewRecord(String userId) {

        String key = mDatabase.child("posts").push().getKey();
        Map<String, Object> recordValues=convertRecordToMap();
        Map<String, Object> childUpdates = new HashMap<>();

//        childUpdates.put("/posts/" + userId + "/" + key, recordValues);
        childUpdates.put("/posts/" + key, recordValues);

        mDatabase.updateChildren(childUpdates);
    }

    public Map convertRecordToMap(){
        Map<String,Object> resultMap=new HashMap<>();

        resultMap.put("userId",exerciseRecord.userId);
        resultMap.put("exerciseTime",exerciseRecord.exerciseTime);
        resultMap.put("exerciseDistance",exerciseRecord.execiseDistance);
        resultMap.put("exerciseCalorie",exerciseRecord.exerciseCalorie);
        resultMap.put("exerciseStep",exerciseRecord.exerciseStep);
        resultMap.put("exerciseDate",exerciseRecord.exerciseDate);
        resultMap.put("exerciseComment",comment);

        return resultMap;
    }

    private void writeComment(){

        AlertDialog.Builder ad = new AlertDialog.Builder(ExerciseResultActivity.this);

        ad.setTitle("메모를 입력해주세요");       // 제목 설정

        final EditText et = new EditText(ExerciseResultActivity.this);
        ad.setView(et);

        // 취소 버튼 설정
        ad.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // 확인 버튼 설정
        ad.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                comment = et.getText().toString();
                submitPost();

                dialog.dismiss();     //닫기
                // Event
            }
        });

        // 창 띄우기
        ad.show();
    }

}