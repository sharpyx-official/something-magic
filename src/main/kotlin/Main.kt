import java.util.UUID
import kotlin.math.abs

const val CATEGORY_COFFEE = 0
const val CATEGORY_PRODUCTS = 1
const val CATEGORY_SUBSCRIPTIONS = 2

val categories = listOf(Category("Coffee"), Category("Products"), Category("Subscriptions"))

val transactions = listOf(
    Transaction(UUID.randomUUID(), 230.0, categories[CATEGORY_COFFEE]),
    Transaction(UUID.randomUUID(), 180.0, categories[CATEGORY_COFFEE]),
    Transaction(UUID.randomUUID(), 270.0, categories[CATEGORY_COFFEE]),
    Transaction(UUID.randomUUID(), 1267.5, categories[CATEGORY_PRODUCTS]),
    Transaction(UUID.randomUUID(), 851.2, categories[CATEGORY_PRODUCTS]),
    Transaction(UUID.randomUUID(), 200.2, categories[CATEGORY_PRODUCTS]),
    Transaction(UUID.randomUUID(), 550.0, categories[CATEGORY_SUBSCRIPTIONS]),
    Transaction(UUID.randomUUID(), 259.0, categories[CATEGORY_SUBSCRIPTIONS]),
    Transaction(UUID.randomUUID(), 159.0, categories[CATEGORY_SUBSCRIPTIONS]),
    Transaction(UUID.randomUUID(), 99.0, categories[CATEGORY_SUBSCRIPTIONS]),
)

fun calculateCategorySummAverage(): Map<Category, Double> {
    val categoriesAverageSumm: MutableMap<Category, Double> = mutableMapOf()

    categories.forEach { category ->
        val transactionList = transactions.filter { it.category == category }.map { it.amount }
        categoriesAverageSumm[category] = transactionList.average()
    }

    println(categoriesAverageSumm)

    return categoriesAverageSumm
}

fun getCategoryBySumm(summ: Double): List<Category> {
    val categoriesAverageSumm = calculateCategorySummAverage()

    var min = Double.MAX_VALUE
    var result: Pair<Category?, Double> = null to summ
    var resultByStrongEquality: Category? = null

    categoriesAverageSumm.entries.forEach { entry ->
        val diff = abs(entry.value - summ)

        if (diff < min) {
            min = diff
            result = entry.key to entry.value
        }
    }

    transactions.forEach { transaction ->
        if (transaction.amount == summ) {
            resultByStrongEquality = transaction.category
        }
    }

    return listOfNotNull(resultByStrongEquality, if (result.first == resultByStrongEquality) null else result.first)
}

fun main(args: Array<String>) {
    while (true) {
        val inputSumm = readlnOrNull()?.toDoubleOrNull() ?: return
        val category = getCategoryBySumm(inputSumm)

        println(category)
    }
}