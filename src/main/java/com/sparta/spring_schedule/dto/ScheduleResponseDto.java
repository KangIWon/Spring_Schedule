package com.sparta.spring_schedule.dto;

import com.sparta.spring_schedule.entity.Schedule;
import lombok.Getter;
/*
client에 데이터를 반환할 때 사용할 ScheduleResponseDto 클래스 생성
 */
@Getter
public class ScheduleResponseDto {
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

    public ScheduleResponseDto(Schedule schedule) {
        this.title = schedule.getTitle();
        this.date = schedule.getDate();
        this.time = schedule.getTime();
        this.name = schedule.getName();
        this.pw = schedule.getPw();
        this.c_m_date = schedule.getC_m_date();
//        this.id = schedule.getId();
    }

    public ScheduleResponseDto(Long id, String title, String date, String time, String name, String pw, String c_m_date) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.name = name;
        this.pw = pw;
        this.c_m_date = c_m_date;
        this.id = id;
    }
}
