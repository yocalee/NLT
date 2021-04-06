package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;

import javax.xml.bind.JAXBException;

@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {
    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final CompanyService companyService;

    @Autowired
    public ImportController(EmployeeService employeeService, ProjectService projectService, CompanyService companyService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.companyService = companyService;
    }


    @GetMapping("/xml")
    public ModelAndView xml(){
        ModelAndView view = super.view("/xml/import-xml");
        boolean[] areImported = {this.companyService.areImported(), this.projectService.areImported(), this.employeeService.areImported()};

        view.addObject("areImported", areImported);

        return view;
    }

    @GetMapping("/companies")
    public ModelAndView companies(){
        ModelAndView view = super.view("xml/import-companies");
        view.addObject("companies", this.companyService.readCompaniesXmlFile());

        return view;
    }

    @PostMapping("/companies")
    public ModelAndView companiesConfirm() throws JAXBException {
        this.companyService.importCompanies();

        return super.redirect("/import/xml");
    }



    @GetMapping("/projects")
    public ModelAndView projects(){
        ModelAndView view = super.view("xml/import-projects");
        view.addObject("projects", this.projectService.readProjectsXmlFile());

        return view;
    }
    @PostMapping("/projects")
    public ModelAndView projectsConfirm() {
        try {
            this.projectService.importProjects();
            return super.redirect("/import/xml");
        }catch (JAXBException e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/employees")
    public ModelAndView employees(){
        ModelAndView view = super.view("xml/import-employees");
        view.addObject("employees", this.employeeService.readEmployeesXmlFile());

        return view;
    }

    @PostMapping("/employees")
    public ModelAndView employeesConfirm(){
        try{
            this.employeeService.importEmployees();
            ModelAndView view = super.view("/xml/import-xml");
            boolean[] areImported = {this.companyService.areImported(), this.projectService.areImported(),this.employeeService.areImported()};
            view.addObject("areImported",areImported);
            return view;
        }catch (JAXBException e){
            e.printStackTrace();
        }
        return null;
    }


}
