package bank.errors

sealed trait Error {
  val message: String
}

object Error {
  final case class TransactionError(message: String) extends Error
  final case class ParseError(message: String)       extends Error
}
