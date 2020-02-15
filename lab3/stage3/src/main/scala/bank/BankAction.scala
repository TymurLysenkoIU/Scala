package bank

sealed trait BankAction

object BankAction {
  final case class AccountCreation(
    id: Int, currency: Currency
  ) extends BankAction {
    override def toString: String = s"New account in $currency, ID $id"
  }

  final case class Deposition(
    accountID: Int, amount: BigDecimal, account: Account
  ) extends BankAction {
    override def toString: String =
      s"""Deposited $amount ${account.currency}
         |to account $accountID,
         |new balance: ${account.balance} ${account.currency}"""
        .stripMargin.replaceAll("\n", " ")
  }

  final case class Withdrawal(
    accountID: Int, amount: BigDecimal, account: Account
   ) extends BankAction {
    override def toString: String =
      s"""Withdrew $amount ${account.currency}
         |from account $accountID,
         |new balance: ${account.balance} ${account.currency}"""
        .stripMargin.replaceAll("\n", " ")
  }

  final case class MoneyTransfer(
    fromAccountID: Int, toAccountID: Int, amount: BigDecimal,
    fromAccount: Account, toAccount: Account
  ) extends BankAction {
    override def toString: String =
      s"""Transferred $amount ${fromAccount.currency}
         |to account $toAccountID,
         |new account $fromAccountID
         |balance: ${fromAccount.balance} ${fromAccount.currency}"""
        .stripMargin.replaceAll("\n", " ")
  }
}
