package dz.couture.book.util

object Dzd {
    fun format(amountDzd: Long): String = "%,d DZD".format(amountDzd).replace(',', ' ')
    fun balance(priceDzd: Long, depositDzd: Long): Long = (priceDzd - depositDzd).coerceAtLeast(0)
}

object Validate {
    fun clientName(name: String): Boolean = name.trim().length >= 2
    fun measurement(v: Float): Boolean = v in 0f..300f
    fun money(v: Long): Boolean = v >= 0
}
