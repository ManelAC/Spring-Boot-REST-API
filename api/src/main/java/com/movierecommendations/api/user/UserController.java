package com.movierecommendations.api.user;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Returns a list of all the users.")
    @GetMapping("/getallusers")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

    @Operation(summary = "Returns the id of the user with a specific email.")
    @GetMapping("/getuserid/{email}")
	public Long getSpecificUserId(@Valid @PathVariable("email") @Parameter(description = "Email of the user.") String email) {
		return userService.getSpecificUserId(email);
	}

    @Operation(summary = "Adds a new user into the database based on the information provided.")
    @PostMapping("/addnewuser")
    public User newUser(@Valid @RequestBody @Parameter(description = "Data of the new user.") User user) {
        return userService.addNewUser(user);
    }

    @Operation(summary = "Updates the values of the corresponding user.")
    @PutMapping("/updateuser/{id}")
    public User updateUser(@Valid @PathVariable("id") @Parameter(description = "Id of the user to be updated.") Long id, 
        @Valid @RequestBody @Parameter(description = "Updated data of the user.") User user) {
        return userService.updateUser(id, user);
    }

    @Operation(summary = "Deletes the corresponding user from the database.")
    @DeleteMapping("/deleteuser/{id}")
    public void deleteUser(@Valid @PathVariable("id") @Parameter(description = "Id of the user to be deleted.") Long id) {
        userService.deleteUser(id);
    }
}
