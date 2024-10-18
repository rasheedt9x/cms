package com.sgdc.cms.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "username")
    private String username;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "due_date")
    private LocalDate dueDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "return_date")
    private LocalDate returnDate;
}
