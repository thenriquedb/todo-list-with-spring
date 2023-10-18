package br.com.thenriquedb.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "User")
public class UserController {
    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/{id}")
    @Operation(summary = "Get user from ID")
    @ApiResponse(responseCode = "201")
    public  ResponseEntity<Optional<UserModel>> get(@PathVariable UUID id) {
        Optional<UserModel> user = this.userRepository.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "409", description = "Username already exists"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if(user != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        var passwordwordHash = BCrypt
                .withDefaults()
                .hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordwordHash);

        var createdUser =  this.userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
