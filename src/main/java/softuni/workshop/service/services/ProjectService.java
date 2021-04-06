package softuni.workshop.service.services;

import softuni.workshop.data.entities.Project;

import javax.xml.bind.JAXBException;

public interface ProjectService {

    void importProjects() throws JAXBException;

    boolean areImported();

    String readProjectsXmlFile();

    String exportFinishedProjects();

    Project findByName(String projectName);
}
