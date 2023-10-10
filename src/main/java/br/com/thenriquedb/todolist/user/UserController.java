package br.com.thenriquedb.todolist.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping
    public  String get() {
        return "Any user";
    }

    @PostMapping
    public void create(@RequestBody UserModel userModel) {
        System.out.println(userModel.getName());
        System.out.println(userModel.getUsername());
        System.out.println(userModel.getPassword());
    }
}
