package dz.couture.book.util

import org.junit.Assert.*
import org.junit.Test

class DzdTest {
    @Test fun balance_normal() { assertEquals(7000L, Dzd.balance(10000, 3000)) }
    @Test fun balance_floorZero() { assertEquals(0L, Dzd.balance(5000, 9000)) }
    @Test fun format_containsDZD() { assertTrue(Dzd.format(10000).contains("DZD")) }
    @Test fun name_valid() { assertTrue(Validate.clientName("Ahmed")) }
    @Test fun name_invalid() { assertFalse(Validate.clientName(" ")) }
    @Test fun measurement_ok() { assertTrue(Validate.measurement(96f)) }
    @Test fun measurement_bad() { assertFalse(Validate.measurement(999f)) }
    @Test fun money_ok() { assertTrue(Validate.money(0)) }
}
