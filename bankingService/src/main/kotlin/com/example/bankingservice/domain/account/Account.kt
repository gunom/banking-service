package com.example.bankingservice.domain.account

import com.example.bankingservice.domain.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "account")
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(name = "account_number", nullable = false)
    var accountNumber: String,

    @Column(name = "asset", nullable = false)
    var asset: Long = 0,

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp()")
    var createdAt: LocalDateTime = LocalDateTime.now(),
)
