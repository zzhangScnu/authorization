package com.lihua.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class AuthorizationApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AuthorizationApplication.class, args);
//        ConfigurableApplicationContext applicationContext = SpringApplication.run(AuthorizationApplication.class, args);
//        ResourceRepository resourceRepository = applicationContext.getBean(ResourceRepository.class);
//        List<Resource> all = resourceRepository.findAll();
//        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
//        List<User> allUser = userRepository.findAll();
//        System.out.println("finish");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String s = objectMapper.writeValueAsString(all);
//        System.out.println(s);
    }

}
