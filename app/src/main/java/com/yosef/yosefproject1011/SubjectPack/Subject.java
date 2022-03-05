package com.yosef.yosefproject1011.SubjectPack;

public class Subject {
    private String Subject, Info, Photo;

    public Subject(String Subject, String Info, String Photo) {
        this.Subject = Subject;
        this.Info = Info;
        this.Photo = Photo;
    }
    @Override
    public String toString() {
        return "Subject{" +
                "Subject='" + Subject + '\'' +
                ", Info='" + Info + '\'' +
                '}';
    }

    public String getSubject() { return Subject; }

    public void setSubject(String subject) { Subject = subject; }

    public String getInfo() { return Info; }

    public void setInfo(String info) { Info = info; }

    public String getPhoto() { return Photo; }

    public void setPhoto(String photo) { Photo = photo; }
}
