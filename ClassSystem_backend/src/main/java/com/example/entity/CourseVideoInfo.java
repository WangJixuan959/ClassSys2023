package com.example.entity;

import lombok.Data;

@Data
public class CourseVideoInfo {
    private int id;
    private String course_name;
    private String teacher_name;
    private String classroom_id;
    private String date1;
    private String date2;
    private String video_path;
    private int status;
    private String desc;
}
