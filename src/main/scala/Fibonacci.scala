import scala.annotation.tailrec
import scala.math.BigInt

object Fibonacci {
  /**
   * Naive recursive implementation of the fibonacci function.
   *
   * It has a severe performance problem in that it branches
   * recursively and exponentially with respect to `n`. It also
   * re-computes several values in the sequence many times
   * for no good reason.
   */
  def fibonacci(n: Int): BigInt = n match {
    case 1 => BigInt(1)
    case 2 => BigInt(1)
    case _ => fibonacci(n - 1) + fibonacci(n - 2)
  }

  /**
   * Better performing, iterative implementation with an
   * old-fashioned for-loop.
   */
  def fibonacci2(n: Int): BigInt = n match {
    case 1 => BigInt(1)
    case 2 => BigInt(1)
    case _ => {
      var x = BigInt(1)
      var y = BigInt(1)
      for (_ <- 0 to (n - 3)) {
        val oldy = y
        y = x + y
        x = oldy
      }
      y
    }
  }

  /**
   * More idiomatic, tail-recursive implementation.
   * `recur` gets optimized back into a loop.
   */
  def fibonacci3(n: Int): BigInt = n match {
    case 1 => BigInt(1)
    case 2 => BigInt(1)
    case _ => {
      @tailrec
      def recur(x: BigInt, y: BigInt, m: Int): BigInt = m match {
        case 0 => y
        case _ => recur(y, x + y, m - 1)
      }
      recur(BigInt(1), BigInt(1), n - 2)
    }
  }
}