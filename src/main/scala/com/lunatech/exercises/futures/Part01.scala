package com.lunatech.exercises.futures

import scala.concurrent.{ blocking, Future }
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Elementary exercises on futures.
 * 
 * Fill out all the ??? in the file.
 */
object Part01 {
  
  /**
   * Example: Create a future that contains the value 5.
   */
  def exercise1: Future[Int] = Future.successful { 5 }
    
  
  /**
   * Exercise: Create a future that runs `expensiveComputation`
   * on the calling thread.
   * 
   * NOTE: This is not what you'd normally want in an application!
   */
  def exercise2: Future[Int] = ???
  
  /**
   * Exercise: Create a future that runs `expensiveComputation`
   * asynchronously.
   * 
   * Note: This is what you'd normally want.
   */
  def exercise3: Future[Int] = ???
  
  /**
   * Exercise: Return the product of the values in the futures
   * getX and getY. Use `map` and/or `flatMap`.
   * 
   * Don't worry about whether it's parallel or sequential.
   * 
   * Additional questions: 
   * - What happens if you use only `map`?
   * - What happens if you use only `flatMap`?
   */
  def exercise4: Future[Int] = ???

  /**
   * Exercise: Same as exercise4, but use a for-comprehension.
   */
  def exercise5: Future[Int] = ??? 

  /**
   * Exercise: Same as exercise4, with `map` and `flatMap`,
   * but, since they are independent, make sure they execute
   * in parallel.
   */
  def exercise6: Future[Int] = ???
    
  /**
   * Exercise: Same as exercise6, but with a for-comprehension.
   */
  def exercise7: Future[Int] = ???
  
  /**
   * To express intent (parallelism) better, we 
   * can also use Applicative for composition. Various variations
   * are available from the Scalaz library.
   */
  import scalaz.std.scalaFuture._
  import scalaz.syntax.applicative._
  
  /**
   * Example: With the |@| operator we can create an Applicative expression.
   * When done, we can use the 'tupled' method to apply it.
   * 
   * The result is a Future containing the tuple of the results.
   */
  def exercise8: Future[Int] = {
    val tupleFuture = (getX |@| getY).tupled
    tupleFuture.map { case (x, y) => x * y }
  }
  
  /**
   * Example: Instead of 'tupled', you can also pass in a function to combine
   * the results yourself.
   */
  def exercise9: Future[Int] = 
    (getX |@| getY){ _ * _ }   
  
  /**
   * Compute the credit rating for a customer, based on his id.
   * This requires both sequential and parallel composition
   * in one method. You can use the following methods, that are
   * defined in this object:
   * 
   * def getUser(userId: String): Future[User]
   * def getAccounts(user: User): Future[Seq[Account]]
   * def getAddress(user: User): Future[Address]
   * def getCreditRating(user: User, accounts: Seq[Account], address: Address)
   */
  def exercise10(userId: String): Future[CreditRating] = ???
  
  //
  // Methods that the exercises use
  //
  def expensiveComputation: Int = {
    Thread.sleep(30)
    42
  }
  
  def getX: Future[Int] = Future { blocking { Thread.sleep(30) }; 5 }
  def getY: Future[Int] = Future { blocking { Thread.sleep(30) }; 4 }
  
  trait User
  trait CreditRating
  trait Account
  trait Address
  
  def getUser(userId: String): Future[User] = Future {
    blocking { Thread.sleep(30) }
    new User {}
  }
  def getAccounts(user: User): Future[Seq[Account]] = Future {
    blocking { Thread.sleep(30) }
    Nil
  }
  
  def getAddress(user: User): Future[Address] = Future {
    blocking { Thread.sleep(30) }
    new Address {}
  }
  def getCreditRating(user: User, accounts: Seq[Account], address: Address): Future[CreditRating] = Future {
    blocking { Thread.sleep(30) }
    new CreditRating {}
  }
}