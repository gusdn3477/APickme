package com.example.hrservice.service;

import com.example.hrservice.dto.HrDto;
import com.example.hrservice.entity.HrEntity;
import com.example.hrservice.jpa.HrRepository;
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
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class HrServiceImpl implements HrService {

    BCryptPasswordEncoder bCryptPasswordEncoder;
    HrRepository hrRepository;

    @Autowired
    public HrServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, HrRepository hrRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.hrRepository = hrRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String empNo) throws UsernameNotFoundException {
        Optional<HrEntity> hrEntity = hrRepository.findById(empNo);

        if (hrEntity == null)
            throw new UsernameNotFoundException(empNo + ": not found");
        // User is an UserDetails
        User user = new User(hrEntity.get().getEmail(), hrEntity.get().getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());

        return user;

    }

    @Override
    public HrDto createSuperUser(HrDto hrDto) {
        log.info("pwd Value  ::::  "+ hrDto.getPwd());
        hrDto.setEmpNo(UUID.randomUUID().toString());
        hrDto.setCorpNo(UUID.randomUUID().toString());
        hrDto.setParents("admin");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        HrEntity hrEntity = mapper.map(hrDto, HrEntity.class);
        hrEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(hrDto.getPwd()));

        hrRepository.save(hrEntity);

        return null;
    }
}
