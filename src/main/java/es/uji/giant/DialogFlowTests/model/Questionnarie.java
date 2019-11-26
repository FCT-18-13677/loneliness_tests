package es.uji.giant.DialogFlowTests.model;

import java.util.ArrayList;
import java.util.List;

public class Questionnarie {
    private String id;
    private String city;
    private String sex;
    private int age;
    private boolean liveAlone;
    private long timestamp;
    private String userComments;
    private int uclaScore;
    private int jongScore;
    private String diagnosis;
    private List<String> answers;

    public Questionnarie() {
        answers = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getUclaScore() {
        return uclaScore;
    }

    public void setUclaScore(int uclaScore) {
        this.uclaScore = uclaScore;
    }

    public int getJongScore() {
        return jongScore;
    }

    public void setJongScore(int jongScore) {
        this.jongScore = jongScore;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Questionnarie{" +
                "id='" + id + '\'' +
                "city='" + city + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", liveAlone=" + liveAlone +
                ", timestamp=" + timestamp +
                ", userComments='" + userComments + '\'' +
                ", uclaScore=" + uclaScore +
                ", jongScore=" + jongScore +
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

        jongScore = score;
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

        uclaScore = score;
    }

    public void createDiagnosis() {
        boolean lonelinessUCLA = false;

        if (uclaScore >= 6) lonelinessUCLA = true;
        diagnosis = "Jong -> " + jongScore + ", UCLA -> " + uclaScore + " Soledad=" + lonelinessUCLA;
    }

}
