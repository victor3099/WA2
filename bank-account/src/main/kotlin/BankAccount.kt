import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

class BankAccount {

    var balance = 0
        get() {
            if(!this.open)
                throw IllegalStateException()
            return field
        }

    private var act_balance: AtomicInteger = AtomicInteger(0)
    var open = true

    fun adjustBalance(amount: Long){
        if(open) {
            balance = act_balance.addAndGet(amount.toInt())
        } else {
            throw IllegalStateException()
        }

    }

    fun close() {
        open = false
    }
}
