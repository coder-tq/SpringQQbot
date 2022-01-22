package cn.codertq.springrobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringRobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRobotApplication.class, args);
    }

}
