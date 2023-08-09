package entities

import java.util.UUID

data class Transaction(
    val id: UUID,
    val amount: Double,
    val category: Category
)
