package org.example.boardproject.api.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.admin.dto.RequestLogin;
import org.example.boardproject.api.admin.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestBody @Valid RequestLogin requestLogin,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {
        adminService.loginAdminService(requestLogin, request, response);
        return ResponseEntity.ok("로그인이 완료되었습니다.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutAdmin(HttpServletRequest request, HttpServletResponse response) {
        adminService.logoutAdminService(request, response);
        return ResponseEntity.ok("로그아웃이 완료되었습니다.");
    }
    
}
