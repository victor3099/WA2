data class MinesweeperBoard(val input: List<String>) {

    fun withNumbers(): List<String> {

        if(this.input.isEmpty()) return this.input
        val finalInput = this.input.map {
            val tmpList = ArrayDeque<Int>()
            for((i, x)in it.withIndex()) {
                if(x == ' ')
                    tmpList.add(0)
                else
                    tmpList.add(-1)
            }
            tmpList
        }

        for((i, x) in finalInput.withIndex())
            for((j, y) in x.withIndex())
                if(y == -1) {
                    try {
                        if(finalInput[i - 1][j] != -1)
                            finalInput[i - 1][j] = finalInput[i - 1][j] + 1
                    } catch (e: Exception) {
                        println()
                    }
                    try {
                        if(finalInput[i - 1][j-1] != -1)
                            finalInput[i - 1][j-1] = finalInput[i - 1][j-1] + 1
                    } catch (e: Exception) {
                        println()
                    }
                    try {
                        if(finalInput[i - 1][j+1] != -1)
                            finalInput[i - 1][j+1] = finalInput[i - 1][j+1] + 1
                    } catch (e: Exception) {
                        println()
                    }
                    try {
                        if(finalInput[i + 1][j] != -1)
                            finalInput[i + 1][j] = finalInput[i + 1][j] + 1
                    } catch (e: Exception) {
                        println()
                    }
                    try {
                        if(finalInput[i + 1][j-1] != -1)
                            finalInput[i + 1][j-1] = finalInput[i + 1][j-1] + 1
                    } catch (e: Exception) {
                        println()
                    }
                    try {
                        if(finalInput[i + 1][j+1] != -1)
                            finalInput[i + 1][j+1] = finalInput[i + 1][j+1] + 1
                    } catch (e: Exception) {
                        println()
                    }
                    try {
                        if(finalInput[i][j+1] != -1)
                            finalInput[i][j+1] = finalInput[i][j+1] + 1
                    } catch (e: Exception) {
                        println()
                    }
                    try {
                        if(finalInput[i][j-1] != -1)
                            finalInput[i][j-1] = finalInput[i][j-1] + 1
                    } catch (e: Exception) {
                        println()
                    }

                }

        val finalRes = finalInput.map {
            val tmpString = ArrayDeque<String>()
            for(x in it) {
                if(x == -1)
                    tmpString.add("*")
                else if(x == 0)
                    tmpString.add(" ")
                else
                    tmpString.add(x.toString())
            }
            tmpString.joinToString("")
        }

        return finalRes
    }
}
