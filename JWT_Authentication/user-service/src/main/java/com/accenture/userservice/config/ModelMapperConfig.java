package com.accenture.userservice.config;

import com.accenture.userservice.dto.UserDto;
import com.accenture.userservice.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        // 🔥 CRITICAL FIX
        modelMapper.typeMap(UserDto.class, User.class)
                .addMappings(mapper -> mapper.skip(User::setRoles));

        return modelMapper;
    }
}

