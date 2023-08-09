import entities.Category
import entities.Transaction
import java.util.*
import kotlin.math.abs

class Application {

    companion object {
        // Удобства ради
        const val CATEGORY_COFFEE = 0
        const val CATEGORY_PRODUCTS = 1
        const val CATEGORY_SUBSCRIPTIONS = 2
    }

    private val categories = mutableListOf(Category("Coffee"), Category("Products"), Category("Subscriptions"))

    private val transactions = mutableListOf(
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

    private val categoriesSummAverage: MutableMap<Category, Double> = mutableMapOf()

    init {
        calculateCategoriesSummAverage()
    }

    private fun calculateCategoriesSummAverage() {
        categories.forEach { category ->
            calculateSummAverageForCategory(category)
        }
    }

    private fun calculateSummAverageForCategory(category: Category) {
        val transactions = transactions.filter { it.category == category }.map { it.amount }
        categoriesSummAverage[category] = transactions.average()
    }

    fun getCategoryBySumm(summ: Double): List<Category> {
        var result: Pair<Category?, Double> = null to summ
        var resultByStrongEquality: Category? = null

        // Выбираем наиболее подходящую категорию по среднему расходу во всех категориях
        var minDiff = Double.MAX_VALUE
        categoriesSummAverage.entries.forEach { entry ->
            val diff = abs(entry.value - summ)

            if (diff < minDiff) {
                minDiff = diff
                result = entry.key to entry.value
            }
        }

        // Выясняем, есть ли транзакция, в которой сумма полностью соответствует введенной пользователем
        transactions.forEach { transaction ->
            if (transaction.amount == summ) {
                resultByStrongEquality = transaction.category
            }
        }

        // Если в какой-то транзакции есть расход полностью соответствующий введенному (220.0 == 220.0),
        // то берем эту категорию и выводим ее первым вариантом, как наиболее подходящую.
        // Вторым выводим ту, в которой средний расход наиболее близок к введенному числу.
        return listOfNotNull(resultByStrongEquality, if (result.first == resultByStrongEquality) null else result.first)
    }

    fun createCategory(name: String) {
        categories.add(Category(name))
    }

    fun addTransaction(category: Category, summ: Double) {
        // Каждый раз при добавлении транзакции пересчитываем средний расход в этой категории
        transactions.add(Transaction(UUID.randomUUID(), summ, category))
        calculateSummAverageForCategory(category)
    }
}