package nicestring

fun String.isNice(): Boolean {
    val zipped = this.zipWithNext()

    val firstCondition = listOf("bu", "ba", "be").none{this.contains(it)}

    val secondCondition = this.count { it in "aeoui" } >= 3

    val thirdCondition = zipped.any{it.first == it.second}

    return listOf(firstCondition, secondCondition, thirdCondition).count{it} >= 2
}