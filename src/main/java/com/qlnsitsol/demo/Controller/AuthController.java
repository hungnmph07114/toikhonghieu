package com.qlnsitsol.demo.Controller;
import com.qlnsitsol.demo.DTO.JwtDto;
import com.qlnsitsol.demo.DTO.LoginUser;
import com.qlnsitsol.demo.DTO.Message;
import com.qlnsitsol.demo.DTO.UserDTO;
import com.qlnsitsol.demo.Service.RoleService;
import com.qlnsitsol.demo.Service.UserService;
import com.qlnsitsol.demo.entity.Role;
import com.qlnsitsol.demo.entity.User;
import com.qlnsitsol.demo.enums.RoleName;
import com.qlnsitsol.demo.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult){
        System.out.println(userDTO.getEmail());
        System.out.println();
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        if(userService.existsByUserName(userDTO.getUserName()))
            return new ResponseEntity(new Message("tên đó đã tồn tại"), HttpStatus.BAD_REQUEST);
        if(userService.existsByEmail(userDTO.getEmail()))
            return new ResponseEntity(new Message("ese email ya existe"), HttpStatus.BAD_REQUEST);
        User user =
                new User(userDTO.getName(), userDTO.getUserName(), userDTO.getEmail(),
                        passwordEncoder.encode(userDTO.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByRoleName(RoleName.ROLE_USER).get());
        if(userDTO.getRoles().contains("admin"))
            roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity(new Message("Tao thanh cong"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Message("badly placed fields"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword()));// kiem tra user va password
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}
