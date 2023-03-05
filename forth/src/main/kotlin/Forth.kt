import java.lang.Exception

class Forth {

    fun division(op1: Int, op2: Int): Int {
        if(op2 == 0) {
            throw Exception("divide by zero")
        }
        return op1/op2
    }


    fun checkListParam(parameters: List<String>): Boolean {
        val stackOperands = ArrayDeque(parameters.filter { it.all { c -> c.isDigit() } }.toList().map { it.toInt() })
        val operatorsFun = parameters.filter{ !it.all { c -> c.isDigit() } }.map { it.lowercase() }.toList()
        for(x in operatorsFun) {
            if(!checkDefaultFunction(x)) {
                throw Exception("undefined operation")
            }
        }

        if(operatorsFun.isEmpty()) {
            return true
        }

        if(stackOperands.size == 1 && !(operatorsFun.contains("dup") || operatorsFun.contains("drop"))) {
            throw Exception("only one value on the stack")
        }
        if(stackOperands.isEmpty()) {
            throw Exception("empty stack")
        }
        return false
    }

    fun checkDefaultFunction(operation: String): Boolean {
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

    fun evaluate(vararg line: String): List<Int> {

        // Take the first element of Sequence vararg
        //var operands = line.last().split("\\s+".toRegex()).toList()
        var parametersPassed = ArrayDeque(line.toList())
        var operands = parametersPassed.removeLast().split("\\s+".toRegex()).toList()
        operands = operands.map { it.lowercase() }
        var userDefinedFunction = mutableMapOf<String?, ArrayDeque<String>>()

        for(x in parametersPassed) {

            var definedWords = ArrayDeque(x.replace(":", "").replace(";", "").trim().split("\\s+".toRegex()))
            var wordDefined = definedWords.removeFirst()

            for((i, e) in definedWords.withIndex()) {
                if(userDefinedFunction.contains(e)) {
                    definedWords.set(i, userDefinedFunction.get(e)!!.joinToString(" "))
                }
            }

            userDefinedFunction.put(wordDefined.lowercase(), definedWords)

        }

        if(operands.first() == ":") {
            throw Exception("illegal operation")
        }

        var finalOperands = ArrayDeque<String>()
        for(x in operands) {
            if(userDefinedFunction.contains(x)) {
                for(i in userDefinedFunction.get(x)!!.iterator()) {
                    finalOperands.add(i.lowercase())
                }
            } else {
                finalOperands.add(x.lowercase())
            }
        }
        operands = finalOperands
        println(operands)
        val checking = checkListParam(operands)
        if(checking) { return ArrayDeque(operands.filter { it.all { c -> c.isDigit() } }.toList().map { it.toInt() })}


        val res = ArrayDeque<Int>()
        for(x in operands) {
            if(x.all { c -> c.isDigit() }) {
                res.add(x.toInt())
            } else {
                when(x.lowercase()) {
                    "dup" -> {
                        res.add(res.last())
                    }
                    "drop" -> {
                        res.removeLast()
                    }
                    "swap" -> {
                        val v1 = res.removeLast()
                        val v2 = res.removeLast()
                        res.addLast(v1)
                        res.addLast(v2)
                    }
                    "over" -> {
                        res.addLast(res.get(res.size-2))
                    }
                    "+" -> {
                        val v1 = res.removeLast()
                        val v2 = res.removeLast()
                        res.addLast(v1+v2)
                    }
                    "-" -> {
                        val v1 = res.removeLast()
                        val v2 = res.removeLast()
                        res.addLast(v2-v1)
                    }
                    "*" -> {
                        val v1 = res.removeLast()
                        val v2 = res.removeLast()
                        res.addLast(v1*v2)
                    }
                    "/" -> {
                        val v1 = res.removeLast()
                        val v2 = res.removeLast()
                        res.addLast(division(v2, v1))
                    }
                    else -> {
                        break
                    }
                }
            }
        }

        return res

    }
}
