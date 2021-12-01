package com.example.job.vo;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Date;

@Data
public class ResponseCalender {

    private String title;
    private Date start;
    private Date end;
}
