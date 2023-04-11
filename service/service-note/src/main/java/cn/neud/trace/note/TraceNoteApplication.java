package cn.neud.trace.note;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.neud.trace.note.mapper")
@SpringBootApplication
public class TraceNoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(TraceNoteApplication.class, args);
    }

}
