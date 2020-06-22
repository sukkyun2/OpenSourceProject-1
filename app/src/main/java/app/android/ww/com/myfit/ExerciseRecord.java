package app.android.ww.com.myfit;

import java.io.Serializable;

/**
 * 프로그램명 : ExerciseRecord
 * 작성자 : 홍석균
 * 작성일 : 2020.06.09
 * 프로그램 설명 : 운동하기(ExerciseActivity)에서 운동기록을 저장하는데 사용되는 VO(Value Object)입니다.
 **/
public class ExerciseRecord implements Serializable {

    String userId; //운동한 사람
    long exerciseTime; //운동 시간
    double execiseDistance; //운동 거리
    int exerciseCalorie; //소모 칼로리
    int exerciseStep; //걸음수
    String exerciseDate; //운동날짜
    String exerciseComment; //메모

    public ExerciseRecord(){}

    public ExerciseRecord(String userid, long exerciseTime, double execiseDistance, int exerciseCalorie, int exerciseStep, String exerciseDate) {
        this.userId = userid;
        this.exerciseTime = exerciseTime;
        this.execiseDistance = execiseDistance;
        this.exerciseCalorie = exerciseCalorie;
        this.exerciseStep = exerciseStep;
        this.exerciseDate = exerciseDate;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getExerciseComment() { return exerciseComment; }

    public void setExerciseComment(String exerciseComment) { this.exerciseComment = exerciseComment; }

    public long getExerciseTime() {
        return exerciseTime;
    }

    public void setExerciseTime(long exerciseTime) {
        this.exerciseTime = exerciseTime;
    }

    public double getExeciseDistance() {
        return execiseDistance;
    }

    public void setExeciseDistance(double execiseDistance) { this.execiseDistance = execiseDistance; }

    public int getExerciseCalorie() {
        return exerciseCalorie;
    }

    public void setExerciseCalorie(int exerciseCalorie) {
        this.exerciseCalorie = exerciseCalorie;
    }

    public int getExerciseStep() {
        return exerciseStep;
    }

    public void setExerciseStep(int exerciseStep) {
        this.exerciseStep = exerciseStep;
    }

    public String getExerciseDate() {
        return exerciseDate;
    }

    public void setExerciseDate(String exerciseDate) {
        this.exerciseDate = exerciseDate;
    }
}
