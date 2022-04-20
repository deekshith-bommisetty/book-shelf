package com.deekshith.bookshelf.controller;

import com.deekshith.bookshelf.config.jwt.JwtUtils;
import com.deekshith.bookshelf.config.service.UserDetailsImpl;
import com.deekshith.bookshelf.model.ERole;
import com.deekshith.bookshelf.model.Role;
import com.deekshith.bookshelf.model.User;
import com.deekshith.bookshelf.payload.request.LoginRequest;
import com.deekshith.bookshelf.payload.request.ProfileRequest;
import com.deekshith.bookshelf.payload.request.SignupRequest;
import com.deekshith.bookshelf.payload.response.JwtResponse;
import com.deekshith.bookshelf.payload.response.MessageResponse;
import com.deekshith.bookshelf.payload.response.profileResponse;
import com.deekshith.bookshelf.repository.RoleRepository;
import com.deekshith.bookshelf.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserServiceImpl userService;

    // @desc    Update user
    // @route   PUT /api/users/:id
    // @access  Private/Admin
    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id) {
        // unable to update security context
        User user = userService.getUser(id);
        if(user == null){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Unauthorized profile update!"));
        }
        return ResponseEntity.ok(userService.saveUser(user));
    }

    // @desc    Get user by ID
    // @route   GET /api/users/:id
    // @access  Private/Admin
    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        User user = userService.getUser(id);
        if(user == null){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Internal server error"));
        }
        return ResponseEntity.ok(user);
    }

    // @desc    Delete user
    // @route   DELETE /api/users/:id
    // @access  Private/Admin
    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new MessageResponse("user deletion successful"));
    }

    // @desc    Get all users
    // @route   GET /api/users
    // @access  Private/Admin
    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUsers() {
        List<User> userList = userService.getUsers();
        return ResponseEntity.ok(userList);
    }

    // @desc    Update user profile
    // @route   PUT /api/users/profile
    // @access  Private
    @RequestMapping(value = "/api/users/profile", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserProfile(@RequestBody ProfileRequest profileRequest) {
    // unable to update security context
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user =  userService.updateUser(profileRequest);
        if(user == null){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Unauthorized profile update!"));
        }
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new profileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                roles));
             //   jwt));
    }

    // @desc    Get user profile
    // @route   GET /api/users/profile
    // @access  Private
    @RequestMapping(value = "/api/users/profile", method = RequestMethod.GET)
    public ResponseEntity<?> getUserProfile() {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new profileResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    // @desc    Auth user & get token
    // @route   POST /api/users/login
    // @access  Public
    @RequestMapping(value = "/api/users/login", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =  new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    // @desc    Register a new user
    // @route   POST /api/users
    // @access  Public
    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (!Objects.isNull(userService.getUserByEmail(signUpRequest.getEmail()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        User user = new User(signUpRequest.getName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = userService.getRoleByName(ERole.ROLE_USER);
            if(userRole == null){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Role is not found."));
            }
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        User savedUser = userService.saveUser(user);
        savedUser.setPassword("encrypted");
        return ResponseEntity.ok(savedUser);
    }
}
