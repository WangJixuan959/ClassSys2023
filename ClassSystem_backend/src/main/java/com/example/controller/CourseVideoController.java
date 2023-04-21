package com.example.controller;


import com.example.entity.CourseVideoInfo;
import com.example.entity.RestBean;
import com.example.mapper.CourseVideoMapper;
import com.example.service.CourseVideoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseVideoController {

    @Resource
    CourseVideoMapper courseVideoMapper;

    @Resource
    CourseVideoService courseVideoService;

    @PostMapping("/new")
    public RestBean<String> newCourseVideoInfo(@RequestParam("course_name") String course_name,
                                               @RequestParam("teacher_name") String teacher_name,
                                               @RequestParam("classroom_id") String classroom_id,
                                               @RequestParam("date1") String date1,
                                               @RequestParam("date2") String date2,
                                               @RequestParam("video_path") String video_path,
                                               @RequestParam("status") String status,
                                               @RequestParam("desc") String desc){
        CourseVideoInfo courseVideoInfo = new CourseVideoInfo();
        courseVideoInfo.setCourse_name(course_name);
        courseVideoInfo.setTeacher_name(teacher_name);
        courseVideoInfo.setClassroom_id(classroom_id);
        courseVideoInfo.setDate1(date1);
        courseVideoInfo.setDate2(date2);
        courseVideoInfo.setVideo_path(video_path);
        courseVideoInfo.setStatus(status2Int(status));
        courseVideoInfo.setDesc(desc);
        try {
            courseVideoMapper.newCourseVideo(courseVideoInfo);
            return RestBean.success("新建成功");
        } catch (Exception e) {
            return RestBean.failure(400, "数据库异常");
        }
    }

    private static int status2Int(String status){
        HashMap<Object,Integer> map = new HashMap<>();
        map.put("未分析", 0);
        map.put("正在分析", 1);
        map.put("已分析", 2);
        if(map.containsKey(status)){
            return map.get(status);
        }else{
            return map.get("其他");
        }
    }

    // 分页查询（暂时为全部查询，后续可添加条件）
    @GetMapping("/all")
    public RestBean<HashMap<String, Object>> findAll(){
        HashMap<String, Object> result = new HashMap<>();
        try {
            List<CourseVideoInfo> courseVideoInfos = courseVideoService.selectAll();
            List<Map> mapList = filedTransform(courseVideoInfos);
            result.put("status", 200);
            result.put("message", "状态查询成功");
            result.put("data", mapList);
            System.out.println(result);
            return RestBean.success(result);
        } catch (Exception e) {
            result.put("status", 1010);
            result.put("message", "状态查询失败");
            result.put("data", "");
            return RestBean.failure(400, result);
        }
    }

    // 数据库数据转化为前台可显示数据
    private List<Map> filedTransform(List<CourseVideoInfo> list){
        List<Map>  mapList = new ArrayList<>();
        for (CourseVideoInfo courseVideoInfo : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", courseVideoInfo.getId());
            map.put("course_name", courseVideoInfo.getCourse_name());
            map.put("teacher_name", courseVideoInfo.getTeacher_name());
            map.put("classroom_id", courseVideoInfo.getClassroom_id());
            map.put("date1", courseVideoInfo.getDate1());
            map.put("date2", courseVideoInfo.getDate2());
            map.put("video_path", courseVideoInfo.getVideo_path());
            map.put("status", int2Status(courseVideoInfo.getStatus()));
            map.put("desc", courseVideoInfo.getDesc());
            mapList.add(map);
        }
        return mapList;
    }

    public static String int2Status(int num){
        HashMap<Integer,String> map = new HashMap<>();
        map.put(0, "未分析");
        map.put(1, "正在分析");
        map.put(2, "已分析");
        map.put(-1, "其他");

        if(map.containsKey(num)){
            return map.get(num);
        }else{
            return map.get("其他");
        }
    }
}
