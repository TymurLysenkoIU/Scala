package bank

import bank.Transaction.{CreateAccount, Deposit, Withdraw}
import bank.errors.Error.ParseError

package object app {
  def parseCommand(input: String): Either[ParseError, Command] = {
    input match {
      case "exit" => Right(Command.Exit)
      case "help" => Right(Command.Help)
      case s"history of account $id" =>
        id.toIntOption.map { i => Right(Command.PrintHistory(i)) }.getOrElse(
          Left(
            ParseError(
              """History of account command has the following format:
                |history of account $id
                |where:
                |  $id is an integer number
                |""".stripMargin
            )
          )
        )
      case s"open account in $currencyName" =>
        Currency
          .fromString(currencyName)
          .map { c => Right(Command.TransactionCommand(CreateAccount(c))) }
          .getOrElse(
            Left(ParseError(
              """Only the following currencies are supported:
                |RUB
                |USD.""".stripMargin
            ))
          )
      case s"deposit $amount $currency to account $accountID" =>
        (for (
          amt <- Try(BigDecimal(amount));
          cur <- Currency.fromString(currency);
          id  <- accountID.toIntOption
        ) yield {
          Right(Command.TransactionCommand(Deposit(id, amt, cur)))
        })
          .getOrElse(Left(ParseError(
            """Deposit command format:
              |deposit $amount $currency to account $accountID
              |where:
              |  $amount is a decimal number with possibly number after decimal point,
              |  $currency is: RUB or USD
              |  $accountID is an integer number
              |""".stripMargin
          )))
      case s"withdraw $amount $currency from account $accountID" =>
        (for (
          amt <- Try(BigDecimal(amount));
          cur <- Currency.fromString(currency);
          id  <- accountID.toIntOption
        ) yield {
          Right(Command.TransactionCommand(Withdraw(id, amt, cur)))
        })
          .getOrElse(Left(ParseError(
            """Withdrawal command format:
              |Withdraw $amount $currency from account $accountID
              |where:
              |  $amount is a decimal number with possibly number after decimal point,
              |  $currency is: RUB or USD
              |  $accountID is an integer number
              |""".stripMargin
          )))
      case s"transfer $amount $currency from account $fromAccountID to account $toAccountID" =>
        for (
          amt <- Try(BigDecimal(amount));
          currency <- Currency.fromString(currency);
          fromID <- fromAccountID.toIntOption;
          toAccountID <- toAccountID.toIntOption
        ) yield {
          ??? // TODO
        }
        ???
      case _ => Left(ParseError(s"Unknown input: $input"))
    }
  }
}
