package rs.raf.demo.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.User;
import rs.raf.demo.requests.UpdateUserRequest;
import rs.raf.demo.services.impl.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserRestController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@RequestParam("userId") Long id){
        Optional<User> optionalUser = userService.findById(id);
        if(optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        }

        throw new EntityNotFoundException();
    }

    @GetMapping(value = "/loginuser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLoginUser(){
        UserDetails optionalUser = userService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(optionalUser != null) {
            return ResponseEntity.ok(optionalUser);
        }

        throw new EntityNotFoundException();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user){
        List<User> allUsers = userService.findAll();
        for(User u : allUsers){
            if (u.getUsername().equals(user.getUsername())){
                return ResponseEntity.status(HttpStatus.valueOf(403)).build();
            }
        }
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody UpdateUserRequest updatedUser){
        Optional<User> optionalUser = userService.findById(updatedUser.getUserId());
        if (optionalUser.isPresent()){
            optionalUser.get().setUsername(updatedUser.getUsername());
            optionalUser.get().setFirstName(updatedUser.getFirstName());
            optionalUser.get().setLastName(updatedUser.getLastName());
            optionalUser.get().setPermissions(updatedUser.getPermissions());
            return ResponseEntity.ok(userService.save(optionalUser.get()));
        }

        throw new EntityNotFoundException();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        throw new EntityNotFoundException();
    }

}
