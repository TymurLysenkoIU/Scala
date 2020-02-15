package tymur.lysenko.order

/**
  * Represents an order of tuples. First compares by the second value, and in
  * case of equality uses the first value for the comparison.
  */
case class SecondThenFirst[A, B](ob: Ordering[B], oa: Ordering[A]) extends Ordering[(A, B)] {
  private val ordering                                        = FirstThenSecond(ob, oa)
  override def compare(a: (A, B), b: (A, B)): Ordering.Result = ordering.compare(a.swap, b.swap)
}
