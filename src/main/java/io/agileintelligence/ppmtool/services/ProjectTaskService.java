package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.domain.ProjectTask;
import io.agileintelligence.ppmtool.exception.ProjectNotFoundException;
import io.agileintelligence.ppmtool.repositories.BacklogRespository;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import io.agileintelligence.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRespository backlogRespository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        try {
            // PT's to be added to a specific project, project != null, Backlog exists
            Backlog backlog = backlogRespository.findByProjectIdentifier(projectIdentifier);

            // Set the backlog to the project task
            projectTask.setBacklog(backlog);

            // we want our project sequence to be like this IDPRO-1 IDPRO-2 ... 100 101
            Integer BacklogSequence = backlog.getPTSequence();

            // update the Backlog sequence
            BacklogSequence++;

            backlog.setPTSequence(BacklogSequence);

            // Add sequence to PT
            projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence );
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

        } catch (Exception e) {
            throw new ProjectNotFoundException("Project Not Found");
        }
    }

    public Iterable<ProjectTask>findBacklogById(String id) {

        Project project = projectRepository.findByProjectIdentifier(id);

        if ( project == null ) {
            throw new ProjectNotFoundException("Project with ID: '" + id + "' does not exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {

        // make sure we are searching on an existing backlog
        Backlog backlog = backlogRespository.findByProjectIdentifier(backlog_id);

        if (backlog == null ) {
            throw new ProjectNotFoundException("Project with ID '" + backlog_id + "' not found");
        }

        // make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if ( projectTask == null ) {
            throw new ProjectNotFoundException("Project Task '" + pt_id + "' not found");
        }
        // make sure that the backlog/project id in the path corresponds to the right project

        if ( !projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project Task '" + pt_id + "'does not exist in project '" + backlog_id);
        }

        return projectTask;
    }
}
