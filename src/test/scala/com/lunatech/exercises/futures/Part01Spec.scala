package com.lunatech.exercises.futures

import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.SpanSugar
import scala.concurrent.Future
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.matchers.Matcher
import org.scalatest.matchers.MatchResult
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global

class Part01Spec extends FlatSpec with Matchers with ScalaFutures {
  import Part01._

  "Exercise 1" should "return a future containing 5 in ~30ms" in {
    exercise1.futureValue shouldBe 5
    exercise1 should takeAboutMillis(0)
  }

  "Exercise 2" should "return a future after ~30ms, that completes immediately" in {
    val r = Future { exercise2 }
    r should takeAboutMillis(30)
    r.flatten should takeAboutMillis(0)
  }

  "Exercise 3" should "return a future immediately, that completes after ~30ms" in {
    val r = Future { exercise3 }
    r should takeAboutMillis(0)
    r.flatten should takeAboutMillis(30)
  }

  "Exercise 4" should "return the product of two values in a future" in {
    exercise4.futureValue shouldBe 20
  }

  "Exercise 5" should "return the product of two values in a future" in {
    exercise5.futureValue shouldBe 20
  }

  "Exercise 6" should "run two futures in parallel, combining their results" in {
    val r = Future { exercise6 }
    r should takeAboutMillis(0)
    r.flatten should takeAboutMillis(30)
    r.flatten.futureValue shouldBe 20
  }

  "Exercise 7" should "run two futures in parallel, combining their results" in {
    val r = Future { exercise7 }
    r should takeAboutMillis(0)
    r.flatten should takeAboutMillis(30)
    r.flatten.futureValue shouldBe 20
  }

  "Example 8" should "run two futures in parallel, combining their results" in {
    // This initializes the first Scalaz shizzle and needs some warmup.
    Await.ready(exercise8, Duration(100, "milliseconds"))

    val r = Future { exercise8 }
    r should takeAboutMillis(0)
    r.flatten should takeAboutMillis(30)
    r.flatten.futureValue shouldBe 20
  }

  "Example 9" should "run two futures in parallel, combining their results" in {
    val r = Future { exercise9 }
    r should takeAboutMillis(0)
    r.flatten should takeAboutMillis(30)
    r.flatten.futureValue shouldBe 20
  }

  "Example 10" should "properly compose futures" in {
    val r = Future { exercise10("test") }
    r should takeAboutMillis(0)
    // Allow a bit more delta because of non-jit-optimized code
    r.flatten should takeAboutMillis(90, 20)
  }


  implicit class RichFuture[A](v: Future[Future[A]]) {
    def flatten = v flatMap identity
  }

  def takeAboutMillis(i: Int, delta: Int = 10) = new Matcher[Future[_]] {
    override def apply(v: Future[_]): MatchResult = {
      val start = System.currentTimeMillis()

      Await.result(v, Duration(i + delta, "milliseconds"))

      val duration = System.currentTimeMillis - start
      val min = math.max(0, i - delta)
      val max = i + delta

      MatchResult(duration >= min && duration <= max,
        s"Future did not complete in ~$i milliseconds, but in $duration." ,
        s"Future completed in ~$i milliseconds")
    }
  }

}
