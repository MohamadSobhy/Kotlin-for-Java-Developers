package board

class GameBoardImpl<T>(private val squareBoard: SquareBoard) : GameBoard<T>, SquareBoard by squareBoard {
    private val gameBoard = mutableMapOf<Cell, T?>()

    init {
        for(cell in squareBoard.getAllCells()){
            gameBoard[cell] = null
        }
    }

    override fun get(cell: Cell): T? {
        return gameBoard[cell]
    }

    override fun set(cell: Cell, value: T?) {
        gameBoard[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return gameBoard.filterValues{ predicate(it) }.keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return gameBoard.filterValues(predicate).keys.firstOrNull()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        println(gameBoard)
        println(gameBoard.values.any(predicate))
        return gameBoard.any{predicate(it.value)}
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        println(gameBoard)
        return gameBoard.all{predicate(it.value)}
    }

}