package bitc.fullstack503.ordernetserver.dto;

import lombok.Data;

@Data
public class NoticeDTO {

    // 공지 번호
    private int noticeId;

    // 공지 제목
    private String noticeTitle;

    // 공지 내용
    private String noticeContent;

    // 공지 작성일
    private String noticeWriteDate;

    // 공지 수정일
    private String noticeUpdateDate;

    // 공지 작성자
    private String userId;

}
