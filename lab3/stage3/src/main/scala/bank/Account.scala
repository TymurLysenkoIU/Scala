package bank

import bank.AccountAction.{Creation, Deposition}
import bank.errors.Error.TransactionError

case class Account(
  balance: BigDecimal,
  currency: Currency,
  history: List[AccountAction] = List(Creation())
) {
  def historyString: String = history.reverse.map { "- " + _.toString }.mkString("\n")

  def deposit(amount: BigDecimal, depositCurrency: Currency): Either[TransactionError, Account] =
    if (depositCurrency == currency)
      Right(Account(
        balance + amount,
        currency,
        Deposition(amount, currency) :: history
      ))
    else
      Left(TransactionError(
        s"Cannot deposit $depositCurrency to $currency account"
      ))

  def withdrawal(amount: BigDecimal, withdrawalCurrency: Currency): Either[TransactionError, Account] =
    if (withdrawalCurrency == currency)
      if (balance >= amount)
        Right(Account(
          balance - amount,
          currency,
          AccountAction.Withdrawal(amount, currency) :: history
        ))
      else
        Left(TransactionError(
          s"Cannot withdrawal $amount from the account. The current balance is: $balance"
        ))
    else
      Left(TransactionError(
        s"Cannot withdrawal $withdrawalCurrency from $currency account"
      ))

  def transfer(amount: BigDecimal, transferCurrency: Currency, toAccount: Account): Either[TransactionError, (Int, Int) => (Account, Account)] =
    if (transferCurrency == currency)
      if (transferCurrency == toAccount.currency)
        if (balance >= amount)
          Right(
            (fromAccountId, toAccountId) => {
              (Account(balance - amount, currency, AccountAction.TransferTo(amount, currency, toAccountId) :: history),
               Account(balance + amount, currency, AccountAction.TransferFrom(amount, currency, fromAccountId) :: history))
            }
          )
        else
          Left(TransactionError(
            s"Cannot transfer $amount from the account. The current balance is: $balance"
          ))
      else
        Left(TransactionError(
          s"Cannot transfer $transferCurrency to $currency account"
        ))
    else
      Left(TransactionError(
        s"Cannot transfer $transferCurrency from $currency account"
      ))
}
