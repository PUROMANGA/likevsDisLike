package org.example.boardproject.api.admin.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.boardproject.api.admin.dto.RequestLogin;

public interface AdminService {
    void loginAdminService(RequestLogin requestLogin, HttpServletRequest request, HttpServletResponse response);
    void logoutAdminService(HttpServletRequest request, HttpServletResponse response);
}
