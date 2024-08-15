package com.sparta.spring_schedule.dto;

import lombok.Getter;
/*
client의 요청 데이터를 받아줄 ScheduleRequestDto 클래스 생성
 */
@Getter
public class ScheduleRequestDto {
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
}
