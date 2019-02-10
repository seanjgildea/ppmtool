package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.domain.User;
import io.agileintelligence.ppmtool.exception.ProjectIdException;
import io.agileintelligence.ppmtool.exception.ProjectNotFoundException;
import io.agileintelligence.ppmtool.repositories.BacklogRespository;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import io.agileintelligence.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRespository backlogRespository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username ) {

        if (project.getId() != null ) {
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

            if (existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
                throw new ProjectNotFoundException("Project not found in your account");
            } else if (existingProject == null ) {
                throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier() + "' cannot be updated because it doesn't exist");
            }
        }

        try {

            String _UC_Project_ID = project.getProjectIdentifier().toUpperCase();

            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(username);
            project.setProjectIdentifier(_UC_Project_ID);

            if ( project.getId() == null) {

                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(_UC_Project_ID);
            }

            if(project.getId() != null ) {
                project.setBacklog(backlogRespository.findByProjectIdentifier(_UC_Project_ID));
            }



            return projectRepository.save(project);
        } catch (Exception e ) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' is used already");
        }
    }

    public Project findProjectByIdentifier(String projectId, String username) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null ) {
            throw new ProjectIdException("Project ID '"+ projectId +"' does not exist");
        }

        if (!project.getProjectLeader().equals(username)) {
            throw new ProjectNotFoundException("Project not found in your account");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username) {
        projectRepository.delete(findProjectByIdentifier(projectId,username));
    }

    public Project updateProjectByIdentifier(Project project ) {
        Project project1 = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
        if (project == null ) {
            throw new ProjectIdException("Cannot update Project. Does not exist!");
        }
        project1.setDescription(project.getDescription());
        project1.setProjectName(project.getProjectName());
        projectRepository.save(project1);
        return project1;
    }


}
