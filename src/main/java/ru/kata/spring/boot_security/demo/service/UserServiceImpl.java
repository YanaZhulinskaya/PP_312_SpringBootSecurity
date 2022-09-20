package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(
            readOnly = true
    )
    public User getUserById(Long id) {

        return this.userRepository.getReferenceById(id);
    }

    @Transactional
    public void updateUserById(User user) {
        if (this.userRepository.findByUsername(user.getUsername()) != null && !this.userRepository.findByUsername(user.getUsername()).getId().equals(user.getId())) {
            throw new InvalidParameterException("Dont save user, such email " + user.getUsername());
        } else {
            if (user.getPassword().isEmpty()) {
                user.setPassword((this.userRepository.findById(user.getId()).get()).getPassword());
            } else {
                user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            }

            this.userRepository.save(user);
        }
    }

    @Transactional(
            readOnly = true
    )
    public List<User> getAllUsers() {

        return this.userRepository.findAll();
    }

    @Transactional
    public void addUser(User user) {
        User userFromDb = this.userRepository.findByUsername(user.getUsername());
        if (userFromDb == null) {
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            this.userRepository.save(user);
        }

    }

    @Transactional
    public void deleteUserById(Long id) {
        if (this.userRepository.findById(id).isPresent()) {
            this.userRepository.deleteById(id);
        }

    }

    public User getUserByName(String username) {

        return this.userRepository.findByUsername(username);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        } else {
            return user;
        }
    }
}