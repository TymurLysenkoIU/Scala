package tymur.lysenko

import org.scalatest.{FlatSpec, Matchers}
import order._

class OrderingSpec extends FlatSpec with Matchers {
  // Provided
  "max of ints" should "find maximal int among other ints" in {
    val actual   = max(IntOrdering)(5, 1, 2, 3)
    val expected = 5
    actual shouldEqual expected
  }

  // Additional
  "max of negative ints" should "find maximal int among other negative ints" in {
    val actual   = max(IntOrdering)(-3, -7, -2, -78)
    val expected = -2
    actual shouldEqual expected
  }

  // Provided
  "max of list" should "find one of the longest list among other lists" in {
    val actual   = max(ListOrdering)(List(1), List(2, 3), List(4))
    val expected = List(2, 3)
    actual shouldEqual expected
  }

  // Additional
  "max of list of any types" should "find one of the longest list among other lists regardless of the types" in {
    val actual   = max(ListOrdering)(List(7), List("lol", "kek"), List('c'))
    val expected = List("lol", "kek")
    actual shouldEqual expected
  }

  // Provided
  "max of strings" should "find the greatest string (lexicographical order)" in {
    val actual   = max(StringOrdering)("kek", "lol", "aaa")
    val expected = "lol"
    actual shouldEqual expected
  }

  // Additional
  "max of strings of different length" should "find the longest string in case of prefix equality" in {
    val actual   = max(StringOrdering)("Monad", "Monadic")
    val expected = "Monadic"
    actual shouldEqual expected
  }

  // Provided
  "max of pairs (first then second)" should "return the pair, greatest element of which is the first, but in case of equality the second" in {
    val actual   = max(FirstThenSecond(IntOrdering, IntOrdering))((1, 1), (1, 2))
    val expected = (1, 2)
    actual shouldEqual expected
  }

  // Provided
  "max of pairs (first then second)" should "return the pair, greatest element of which is the first" in {
    val actual   = max(FirstThenSecond(IntOrdering, IntOrdering))((1, 1), (0, 2))
    val expected = (1, 1)
    actual shouldEqual expected
  }

  // Additional
  "max of pairs (first then second)" should "return the pair, greatest element of which is the first (Additional)" in {
    val actual   = max(FirstThenSecond(IntOrdering, IntOrdering))((1, 1), (1, 2), (7, 0))
    val expected = (7, 0)
    actual shouldEqual expected
  }

  // Provided
  "max of pairs (second then first)" should "return the pair, greatest element of which is the second, but in case of equality the first" in {
    val actual   = max(SecondThenFirst(IntOrdering, IntOrdering))((3, 1), (2, 1))
    val expected = (3, 1)
    actual shouldEqual expected
  }

  // Provided
  "max of pairs (second then first)" should "return the pair, greatest element of which is the second" in {
    val actual   = max(SecondThenFirst(IntOrdering, IntOrdering))((3, 1), (1, 2))
    val expected = (1, 2)
    actual shouldEqual expected
  }

  // Actual
  "max of pairs (second then first)" should "return the pair, greatest element of which is the second (Additional)" in {
    val actual   = max(SecondThenFirst(IntOrdering, IntOrdering))((7, 9), (8, 1), (1, 10))
    val expected = (1, 10)
    actual shouldEqual expected
  }
}
