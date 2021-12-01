package com.example.hrservice.client;

import com.example.hrservice.vo.ResponsePc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//,configuration = FeignErrorDecoder.class //사용 안 할 예정
@FeignClient(name="process-service")
public interface PcServiceClient {

    @GetMapping("/{empNo}/toPc")
    List<ResponsePc> getPcs(@PathVariable String empNo);

//    @GetMapping("/{empNo}/toPc_wrong")
//    List<ResponsePc> getPcsWrong(@PathVariable String empNo);
}
