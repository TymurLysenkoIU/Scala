package bank.app

import bank.Transaction

sealed trait Command

object Command {
  case object Exit                                    extends Command
  case object Help                                    extends Command
  final case class PrintHistory(id: Int)              extends Command
  final case class TransactionCommand(t: Transaction) extends Command
}
