package com.example.hrservice.controller;

import com.example.hrservice.dto.HrDto;
import com.example.hrservice.entity.HrEntity;
import com.example.hrservice.service.HrService;
import com.example.hrservice.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;

@Slf4j
@RestController
@RequestMapping("/hr")
public class HrController {

    private final HrService hrService;
    private final Environment env;


    @Autowired
    public HrController(HrService hrService,
                        Environment env) {
        this.hrService = hrService;
        this.env = env;
    }

// 테스트 용이므로 완료시 삭제
    @GetMapping("/health_check")
    public String status(HttpServletRequest request) {
        return String.format("It's Working in User Service, " +
                        "port(local.server.port)=%s, port(server.port)=%s, " +
                        "token_secret=%s, token_expiration_time=%s, gateway_ip=%s",
                env.getProperty("local.server.port"), env.getProperty("server.port"),
                env.getProperty("token.secret"), env.getProperty("token.expiration_time"), env.getProperty("gateway.ip"));
    }

    @Operation(summary = "Super의 가입 요청", description = "super 인사담당자가 관리자에게 계정을 신청한다.")
    @PostMapping("/request")
    public ResponseEntity createSuperUser(@RequestBody @Valid RequestSuperInfo superInfo){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        HrDto hrDto = mapper.map(superInfo, HrDto.class);
        hrDto = hrService.createSuperUser(hrDto);

        //return ResponseEntity.status(HttpStatus.CREATED).body("가입신청이 완료되었습니다.");
        return ResponseEntity.status(HttpStatus.CREATED).body(hrDto.getEmpNo());

    }

    @Operation(summary = "인사직원 등록", description = "super 인사 관리자가 normal 인사직원을 할당한다.")
    @PostMapping("/register")
    public ResponseEntity createNormalUser(@RequestBody @Valid RequestNormalInfo normalInfo){

        String empNo;
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        HrDto hrDto = mapper.map(normalInfo, HrDto.class);
        empNo = hrDto.getEmpNo();
        hrService.createNormalUser(empNo, hrDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("입력 완료");
    }

    /*@Operation(summary = "인사직원 전체 조회", description = "super 인사 관리자가 같은 회사에 속한 인사직원 모두를 리스트로 확인할 수 있다.")
    @GetMapping("/{superId}")
    public List<ResponseUser> getNormals(@PathVariable("superId") String superId){

        Iterable<HrEntity> normalsList = hrService.getNormalsAll(superId);
        List<ResponseUser> result = new ArrayList<>();

        normalsList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return result;
    }*/
    @Operation(summary = "인사직원 전체 조회", description = "인사직원이 같은 회사에 속한 인사직원 모두를 리스트로 확인할 수 있다.")
    @GetMapping("/{corpNo}")
    public List<ResponseUser> getNormals(@PathVariable("corpNo") String corpNo){

        Iterable<HrEntity> normalsList = hrService.getNormalsAll(corpNo);
        List<ResponseUser> result = new ArrayList<>();

        normalsList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return result;
    }

/* 보류
    @Operation(summary = "인사직원 상세 조회", description = "super 인사 관리자가 같은 회사에 속한 인사직원 한명의 상세정보와 담당하는 면접를 확인할 수 있다.")
    @GetMapping("/detail/{empNo}")
    public ResponseEntity getNormal(@PathVariable("empNo") String empNo){

        HrDto hrDto = hrService.getNormalById(empNo);
        ResponseUser returnValue = new ModelMapper().map(hrDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }*/

    /* 삭제는 일단 없고 탈퇴하기로 바뀜
    @Operation(summary = "인사직원 삭제", description = "super 인사 관리자가 등록한 normal 인사직원의 계정을 삭제한다.")
    @DeleteMapping()
    public ResponseEntity deleteNormal(@RequestBody RequestUser norMalEmpNo){

        String normalId = norMalEmpNo.getEmpNo();
        hrService.deleteNormal(normalId);
        return ResponseEntity.status(HttpStatus.OK).body("Normal employee 삭제 완료");
    }*/

    @Operation(summary = "인사직원 탈퇴", description = "normal 인사직원이 탈퇴할 수 있다.")
    @DeleteMapping()
    public ResponseEntity deleteNormal(@RequestBody RequestUser norMalEmpNo){

        String normalId = norMalEmpNo.getEmpNo();
        if (hrService.getSimpleById(norMalEmpNo)){
            hrService.deleteNormal(normalId);
        }else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호를 확인해 주세요.");

        return ResponseEntity.status(HttpStatus.OK).body("Normal employee 삭제 완료");
    }

    @Operation(summary = "인사직원 정보수정", description = "normal 인사 직원이 이름, PWD, 닉네임을 수정할 수 있다.")
    @PutMapping()
    public ResponseEntity updateNormal(@RequestBody RequestPutInfo putInfo){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        HrDto hrDto = mapper.map(putInfo, HrDto.class);
        hrService.updateNormal(hrDto);

        return ResponseEntity.status(HttpStatus.OK).body("수정 완료");
    }

    @Operation(summary = "인사직원 비밀번호 변경", description = "normal 인사 직원이 입력한 이메일과 이름을 통해 확인한 후 임시 비밀 번호를 발급 해준다.")
    @PostMapping("/findpwd")
    public ResponseEntity findPwd(@RequestBody @Valid RequestFindPwd findPwdInfo){

        HrDto hrDto = hrService.getUserDetailsByEmail(findPwdInfo.getEmail());
        String resultValue = "FALSE";
        if (hrDto.getName().equals(findPwdInfo.getName())) {

            hrService.findPwd(hrDto.getEmail());

            resultValue = "TRUE";
        }
        return ResponseEntity.status(HttpStatus.OK).body(resultValue);
    }

    @Operation(summary = "비밀번호 유무 확익", description = "입력한 비밀번호가 있는 비밀번호 인지 확인한다.")
    @PostMapping("/checkpwd")
    public ResponseEntity checkPwd(@RequestBody RequestUser checkPwdInfo ){

        Boolean resultValue = FALSE;
        resultValue = hrService.getSimpleById(checkPwdInfo);

        return ResponseEntity.status(HttpStatus.OK).body(resultValue);

    }




}
