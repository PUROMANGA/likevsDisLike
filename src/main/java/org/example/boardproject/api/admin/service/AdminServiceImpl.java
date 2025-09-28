package org.example.boardproject.api.admin.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.boardproject.api.admin.dto.RequestLogin;
import org.example.boardproject.api.admin.entity.Admin;
import org.example.boardproject.api.admin.repository.AdminRepository;
import org.example.boardproject.common.error.CustomRuntimeException;
import org.example.boardproject.common.error.ErrorResponseStatus;
import org.example.boardproject.common.security.SecurityHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final SecurityHandler securityHandler;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void loginAdminService(RequestLogin requestLogin, HttpServletRequest request, HttpServletResponse response) {

        Admin admin = adminRepository.findByUsername(requestLogin.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(ErrorResponseStatus.LOGIN_ERROR));

        if (!passwordEncoder.matches(requestLogin.getPassword(), admin.getPassword())) {
            throw new CustomRuntimeException(ErrorResponseStatus.LOGIN_ERROR);
        }

        securityHandler.SetSecurityContext(requestLogin, request, response);
    }

    @Override
    @Transactional
    public void logoutAdminService(HttpServletRequest request, HttpServletResponse response) {
        securityHandler.logoutSecurityContext(request, response);
    }
}
