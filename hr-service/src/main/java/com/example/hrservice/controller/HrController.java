package com.example.hrservice.controller;

import com.example.hrservice.dto.HrDto;
import com.example.hrservice.service.HrService;
import com.example.hrservice.vo.RequestSuperInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/")
public class HrController {

    private final HrService hrService;

    @Autowired
    public HrController(HrService hrService) {
        this.hrService = hrService;
    }

    @Operation(summary = "Super의 가입 요청", description = "super 인사담당자가 관리자에게 계정을 신청한다.")
    @PostMapping("/request")
    public ResponseEntity createSuperUser(@RequestBody RequestSuperInfo superInfo){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        HrDto hrDto = mapper.map(superInfo, HrDto.class);
        hrService.createSuperUser(hrDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("가입신청이 완료되었습니다.");

    }

    @PostMapping("/welcome")
    public String welcome(@RequestBody String test) {

        return test;
    }

}
