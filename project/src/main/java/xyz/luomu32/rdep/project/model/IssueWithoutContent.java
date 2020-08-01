package xyz.luomu32.rdep.project.model;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class IssueWithoutContent {

    private Long id;

    private Long projectId;

    private Long moduleId;

    private String title;

    private IssueStatus status;

    private Integer level;

    private String tags;

    private Long processingPersonId;

    private String processingPersonName;

    private LocalDateTime processingDate;

    private Integer processingDuration;

    private LocalDateTime createDatetime;


    private Long createBy;


    private LocalDateTime lastUpdateDatetime;


    private Long lastUpdateBy;
}
