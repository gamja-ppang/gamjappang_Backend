package demago.gamjappang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GamjaPpangApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamjaPpangApplication.class, args);
    }

}
