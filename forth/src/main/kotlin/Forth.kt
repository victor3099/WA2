import java.lang.Exception

class Forth {

    private var parametersPassed: ArrayDeque<String> = ArrayDeque()
    private val userDefinedFunction by lazy {
        val userDefinedFunction = mutableMapOf<String?, ArrayDeque<String>>()
        for(x in parametersPassed) {

            val definedWords = ArrayDeque(x.trim().split("\\s+".toRegex()).filter { it != ":" && it != ";" })
            val wordDefined = definedWords.removeFirst()

            for((i, e) in definedWords.withIndex()) {
                if(userDefinedFunction.contains(e)) {
                    definedWords[i] = userDefinedFunction[e]!!.joinToString(" ")
                }
            }
            userDefinedFunction[wordDefined.lowercase()] = definedWords
        }
        userDefinedFunction
    }
    private fun checkListParam(parameters: List<String>): ArrayDeque<Int> {

        val stackOperands = ArrayDeque(parameters.filter { it.all { c -> c.isDigit() } }.toList().map { it.toInt() })
        val operatorsFun = parameters.filter{ !it.all { c -> c.isDigit() } }.map { it.lowercase() }.toList()

        if(parameters.first() == ":") {
            throw Exception("illegal operation")
        }

        for(x in operatorsFun) {
            if(!checkDefaultFunction(x)) {
                throw Exception("undefined operation")
            }
        }

        if(operatorsFun.isEmpty()) {
            return ArrayDeque(parameters.filter { it.all { c -> c.isDigit() } }.toList().map { it.toInt() })
        }

        if(stackOperands.size == 1 && !(operatorsFun.contains("dup") || operatorsFun.contains("drop"))) {
            throw Exception("only one value on the stack")
        }
        if(stackOperands.isEmpty()) {
            throw Exception("empty stack")
        }
        return ArrayDeque()
    }
    private fun checkDefaultFunction(operation: String): Boolean {
        return when(operation.lowercase()) {
            "+" -> true
            "-" -> true
            "/" -> true
            "*" -> true
            "swap" -> true
            "over" -> true
            "drop" -> true
            "dup" -> true
            else -> false
        }
    }
    private fun evaluateOperands(parametersPassed: ArrayDeque<String>): List<String> {
        val tmpValue = parametersPassed
            .removeLast()
            .split("\\s+".toRegex())
            .toList()
            .map { it.lowercase()  }

        val finalOperands = ArrayDeque<String>()
        for(x in tmpValue) {
            if(userDefinedFunction.contains(x)) {
                for(i in userDefinedFunction[x]!!.iterator()) {
                    finalOperands.add(i.lowercase())
                }
            } else {
                finalOperands.add(x.lowercase())
            }
        }
        return finalOperands
    }
    private fun duplicate(res: ArrayDeque<Int>) = res.add(res.last())
    private fun dropping(res: ArrayDeque<Int>) =  res.removeLast()
    private fun over(res: ArrayDeque<Int>) = res.addLast(res[res.size-2])
    private fun swapping(res: ArrayDeque<Int>) {
        val v1 = res.removeLast()
        val v2 = res.removeLast()
        res.addLast(v1)
        res.addLast(v2)
    }
    private fun plus(res: ArrayDeque<Int>) {
        val v1 = res.removeLast()
        val v2 = res.removeLast()
        res.addLast(v1+v2)
    }
    private fun minus(res: ArrayDeque<Int>) {
        val v1 = res.removeLast()
        val v2 = res.removeLast()
        res.addLast(v2-v1)
    }
    private fun mul(res: ArrayDeque<Int>) {
        val v1 = res.removeLast()
        val v2 = res.removeLast()
        res.addLast(v1*v2)
    }
    private fun division(res: ArrayDeque<Int>) {
        val v2 = res.removeLast()
        val v1 = res.removeLast()
        if(v2 == 0) {
            throw Exception("divide by zero")
        }
        res.addLast(v1/v2)
    }
    fun evaluate(vararg line: String): List<Int> {

        this.parametersPassed.addAll(line.toList())
        val operands = evaluateOperands(this.parametersPassed)

        val checkingList = checkListParam(operands)
        if(!checkingList.isEmpty()) {
            return checkingList
        }

        val res = ArrayDeque<Int>()
        for(x in operands) {
            if(x.all { c -> c.isDigit() }) {
                res.add(x.toInt())
            } else {
                when(x.lowercase()) {
                    "dup" -> duplicate(res)
                    "drop" -> dropping(res)
                    "swap" -> swapping(res)
                    "over" -> over(res)
                    "+" -> plus(res)
                    "-" -> minus(res)
                    "*" -> mul(res)
                    "/" -> division(res)
                    else -> {
                        break
                    }
                }
            }
        }

        return res

    }
}
