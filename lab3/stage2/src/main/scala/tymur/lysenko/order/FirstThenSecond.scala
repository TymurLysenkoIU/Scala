package tymur.lysenko.order

/**
  * Represents an order of tuples. First compares by the first value, and in case
  * of equality uses the second value for the comparison.
  */
case class FirstThenSecond[A, B](oa: Ordering[A], ob: Ordering[B]) extends Ordering[(A, B)] {
  override def compare(a: (A, B), b: (A, B)): Ordering.Result =
    Some(oa.compare(a._1, b._1)).filter { _ != Ordering.Equal }
      .getOrElse(ob.compare(a._2, b._2))
}
