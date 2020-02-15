package tymur.lysenko

package object order {
  def max[T](ordering: Ordering[T])(first: T, other: T*): T = {
    other.foldLeft(first) { (m, c) =>
      ordering.compare(m, c) match {
        case Ordering.Lesser => c
        case _               => m
      }
    }
  }
}
