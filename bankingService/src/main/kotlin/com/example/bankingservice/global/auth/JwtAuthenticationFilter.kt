package com.example.bankingservice.global.auth

import com.example.bankingservice.global.enum.JWTExceptionCode
import com.example.bankingservice.service.auth.CustomUserDetailsService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.security.SignatureException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtAuthenticationFilter(
    private val tokenProvider: JwtTokenProvider,
    private val customUserDetailsService: CustomUserDetailsService,
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = getJwtFromRequest(request)
            if (StringUtils.hasText(jwt)) {
                val claims = tokenProvider.getUserIdFromToken(jwt).getOrThrow()
                val userDetails: UserDetails = claims["id"]?.let {
                    customUserDetailsService.loadUserByUserId(
                        it.toString().toLong()
                    )
                } ?: throw JwtException("Failed to retrieve user details from JWT token")
                val authentication =
                    UsernamePasswordAuthenticationToken(
                        userDetails.username,
                        null,
                        userDetails.authorities
                    )
                authentication.details = userDetails
                SecurityContextHolder.getContext().authentication = authentication
                filterChain.doFilter(request, response)
            } else {
                setErrorResponse(request, response, JWTExceptionCode.MISSING_TOKEN)
            }
        } catch (e: ExpiredJwtException) {
            setErrorResponse(request, response, JWTExceptionCode.EXPIRED_TOKEN)
        } catch (e: MalformedJwtException) {
            setErrorResponse(request, response, JWTExceptionCode.MALFORMED_TOKEN)
        } catch (e: SignatureException) {
            setErrorResponse(request, response, JWTExceptionCode.INVALID_SIGNATURE_TOKEN)
        } catch (e: JwtException) {
            setErrorResponse(request, response, JWTExceptionCode.UNKNOWN_ERROR)
        } catch (e: Exception) {
            setErrorResponse(request, response, JWTExceptionCode.UNKNOWN_ERROR)
        }
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    @Throws(JwtException::class)
    private fun setErrorResponse(
        request: ServletRequest,
        response: ServletResponse,
        jwtExceptionCode: JWTExceptionCode
    ) {
        val errorMessage = "Error during JWT authentication: " + jwtExceptionCode.message
        val httpServletResponse = response as HttpServletResponse
        httpServletResponse.status = HttpServletResponse.SC_UNAUTHORIZED
        httpServletResponse.contentType = MediaType.APPLICATION_JSON_VALUE
        httpServletResponse.writer.write(errorMessage)
    }
}