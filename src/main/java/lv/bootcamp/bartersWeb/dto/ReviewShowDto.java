package lv.bootcamp.bartersWeb.dto;

import lombok.Data;

@Data
public class ReviewShowDto {
    private Long id;
    private String reviewer;
    private String reviewed;
    private String grade;
    private String comment;
}
