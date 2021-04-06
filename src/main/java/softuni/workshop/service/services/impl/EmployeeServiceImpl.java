package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.entities.Employee;
import softuni.workshop.data.entities.Project;
import softuni.workshop.data.repositories.EmployeeRepository;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;
import softuni.workshop.service.services.dtos.EmployeeDto;
import softuni.workshop.service.services.dtos.EmployeeRootDto;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ProjectService projectService;
    private final static String EMPLOYEE_FILE = "src/main/resources/files/xmls/employees.xml";
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectService projectService, XmlParser xmlParser, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.projectService = projectService;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void importEmployees() throws JAXBException {
        EmployeeRootDto employeeRootDto = xmlParser.parseXML(EmployeeRootDto.class, EMPLOYEE_FILE);

        for (EmployeeDto dto : employeeRootDto.getEmployeeDtoList()) {
            Employee e = new Employee();
            e.setFirstName(dto.getFirstName());
            e.setLastName(dto.getLastName());
            e.setAge(dto.getAge());
            String projectName = modelMapper.map(dto.getProject(), Project.class).getName();
            e.setProject(this.projectService.findByName(projectName));
            employeeRepository.saveAndFlush(e);
        }
    }

    @Override
    public boolean areImported() {
       return employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() {
        try{
            return String.join("\n", Files.readAllLines(Path.of(EMPLOYEE_FILE)));
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        List<Employee> employees = this.employeeRepository.findAllByAgeAbove();
        String out = "";


        for (Employee employee : employees) {
            out += String.format("Name: %s %s\n    Age: %d\n   Project name: %s\n",
                        employee.getFirstName(), employee.getLastName(), employee.getAge(), employee.getProject().getName());
        }

        return out;
    }
}
