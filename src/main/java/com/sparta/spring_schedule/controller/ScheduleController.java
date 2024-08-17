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
        // DB 조회
        String sql = "SELECT * FROM schedule WHERE id = ?";
        try {
            Schedule schedule = jdbcTemplate.queryForObject(sql, scheduleRowMapper(), id);
            return Optional.of(schedule);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

//        return jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?");
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return (((rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setTitle(rs.getString("title"));
            schedule.setDate(rs.getString("date"));
            schedule.setTime(rs.getString("time"));
            schedule.setName(rs.getString("name"));
            schedule.setPw(rs.getString("pw"));
            schedule.setC_m_date(rs.getString("c_m_date"));
            return schedule;
        } ));
    }

    // 목록 조회
    @GetMapping()
    public List<ScheduleResponseDto> getSchedulelist() {
        // DB 조회
        String sql = "SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Schedule 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String name = rs.getString("name");
                String pw = rs.getString("pw");
                String c_m_date = rs.getString("c_m_date");
                return new ScheduleResponseDto(id, title, date, time, name, pw, c_m_date);
            }
        });
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