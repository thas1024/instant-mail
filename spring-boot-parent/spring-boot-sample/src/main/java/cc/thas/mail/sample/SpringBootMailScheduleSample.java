package cc.thas.mail.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="mailto:thascc1024@gmail.com">thas</a>
 * @date 2020/1/5 14:42
 */
@SpringBootApplication
public class SpringBootMailScheduleSample {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SpringBootMailScheduleSample.class, args);
        Thread.currentThread().join();
    }
}
