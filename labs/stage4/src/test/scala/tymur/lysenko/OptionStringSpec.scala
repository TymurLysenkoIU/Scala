package tymur.lysenko

import org.scalatest.{FlatSpec, Matchers}

class OptionStringSpec extends FlatSpec with Matchers {
  "SomeString map" should "apply f to the stored value" in {
    val actual = SomeString(" hello world ") map { _.trim }
    val expected = SomeString("hello world")
    actual shouldEqual expected
  }

  "SomeString flatMap" should "enable composition of SomeString-s" in {
    val actual = SomeString("hello") flatMap { s =>
      SomeString(" world") map { s + _ }
    }
    val expected = SomeString("hello world")
    actual shouldEqual expected
  }

  "SomeString filter success" should "return the same SomeString with the original string" in {
    val actual = SomeString("original") filter { _.nonEmpty }
    val expected = SomeString("original")
    actual shouldEqual expected
  }

  "SomeString filter failure" should "return NoneString" in {
    val actual = SomeString("original") filter { _ => 1 == 0 }
    val expected = NoneString()
    actual shouldEqual expected
  }

  "SomeString getOrElse" should "return the given string" in {
    val expected = "string"
    val actual = SomeString(expected).getOrElse("other string")
    actual shouldEqual expected
  }

  "NoneString map" should "discard f and return NoneString" in {
    val actual = NoneString() map { _.trim }
    val expected = NoneString()
    actual shouldEqual expected
  }

  "NoneString flatMap" should "enable composition of NoneString-s resulting in the return NoneString" in {
    val actual = NoneString() flatMap { s =>
      NoneString() map { s + _ }
    }
    val expected = NoneString()
    actual shouldEqual expected
  }

  "NoneString filter success" should "return NoneString" in {
    val actual = NoneString() filter { _ => 1 == 1 }
    val expected = NoneString()
    actual shouldEqual expected
  }

  "NoneString filter failure" should "return NoneString" in {
    val actual = NoneString() filter { _ => 1 == 0 }
    val expected = NoneString()
    actual shouldEqual expected
  }

  "NoneString getOrElse" should "return the fallback string" in {
    val expected = "fallback"
    val actual = NoneString().getOrElse("fallback")
    actual shouldEqual expected
  }

  "SomeString flatMap NoneString" should "return NoneString" in {
    val actual = SomeString("hello") flatMap { _ => NoneString() }
    val expected = NoneString()
    actual shouldEqual expected
  }
}
