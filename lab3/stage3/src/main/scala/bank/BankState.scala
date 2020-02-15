package bank

import bank.Transaction.Deposit
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
            Account(0, currency)
          ),
          BankAction.AccountCreation(newAccountID, currency) :: history
        ))
      case Transaction.Deposit(accountID, amount, currency) =>
        getAccountOrError(accountID).flatMap{ account =>
          account.deposit(amount, currency).map{ updatedAccount =>
            BankState(
              accounts.updated(accountID, updatedAccount),
              BankAction.Deposition(accountID, amount, updatedAccount) :: history
            )
          }
        }
      case Transaction.Withdraw(accountID, amount, currency) =>
        getAccountOrError(accountID).flatMap{ account =>
          account.withdrawal(amount, currency).map{ updatedAccount =>
            BankState(
              accounts.updated(accountID, updatedAccount),
              BankAction.Withdrawal(accountID, amount, updatedAccount) :: history
            )
          }
        }
      case _ => Left(TransactionError("Unknown transaction"))
    }

  def historyOfAccount(id: Int): Option[String] =
    accounts.get(id).map{ account =>
      s"Operations of account $id (${account.currency}):\n" +
      account.historyString
    }

  private def getAccountOrError(accountID: Int): Either[TransactionError, Account] =
    accounts.get(accountID).map{ Right(_) }.getOrElse(Left(TransactionError(
      s"Account $accountID does not exist"
    )))

//  def map(f: => ): BankState

//  def flatMap(f: => BankState): BankState
}

object BankState {
  implicit val keyOrdering: Ordering[(Int, Account)] = new Ordering[(Int, Account)] {
    override def compare(x: (Int, Account), y: (Int, Account)): Int = x._1 compareTo y._1
  }
}
