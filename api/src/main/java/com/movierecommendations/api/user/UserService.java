package com.movierecommendations.api.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Autowired
    private UserConverter userConverter;

    public UserDTO saveUser(UserDTO userDTO) {
        User user = userConverter.convertDTOToEntity(userDTO);
        user = userRepository.save(user);
        return userConverter.convertEntityToDTO(user);
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

    public UserDTO addNewUser(UserDTO userDTO) {
        Optional<User> userWithSameEmail = userRepository.findByEmailAllIgnoreCase(userDTO.getEmail());

        if(userWithSameEmail.isPresent()) {
            throw new IllegalStateException("User not created, this email is already in use.");
        }

        if(userDTO.getName() == null) {
            throw new IllegalStateException("User not created, name can not be null.");
        }
        else if(userDTO.getName().length() < 1) {
            throw new IllegalStateException("User not created, name's length can't be 0.");
        }

        if(userDTO.getEmail() == null) {
            throw new IllegalStateException("User not created, email can not be null.");
        }
        else if(userDTO.getEmail().length() < 5) {
            throw new IllegalStateException("User not created, email's length can't be smaller than 5.");
        }

        return saveUser(userDTO);
    }

    @Transactional
    public UserDTO updateUser(Long userId, UserDTO updatedUserDTO) {
        User userFromDatabase = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("No user has the ID: " + userId));

        if(updatedUserDTO == null) {
            throw new IllegalStateException("User not updated, the updated user object is null.");
        }

        if(updatedUserDTO.getName() != null && updatedUserDTO.getName().length() >= 1) {
            userFromDatabase.setName(updatedUserDTO.getName());
        }
        else {
            // We do nothing since the name doesn't have to be updated.
        }

        if(updatedUserDTO.getEmail() != null && updatedUserDTO.getEmail().length() == 0) {
            // We do nothing since the email doesn't have to be updated.
        }
        else if(updatedUserDTO.getEmail() != null && updatedUserDTO.getEmail().length() >= 1 && updatedUserDTO.getEmail().length() < 5) {
            // We throw exception when the updated email is between 1 and 4 characters long,
            // but not when is 0 characters long since that means we are simply not updating it.
            throw new IllegalStateException("User not updated, email's length can't be smaller than 5.");
        }
        else {
            // This is the case in which the new email exists and has a valid length.

            Optional<User> userWithSameEmail = userRepository.findByEmailAllIgnoreCase(updatedUserDTO.getEmail());

            if(userWithSameEmail.isPresent() && (userWithSameEmail.get().getUserId() != userId)) {
                throw new IllegalStateException("User not updated, the email " + updatedUserDTO.getEmail() + " is already in use.");
            }
            else {
                userFromDatabase.setEmail(updatedUserDTO.getEmail());
            }
        }

        User userToReturn = userRepository.save(userFromDatabase);

        return userConverter.convertEntityToDTO(userToReturn);
    }

    public void deleteUser(Long userId) {
        if(userRepository.existsById(userId) == false) {
            throw new IllegalStateException("No user has the ID: " + userId);
        }

        userRepository.deleteById(userId);
    }
}
