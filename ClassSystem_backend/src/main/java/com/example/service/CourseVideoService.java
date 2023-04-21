package com.example.service;

import com.example.entity.CourseVideoInfo;
import com.example.mapper.CourseVideoMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseVideoService {

    @Resource
    CourseVideoMapper courseVideoMapper;

    public List<CourseVideoInfo> selectAll() {
        return courseVideoMapper.selectAll();
    }
}
