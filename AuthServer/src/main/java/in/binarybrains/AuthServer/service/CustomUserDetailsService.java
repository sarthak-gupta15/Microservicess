package in.binarybrains.AuthServer.service;

import in.binarybrains.AuthServer.model.User;
import in.binarybrains.AuthServer.model.CustomUserDetails;
import in.binarybrains.AuthServer.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByName(username);
        if (user == null) {
            log.info("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }
        UserDetails userDetails = new CustomUserDetails(user);
        
        return userDetails;
    }
}