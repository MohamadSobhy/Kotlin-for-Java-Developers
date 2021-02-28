package games.gameOfFifteen

import board.*
import games.game.Game
import games.game2048.Game2048Initializer
import games.game2048.addNewValue
import games.game2048.moveAndMergeEqual

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game {
    return GameOfFifteen(initializer)
}


class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game{
    private val board: GameBoard<Int?> = createGameBoard(4)

    override fun initialize() {
        board.addValues(initializer)
    }

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean {
        val values = board.getAllCells().mapNotNull { board[it] }

        println("End Values: $values")

        return values == values.sortedBy{it}
    }

    override fun processMove(direction: Direction) {
        board.moveValuesFifteen(direction)
    }

    override fun get(i: Int, j: Int): Int? {
        return board[Cell(i, j)]
    }
}


fun GameBoard<Int?>.addValues(initializer: GameOfFifteenInitializer) {
    val permutations = initializer.initialPermutation

    println(permutations)

    var index = 0

    for (i in 1..width){
        for(j in 1..width){
            if(index < permutations.size)
            this[Cell(i, j)] = permutations[index]
            index++
        }
    }

    getAllCells().map { print("${this[it]}, ") }
    println()
}

fun GameBoard<Int?>.moveValuesFifteen(direction: Direction) {
    val emptyCell = this.getAllCells().first { this[it] == null }

    if(direction == Direction.LEFT){
        swapValues(Cell(emptyCell.i, emptyCell.j+1), emptyCell)
    }else if(direction == Direction.RIGHT){
        swapValues(Cell(emptyCell.i, emptyCell.j-1), emptyCell)
    }else if(direction == Direction.UP){
        swapValues(Cell(emptyCell.i+1, emptyCell.j), emptyCell)
    }else{
        swapValues(Cell(emptyCell.i-1, emptyCell.j), emptyCell)
    }
}

fun GameBoard<Int?>.swapValues(cellToBeUpdated: Cell, emptyCell: Cell) {
    val temp = this[cellToBeUpdated]
    this[cellToBeUpdated] = this[emptyCell]
    this[emptyCell] = temp
}