/**
 * ПРИВЕТ! Тут важная информация:
 *
 * Написано чуть ли не на коленке за нехваткой времени. Но идея, в целом, передана.
 * Решение и логика поиска нужной категории описана в методах в классе Application.
 *
 * В данном решении взял за учитываемый параметр только саму сумму расхода, но в рамках "мозгового штурма"
 * были и другие идеи.
 *
 * P.S.'ы
 * 1. Само собой это решение не говорит о том, что все было бы реализовано именно так. Была бы база, были бы
 *    дополнительная(-ые) мета-таблицы в ней и тд, и тп.
 * 2. Упор на производительность в своем решении не делал (только самая базовая из соображений адекватности).
 * 3. Хорошего утра/дня/вечера!
 */
import entities.Category

fun main(args: Array<String>) {
    val application = Application()

    // Просто пример для комментария в методе addTransaction, в классе Application
    application.createCategory("Health")
    application.addTransaction(Category("Health"), 10000.0)

    while (true) {
        val inputSumm = readlnOrNull()?.toDoubleOrNull() ?: return
        val category = application.getCategoryBySumm(inputSumm)

        println(category)
    }
}