package bank

sealed trait Transaction

object Transaction {
  final case class CreateAccount(currency: Currency) extends Transaction
  final case class Deposit(accountID: Int, amount: BigDecimal, currency: Currency) extends Transaction
  final case class Withdraw(accountID: Int, amount: BigDecimal, currency: Currency) extends Transaction
}
