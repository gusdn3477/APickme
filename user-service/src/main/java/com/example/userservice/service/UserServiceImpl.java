package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.dto.ApplyDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.ApplyEntity;
import com.example.userservice.entity.JobEntity;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.jpa.JobRepository;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.jpa.ApplyRepository;
import com.example.userservice.vo.ResponseJobDetail;
import com.example.userservice.vo.ResponseJobShort;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    ApplyRepository applyRepository;
    Environment env;
    RestTemplate restTemplate;
    OrderServiceClient orderServiceClient;
    CircuitBreakerFactory circuitBreakerFactory;
    JavaMailSender mailSender;
    JobRepository jobRepository;

//    @Autowired
//    JavaMailSender mailSender;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           Environment env,
                           RestTemplate restTemplate,
                           OrderServiceClient orderServiceClient,
                           CircuitBreakerFactory circuitBreakerFactory,
                           ApplyRepository applyRepository, JavaMailSender javaMailSender,
                           JobRepository jobRepository) {
        this.userRepository = userRepository;
        this.applyRepository = applyRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;
        this.restTemplate = restTemplate;
        this.orderServiceClient = orderServiceClient;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.mailSender = javaMailSender;
        this.jobRepository = jobRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null)
            throw new UsernameNotFoundException(email + ": not found");
        // User is an UserDetails
        User user = new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());

        return user;
    }
    /*지원자 회원가입*/
    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(userDto.getPassword()));

        userRepository.save(userEntity);

        return null;
    }

    /* 일반 사용자(지원자) 삭제*/
    @Override
    public boolean deleteUser(String userId, String email, String password) {

        UserEntity userEntity = userRepository.findByUserId(userId);
        ModelMapper mapper = new ModelMapper();
        UserDto userDeleteDto = mapper.map(userEntity, UserDto.class);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(encoder.matches(password, userDeleteDto.getEncryptedPwd())){
            userRepository.deleteByUserId(userId);
            return true;
        }else{
            return false;
        }

    }
    /* 일반 사용자(지원자) 수정*/ //UserDto가 데이터베이스에서 userId로 찾아온 userDto , userDetails는 RequestBOdy 에 수정할 dto
    @Override
    public UserDto updateByUserId(UserDto userDto, UserDto userDetails) {
        UserEntity userEntity = userRepository.findByUserId(userDto.getUserId());
        ModelMapper mapper = new ModelMapper();
        UserDto userUpdateDto = mapper.map(userEntity, UserDto.class);

        userUpdateDto.setAddress(userDetails.getAddress());
        userUpdateDto.setPhoneNum(userDetails.getPhoneNum());
        userUpdateDto.setPassword(userDetails.getPassword());

        ModelMapper usermapper = new ModelMapper();
        usermapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userUpdateEntity = usermapper.map(userUpdateDto, UserEntity.class);

        userUpdateEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(userUpdateDto.getPassword()));

        userRepository.save(userUpdateEntity);


        return null;
    }

    /* 지원자 공고 지원하기*/
    @Override
    public ApplyDto createApply(ApplyDto applyDto) {
        applyDto.setApplyNum(UUID.randomUUID().toString());


        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ApplyEntity applyEntity = mapper.map(applyDto, ApplyEntity.class);

        applyRepository.save(applyEntity);
        return null;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UsernameNotFoundException("User not found");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(userEntity, UserDto.class);

        return userDto;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(userEntity, UserDto.class);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    //희상추가 = > 모든 지원자 보기
    @Override
    public Iterable<ApplyEntity> getApplyByAll() {return applyRepository.findAll();}

    //희상추가 => 공고별 지원자 보기
    @Override
    public Iterable<ApplyEntity> getJobsAllApply(String jobsNo) {return applyRepository.findAllByJobsNo(jobsNo);}

    @Override
    public boolean deleteApply(String jobsNo, String comfirmPassword, String password, String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDeleteDto = mapper.map(userEntity, UserDto.class);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(encoder.matches(password, userDeleteDto.getEncryptedPwd())){
            applyRepository.deleteByJobsNo(jobsNo);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public ApplyDto getApplyByJobsNo(String jobsNo) {
        ApplyEntity applyEntity = applyRepository.findByJobsNo(jobsNo);

        if (applyEntity == null)
            throw new UsernameNotFoundException("User not found");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ApplyDto applyDto = mapper.map(applyEntity, ApplyDto.class);

        return applyDto;
    }

    @Override
    public ApplyDto updateByJobsNo(ApplyDto applyDto, ApplyDto applyDetails) {
        ApplyEntity applyEntity = applyRepository.findByJobsNo(applyDto.getJobsNo());
        ModelMapper mapper = new ModelMapper();
        ApplyDto applyUpdateDto = mapper.map(applyEntity, ApplyDto.class);

        applyUpdateDto.setPortfolio(applyDetails.getPortfolio());
        applyUpdateDto.setApplyContact(applyDetails.getApplyContact());
        applyUpdateDto.setApplyEmail(applyDetails.getApplyEmail());
        applyUpdateDto.setApplyName(applyDetails.getApplyName());
        applyUpdateDto.setPassword(applyDetails.getPassword());

        ModelMapper applymapper = new ModelMapper();
        applymapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ApplyEntity applyUpdateEntity = applymapper.map(applyUpdateDto, ApplyEntity.class);

        applyUpdateEntity.setJobsNo(applyEntity.getJobsNo());

        applyRepository.save(applyUpdateEntity);

        return null;
    }

    @Override
    public ApplyDto getApplyByUserId(String userId) {
        ApplyEntity applyEntity = applyRepository.findByUserId(userId);

        if (applyEntity == null)
            throw new UsernameNotFoundException("User not found");
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ApplyDto applyDto = mapper.map(applyEntity,ApplyDto.class);

        return applyDto;
    }

    // 지원한 모든 지원서 가져오기
    @Override
    public Iterable<ApplyEntity> getApplys(String userId) {
        Iterable<ApplyEntity> applyEntities = applyRepository.findAllByUserId(userId);
        return applyEntities;
    }

    @Override
    public void findPwd(String email) {

        // PW 임시발급
        UserEntity userEntity = userRepository.findByEmail(email);
        String name = userEntity.getApplyName();
        String newPwd = UUID.randomUUID().toString();

        userEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(newPwd));

        userRepository.save(userEntity);

        //메일 전송
        sendMail(email, name, newPwd);


    }

    public void sendMail(String email, String name, String newPwd) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("A Pick Me - 비밀번호 변경 안내드립니다.");
        message.setText(" 안녕하세요. A Pick Me 입니다. \n\n" +
                name+ " 님의 비밀번호 찾기 요청이 발생하였습니다. 아래의 임시 비밀번호가 발급 되었으니 아래 비밀번호로 로그인 후 비밀번호 변경을 해 주시기 바랍니다.\n\n\t" +
                "임시 발급된 비밀번호 :  " + newPwd + "\n\n 비밀번호 발급을 요청하지 않으셨다면 관리자에게 전화하여 확인을 요청할 수 있습니다.");

        mailSender.send(message);
    }

    @Override
    public boolean checkPwd(UserDto dto){

        UserEntity userEntity = userRepository.findByUserId(dto.getUserId());
        return bCryptPasswordEncoder.matches(dto.getPassword(), userEntity.getEncryptedPwd());
    }

    @Override
    public boolean checkEmail(String email){

        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null){ // false면 겹치는 게 없다는 뜻
            return false;
        }
        return true;
    }

    //내가 지원한 항목(공고) 전체 가져오기
    @Override
    public List<ResponseJobShort> getJobsByUserId(String userId) {
        Iterable<ApplyEntity> applyDtos = applyRepository.findAllByUserId(userId);

        List<String> applyJobsList = new ArrayList<>();

        applyDtos.forEach(v->{
            applyJobsList.add(v.getJobsNo());
        });

        Iterable<JobEntity> jobEntity = jobRepository.findAllByJobsNoIn(applyJobsList);

        List<ResponseJobShort> jobList = new ArrayList<>();
        jobEntity.forEach(v->{
            jobList.add(new ModelMapper().map(v,ResponseJobShort.class));
        });
        return jobList;
    }
}