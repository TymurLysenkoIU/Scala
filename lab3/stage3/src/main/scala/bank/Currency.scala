package bank

sealed trait Currency {
  val stringRepresentation: String
  override def toString: String = stringRepresentation
}

object Currency {
  def fromString(s: String): Option[Currency] = s match {
    case RUB.stringRepresentation => Some(RUB)
    case USD.stringRepresentation => Some(USD)
    case _  => None
  }

  final case object RUB extends Currency {
    val stringRepresentation: String = "RUB"
  }

  final case object USD extends Currency {
    override val stringRepresentation: String = "USD"
  }
}
