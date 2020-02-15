package tymur.lysenko.order

/**
  * Represents a lexicographical order of strings.
  */
case object StringOrdering extends Ordering[String] {
  override def compare(a: String, b: String): Ordering.Result =
    a.compareTo(b) match {
      case x if x > 0 => Ordering.Greater
      case x if x < 0 => Ordering.Lesser
      case _          => Ordering.Equal
    }
}
