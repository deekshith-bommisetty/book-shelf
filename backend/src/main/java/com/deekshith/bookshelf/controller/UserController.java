package com.deekshith.bookshelf.controller;

import com.deekshith.bookshelf.config.jwt.JwtUtils;
import com.deekshith.bookshelf.config.service.UserDetailsImpl;
import com.deekshith.bookshelf.config.service.UserDetailsServiceImpl;
import com.deekshith.bookshelf.model.ERole;
import com.deekshith.bookshelf.model.Role;
import com.deekshith.bookshelf.model.User;
import com.deekshith.bookshelf.payload.request.LoginRequest;
import com.deekshith.bookshelf.payload.request.ProfileRequest;
import com.deekshith.bookshelf.payload.request.SignupRequest;
import com.deekshith.bookshelf.payload.response.JwtResponse;
import com.deekshith.bookshelf.payload.response.MessageResponse;
import com.deekshith.bookshelf.payload.response.profileResponse;
import com.deekshith.bookshelf.service.RoleServiceImpl;
import com.deekshith.bookshelf.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

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
                    .body(new MessageResponse("Unable to fetch user"));
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
        return ResponseEntity.ok(new MessageResponse("user  successfully deleted !"));
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
    public ResponseEntity<?> updateUserProfile(HttpServletRequest request, @RequestBody ProfileRequest profileRequest) {
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
        UserDetails updatedUserDetails = userDetailsService.loadUserByUsername(user.getEmail());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(updatedUserDetails, null,
                updatedUserDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(new profileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                roles,
                jwt));
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
            for (String role : strRoles){
                if (role == "admin") {
                    Role adminRole = roleService.getRoleName(ERole.ROLE_ADMIN);
                    if(Objects.isNull(adminRole)){
                        return ResponseEntity
                                .badRequest()
                                .body(new MessageResponse("Error: Role is not found."));
                    }
                    roles.add(adminRole);
                } else {
                    Role userRole = roleService.getRoleName(ERole.ROLE_USER);
                    if(Objects.isNull(userRole)){
                        return ResponseEntity
                                .badRequest()
                                .body(new MessageResponse("Error: Role is not found."));
                    }
                    roles.add(userRole);
                }
            }
            }
        user.setRoles(roles);
        userService.saveUser(user);
        return ResponseEntity.ok(new MessageResponse("You have successfully registered!!"));
    }
}
