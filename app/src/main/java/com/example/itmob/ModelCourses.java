package com.example.itmob;


import java.io.Serializable;

public class ModelCourses implements Serializable {


    String id , name , trainer ,date , startTime , timeDuration ;


    public ModelCourses() {
    }

    public ModelCourses(String id, String name, String trainer, String date, String startTime, String timeDuration) {
        this.id = id;
        this.name = name;
        this.trainer = trainer;
        this.date = date;
        this.startTime = startTime;
        this.timeDuration = timeDuration;
    }

    public ModelCourses(String name, String trainer, String date, String startTime, String timeDuration) {
        this.name = name;
        this.trainer = trainer;
        this.date = date;
        this.startTime = startTime;
        this.timeDuration = timeDuration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        this.timeDuration = timeDuration;
    }
}
