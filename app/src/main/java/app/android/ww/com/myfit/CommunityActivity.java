package app.android.ww.com.myfit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CommunityActivity extends AppCompatActivity {

    private ListView listView;
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        System.out.println("시스템 시작");

        listView = findViewById(R.id.listView);

        postAdapter = new PostAdapter();

//        downloadImg();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExerciseRecord exerciseRecord = (ExerciseRecord) postAdapter.getItem(position);

                Intent intent=new Intent(getApplicationContext(),PostDetail.class);
                intent.putExtra("RECORD",exerciseRecord);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                postAdapter.items.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    ExerciseRecord get = postSnapshot.getValue(ExerciseRecord.class);

                    System.out.println("사용자 이름 :"+get.userId);
                    System.out.println("운동시간 :"+get.exerciseTime);
                    System.out.println("운동거리 :"+get.execiseDistance);
                    System.out.println("소모칼로리 :"+get.exerciseCalorie);
                    System.out.println("걸음수 :"+get.exerciseStep);
                    System.out.println("운동날짜 :"+get.exerciseDate);
//                    System.out.println("코멘트 :"+get.exerciseComment);


                    postAdapter.addItem(get);

                }

                listView.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("getFirebaseDatabase","loadPost:onCancelled", databaseError.toException());
            }
        };

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("posts").child("hong");
        database.addValueEventListener(postListener);
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

    class PostAdapter extends BaseAdapter {

        /*
         * ListView에 넣어줄 Adapter를 선언
         * Adapter는 4가지 메소드를 오버라이딩해야한다.
         */

        ArrayList<ExerciseRecord> items=new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ExerciseRecord item){
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /*
             * PersonItemView라는 개별 리스트 객체를 선언하여
             * VO에 있는 값으로 set해주고 PersonItemView를 리턴한다.
             */

            PostItemView view=new PostItemView(getApplicationContext());

            ExerciseRecord item=items.get(position);

            view.setProfile(R.drawable.baseline_perm_identity_black_18dp);
            view.setDate(item.getExerciseDate());
            view.setName(item.getUserId());

            return view;
        }
    }

}

