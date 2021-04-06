package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;

@Controller
@RequestMapping("/export")
public class ExportController extends BaseController {
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public ExportController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/project-if-finished")
    public ModelAndView finishedProjects(){
        ModelAndView view = new ModelAndView("export/export-project-if-finished");
        view.addObject("projectsIfFinished", this.projectService.exportFinishedProjects());

        return view;
    }

    @GetMapping("/employees-above")
    public ModelAndView employeesWithAgeAbove(){
        ModelAndView view = new ModelAndView("export/export-employees-with-age");
        view.addObject("employeesAbove", this.employeeService.exportEmployeesWithAgeAbove());

        return view;
    }
}
