package com.example.userservice.controller;

import com.example.userservice.dto.ApplyDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.ApplyEntity;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.*;
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
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    private final Environment env;
    private final UserService userService;


    @Autowired
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/health_check")
    public String status(HttpServletRequest request) {
        return String.format("It's Working in User Service, " +
                        "port(local.server.port)=%s, port(server.port)=%s, " +
                        "token_secret=%s, token_expiration_time=%s, gateway_ip=%s",
                env.getProperty("local.server.port"), env.getProperty("server.port"),
                env.getProperty("token.secret"), env.getProperty("token.expiration_time"), env.getProperty("gateway.ip"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message");
    }

    /*일반 사용자 회원가입*/
    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody @Valid RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        // convert UserDto to ResponseUser
        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }


    /*일반 사용자(지원자) 회원삭제(탈퇴)*/
    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUser(@RequestBody @Valid RequestDeleteUser user){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(user, UserDto.class);

        boolean status = userService.deleteUser(userDto.getUserId(),userDto.getEmail(), userDto.getPassword());

        String okMsg = "OK";
        String errorMsg = "NO";
        if(status) {
            return ResponseEntity.status(HttpStatus.OK).body(okMsg);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(errorMsg);
        }
    }

    /* 일반사용자(지원자) 정보 수정*/
    @PutMapping("/users")
    public ResponseEntity<String> updateUser(@RequestBody @Valid RequestUpdateUser user){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDetails = mapper.map(user, UserDto.class);

        UserDto userDto = userService.getUserByUserId(user.getUserId());

        userService.updateByUserId(userDto, userDetails);

        String okMsg = "update user , 200 OK";
        return ResponseEntity.status(HttpStatus.OK).body(okMsg);
    }


    /* 전체 일반사용자(지원자) 목록 */
    @GetMapping("/users")
    public List<ResponseUser> getUsers(HttpServletRequest request) {
        Iterable<UserEntity> usersList = userService.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();

        usersList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return result;
    }

    /* 지원내역 전체 보기
    * 모든 지원테이블 값 가져옴  -- 왜 필요한지는 모르겠음 일단 */
    @GetMapping("/users/apply")
    public ResponseEntity<List<ResponseApply>> getApply(){
        Iterable<ApplyEntity> applyList = userService.getApplyByAll();
        List<ResponseApply> result = new ArrayList<>();

        applyList.forEach(v->{
            result.add(new ModelMapper().map(v, ResponseApply.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /* 공고별 지원내역 전체 보기*/
    @GetMapping("/users/apply/{jobsNo}")
    public ResponseEntity<List<ResponseApply>> getJobsNoApply(@PathVariable("jobsNo") String jobsNo){
        Iterable<ApplyEntity> jobApplyList = userService.getJobsAllApply(jobsNo);
        List<ResponseApply> result = new ArrayList<>();

        jobApplyList.forEach(v->{
            result.add(new ModelMapper().map(v, ResponseApply.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /* 사용자 상세 보기 (with 주문 목록)
    * 사용자가 자신의 정보 상세보기*/
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);

        ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    /* 지원자 공고 지원하기*/
    @PostMapping("/users/apply")
    public ResponseEntity<ResponseApply> createApply(@RequestBody @Valid RequestApply apply){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ApplyDto applyDto = mapper.map(apply, ApplyDto.class);

        userService.createApply(applyDto);

        UserDto userDto = new UserDto();
        ResponseApply returnValue = new ModelMapper().map(applyDto, ResponseApply.class);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);

    }

    /*일반 사용자 회원가입*/
//    @PostMapping("/users/register")
//    public ResponseEntity createUser(@RequestBody @Valid RequestUser user) {
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        UserDto userDto = mapper.map(user, UserDto.class);
//        userService.createUser(userDto);
//
//        // convert UserDto to ResponseUser
//        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
//    }

    /* 비밀번호 체크 */
    @PostMapping("/users/checkpwd")
    public ResponseEntity checkPwd(@RequestBody @Valid RequestCheckPwd request){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(request, UserDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkPwd(userDto));

    }
    /*지원자 비밀번호 찾기 */
    @PostMapping("/users/findpwd")
    public ResponseEntity findPwd(@RequestBody @Valid RequestFindPwd findPwdInfo){

        UserDto userDto = userService.getUserDetailsByEmail(findPwdInfo.getEmail());
        String resultValue = "FALSE";
        if (userDto.getPhoneNum().equals(findPwdInfo.getPhoneNum())) {

            userService.findPwd(userDto.getEmail());

            resultValue = "TRUE";
        }
        return ResponseEntity.status(HttpStatus.OK).body(resultValue);
    }


    /* 지원자 공고 삭제하기 */
    @DeleteMapping("/users/apply")
    public ResponseEntity<String> deleteApply(@RequestBody @Valid RequestDeleteApply apply){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ApplyDto applyDto = mapper.map(apply, ApplyDto.class);

        userService.deleteApply(applyDto.getUserId(), applyDto.getJobsNo());

//        boolean status = userService.deleteApply(applyDto.getJobsNo(), applyDto.getComfirmPassword(), applyDto.getPassword(), applyDto.getUserId());

        String okMsg = "delete userId , 200 OK";
        String errorMsg = "error~";
        return ResponseEntity.status(HttpStatus.OK).body(okMsg);
//        if(status) {
//            return ResponseEntity.status(HttpStatus.OK).body(okMsg);
//        }else{
//            return ResponseEntity.status(HttpStatus.OK).body(errorMsg);
//        }

    }

    /* 공고 정보 수정*/
    @PutMapping("/users/apply")
    public ResponseEntity<String> updateApply(@RequestBody @Valid RequestUpdateApply apply){

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ApplyDto applyDetails = mapper.map(apply, ApplyDto.class);

        ApplyDto applyDto = userService.getApplyByJobsNo(apply.getJobsNo());

        userService.updateByJobsNo(applyDto, applyDetails);

        String okMsg = "update user , 200 OK";
        return ResponseEntity.status(HttpStatus.OK).body(okMsg);
    }


    /*-------------내가 지원한 회사의 공고들 리스트로 가져오기------------*/
    @GetMapping("/users/jobs/{userId}")
    public ResponseEntity getApplyJobs(@PathVariable("userId") String userId){

        List<ResponseJobShort> applyJobShortList = userService.getJobsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(applyJobShortList);
    }

    @PostMapping("/users/checkemail")
    public Boolean checkEmail(@RequestBody @Valid RequestCheckEmail Info){

        if(userService.checkEmail(Info.getEmail())){
            return true;
        }

        return false;
    }

    /*공고별 지원자수 count*/
    @GetMapping("/users/apply/count/{corpNo}")
    public List<ResponseApplyCount> getApplysByCorpNo(@PathVariable("corpNo") String corpNo){
       // Iterable<ApplyEntity> applyEntities = userService.getApplysByCorpNo(corpNo);

        List<ResponseApplyCount> jobList;
        jobList = userService.getApplysByCorpNo(corpNo);

        return jobList;

    }

    // 자신이 지원한 지원정보 전체 목록으로 불러오기  - 진희 --> 전체가져오는건 필요없을거 같고
    @GetMapping("/users/applys/{userId}")
    public List<ResponseApply> getApplysByUserId(@PathVariable("userId") String userId){
        Iterable<ApplyEntity> applysList = userService.getApplys(userId);

        List<ResponseApply> result = new ArrayList<>();

        applysList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseApply.class));
        });
        return result;
    }

    // 공고별 자신이 지원한 지원상세 모달 용 1개 가져오는것임 - 진희
    @PostMapping("/users/apply/detail")
    public ResponseApply getApplyByUserId(@RequestBody RequestUserApply info){
        ApplyDto apply = userService.getApply(info);

        return new ModelMapper().map(apply, ResponseApply.class);
    }


    /*내가 지원한 전체 공고(전형) 내역 리스트로 보기 ( 관리자+ 인사팀전체 + 자기자신만 )
    * 기존에 있었는데 무슨 용도인지 몰라서 바꿨었는데 다시 살려놓겠습니다 일단은 */
    @GetMapping("/users/apply/{userId}")
    public List<ResponseApply> getApply(@PathVariable("userId") String userId){
        Iterable<ApplyEntity> applysList = userService.getApplyByAll();

        List<ResponseApply> result = new ArrayList<>();

        applysList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseApply.class));
        });
        return result;
    }
    /*영모 모달용*/
    @GetMapping("/users/apply/{userId}/{jobsNo}")
    public ResponseApply getApplyByUserId(@PathVariable("userId") String userId, @PathVariable("jobsNo") String jobsNo){

        RequestUserApply requestUserApply = new RequestUserApply();
        requestUserApply.setUserId(userId);
        requestUserApply.setJobsNo(jobsNo);
        ApplyDto apply = userService.getApply(requestUserApply);
        return new ModelMapper().map(apply, ResponseApply.class);
    }
}