package com.sparta.spring_schedule.repository;

import com.sparta.spring_schedule.dto.ScheduleRequestDto;
import com.sparta.spring_schedule.dto.ScheduleResponseDto;
import com.sparta.spring_schedule.entity.Schedule;
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

public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule save(Schedule schedule) {
        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        LocalDate now = LocalDate.now();
        String sql = "INSERT INTO schedule (title, date, time, name, pw, c_m_date) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getTitle());
                    preparedStatement.setString(2, schedule.getDate());
                    preparedStatement.setString(3, schedule.getTime());
                    preparedStatement.setString(4, schedule.getName());
                    preparedStatement.setString(5, schedule.getPw());
                    preparedStatement.setString(6, String.valueOf(now));
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        return schedule;
    }

    public Optional<Schedule> find(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";

        Schedule schedule = jdbcTemplate.queryForObject(sql, scheduleRowMapper(), id);
        return Optional.of(schedule);

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

    public List<ScheduleResponseDto> findAll() {
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

    public void update(Long id, ScheduleRequestDto requestDto) {
        String sql = "UPDATE memo SET username = ?, contents = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getDate(), requestDto.getTime(),
                requestDto.getName(), requestDto.getPw(), requestDto.getC_m_date(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Schedule findById(Long id) {
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