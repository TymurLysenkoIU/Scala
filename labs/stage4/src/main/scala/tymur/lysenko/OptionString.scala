package tymur.lysenko

sealed trait OptionString {
  def map(f: String => String): OptionString
  def flatMap(f: String => OptionString): OptionString
  def filter(f: String => Boolean): OptionString
  def getOrElse(fallback: String): String
}

final case class SomeString(value: String) extends OptionString {
  override def map(f: String => String): OptionString = SomeString(f(value))

  override def flatMap(f: String => OptionString): OptionString = f(value)

  override def filter(f: String => Boolean): OptionString =
    if (f(value)) this else NoneString()

  override def getOrElse(fallback: String): String = value
}

final case class NoneString() extends OptionString {
  override def map(f: String => String): OptionString = this

  override def flatMap(f: String => OptionString): OptionString = this

  override def filter(f: String => Boolean): OptionString = this

  override def getOrElse(fallback: String): String = fallback
}
