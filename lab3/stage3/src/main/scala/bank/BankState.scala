package bank

import bank.AccountAction.Creation
import bank.errors.Error.TransactionError

final case class BankState(accounts: Map[Int, Account], history: List[BankAction]) {
  import BankState.keyOrdering

  def applyTransaction(transaction: Transaction): Either[TransactionError, BankState] =
    transaction match {
      case Transaction.CreateAccount(currency) =>
        val newAccountID = accounts.maxOption.map(_._1 + 1).getOrElse(0)

        Right(BankState(
          accounts.updated(
            newAccountID,
            Account(0, currency, List(Creation()))
          ),
          BankAction.AccountCreation(newAccountID, currency) :: history
        ))
//      case
      case _ => Left(TransactionError("Unknown transaction"))
    }

  def historyOfAccount(id: Int): Option[String] =
    accounts.get(id).map{ account =>
      s"Operations of account $id (${account.currency}):\n" +
      account.historyString
    }

//  def map(f: => ): BankState

//  def flatMap(f: => BankState): BankState
}

object BankState {
  implicit val keyOrdering: Ordering[(Int, Account)] = new Ordering[(Int, Account)] {
    override def compare(x: (Int, Account), y: (Int, Account)): Int = x._1 compareTo y._1
  }
}
