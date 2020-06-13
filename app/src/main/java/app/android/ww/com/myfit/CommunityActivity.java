package app.android.ww.com.myfit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class CommunityActivity extends AppCompatActivity {

    private ListView listView;
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        listView = findViewById(R.id.listView);

//        postAdapter = new PostAdapter();
//
//        listView.setAdapter(postAdapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Post post = (Post) postAdapter.getItem(position);
//                Toast.makeText(getApplicationContext(), "선택 : " + post.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.e("getFirebaseDatabase", "key: " + dataSnapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    ExerciseRecord get = postSnapshot.getValue(ExerciseRecord.class);

                    System.out.println("사용자 이름 :"+get.userId);
                    System.out.println("운동시간 :"+get.exerciseTime);
                    System.out.println("운동거리 :"+get.execiseDistance);
                    System.out.println("소모칼로리 :"+get.exerciseCalorie);
                    System.out.println("걸음수 :"+get.exerciseStep);
                    System.out.println("운동날짜 :"+get.exerciseDate);
                    System.out.println("코멘트 :"+get.exerciseComment);

//                    postAdapter.addItem(get);
//                    Log.d("getFirebaseDatabase", "key: " + key);
                }

                listView.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("getFirebaseDatabase","loadPost:onCancelled", databaseError.toException());
            }
        };

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("posts").child("fftzwhv");
        database.addValueEventListener(postListener);

//        String sort="Date";
//        Query sortbyTime = FirebaseDatabase.getInstance().getReference().child("posts").orderByChild(sort);
//        sortbyTime.addValueEventListener(postListener);
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

//            System.out.println(item.toString());

//            view.setName(item.getName());
//            view.setDate(item.getExerciseDate());
//            view.setProfile(item.getProfile());

            return view;
        }
    }

}

