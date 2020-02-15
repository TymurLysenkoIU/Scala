package bank

import bank.app.{Command, parseCommand}
import bank.errors.Error.{ParseError, TransactionError}
import org.scalatest.{FlatSpec, Matchers}

class BankSpec extends FlatSpec with Matchers {
  
}

object BankSpec {
  def executionLoop(inputs: List[String]): (BankState, List[String], Boolean) = {
    inputs.foldLeft((BankState(Map.empty, List.empty), List.empty[String], false)) { (state, input) =>
      val (currentBankState, outputList, isExited) = state
      if (isExited)
        state
      else {
        parseCommand(input) match {
          case Left(ParseError(message)) =>
            (currentBankState, outputList :+ message, false)
          case Right(Command.Exit) =>
            (currentBankState, outputList :+ "Bye!", true)
          case Right(Command.Help) =>
            (currentBankState, outputList :+
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
                |""".stripMargin,
            false)
          case Right(Command.PrintHistory(accountId)) =>
            currentBankState.historyOfAccount(accountId) match {
              case Some(accountHistory) => (currentBankState, outputList :+ accountHistory, false)
              case _ => (currentBankState, outputList :+ s"Account with $accountId does not exist", false)
            }
          case Right(Command.TransactionCommand(transaction)) =>
            currentBankState.applyTransaction(transaction) match {
              case Left(TransactionError(message)) =>
                (currentBankState, outputList :+ message, false)
              case Right(updatedState) =>
                (updatedState,
                updatedState
                  .history
                  .headOption
                  .map{ lastAction => outputList :+ lastAction.toString }
                  .getOrElse(outputList),
                false)
            }
        }
      }
    }
  }
}
