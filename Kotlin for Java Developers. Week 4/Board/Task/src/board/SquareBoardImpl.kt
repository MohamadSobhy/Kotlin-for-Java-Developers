package board

class SquareBoardImpl(private val boardWidth: Int) : SquareBoard {
    override val width: Int
        get() = boardWidth

    private val cells: Array<Array<Cell>>

    init {
        cells = Array(width) { i -> Array(width) { j -> Cell(i + 1, j + 1) } }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {

        if (i in 1..width && j in 1..width) return cells[i - 1][j - 1]

        return null
    }

    override fun getCell(i: Int, j: Int): Cell {
        require(i in 1..width && j in 1..width) { "Cell isn't exist on the board" }

        return cells[i - 1][j - 1]
    }

    override fun getAllCells(): Collection<Cell> {
        return cells.map { it.toList() }.flatten()
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
//        return cells[i-1].withIndex().filter { (index, _) -> (index+1) in jRange }.map { it.value }

        val row = mutableListOf<Cell>()

        for (j in jRange) {
            if (j > width) break
            row.add(cells[i-1][j-1])
        }
        return row
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val column = mutableListOf<Cell>()

        for (i in iRange) {
            if (i > width) break
            column.add(cells[i-1][j-1])
        }
        return column
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        fun isCellOnBoard(i: Int): Boolean {
            if (i == 0 || i > width) return false
            return true
        }

        return when (direction) {
            Direction.DOWN -> if (isCellOnBoard(this.i + 1)) cells[this.i][this.j - 1] else null
            Direction.UP -> if (isCellOnBoard(this.i - 1)) cells[this.i - 2][this.j - 1] else null
            Direction.LEFT -> if (isCellOnBoard(this.j - 1)) cells[this.i - 1][this.j - 2] else null
            Direction.RIGHT -> if (isCellOnBoard(this.j + 1)) cells[this.i - 1][this.j] else null
        }
    }
}