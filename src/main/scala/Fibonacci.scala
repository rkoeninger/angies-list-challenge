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
  def naiveRecursive(n: Int): Int = n match {
    case 1 => 1
    case 2 => 1
    case _ => naiveRecursive(n - 1) + naiveRecursive(n - 2)
  }

  /**
   * Better performing, iterative implementation with an
   * old-fashioned for-loop.
   */
  def linearIterative(n: Int): Int = n match {
    case 1 => 1
    case 2 => 1
    case _ => {
      var x = 1
      var y = 1
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
  def linearTailRecursive(n: Int): Int = n match {
    case 1 => 1
    case 2 => 1
    case _ => {
      @tailrec
      def recur(x: Int, y: Int, m: Int): Int = m match {
        case 0 => y
        case _ => recur(y, x + y, m - 1)
      }
      recur(1, 1, n - 2)
    }
  }

  /**
   * Constant-time implementation based on the fact that
   * the ratio between successive elements in the sequence
   * approaches the golden ratio.
   */
  def constantTime(n: Int): Int = {
    val sqrt5 = Math.sqrt(5.0)
    val gold = (1.0 + sqrt5) / 2.0
    Math.round(Math.pow(gold, n) / sqrt5).asInstanceOf[Int]
  }
}