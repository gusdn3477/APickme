package com.example.hrservice.service;

import com.example.hrservice.client.PcServiceClient;
import com.example.hrservice.dto.CorpDto;
import com.example.hrservice.dto.HrDto;
import com.example.hrservice.entity.CorpEntity;
import com.example.hrservice.entity.HrEntity;
import com.example.hrservice.jpa.CorpRepository;
import com.example.hrservice.jpa.HrRepository;
import com.example.hrservice.vo.ResponsePc;
import com.example.hrservice.vo.ResponseUser;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public HrServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, HrRepository hrRepository,
                         CorpRepository corpRepository, PcServiceClient pcServiceClient) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.hrRepository = hrRepository;
        this.corpRepository = corpRepository;
        this.pcServiceClient = pcServiceClient;
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
        HrDto hrDto = mapper.map(hrEntity, HrDto.class);

        // Open feign
        List<ResponsePc> pcList = null;
        try {
            pcList = pcServiceClient.getPcs(empNo);
        } catch (FeignException ex) {
            log.error(ex.getMessage());
        }

        hrDto.setPcs(pcList);

        return hrDto;
    }

    @Override
    public void deleteNormal(String empNo) {
        hrRepository.deleteById(empNo);
    }

    @Override
    public void updateNormal(HrDto hrDto) {

        HrEntity normalEntity = hrRepository.findById(hrDto.getEmpNo()).get();

        normalEntity.setName(hrDto.getName());
        normalEntity.setNickname(hrDto.getNickname());
        normalEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(hrDto.getPwd()));

        hrRepository.save(normalEntity);

        return ;
    }

}
