import org.scalatest.funsuite.AnyFunSuite

import scala.collection.mutable
import scala.util.Random

class BankDetailsTestcase extends AnyFunSuite {
  val BankService = new BankDetails
  //Some stored Account in the Bank
  BankService.createAccount(10000)
  BankService.createAccount(20000)
  BankService.createAccount(25000)
  BankService.createAccount(20000)


  val accountsBeforeOperations: mutable.Map[Long, Double] = BankService.listAccounts()
  val accountNumberBeforeOperations: List[Long] = accountsBeforeOperations.keySet.toList

  test("fetchAccountBalance method should return the balance in the account") {
    val currentBalance: Double = BankService.fetchAccountBalance(accountNumberBeforeOperations.head)
    assert(BankService.fetchAccountBalance(accountNumberBeforeOperations.head) == currentBalance)
  }
  test("Credit:-update balance should perform operation based on the list of the transactions") {
    val transaction: List[Transactions] = List(Transactions(Random.nextLong().abs, accountNumberBeforeOperations(1), "credit", 1000))
    val beforeCredit: Double = BankService.fetchAccountBalance(accountNumberBeforeOperations(1))
    val afterCredit: mutable.Map[Long, Double] = BankService.updateBalance(transaction)
    assert(afterCredit(accountNumberBeforeOperations(1)) > beforeCredit)
  }
  test("Debit:-update balance should perform operation based on the list of the transactions") {
    val transaction: List[Transactions] = List(Transactions(Random.nextLong().abs, accountNumberBeforeOperations(2), "debit", 1000))
    val beforeDebit: Double = BankService.fetchAccountBalance(accountNumberBeforeOperations(2))
    val afterDebit: mutable.Map[Long, Double] = BankService.updateBalance(transaction)
    assert(afterDebit(accountNumberBeforeOperations(2)) < beforeDebit)
  }
  test("deleteAccount should safely delete the account") {
    assert(BankService.deleteAccount(accountNumberBeforeOperations(1)) == true)
  }

}
