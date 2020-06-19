package ru.mativ.lrfbb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController {

//    @Autowired
//    private RoleService roleService;



//    @ModelAttribute("role")
//    public RoleDto roleDto() {
//        return new RoleDto();
//    }

//    public ManagerController(ManagerUserUtil managerUserUtil) {
//        super();
//        this.managerUserUtil = managerUserUtil;
//        managerUserUtil.getOrCreateManagerUser();
//    }

    @GetMapping("/manager")
    public String showManagerPage() {
        return "manager/manager";
    }

//    @GetMapping("/roles")
//    public String manageRoles(Model model) {
//        List<RoleEntity> roles = roleService.getAll();
//        model.addAttribute("roles", roles);
//        return "manager/roles";
//    }

}
