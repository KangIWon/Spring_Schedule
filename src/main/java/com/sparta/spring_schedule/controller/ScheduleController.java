package com.sparta.spring_schedule.controller;

import com.sparta.spring_schedule.dto.ScheduleRequestDto;
import com.sparta.spring_schedule.dto.ScheduleResponseDto;
import com.sparta.spring_schedule.entity.Schedule;
import com.sparta.spring_schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
일정 생성하기 API를 받을 수 있는 controller와 메서드 생성
 */
@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {

    private final ScheduleService scheduleService;

    // @RequiredArgsConstructor을 써주면 아래 코드 생략 가능
//    public ScheduleController(ScheduleService scheduleService) {
//        this.scheduleService = scheduleService;
//    }

//    // 클래스 속성
//    private final JdbcTemplate jdbcTemplate;
//
//    // 클래스 생성자
//    public ScheduleController(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    // post
    // 일정 등록
    // DB에 저장은 잘 되는 것 같은데 postman에서는 몇몇 값이 null값으로 표시가 됨
    @PostMapping()
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
//        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.createSchedule(requestDto);
    }

    // get
    // 단 건 조회
    // 안되는 거 확인
    @GetMapping("/{id}")
    public Optional<Schedule> getSchedule(Long id) {
//        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getSchedule(id);
    }

    // 목록 조회
    @GetMapping()
    public List<ScheduleResponseDto> getSchedulelist() {
//        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getSchedulelist();
    }
    // put
    // 수정
    //안되는 거 확인
    @PutMapping("/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
//        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        log.info("id값{}", id);
        return scheduleService.updateSchedule(id, requestDto);
    }
    // delete
    // 삭제
    @DeleteMapping("/{id}")
    public Long deleteSchedule(@PathVariable Long id) {
//        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.deleteSchedule(id);
    }
}