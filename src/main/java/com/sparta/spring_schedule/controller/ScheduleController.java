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
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findById(id);
        if(schedule != null) {
            // schedule 내용 수정
            String sql = "UPDATE memo SET username = ?, contents = ? WHERE id = ?";
            jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getDate(), requestDto.getTime(),
                    requestDto.getName(), requestDto.getPw(), requestDto.getC_m_date(), id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }
    // delete
    // 삭제
    @DeleteMapping("/{id}")
    public Long deleteSchedule(@PathVariable Long id) {
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = findById(id);
        if(schedule != null) {
            // schedule 삭제
            String sql = "DELETE FROM schedule WHERE id = ?";
            jdbcTemplate.update(sql, id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }
    private Schedule findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM schedule WHERE id = ?";
        LocalDate now = LocalDate.now();
        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setTitle(resultSet.getString("title"));
                schedule.setDate(resultSet.getString("date"));
                schedule.setTime(resultSet.getString("time"));
                schedule.setName(resultSet.getString("name"));
                schedule.setPw(resultSet.getString("pw"));
                schedule.setC_m_date(String.valueOf(now));
                return schedule;
            } else {
                return null;
            }
        }, id);
    }
}