class Matrix(private val matrixAsString: String) {

    fun column(colNr: Int): List<Int> {
        val rows = matrixAsString.split("\n")
        val column = mutableListOf<Int>()
        for(x in rows) {
            column.add(x.split(" ")[colNr - 1].toInt())
        }
        return column
    }

    fun row(rowNr: Int): List<Int> {
        val rows = matrixAsString.split("\n")
        return rows[rowNr - 1].split(" ").map { it.toInt() }
    }
}
