package io.agileintelligence.ppmtool.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updateable = false)
    private String projectSequence;
    @NotBlank( message = "Please include a project summary")
    private String summary;
    private String acceptanceCriteria;
    private String status;
    private Integer priority;
    private Date dueDate;
    private Date create_At;
    private Date update_At;

    @PrePersist
    protected void onCreate() {
        this.create_At = new Date();
    }

}
