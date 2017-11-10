/**
 * These methods were originally implemented using pattern matching,
 * but I re-worked them to use subtype polymorphism. (more idiomatic?)
 */
sealed trait MyList[+A] {
  def map[B](f: A => B): MyList[B]
  def filter(f: A => Boolean): MyList[A]
  def zip[B, C](f: A => B => C)(that: MyList[B]): MyList[C]
  def foldLeft[B](f: B => A => B)(acc: B): B
  def distinct(): MyList[A] = distinct(Set[Any]())
  def distinct(set: Set[Any]): MyList[A]
  def groupBy[B](f: A => B): Map[B, MyList[A]]
}

/**
 * An item in the singly-linked list.
 */
case class MyNode[+A](head: A, tail: MyList[A]) extends MyList[A] {
  def map[B](f: A => B) = MyNode(f(head), tail map f)
  def filter(f: A => Boolean) = if (f(head)) MyNode(head, tail filter f) else tail filter f
  def zip[B, C](f: A => B => C)(that: MyList[B]) = that match {
    case MyEmpty => MyEmpty
    case MyNode(thatHead, thatTail) => MyNode(f(head)(thatHead), tail.zip(f)(thatTail))
  }
  def foldLeft[B](f: B => A => B)(acc: B) = tail.foldLeft(f)(f(acc)(head))
  def distinct(set: Set[Any]) = if (set(head)) tail distinct set else MyNode(head, tail distinct (set + head))
  def groupBy[B](f: A => B) = {
    val m = tail groupBy f
    val k = f(head)
    m + Tuple2(k, MyNode(head, if (m contains k) m(k) else MyEmpty))
  }
}

/**
 * Empty list object. Method implementations
 * all represent the base case of recursion so they
 * all return MyEmpty or default value.
 */
case object MyEmpty extends MyList[Nothing] {
  def map[B](f: Nothing => B) = MyEmpty
  def filter(f: Nothing => Boolean) = MyEmpty
  def zip[B, C](f: Nothing => B => C)(that: MyList[B]) = MyEmpty
  def foldLeft[B](f: B => Nothing => B)(acc: B) = acc
  def distinct(set: Set[Any]) = MyEmpty
  def groupBy[B](f: Nothing => B) = Map[B, MyList[Nothing]]()
}



/**
 * I skipped the tail-recursive versions as the foldLeft-based
 * functions below are all tail-recursive by virtue of being
 * implemented in terms of foldLeft, which is tail-recursive (I believe).
 *
 * And I don't have anymore time.
 */



trait FLMyList[+A] {
  /**
   * `foldLeft` is abstract; it is implemented by `FLMyNode` and `FLMyEmpty`.
   */
  def foldLeft[B](f: B => A => B)(acc: B): B

  /**
   * `foldLeft` returns results in the reverse of the desired order,
   * so they have to be reversed again.
   *
   * Maybe should have used `foldRight`, instead.
   */
  def reverse() = foldLeft[FLMyList[A]](list => value => FLMyNode(value, list))(FLMyEmpty)

  /**
   * All other methods are implemented in terms of `foldLeft`.
   */
  def map[B](f: A => B) = foldLeft[FLMyList[B]](list => value => FLMyNode(f(value), list))(FLMyEmpty).reverse()
  def filter(f: A => Boolean) = foldLeft[FLMyList[A]](list => value => if (f(value)) FLMyNode(value, list) else list)(FLMyEmpty).reverse()
  def zip[B, C](f: A => B => C)(that: FLMyList[B]) =
    foldLeft[Tuple2[FLMyList[C], FLMyList[B]]](
      list_that => value => list_that match {
        case (result, FLMyEmpty) => Tuple2(FLMyEmpty, FLMyEmpty)
        case (result, FLMyNode(thatHead, thatTail)) => Tuple2(FLMyNode(f(value)(thatHead), list_that._1), thatTail)
      })(Tuple2(FLMyEmpty, that))._1.reverse()
  def groupBy[B](f: A => B): Map[B, FLMyList[A]] =
    foldLeft[Map[B, FLMyList[A]]](
      m => value => {
        val k = f(value)
        m + Tuple2(k, FLMyNode(value, if (m contains k) m(k) else FLMyEmpty))
      }
      )(Map[B, FLMyList[A]]())
  def distinct() = groupBy(x => x).keys.toList // Loses element order, but easier to write
}

case class FLMyNode[+A](head: A, tail: FLMyList[A]) extends FLMyList[A] {
  def foldLeft[B](f: B => A => B)(acc: B) = tail.foldLeft(f)(f(acc)(head))
}

case object FLMyEmpty extends FLMyList[Nothing] {
  def foldLeft[B](f: B => Nothing => B)(acc: B) = acc
}