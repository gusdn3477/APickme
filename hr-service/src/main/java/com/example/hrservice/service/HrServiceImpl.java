package com.example.hrservice.service;

import com.example.hrservice.client.PcServiceClient;
import com.example.hrservice.dto.CorpDto;
import com.example.hrservice.dto.HrDto;
import com.example.hrservice.entity.CorpEntity;
import com.example.hrservice.entity.HrEntity;
import com.example.hrservice.jpa.CorpRepository;
import com.example.hrservice.jpa.HrRepository;
import com.example.hrservice.vo.RequestCheckPwd;
import com.example.hrservice.vo.RequestDeleteUserBySuper;
import com.example.hrservice.vo.RequestUser;
import com.example.hrservice.vo.ResponsePc;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class HrServiceImpl implements HrService {

    BCryptPasswordEncoder bCryptPasswordEncoder;
    HrRepository hrRepository;
    CorpRepository corpRepository;
    PcServiceClient pcServiceClient;
    CircuitBreakerFactory circuitBreakerFactory;
    JavaMailSender mailSender;

    @Autowired
    public HrServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, HrRepository hrRepository,
                         CorpRepository corpRepository, PcServiceClient pcServiceClient,
                         CircuitBreakerFactory circuitBreakerFactory, JavaMailSender mailSender) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.hrRepository = hrRepository;
        this.corpRepository = corpRepository;
        this.pcServiceClient = pcServiceClient;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        HrEntity hrEntity = hrRepository.findByEmail(email);

        if (hrEntity == null)
            throw new UsernameNotFoundException(email + ": not found");
        // User is an UserDetails
        // 3번째 항목이 enabled이고 true값이었는데 테스트 해봄
        User user = new User(hrEntity.getEmail(), hrEntity.getEncryptedPwd(),
                Boolean.parseBoolean(hrEntity.getAuth()), true, true, true,
                new ArrayList<>());

        return user;

    }

    //security
    @Override
    public HrDto getUserDetailsByEmail(String email) {
        HrEntity hrEntity = hrRepository.findByEmail(email);
        if (hrEntity == null)
            throw new UsernameNotFoundException(email);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        HrDto hrDto = mapper.map(hrEntity, HrDto.class);

        return hrDto;
    }

    @Override
    public HrDto createSuperUser(HrDto hrDto) {
        CorpDto corpDto = new CorpDto();

        hrDto.setEmpNo(UUID.randomUUID().toString());
        hrDto.setCorpNo(UUID.randomUUID().toString());
        hrDto.setParents("admin");

        //4Admin Table
        corpDto.setCorpNo(hrDto.getCorpNo());
        corpDto.setCorpName(hrDto.getCorpName());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        HrEntity hrEntity = mapper.map(hrDto, HrEntity.class);
        hrEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(hrDto.getPwd()));
        hrEntity.setAuth("true");
        hrRepository.save(hrEntity);

        //4Admin Table
        ModelMapper mapper2 = new ModelMapper();
        mapper2.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CorpEntity corpEntity =mapper2.map(corpDto, CorpEntity.class);
        corpRepository.save(corpEntity);

        return hrDto;
    }

    @Override
    public HrDto createNormalUser(String empNo, HrDto hrDto) {

        hrDto.setEmpNo(UUID.randomUUID().toString());
        Optional<HrEntity> superEntity = hrRepository.findById(empNo);
        hrDto.setCorpNo(superEntity.get().getCorpNo());
        hrDto.setParents("super");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        HrEntity hrEntity = mapper.map(hrDto, HrEntity.class);
        hrEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(hrDto.getPwd()));
        hrEntity.setAuth("true");

        hrRepository.save(hrEntity);

        return null;
    }

    @Override
    public List<HrEntity> getNormalsAll(String empNo) {

        Optional<HrEntity> superEntity = hrRepository.findById(empNo);
        String corpNo = superEntity.get().getCorpNo();

        return hrRepository.findByCorpNo(corpNo);
    }

    @Override
    public HrDto getNormalById(String empNo) {

        Optional<HrEntity> hrEntity = hrRepository.findById(empNo);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        HrDto hrDto = mapper.map(hrEntity.get(), HrDto.class);

        // Open feign
        List<ResponsePc> pcList = null;
        log.info("------------------------------------------------------Start Feign");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("my-circuitbreaker");
        pcList = circuitBreaker.run(() -> pcServiceClient.getPcs(empNo),
                throwable -> new ArrayList<>());
        log.info("------------------------------------------------------End Feign");

        hrDto.setPcs(pcList);

        return hrDto;
    }

    @Override // 회원탈퇴
    public void deleteNormal(String empNo) {
        hrRepository.deleteById(empNo);
    }

    @Override // 회원 삭제
    public Boolean deleteNorMalBySuper(String parents, String empNo){

        if(parents.equals("admin")){
            hrRepository.deleteByEmpNo(empNo);
            return true;
        }
        return false;
    }

    @Override
    public void updateNormal(HrDto hrDto) {

        HrEntity normalEntity = hrRepository.findById(hrDto.getEmpNo()).get();
        normalEntity.setName(hrDto.getName());
        normalEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(hrDto.getPwd()));
        hrRepository.save(normalEntity);

    }

    @Override
    public void findPwd(String email) {

        // PW 임시발급
        HrEntity hrEntity = hrRepository.findByEmail(email);
        String name = hrEntity.getName();
        String newPwd = UUID.randomUUID().toString();
        hrEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(newPwd));
        hrRepository.save(hrEntity);

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
    public Boolean getSimpleById(RequestUser checkPwdInfo) {

        HrEntity hrEntity = hrRepository.findByEmpNo(checkPwdInfo.getEmpNo());
//        Optional<HrEntity> hrEntity = hrRepository.findById(checkPwdInfo.getEmpNo());

        return bCryptPasswordEncoder.matches(checkPwdInfo.getPwd(), hrEntity.getEncryptedPwd());
    }

    @Override
    public boolean checkEmail(String email){

        HrEntity hrEntity = hrRepository.findByEmail(email);
        if(hrEntity == null){ // false면 겹치는 게 없다는 뜻
            return false;
        }
        return true;
    }

}
