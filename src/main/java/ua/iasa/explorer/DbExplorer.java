package ua.iasa.explorer;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DbExplorer {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(DbExplorer.class).bannerMode(Banner.Mode.OFF).run(args);
    }
}
