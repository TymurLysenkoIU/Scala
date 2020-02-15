package object bank {
  def Try[A](a: => A): Option[A] = try Some(a) catch { case _: Throwable => None}
}
