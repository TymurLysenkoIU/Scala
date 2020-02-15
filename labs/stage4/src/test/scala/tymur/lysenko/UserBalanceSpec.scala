package tymur.lysenko

import org.scalatest.{FlatSpec, Matchers}

class UserBalanceSpec extends FlatSpec with Matchers {
  "+" should "be UserBalance(10.1, 25.7, 39.3) for UserBalance(10.0, 25.3, 12.9) and UserBalance(0.1, 0.4, 26.4)" in {
    val actual = UserBalance(10, 25, 13) + UserBalance(0, -1, 26)
    val expected = UserBalance(10, 24, 39)
    actual shouldEqual expected
  }

  "-" should "subtract" in {
    val actual = UserBalance(100, -17, 0) - UserBalance(54, -24, 2)
    val expected = UserBalance(46, 7, -2)
    actual shouldEqual expected
  }

  "unary_-" should "negate" in {
    val actual = -UserBalance(10, -12, 17)
    val expected = UserBalance(-10, 12, -17)
      actual shouldEqual expected
  }
}
