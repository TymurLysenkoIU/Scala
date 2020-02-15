package tymur.lysenko.order

/**
  * Represents an order of lists by the number of elements.
  */
case object ListOrdering extends Ordering[List[Any]] {
  override def compare(a: List[Any], b: List[Any]): Ordering.Result =
    IntOrdering.compare(a.length, b.length)
}
