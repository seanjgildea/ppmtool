package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.exception.ProjectIdException;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {

        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e ) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' is used already");
        }
    }

    public Project findProjectByIdentifier(String projectId) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null ) {
            throw new ProjectIdException("Project ID '"+ projectId +"' does not exist");
        }
        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId);
        if (project == null ) {
            throw new ProjectIdException("Cannot delete Project with ID '"+ projectId +"'. Does not exist!");
        }
        projectRepository.delete(project);
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
