package games.gameOfFifteen

import kotlin.random.Random

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        var permutations = sequence {
            for (i in 1..15)
                yield(Random.nextInt(15) + 1)
        }.toList()

        println(permutations)

        while (true){
            if(isEven(permutations)) break

            permutations = permutations.shuffled()
        }

        permutations
    }
}

