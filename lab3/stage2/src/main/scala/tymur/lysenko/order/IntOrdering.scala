package tymur.lysenko.order

/**
  * Represents an order of natural numbers by their value.
  */
case object IntOrdering extends Ordering[Int] {
  override def compare(a: Int, b: Int): Ordering.Result =
    if (a > b)
      Ordering.Greater
    else if (a < b)
      Ordering.Lesser
    else
      Ordering.Equal
}
