package tymur.lysenko

class Rational private (val n: Int, val d: Int) {

  private def this(n: Int) = this(n, 1)

  def + (that: Rational): Rational =
    Rational(n * that.d + that.n * d, d * that.d)
  def + (i: Int): Rational = this - Rational(i)

  def - (that: Rational): Rational =
    Rational(n * that.d - that.n * d, d * that.d)
  def - (i: Int): Rational = this - Rational(i)

  def * (that: Rational): Rational =
    Rational(n * that.n, d * that.d)
  def * (i: Int): Rational = this * Rational(i)

  def / (that: Rational): Rational =
    Rational(n * that.d, d * that.n)
  def / (i: Int): Rational = this / Rational(i)

  def == (other: Rational): Boolean =
    (n == other.n) && (d == other.d)

  override def toString: String = n + "/" + d
}

object Rational {
  @scala.annotation.tailrec
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  def apply(n: Int): Rational = new Rational(n)
  def apply(n: Int, d: Int): Rational = {
    require(d != 0)

    val g = gcd(n.abs, d.abs)
    new Rational(n / g, d / g)
  }

  def unapply(r: Rational): Some[(Int, Int)] = Some(r.n, r.d)
}
