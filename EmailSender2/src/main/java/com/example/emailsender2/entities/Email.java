package com.example.emailsender2.entities;

import com.example.emailsender2.enums.StatusEmail;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "emails")
@Getter
@Setter
@Data
public class Email {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "ownerRef", nullable = false)
    private String ownerRef;

    @Column(name = "emailFrom", nullable = false)
    private String emailFrom;

    @Column(name = "emailTo", nullable = false)
    private String emailTo;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "roomNumbers", nullable = false)
    private String roomNumbers;

    @Column(name = "reservationCost", nullable = false)
    private int reservationCost;

    @Column(name = "reservationId", nullable = true)
    private UUID reservationId;

    @Column(name = "emailDate", nullable = false)
    private LocalDateTime emailDate;

    @Column(name = "emailStatus", nullable = false)
    private StatusEmail emailStatus;
}
