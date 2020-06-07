package app.android.ww.com.myfit;

import java.io.Serializable;
import java.util.Date;

public class ExerciseRecord implements Serializable {

    long exerciseTime; //운동 시간
    double execiseDistance; //운동 거리
    int exerciseCalorie; //소모 칼로리
    int exerciseStep; //걸음수
    Date exerciseDate; //운동날짜
    //이미지

    public ExerciseRecord(long exerciseTime, double execiseDistance, int exerciseCalorie, int exerciseStep) {
        this.exerciseTime = exerciseTime;
        this.execiseDistance = execiseDistance;
        this.exerciseCalorie = exerciseCalorie;
        this.exerciseStep = exerciseStep;
        this.exerciseDate = new Date();
    }

    public long getExerciseTime() {
        return exerciseTime;
    }

    public void setExerciseTime(long exerciseTime) {
        this.exerciseTime = exerciseTime;
    }

    public double getExeciseDistance() {
        return execiseDistance;
    }

    public void setExeciseDistance(double execiseDistance) {
        this.execiseDistance = execiseDistance;
    }

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

    public Date getExerciseDate() {
        return exerciseDate;
    }

    public void setExerciseDate(Date exerciseDate) {
        this.exerciseDate = exerciseDate;
    }
}
