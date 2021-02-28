package games.game2048

/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> {
    var isPreviousMatch = false

    val resultList = this.asSequence().mapNotNull { it }.zipWithNext().map {
        if (isPreviousMatch) {
            isPreviousMatch = false

            println("Matching")
            return@map null
        } else {
            if (it.first == it.second) {
                isPreviousMatch = true
                println("Merging")
                return@map merge(it.first)
            } else {
                isPreviousMatch = false

                println("\nSingle")
                return@map it.first
            }
        }
    }.filterNotNull().toMutableList()

    var lastElement: T? = null
    val notNllElements = this.filterNotNull()

    if (notNllElements.isNotEmpty()) {
        lastElement = notNllElements.last()

        println("Last Element: $lastElement")
        if (!isPreviousMatch)
            resultList.add(lastElement)
    }

    println("\n$resultList")

    return resultList
}

