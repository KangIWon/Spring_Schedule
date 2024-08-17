package com.sparta.spring_schedule.service;

import com.sparta.spring_schedule.dto.ScheduleRequestDto;
import com.sparta.spring_schedule.dto.ScheduleResponseDto;
import com.sparta.spring_schedule.entity.Schedule;
import com.sparta.spring_schedule.repository.ScheduleRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ScheduleService {

    // 클래스 속성
    private final JdbcTemplate jdbcTemplate;

    // 클래스 생성자
    public ScheduleService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        // ResponseDto -> Entity
        Schedule schedule = new Schedule(requestDto);
        // DB 저장
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        Schedule saveSchedule = scheduleRepository.save(schedule);

        // Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    public Optional<Schedule> getSchedule(Long id) {
        // DB 조회
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        try {
            Optional<Schedule> schedule = scheduleRepository.find(id);
            return schedule;
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<ScheduleResponseDto> getSchedulelist() {
        // DB 조회
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        return scheduleRepository.findAll();
    }

    public Long updateSchedule(Long id, ScheduleRequestDto requestDto) {
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        // 해당 일정이 DB에 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(id);
        if(schedule != null) {
            // schedule 내용 수정
            scheduleRepository.update(id, requestDto);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다.");
        }
    }

    public Long deleteSchedule(Long id) {
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
}
