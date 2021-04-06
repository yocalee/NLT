package softuni.workshop.service.services;

import softuni.workshop.data.entities.Company;

import javax.xml.bind.JAXBException;

public interface CompanyService {

    void importCompanies() throws JAXBException;

    boolean areImported();

    String readCompaniesXmlFile();

    Company findByName(String name);
}
