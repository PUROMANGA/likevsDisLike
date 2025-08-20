package org.example.boardproject.api.admin.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.admin.dto.RequestLogin;
import org.example.boardproject.api.admin.repository.AdminRepository;
import org.example.boardproject.common.security.SecurityHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final SecurityHandler securityHandler;

    @Override
    @Transactional
    public void loginAdminService(RequestLogin requestLogin, HttpServletRequest request, HttpServletResponse response) {
        if(!adminRepository.existsByUsername(requestLogin.getEmail())) {
            throw new RuntimeException("not exists admin");
        }
        securityHandler.SetSecurityContext(requestLogin, request, response);
    }

    @Override
    @Transactional
    public void logoutAdminService(HttpServletRequest request, HttpServletResponse response) {
        securityHandler.logoutSecurityContext(request, response);
    }
}
