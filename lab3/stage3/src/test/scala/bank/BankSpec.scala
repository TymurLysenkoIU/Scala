package bank

import bank.app.{Command, parseCommand}
import bank.errors.Error.{ParseError, TransactionError}
import org.scalatest.{FlatSpec, Matchers}

class BankSpec extends FlatSpec with Matchers {
  import BankSpec.submitInput

  "random input" should "result in error" in {
    val actual = submitInput(List("Monad is just a monoid in a category of endofunctors, please don't take me to the mental hospital"))
    val expected = (BankState(Map.empty, List.empty), List("Unknown input: Monad is just a monoid in a category of endofunctors, please don't take me to the mental hospital"), false)
    actual shouldEqual expected
  }

  "exit" should "exit the program" in {
    val actual = submitInput(List("exit"))
    val expected = (BankState(Map.empty, List.empty), List("Bye!"), true)
    actual shouldEqual expected
  }

  "open account in USD" should "open a dollar account with ID 0" in {
    val actual = submitInput(List("open account in USD"))
    val expected = (
      BankState(
        Map(
          0 -> Account(0, Currency.USD, List(AccountAction.Creation()))
        ),
        List(BankAction.AccountCreation(0, Currency.USD))
      ),
      List("New account in USD, ID 0"),
      false)
    actual shouldEqual expected
  }

  "open account in RUB" should "open a dollar account with ID 0" in {
    val actual = submitInput(List("open account in RUB"))
    val expected = (
      BankState(
        Map(
          0 -> Account(0, Currency.RUB, List(AccountAction.Creation()))
        ),
        List(BankAction.AccountCreation(0, Currency.RUB))
      ),
      List("New account in RUB, ID 0"),
      false)
    actual shouldEqual expected
  }

  "open account in non-existing currency" should "result in error" in {
    val actual = submitInput(List("open account in EUR"))
    val expected = (
      BankState(
        Map(),
        List()
      ),
      List(
      """Only the following currencies are supported:
        |RUB
        |USD.""".stripMargin
      ),
      false)
    actual shouldEqual expected
  }

  "open account in RUB and USD" should "open account in RUB with ID 0 and account un USD with ID 1" in {
    val actual = submitInput(List("open account in RUB", "open account in USD"))
    val expected = (
      BankState(
        Map(
          0 -> Account(0, Currency.RUB, List(AccountAction.Creation())),
          1 -> Account(0, Currency.USD, List(AccountAction.Creation()))
        ),
        List(
          BankAction.AccountCreation(1, Currency.USD),
          BankAction.AccountCreation(0, Currency.RUB)
        )
      ),
      List(
        "New account in RUB, ID 0",
        "New account in USD, ID 1"
      ),
      false)
    actual shouldEqual expected
  }

  "deposit money to account" should "increase the balance of the account" in {
    val actual = submitInput(List("open account in RUB", "deposit 42.42 RUB to account 0"))
    val rubAccount = Account(42.42, Currency.RUB, List(
      AccountAction.Deposition(42.42, Currency.RUB),
      AccountAction.Creation(),
    ))
    val expected = (
      BankState(
        Map(
          0 -> rubAccount,
        ),
        List(
          BankAction.Deposition(0, 42.42, rubAccount),
          BankAction.AccountCreation(0, Currency.RUB),
        )
      ),
      List(
        "New account in RUB, ID 0",
        "Deposited 42.42 RUB to account 0, new balance: 42.42 RUB",
      ),
      false)
    actual shouldEqual expected
  }

  "deposit money of another currency" should "result in error" in {
    val actual = submitInput(List("open account in RUB", "deposit 42.42 USD to account 0"))
    val rubAccount = Account(0, Currency.RUB, List(
      AccountAction.Creation(),
    ))
    val expected = (
      BankState(
        Map(
          0 -> rubAccount,
        ),
        List(
          BankAction.AccountCreation(0, Currency.RUB),
        )
      ),
      List(
        "New account in RUB, ID 0",
        "Cannot deposit USD to RUB account",
      ),
      false)
    actual shouldEqual expected
  }

  // TODO: withdrawal

  // TODO: transfer
}

object BankSpec {
  def submitInput(inputs: List[String]): (BankState, List[String], Boolean) = {
    inputs.foldLeft((BankState(Map.empty, List.empty), List.empty[String], false)) { (state, input) =>
      val (currentBankState, outputList, isExited) = state
      if (isExited)
        state
      else {
        parseCommand(input) match {
          case Left(ParseError(message)) =>
            (currentBankState, outputList ::: List(message), false)
          case Right(Command.Exit) =>
            (currentBankState, outputList ::: List("Bye!"), true)
          case Right(Command.Help) =>
            (currentBankState, outputList ::: List(
              """open account in $currency
                |  Creates new account in the specified $currency and reports its ID.
                |
                |deposit $amount $currency to account $accountID
                |  Deposits the specified $amount of money to the account with ID
                |  $accountID.
                |
                |withdraw $amount $currency from account $accountID
                |  Withdraw the specified $amount of money to the account with ID
                |  $accountID.
                |
                |transfer $amount $currency from account $accountID to account $accountID
                |  Transfers the specified $amount of money from the specified
                |  account with the $accountID to the another specified with another
                |  $accountID.
                |
                |history of account $accountID
                |  Prints the history of the specified account with the $accountID.
                |
                |Supported currencies are:
                |  RUB
                |  USD
                |""".stripMargin),
            false)
          case Right(Command.PrintHistory(accountId)) =>
            currentBankState.historyOfAccount(accountId) match {
              case Some(accountHistory) => (currentBankState, outputList ::: List(accountHistory), false)
              case _ => (currentBankState, outputList ::: List(s"Account with $accountId does not exist"), false)
            }
          case Right(Command.TransactionCommand(transaction)) =>
            currentBankState.applyTransaction(transaction) match {
              case Left(TransactionError(message)) =>
                (currentBankState, outputList ::: List(message), false)
              case Right(updatedState) =>
                (updatedState,
                updatedState
                  .history
                  .headOption
                  .map{ lastAction => outputList ::: List(lastAction.toString) }
                  .getOrElse(outputList),
                false)
            }
        }
      }
    }
  }
}
