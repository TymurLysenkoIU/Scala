package tymur.lysenko

import org.scalatest.{FlatSpec, Matchers}

class RationalSpec extends FlatSpec with Matchers {
  "==" should "be correct for 2/1 and 4/2" in {
    val isEqual = Rational(2, 1) == Rational(4, 2)
    isEqual shouldBe true
  }

  "*" should "be 1/8 for 3/4 and 1/6" in {
    val m = Rational(3, 4) * Rational(1, 6)
    val expected = Rational(1, 8)
    m == expected shouldBe true
  }

  "+" should "be 7/12 for 1/3 and 1/4" in {
    val s = Rational(1, 3) + Rational(1, 4)
    val expected = Rational(7, 12)
    s == expected shouldBe true
  }

  "unapply" should "decompose Rational(4, 2) into (2, 1)" in {
    val r = Rational(4, 2)
    val expected = (2, 1)
    val Rational(actual) = r
    actual shouldEqual expected
  }
}
