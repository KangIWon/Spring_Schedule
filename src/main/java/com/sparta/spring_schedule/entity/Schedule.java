package com.sparta.spring_schedule.entity;

import com.sparta.spring_schedule.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/*
일정 데이터를 저장할 Schedule 클래스 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    // 할일 이름
    private String title;
    // 날짜
    private String date;
    // 시간
    private String time;
    // 담당자명
    private String name;
    // 비밀번호
    private String pw;
    // 작성/수정일
    private String c_m_date;
    //"id":"고유식별자 자동 생성 관리"
    private Long id;

    public Schedule(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.date = requestDto.getDate();
        this.time = requestDto.getTime();
        this.name = requestDto.getName();
        this.pw = requestDto.getPw();
        this.c_m_date = requestDto.getC_m_date();
        this.id = requestDto.getId();
    }

    public void update(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.date = requestDto.getDate();
        this.time = requestDto.getTime();
        this.name = requestDto.getName();
        this.pw = requestDto.getPw();
        this.c_m_date = requestDto.getC_m_date();
        this.id = requestDto.getId();
    }
}
