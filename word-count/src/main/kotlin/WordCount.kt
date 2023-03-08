import java.util.regex.Pattern
object WordCount {

    private val PUNCT_SYMBOLS = Pattern.compile("[!\"#$%&()*+,-./:;<=>?@\\[\\]^_{|}~]")
    private val ALL_PUNCT_SYMBOLS = Pattern.compile("[!\"#$%&()*+,-./:;<=>?@\\[\\]'^_{|}~]")

    fun phrase(phrase: String): Map<String, Int> {
        val inputPh = PUNCT_SYMBOLS.matcher(phrase).replaceAll(" ")
        val inputPhrase = inputPh.trim().split("\\s++".toRegex()).map {
            var first = ALL_PUNCT_SYMBOLS.matcher(it[0].toString()).find()
            var last = ALL_PUNCT_SYMBOLS.matcher(it[it.length - 1].toString()).find()
            if(first || last) {
                ALL_PUNCT_SYMBOLS.matcher(it).replaceAll("").lowercase()
            } else {
                it.lowercase()
            }
        }

        val m = inputPhrase.groupBy { it }.run {
            this.mapValues { e -> e.value.size }
        }

        return m
    }
}
