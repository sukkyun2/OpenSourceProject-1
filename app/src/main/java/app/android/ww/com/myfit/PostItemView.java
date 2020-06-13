package app.android.ww.com.myfit;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;

public class PostItemView extends LinearLayout {

    /*
     * post_item.xml을 Inflation하기 위해서 사용되는 클래스
     */

    TextView name;  // 사용자의 이름
    TextView date; // 사용자가 운동한 시간
    ImageView profile; // 사용자 프로필 사진 모두 동일하게 생성

    public PostItemView(Context context) {
        super(context);
        init(context);
    }

    public PostItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        /*
         * xml파일을 메모리에 객체로 만드는 과정
         * person_item.xml에 있는 구성요소들을
         * 이 객체(PersonItemView)에 inflation 시킨다.
         */

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.post_item, this, true);

        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        profile = findViewById(R.id.profile);

    }

    public void setName(String name1) {
        name.setText(name1);
    }

    public void setDate(Date date1) {
        String time = (new SimpleDateFormat("hh:mm:ss a").format(date1));
        date.setText(time);
    }

    public void setProfile(int id) {
        profile.setImageResource(id);
    }


}