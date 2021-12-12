package com.yosef.yosefproject1011;
public class Question {
    private String Question, NumberOfQuestion, Option1, Option2, Option3, Option4, Photo;
    private int Points;

    public Question(String Question, String NumberOfQuestion, int Points, String Option1, String Option2, String Option3, String Option4, String Photo) {
        this.Question = Question;
        this.NumberOfQuestion = NumberOfQuestion;
        this.Points = Points;
        this.Option1 = Option1;
        this.Option2 = Option2;
        this.Option3 = Option3;
        this.Option4 = Option4;
        this.Photo = Photo;
    }
    @Override
    public String toString() {
        return "Question{" +
                "Question='" + Question + '\'' +
                ", NumberOfQuestion='" + NumberOfQuestion + '\'' +
                ", Option1='" + Option1 + '\'' +
                ", Option2='" + Option2 + '\'' +
                ", Option3='" + Option3 + '\'' +
                ", Option4='" + Option4 + '\'' +
                ", Points=" + Points +
                '}';
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getNumberOfQuestion() {
        return NumberOfQuestion;
    }

    public void setNumberOfQuestion(String numberOfQuestion) {
        NumberOfQuestion = numberOfQuestion;
    }

    public String getOption1() {
        return Option1;
    }

    public void setOption1(String option1) {
        Option1 = option1;
    }

    public String getOption2() {
        return Option2;
    }

    public void setOption2(String option2) {
        Option2 = option2;
    }

    public String getOption3() {
        return Option3;
    }

    public void setOption3(String option3) {
        Option3 = option3;
    }

    public String getOption4() {
        return Option4;
    }

    public void setOption4(String option4) {
        Option4 = option4;
    }

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }

    public String getPhoto() { return Photo; }

    public void setPhoto(String photo) { Photo = photo; }
}
