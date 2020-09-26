package com.lihua.authorization.serviceimpl;

import com.lihua.authorization.persistent.pojo.User;
import com.lihua.authorization.persistent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByNickName(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("数据库中无此用户！");
        }
        return user;
    }
}
