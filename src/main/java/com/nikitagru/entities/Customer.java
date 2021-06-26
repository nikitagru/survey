package com.nikitagru.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/***
 * Клиент
 */
@Entity
@Data
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uniqueId;

    @ManyToMany
    @JoinTable(name = "customers_surveys",
            joinColumns = @JoinColumn(name = "customerId"),
            inverseJoinColumns = @JoinColumn(name = "surveyId"))
    private Collection<Survey> surveys = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private Collection<Answer> answers = new ArrayList<>();
}
