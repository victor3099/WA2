object Flattener {
    fun flatten(source: Collection<Any?>): List<Any> {
        var l = listOf<Any?>()
        for(x in source) {
            if(x is Collection<*>) {
                l += flatten(x)
            } else if(x == null) {
                continue
            } else {
                l += x
            }
        }
        return l as List<Any>
    }
}
