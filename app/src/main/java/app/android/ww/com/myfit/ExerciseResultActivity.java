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

    Fragment resultFragment;
    Fragment recordFragment;

    ExerciseRecord exerciseRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_result);

        Intent intent=getIntent();
        exerciseRecord=(ExerciseRecord)intent.getSerializableExtra("RECORD");

        Bundle bundle1=new Bundle();
        bundle1.putSerializable("RECORD",exerciseRecord);

        Bundle bundle2=new Bundle();
        bundle2.putSerializable("RECORD",exerciseRecord);

        recordFragment=new RecordFragment();
        resultFragment=new ResultFragment();

        resultFragment.setArguments(bundle1);
        recordFragment.setArguments(bundle2);

        vp=findViewById(R.id.vp);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);

    }

    //View Pager에 관련된 Adapter
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
