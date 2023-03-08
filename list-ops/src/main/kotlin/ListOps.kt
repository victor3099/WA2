fun <T> List<T>.customAppend(list: List<T>): List<T> {
    val myList = ArrayDeque(this)
    for(x in list)
        myList.add(x)
    return myList
}

fun List<Any>.customConcat(): List<Any> {

    var l = listOf<Any>()
    for(x in this) {
        if(x is List<*>) {
             l += (x as List<Any>).customConcat()
        } else
            l += x
    }

    return l


}

fun <T> List<T>.customFilter(predicate: (T) -> Boolean): List<T> {
    val l = mutableListOf<T>()
    for(e in this) {
        if(predicate(e))
            l.add(e)
    }
    return l
}

val List<Any>.customSize: Int get() {
    var c = 0
    for(i in this) {
        c += 1
    }
    return c
}

fun <T, U> List<T>.customMap(transform: (T) -> U): List<U> {
    val l = mutableListOf<U>()
    for(x in this) {
        val tmp = transform(x)
        l.add(tmp)
    }
    return l
}

fun <T, U> List<T>.customFoldLeft(initial: U, f: (U, T) -> U): U {

    var res = initial
    if(this.isEmpty())
        return res
    else if(this.size == 1) {
        return this[0] as U
    } else {
        res = f(this.slice(0..this.size - 1).customFoldLeft(initial, f), res as T)
        return res
    }

}

fun <T, U> List<T>.customFoldRight(initial: U, f: (T, U) -> U): U {
    TODO("Implement this function to complete the task")

}



fun <T> List<T>.customReverse(): List<T> {

    val res = mutableListOf<T>()
    for(x in 0..this.size-1)
        res.add(this[this.size - 1 - x])

    return res
}
