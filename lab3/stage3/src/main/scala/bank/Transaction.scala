package bank

sealed trait Transaction

object Transaction {
  final case class CreateAccount(currency: Currency) extends Transaction
}
