package me.lenglet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private CaseRepository caseRepository;

    public static void main(String... args) {
        SpringApplication.run(App.class, args);
    }


    public void run(String... args) throws Exception {
        caseRepository.save(new Case(1L, LocalDate.parse("2020-10-11")));
        caseRepository.save(new Case(2L, LocalDate.parse("1992-01-22")));
        caseRepository.save(new Case(3L, LocalDate.parse("2020-04-13")));
        int i = 0;
        i++;
    }
}
