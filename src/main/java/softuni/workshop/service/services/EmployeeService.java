package softuni.workshop.service.services;

import javax.xml.bind.JAXBException;

public interface EmployeeService {

    void importEmployees() throws JAXBException;

    boolean areImported();

    String readEmployeesXmlFile();

    String exportEmployeesWithAgeAbove();
}
