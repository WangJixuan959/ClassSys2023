package com.example.mapper;

import com.example.entity.CourseVideoInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseVideoMapper {
    void newCourseVideo(CourseVideoInfo courseVideoInfo);

    List<CourseVideoInfo> selectAll();
}
