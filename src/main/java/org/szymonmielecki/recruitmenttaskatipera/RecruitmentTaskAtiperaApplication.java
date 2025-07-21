package org.szymonmielecki.recruitmenttaskatipera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.szymonmielecki.recruitmenttaskatipera.config.GithubConfig;

@SpringBootApplication
@EnableConfigurationProperties(GithubConfig.class)
public class RecruitmentTaskAtiperaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitmentTaskAtiperaApplication.class, args);
    }

}
