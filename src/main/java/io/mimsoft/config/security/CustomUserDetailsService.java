package io.mimsoft.config.security;

import io.mimsoft.entity.ProfileEntity;
import io.mimsoft.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername");
        Optional<ProfileEntity> optional = profileRepository.findByPhone(phone);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        ProfileEntity user = optional.get();
        return new CustomUserDetails(user);
    }
}
