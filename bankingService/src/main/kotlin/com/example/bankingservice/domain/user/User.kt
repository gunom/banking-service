package com.example.bankingservice.domain.user

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)