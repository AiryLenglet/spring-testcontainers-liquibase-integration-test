package me.lenglet;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Case_")
public class Case {

    public Case() {
    }

    public Case(Long id, LocalDate date) {
        this.id = id;
        this.date = date;
    }

    @Id
    private Long id;

    @Column(name = "DATE_")
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

}
