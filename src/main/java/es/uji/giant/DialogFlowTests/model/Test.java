package es.uji.giant.DialogFlowTests.model;

import java.util.Map;

public class Test {
    private String sex;
    private int age;
    private boolean liveAlone;
    private long timestamp;
    private Map<String, String> questionsAndAnswers;
    private String userComments;
    private int totalScore;
    private String diagnosis;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isLivingAlone() {
        return liveAlone;
    }

    public void setLiveAlone(boolean liveAlone) {
        this.liveAlone = liveAlone;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getQuestionsAndAnswers() {
        return questionsAndAnswers;
    }

    public void setQuestionsAndAnswers(Map<String, String> questionsAndAnswers) {
        this.questionsAndAnswers = questionsAndAnswers;
    }

    public String getUserComments() {
        return userComments;
    }

    public void setUserComments(String userComments) {
        this.userComments = userComments;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public String toString() {
        return "Test{" +
                "sex=" + sex +
                ", age=" + age +
                ", liveAlone=" + liveAlone +
                ", timestamp=" + timestamp +
                ", questionsAndAnswers=" + questionsAndAnswers +
                '}';
    }
}
