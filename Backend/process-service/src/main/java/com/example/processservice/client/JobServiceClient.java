package com.example.processservice.client;

import com.example.processservice.vo.ResponseJob;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "job-service")
public interface JobServiceClient {

    @GetMapping("/{empNo}/toJob")
    List<ResponseJob> getJobs(@PathVariable String empNo);
}
