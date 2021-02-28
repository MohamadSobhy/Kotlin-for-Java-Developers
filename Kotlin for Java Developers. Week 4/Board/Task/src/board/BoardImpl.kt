package board

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)

fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(createSquareBoard(width))
