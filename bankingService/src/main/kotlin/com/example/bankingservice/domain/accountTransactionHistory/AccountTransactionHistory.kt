package com.example.bankingservice.domain.accountTransactionHistory

import com.example.bankingservice.domain.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "account_transaction_history")
class AccountTransactionHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender", nullable = false)
    var sender: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient", nullable = false)
    var recipient: User,

    @Column(name = "amount", nullable = false)
    var amount: Long,

    @Column(name = "status", nullable = false)
    var status: String,

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp()")
    var createdAt: LocalDateTime = LocalDateTime.now(),
)