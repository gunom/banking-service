package com.example.bankingservice.global.auth

import com.example.bankingservice.global.exception.JwtAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val message: String? = if (authException is JwtAuthenticationException) {
            authException.message
        } else {
            "Authentication failed: " + authException.message
        }
        val error = "{\"error\": \"$message\"}"
        response.writer.write(error)
    }
}