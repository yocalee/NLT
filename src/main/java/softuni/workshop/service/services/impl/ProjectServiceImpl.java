package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.entities.Project;
import softuni.workshop.data.repositories.ProjectRepository;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.ProjectService;
import softuni.workshop.service.services.dtos.ProjectDto;
import softuni.workshop.service.services.dtos.ProjectRootDto;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;


@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final CompanyService companyService;
    private final static String PROJECT_FILE  = "src/main/resources/files/xmls/projects.xml";
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, CompanyService companyService, XmlParser xmlParser, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.companyService = companyService;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void importProjects() throws JAXBException {
        ProjectRootDto projectRootDto = xmlParser.parseXML(ProjectRootDto.class, PROJECT_FILE);

        for (ProjectDto dto : projectRootDto.getProjectDtoList()) {
            Project project = new Project();
            project.setName(dto.getName());
            project.setDescription(dto.getDescription());
            project.setStartDate(dto.getStartDate());
            project.setFinished(dto.isFinished());
            project.setPayment(dto.getPayment());
            project.setEmployees(new HashSet<>());
            project.setCompany(this.companyService.findByName(dto.getCompany().getName()));
            this.projectRepository.saveAndFlush(project);
        }
    }

    @Override
    public boolean areImported() {
       return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() {
      try{
          return String.join("\n", Files.readAllLines(Path.of(PROJECT_FILE)));
      }catch (IOException e){
          e.printStackTrace();
      }
      return null;
    }

    @Override
    public String exportFinishedProjects(){
        List<Project> projects = this.projectRepository.findAllByFinished();
        String output = "";

        for (Project project : projects) {
            output += String.format("Project name: %s\n    Description: %s\n    %f\n",
                        project.getName(), project.getDescription(), project.getPayment());
        }
        return output;
    }

    @Override
    public Project findByName(String projectName) {
        return this.projectRepository.findByName(projectName);
    }
}
