package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.ProjectTask;
import io.agileintelligence.ppmtool.repositories.BacklogRespository;
import io.agileintelligence.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRespository backlogRespository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask() {

        // PT's to be added to a specific project, project != null, Backlog exists

        // Set the backlog to the project task

        // we want our project sequence to be like this IDPRO-1 IDPRO-2 ... 100 101

        // update the Backlog sequence

        // initial priority when priority null

        // initial status when status is null

        ProjectTask projectTask = new ProjectTask();

        return projectTask;
    }
}
