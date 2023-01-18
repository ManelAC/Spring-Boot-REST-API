package com.movierecommendations.api.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
	}

    public Long getSpecificUserId(String email) {
        Optional<User> user = userRepository.findByEmailAllIgnoreCase(email);

        if(user.isPresent()) {
            return user.get().getUserId();
        }
        else {
            return -1L;
        }
    }

    public User addNewUser(User user) {
        Optional<User> userWithSameEmail = userRepository.findByEmailAllIgnoreCase(user.getEmail());

        if(userWithSameEmail.isPresent()) {
            throw new IllegalStateException("User not created, this email is already in use.");
        }

        if(user.getName() == null) {
            throw new IllegalStateException("User not created, name can not be null.");
        }
        else if(user.getName().length() < 1) {
            throw new IllegalStateException("User not created, name's length can't be 0.");
        }

        if(user.getEmail() == null) {
            throw new IllegalStateException("User not created, email can not be null.");
        }
        else if(user.getEmail().length() < 5) {
            throw new IllegalStateException("User not created, email's length can't be smaller than 5.");
        }

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long userId, User updatedUser) {
        User userFromDatabase = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("No user has the ID: " + userId));

        if(updatedUser == null) {
            throw new IllegalStateException("User not updated, the updated user object is null.");
        }

        if(updatedUser.getName() != null && updatedUser.getName().length() >= 1) {
            userFromDatabase.setName(updatedUser.getName());
        }
        else {
            // We do nothing since the name doesn't have to be updated.
        }

        if(updatedUser.getEmail() != null && updatedUser.getEmail().length() == 0) {
            // We do nothing since the email doesn't have to be updated.
        }
        else if(updatedUser.getEmail() != null && updatedUser.getEmail().length() >= 1 && updatedUser.getEmail().length() < 5) {
            // We throw exception when the updated email is between 1 and 4 characters long,
            // but not when is 0 characters long since that means we are simply not updating it.
            throw new IllegalStateException("User not updated, email's length can't be smaller than 5.");
        }
        else {
            // This is the case in which the new email exists and has a valid length.

            Optional<User> userWithSameEmail = userRepository.findByEmailAllIgnoreCase(updatedUser.getEmail());

            if(userWithSameEmail.isPresent() && (userWithSameEmail.get().getUserId() != userId)) {
                throw new IllegalStateException("User not updated, the email " + updatedUser.getEmail() + " is already in use.");
            }
            else {
                userFromDatabase.setEmail(updatedUser.getEmail());
            }
        }

        return userRepository.save(userFromDatabase);
    }

    public void deleteUser(Long userId) {
        if(userRepository.existsById(userId) == false) {
            throw new IllegalStateException("No user has the ID: " + userId);
        }

        userRepository.deleteById(userId);
    }
}
