package ua.iasa.explorer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ua.iasa.repository")
@ComponentScan(basePackages = "ua.iasa")
@EntityScan(basePackages = "ua.iasa.entity")
public class DbExplorer implements CommandLineRunner{

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(DbExplorer.class).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running...");
    }
}
