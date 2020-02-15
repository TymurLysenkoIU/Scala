package bank.app
import bank.BankState
import bank.errors.Error.{ParseError, TransactionError}

import scala.annotation.tailrec
import scala.io.StdIn

object Main extends App {
  @tailrec def mainLoop(state: BankState): Unit = {
    print("> ")
    val input = StdIn.readLine()
    val newState = parseCommand(input) match {
      case Left(ParseError(message)) =>
        println(message)
        state
      case Right(Command.Exit) =>
        println("Bye!")
        return
      case Right(Command.Help) =>
        println(
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
            |""".stripMargin)
        state
      case Right(Command.PrintHistory(accountId)) =>
        state.historyOfAccount(accountId) match {
          case Some(accountHistory) => println(accountHistory)
          case _ => println(s"Account with $accountId does not exist")
        }
        state
      case Right(Command.TransactionCommand(transaction)) =>
        state.applyTransaction(transaction) match {
          case Left(TransactionError(message)) =>
            println(message)
            state
          case Right(updatedState) =>
            updatedState.history.headOption.foreach{ println }
            updatedState
        }
    }

    mainLoop(newState)
  }

  mainLoop(BankState(Map.empty, List.empty))
}
