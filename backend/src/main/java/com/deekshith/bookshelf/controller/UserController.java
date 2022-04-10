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
import com.deekshith.bookshelf.repository.UserRepository;
import com.deekshith.bookshelf.service.UserService;
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
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") String id) {

        User user = userService.retrieveUser(id);

        //User user = userService.updateUser(user, profileRequest);
        if(user == null){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Unauthorized profile update!"));
        }
        //List<String> roles = userDetails.getAuthorities().stream()
          //      .map(item -> item.getAuthority())
            //    .collect(Collectors.toList());
        return ResponseEntity.ok(userRepository.save(user));
    }

    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        // Check for admin role later
        User user = userService.retrieveUser(id);
        if(user == null){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Internal server error"));
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new MessageResponse("user deletion successful"));
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getUsers() {
        // Check for admin role later
        List<User> userList = userService.retrieveALlUsers();
        return ResponseEntity.ok(userList);
    }

    @RequestMapping(value = "/api/users/profile", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserProfile(@RequestBody ProfileRequest profileRequest) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user =  userService.updateUser(userDetails.getId().toString(), profileRequest);
        if(user == null){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Unauthorized profile update!"));
        }
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getEmail());
        System.out.println(userDetails.getPassword());
        System.out.println("USER OBJECT");
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        System.out.println("Authentication Object");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        System.out.println(usernamePasswordAuthenticationToken);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        System.out.println(authentication);

        //SecurityContextHolder.getContext().setAuthentication(authentication);
        //String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(new profileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                roles));
                //jwt));
    }

    @RequestMapping(value = "/api/users/profile", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
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

    @RequestMapping(value = "/api/users/login", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =  new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        System.out.println(usernamePasswordAuthenticationToken);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        System.out.println(authentication);
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

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (!Objects.isNull(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create new user's account
        User user = new User(signUpRequest.getName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
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
        return ResponseEntity.ok(userRepository.save(user));
    }
}
