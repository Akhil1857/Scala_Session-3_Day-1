import scala.collection.mutable
import scala.util.Random

case class Transactions(transactionId: Long, accountNumber: Long, transactionType: String, amount: Double)

class BankDetails {
  private val accounts: mutable.Map[Long, Double] = mutable.Map()

  //creates the account of the new user.
  def createAccount(openingBalance: Double): mutable.Map[Long, Double] = {
    val accountNumber = Random.nextLong().abs
    accounts += (accountNumber -> openingBalance)
    accounts
  }

  def listAccounts(): mutable.Map[Long, Double] = {
    accounts
  }

  //fetchAccountBalance() will return the account balance.
  def fetchAccountBalance(accountNumber: Long): Double = {
    accounts.getOrElse(accountNumber, 0.0)
  }

  //updateBalance() is used to perform the Debit/Credit
  def updateBalance(transactions: List[Transactions]): mutable.Map[Long, Double] = {
    transactions.foreach(transaction => {
      val accountNumber = transaction.accountNumber
      val transactionAmount = transaction.amount
      transaction.transactionType match {
        case "credit" => val newBalance = fetchAccountBalance(accountNumber) + transactionAmount
          accounts += (accountNumber -> newBalance)

        case "debit" => val currentBalance = fetchAccountBalance(accountNumber)
          if (currentBalance >= transactionAmount) {
            val newBalance = currentBalance - transactionAmount
            accounts += (accountNumber -> newBalance)
          }
          else throw new IllegalArgumentException("Not sufficient Amount")
        case _ => throw new IllegalArgumentException("Please Enter Valid Transaction type Debit/Credit")
      }
    })
    accounts
  }

  // deleteAccount deletes the account of the user.
  def deleteAccount(accountNumber: Long): Boolean = {
    if (accounts.contains(accountNumber)) {
      accounts -= accountNumber
      true
    }
    else false
  }
}