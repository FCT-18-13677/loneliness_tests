package es.uji.giant.DialogFlowTests.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Test {
    private String sex;
    private int age;
    private boolean liveAlone;
    private long timestamp;
    private String userComments;
    private int UCLAscore;
    private int JongScore;
    private String diagnosis;
    private List<String> answers;

    public Test() {
        answers = new ArrayList<>();
    }

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


    public void addAnswer(String answer) {
        answers.add(answer);
    }

    public List<String> getAnswers() {
        return answers;
    }

    public boolean isLiveAlone() {
        return liveAlone;
    }

    public int getUCLAscore() {
        return UCLAscore;
    }

    public void setUCLAscore(int UCLAscore) {
        this.UCLAscore = UCLAscore;
    }

    public int getJongScore() {
        return JongScore;
    }

    public void setJongScore(int jongScore) {
        JongScore = jongScore;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getUserComments() {
        return userComments;
    }

    public void setUserComments(String userComments) {
        this.userComments = userComments;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String string) {
        this.diagnosis = string;
    }

    @Override
    public String toString() {
        return "Test{" +
                "sex='" + sex + '\'' +
                ", age=" + age +
                ", liveAlone=" + liveAlone +
                ", timestamp=" + timestamp +
                ", userComments='" + userComments + '\'' +
                ", UCLAscore=" + UCLAscore +
                ", JongScore=" + JongScore +
                ", diagnosis=" + diagnosis +
                ", answers=" + answers +
                '}';
    }

    public void calculateJongScore() {
        int score = 0;
        List<String> first3items = answers.subList(0, 3);
        List<String> next3items = answers.subList(3, 6);

        for (String answer : first3items) {
            if (answer.equals("si") || answer.equals("sí") || answer.equals("mas o menos") || answer.equals("más o menos")) score += 1;
        }

        for (String answer : next3items) {
            if (answer.equals("no") || answer.equals("mas o menos") || answer.equals("más o menos")) score += 1;
        }

        JongScore = score;
    }

    public void calculateUCLAScore() {
        int score = 0;
        List<String> last3items = answers.subList(6, 9);

        for (String answer : last3items) {
            switch (answer) {
                case "casi nunca":      score += 1;
                                        break;

                case "algunas veces":   score += 2;
                                        break;

                case "a menudo":        score += 3;
                                        break;
            }
        }

        UCLAscore = score;
    }

    public void createDiagnosis() {
        boolean lonelinessUCLA = false;

        if (UCLAscore >= 6) lonelinessUCLA = true;
        diagnosis = "Jong -> " + JongScore + ", UCLA -> Soledad=" + lonelinessUCLA;
    }

}
