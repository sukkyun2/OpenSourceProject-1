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

/**
 * 프로그램명 : PostItemView
 * 작성자 : 홍석균
 * 작성일 : 2020.06.12
 * 프로그램 설명 :
 * 커뮤니티 기능(ComunityActivity)에서 커스텀 ListView에 적용될 운동기록들의 항목을 만드는 클래스입니다.
 * 사용자의 이름, 사용자가 운동한 시간, 사용자의 프로필 사진, 메모가 포함되어있습니다.
 **/

public class PostItemView extends LinearLayout {

    /*
     * post_item.xml을 Inflation하기 위해서 사용되는 클래스
     */

    TextView name;  // 사용자의 이름
    TextView date; // 사용자가 운동한 시간
    ImageView profile; // 사용자 프로필 사진 모두 동일하게 생성
    TextView comment;

    public PostItemView(Context context) {
        super(context);
        init(context);
    }

    public PostItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.post_item, this, true);

        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        profile = findViewById(R.id.profile);
        comment = findViewById(R.id.comment);

    }

    public void setName(String name1) {
        name.setText(name1);
    }

    public void setDate(String date1) { date.setText(date1); }

    public void setProfile(int id) {
        profile.setImageResource(id);
    }

    public void setComment(String comment1){comment.setText(comment1);}


}