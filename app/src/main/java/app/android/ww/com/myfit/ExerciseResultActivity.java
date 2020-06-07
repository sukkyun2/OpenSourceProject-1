package app.android.ww.com.myfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExerciseResultActivity extends AppCompatActivity {


    ViewPager vp;

    //FireBase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    Fragment resultFragment;
    Fragment recordFragment;

    ExerciseRecord exerciseRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_result);

//        firebaseDatabase=FirebaseDatabase.getInstance();
//        databaseReference=firebaseDatabase.getReference();

//        Intent intent=getIntent();
//        exerciseRecord=(ExerciseRecord)intent.getSerializableExtra("RECORD");
//
//        recordFragment=new RecordFragment();
//        resultFragment=new ResultFragment();
//
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("RECORD",exerciseRecord);
//
//        resultFragment.setArguments(bundle);
//        recordFragment.setArguments(bundle);

        vp=findViewById(R.id.vp);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);

//        insertExerciseRecord(exerciseRecord);

    }

    private class pagerAdapter extends FragmentStatePagerAdapter{

        private int NUM_ITEMS=2;

        public pagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new ResultFragment();
                case 1:
                    return new RecordFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }


}
