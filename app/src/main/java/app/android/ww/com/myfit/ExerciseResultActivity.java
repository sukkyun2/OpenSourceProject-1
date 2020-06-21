package app.android.ww.com.myfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
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

public class ExerciseResultActivity extends AppCompatActivity {

    //firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;

    //firebase storage
    private StorageReference storageRef;

    //map image
    ImageView imageView;

    //Record
    private ExerciseRecord exerciseRecord;

    Button submitButton;

    TextView tvExerciseTime;
    TextView tvExerciseDistance;
    TextView tvExerciseCalorie;
    TextView tvExerciseStep;
    TextView tvExerciseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_result);


        connect_db();
        showRecord();
        setImageView();
        submitPost();

//        submitButton=findViewById(R.id.submit_button);
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                submitPost();
////                submitImage();
//            }
//        });

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

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    private void submitImage() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        Uri file = Uri.fromFile(new File("/sdcard/"+LoginUtil.USERID+"_"+format.format(now)+".png"));
        StorageReference mapRef = storageRef.child(file.getLastPathSegment());
        System.out.println("/images"+file.getLastPathSegment());
        UploadTask uploadTask = mapRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                System.out.println("업로드 실패");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("업로드 성공");
            }
        });
    }

    private Bitmap imgRotate(Bitmap bmp){
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
        bmp.recycle();

        return resizedBitmap;
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
//        LoginUtil.USERID="fftzwhv"; //임시로 셋팅

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

        childUpdates.put("/posts/" + userId + "/" + key, recordValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, recordValues);

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
//        resultMap.put("exerciseComment",comment.getText().toString());

        return resultMap;
    }

}