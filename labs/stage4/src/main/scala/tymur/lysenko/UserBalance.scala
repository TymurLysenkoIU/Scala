package tymur.lysenko

case class UserBalance(rubles: Int, dollars: Int, euros: Int) {
  def +(other: UserBalance): UserBalance = UserBalance(
      rubles  + other.rubles,
      dollars + other.dollars,
      euros   + other.euros
    )

  def -(other: UserBalance): UserBalance = UserBalance(
      rubles  - other.rubles,
      dollars - other.dollars,
      euros   - other.euros
    )

  def unary_-(): UserBalance = UserBalance(
    -rubles,
    -dollars,
    -euros
  )
}
