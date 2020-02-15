package bank

sealed trait AccountAction

object AccountAction {
  final case class Creation() extends AccountAction {
    override def toString: String = "Creation"
  }

  final case class Deposition(amount: BigDecimal, currency: Currency) extends AccountAction {
    override def toString: String = s"Deposition of $amount $currency"
  }

  final case class Withdrawal(amount: BigDecimal, currency: Currency) extends AccountAction {
    override def toString: String = s"Withdrawal of $amount $currency"
  }

  final case class TransferTo(amount: BigDecimal, currency: Currency, accountID: Int) extends AccountAction {
    override def toString: String = s"Transfer of $amount $currency to account $accountID"
  }

  final case class TransferFrom(amount: BigDecimal, currency: Currency, accountID: Int) extends AccountAction {
    override def toString: String = s"Transfer of $amount $currency from account $accountID"
  }
}
