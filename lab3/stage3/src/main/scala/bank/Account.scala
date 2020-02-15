package bank

case class Account(balance: BigDecimal, currency: Currency, history: List[AccountAction]) {
  def historyString: String =
    history.map { "- " + _.toString }.mkString("\n")
}
