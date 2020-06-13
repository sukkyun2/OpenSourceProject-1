package app.android.ww.com.myfit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
import java.sql.Ref;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class RecordFragment extends Fragment
{
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
    EditText comment;

    public RecordFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        firebaseDatabase=FirebaseDatabase.getInstance();
        mDatabase=firebaseDatabase.getReference();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_record, container, false);

        exerciseRecord=LoginUtil.RECORD;

        imageView=v.findViewById(R.id.imageView);

        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");

        Bitmap bitMap=BitmapFactory.decodeFile("/sdcard/"+LoginUtil.USERID+"_"+format.format(now)+".png");
        imageView.setImageBitmap(bitMap);

        comment=v.findViewById(R.id.comment);
        submitButton=v.findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
//                submitImage();
            }
        });
        return v;
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

    private void submitPost() {
//        LoginUtil.USERID="fftzwhv"; //임시로 셋팅

        final String userId = LoginUtil.USERID;

        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        writeNewRecord(userId);

                        Toast.makeText(getActivity().getApplicationContext(),"기록이 완료되었습니다", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
    }

    private void writeNewRecord(String userId) {

        String key = mDatabase.child("posts").push().getKey();
        Map <String, Object> recordValues=convertRecordToMap();
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
        resultMap.put("exerciseComment",comment.getText().toString());

        return resultMap;
    }
}
