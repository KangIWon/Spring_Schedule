package com.sparta.spring_schedule.controller;

import com.sparta.spring_schedule.dto.ScheduleRequestDto;
import com.sparta.spring_schedule.dto.ScheduleResponseDto;
import com.sparta.spring_schedule.entity.Schedule;
import com.sparta.spring_schedule.service.ScheduleService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

/*
일정 생성하기 API를 받을 수 있는 controller와 메서드 생성
 */
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    // 클래스 속성
    private final JdbcTemplate jdbcTemplate;

    // 클래스 생성자
    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // post
    // 일정 등록
    @PostMapping()
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.createSchedule(requestDto);
    }

    // get
    // 단 건 조회
    @GetMapping("/{id}")
    public Optional<Schedule> getSchedule(Long id) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getSchedule(id);
    }

    // 목록 조회
    @GetMapping()
    public List<ScheduleResponseDto> getSchedulelist() {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getSchedulelist();
    }
    // put
    // 수정
    @PutMapping("/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.updateSchedule(id, requestDto);
    }
    // delete
    // 삭제
    @DeleteMapping("/{id}")
    public Long deleteSchedule(@PathVariable Long id) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.deleteSchedule(id);
    }
}