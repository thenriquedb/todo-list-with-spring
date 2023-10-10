package br.com.thenriquedb.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserRepository userRepository;

    @GetMapping
    public  String get() {
        return "Any user";
    }

    @PostMapping
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if(user != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        var createdUser =  this.userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
