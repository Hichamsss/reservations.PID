package be.iccbxl.pid.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import be.iccbxl.pid.Model.Role;
import be.iccbxl.pid.Model.Show;
import be.iccbxl.pid.Model.User;
import be.iccbxl.pid.Service.RoleService;
import be.iccbxl.pid.Service.ShowService;
import be.iccbxl.pid.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ShowService showService;

    //--------------DASHBOARD-------------//
    @GetMapping()
    public String dashboard(Model model) {
        List<User> users = userService.getAllUsers();
        int totalUsers = users.size();

        List<Show> shows = showService.getAll();
        int totalShows = shows.size();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalShows", totalShows);

        return "admin/dashboard/dashboard";
    }

    //--------------- USER ----------------//
    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("title", "Users");

        return "admin/user/users";
    }

    @GetMapping("/add-user")
    public String createUser(Model model) {
        User user = User.createInstance(); // Assurez-vous que cette méthode existe dans votre classe User

        List<Role> roles = roleService.getAll(); // Récupérer tous les rôles disponibles
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "admin/user/add-user";
    }

    @PostMapping("/add-user")
    public String store(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                        @RequestParam(name = "roles", required = false) List<String> roleIds, Model model) {
        if (bindingResult.hasErrors()) {
            List<Role> roles = roleService.getAll(); // Récupérer les rôles en cas d'erreur
            model.addAttribute("roles", roles);
            return "admin/user/add-user";
        }

        // Ajouter les rôles à l'utilisateur nouvellement créé
        if (roleIds != null) {
            for (String roleId : roleIds) {
                Role role = roleService.findByRole(roleId);
                if (role != null) {
                    user.addRole(role);
                } else {
                    System.out.println("Le rôle avec l'ID " + roleId + " n'a pas été trouvé.");
                }
            }
        }

        // Utiliser la méthode addUser du service pour ajouter un utilisateur avec mot de passe crypté
        userService.addUser(user);

        return "redirect:/admin/users";
    }

    @GetMapping("users/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id, HttpServletRequest request) {
        User user = userService.getUser(id);

        model.addAttribute("user", user);

        List<Role> roles = roleService.getAll();
        model.addAttribute("roles", roles);

        String referrer = request.getHeader("Referer");

        if (referrer != null && !referrer.equals("")) {
            model.addAttribute("back", referrer);
        } else {
            model.addAttribute("back", "/admin/users");
        }

        return "admin/user/edit";
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @PutMapping("/users/{id}/edit")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        User existing = userService.getUser(id);
        
        if (existing == null) {
            return "redirect:/admin/users";
        }

        existing.setLogin(user.getLogin());
        existing.setFirstname(user.getFirstname());
        existing.setLastname(user.getLastname());
        existing.setEmail(user.getEmail());
        existing.setLangue(user.getLangue());
        existing.getRoles().clear();
        for (Role role : user.getRoles()) {
            existing.addRole(role);
        }

        userService.updateUser(String.valueOf(id), existing);

        return "redirect:/admin/users";
    }


    
    
    
    
    
    
    
    
    
    

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        User existing = userService.getUser(id);

        if (existing != null) {
            // Faire une copie de la liste des rôles
            List<Role> rolesCopy = new ArrayList<>(existing.getRoles());

            // Supprimer les rôles associés en utilisant la copie
            for (Role role : rolesCopy) {
                existing.removeRole(role);
            }

            // Sauvegarder les changements avant suppression
            userService.updateUser(String.valueOf(existing.getId()), existing);

            // Supprimer l'utilisateur
            userService.deleteUserById(id);
        }

        return "redirect:/admin/users";
    }



    //------------- SHOW -------------//
    @GetMapping("/shows")
    public String shows(Model model) {
        List<Show> shows = showService.getAll();

        model.addAttribute("shows", shows);
        model.addAttribute("title", "Shows");

        return "admin/show/shows";
    }

    @GetMapping("/add-show")
    public String createShow(Model model) {
        Show show = Show.createInstance();
        model.addAttribute("show", show);

        return "admin/show/add-show";
    }

    @PostMapping("/add-show")
    public String addShow(@Valid @ModelAttribute("show") Show show, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/show/add-show";
        }

        showService.add(show);

        return "redirect:/admin/shows";
    }

    @GetMapping("/shows/{id}/edit")
    public String editShow(Model model, @PathVariable("id") String id, HttpServletRequest request) {
        Show show = showService.get(id);

        model.addAttribute("show", show);

        // Générer le lien retour pour l'annulation
        String referrer = request.getHeader("Referer");

        if (referrer != null && !referrer.equals("")) {
            model.addAttribute("back", referrer);
        } else {
            model.addAttribute("back", "/admin/shows");
        }

        return "admin/show/edit";
    }

    @PutMapping("/shows/{id}/edit")
    public String updateShow(@Valid @ModelAttribute("show") Show show, BindingResult bindingResult,
                             @PathVariable("id") String id, Model model) {

        if (bindingResult.hasErrors()) {
            return "/admin/show/edit";
        }

        Show existing = showService.get(id);

        if (existing == null) {
            return "redirect:/admin/shows";
        }

        Long indice = Long.parseLong(id);

        show.setId(indice);
        showService.update(String.valueOf(show.getId()), show);

        model.addAttribute("show", show);

        return "redirect:/admin/shows";
    }

    @DeleteMapping("/shows/{id}")
    public String deleteShow(@PathVariable("id") String id, Model model) {
        Show existing = showService.get(id);

        if (existing != null) {
            showService.delete(id);
        }

        return "redirect:/admin/shows";
    }
}
