package com.example.bankingservice.global.auth

import com.example.bankingservice.domain.user.SecurityUser
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.sql.Timestamp


@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}")
    private val jwtSecret: String,
    @Value("\${jwt.access-expires-in}")
    private val jwtExpirationInMs: Long
) {
    fun generateToken(authentication: Authentication): String {
        val userPrincipal = authentication.details as SecurityUser
        val now = System.currentTimeMillis()
        val expiryDate = Timestamp(now + jwtExpirationInMs * 1000)

        val claims = Jwts.claims()
        val key = Keys.hmacShaKeyFor(jwtSecret.toByteArray().copyOf(64))
        claims["id"] = userPrincipal.getUserId()

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Timestamp(now))
            .setIssuer("Morgan")
            .setExpiration(expiryDate)
            .setSubject("userInfo")
            .setHeaderParam("typ", "JWT")
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun getUserIdFromToken(token: String?): Result<Claims> {
        return kotlin.runCatching {
            Jwts.parserBuilder()
                .setSigningKey(jwtSecret.toByteArray().copyOf(64))
                .build()
                .parseClaimsJws(token)
                .body
        }.onFailure {
            return Result.failure(it)
        }

    }

    fun validateToken(authToken: String?): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(jwtSecret.toByteArray().copyOf(64))
                .build()
                .parseClaimsJws(authToken)
                .body
            return true
        } catch (ex: Exception) {
            throw RuntimeException()
        }
    }
}