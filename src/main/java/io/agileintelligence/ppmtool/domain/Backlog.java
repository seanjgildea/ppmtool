package io.agileintelligence.ppmtool.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Sequence of project tasks within each backlog
    private Integer PTSequence = 0;
    private String projectIdentifier;

    // one to one mapping

    // one to many mapping
}
