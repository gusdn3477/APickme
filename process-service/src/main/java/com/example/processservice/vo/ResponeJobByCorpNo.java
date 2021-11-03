package com.example.processservice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class ResponeJobByCorpNo {
    private String jobsNo;
    private String corpNo;
    private String empNo;
    private String jobsTitle;
    private String jobsContext;
    private Integer recruitNum;
    private String favoriteLang;
    private String jobLocation;
    private Date createdAt;
    private String jobType;
    private String jobQualify;
    private String employType; //채용유형 무관/신입/
    private Date applyStart;
    private Date applyEnd;
    private Date intv1Start;
    private Date intv1End;
    private Date intv2Start;
    private Date intv2End;
    private String workDetail;
    private String closed;

}
