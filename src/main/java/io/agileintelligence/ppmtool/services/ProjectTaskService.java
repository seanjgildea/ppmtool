package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.Backlog;
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

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        // Exceptions: Project not found

        // PT's to be added to a specific project, project != null, Backlog exists
        Backlog backlog = backlogRespository.findByProjectIdentifier(projectIdentifier);

        // Set the backlog to the project task
        projectTask.setBacklog(backlog);

        // we want our project sequence to be like this IDPRO-1 IDPRO-2 ... 100 101
        Integer backlogSequence = backlog.getPTSequence();

        // update the Backlog sequence
        backlogSequence++;

        // Add sequence to PT
        projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence );
        projectTask.setProjectIdentifier(projectIdentifier);

        // initial priority when priority null
        if ( projectTask.getPriority() == null || projectTask.getPriority() == 0 ) {
            projectTask.setPriority(3);
        }

        // initial status when status is null
        if ( projectTask.getStatus() == "" || projectTask.getStatus() == null ) {
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }
}
