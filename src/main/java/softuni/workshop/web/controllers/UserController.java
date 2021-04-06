package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.UserService;
import softuni.workshop.web.models.UserRegisterModel;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // /users/register ----> url
//    user/register ----> filepath to html file in templates folder
    @GetMapping("/register")
    public ModelAndView register(){
        return new ModelAndView("user/register");
    }
    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("user/login");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(UserRegisterModel urm){
        if (!urm.getPassword().equals(urm.getConfirmPassword())){
            return super.redirect("/users/registers");
        }

        this.userService.registerUser(urm);
        return super.redirect("/users/login");
    }

}
