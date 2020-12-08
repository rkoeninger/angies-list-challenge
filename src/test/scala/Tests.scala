import org.scalatest.funsuite.AnyFunSuite

class FibonacciTests extends AnyFunSuite {
  private def testIt(f: Int => Int): Unit =
    assert(Vector(1, 1, 2, 3, 5, 8) == (1 to 6).map(f))
  test("naiveRecursive") { testIt(Fibonacci.naiveRecursive) }
  test("linearIterative") { testIt(Fibonacci.linearIterative) }
  test("linearTailRecursive") { testIt(Fibonacci.linearTailRecursive) }
  test("constantTime") { testIt(Fibonacci.constantTime) }
}

// Dummy source
class CacheSource extends Source[String, Int] {
  def fetch(key: String) = Some(0)
  def store(key: String, value: Int) = Unit
}

class DataStructuresTests extends AnyFunSuite {
  test("LRUCache never exceeds max size") {
  	val maxSize = 10
  	val cache = new LRUCache[String, Int](new CacheSource(), maxSize)
  	assert(cache.cacheSize <= maxSize)
  	cache.put("a", 0)
  	assert(cache.cacheSize <= maxSize)
  	cache.put("b", 0)
  	assert(cache.cacheSize <= maxSize)
  	cache.put("c", 0)
  	assert(cache.cacheSize <= maxSize)
  	cache.put("d", 0)
  	assert(cache.cacheSize <= maxSize)
  	cache.put("e", 0)
  	assert(cache.cacheSize == 5)
  	assert(cache.cacheSize <= maxSize)
  	cache.put("f", 0)
  	assert(cache.cacheSize <= maxSize)
  	cache.put("g", 0)
  	assert(cache.cacheSize <= maxSize)
  	cache.put("h", 0)
    assert(cache.cacheSize == 8)
  	assert(cache.cacheSize <= maxSize)
  	cache.put("i", 0)
  	assert(cache.cacheSize == 9)
  	assert(cache.cacheSize <= maxSize)
  	cache.put("j", 0)
  	assert(cache.cacheSize == 10)
  	assert(cache.cacheSize <= maxSize)
  	cache.put("k", 0)
  	assert(cache.cacheSize == 10)
  	assert(cache.cacheSize <= maxSize)
  }
}

class FPPracticeTests extends AnyFunSuite {
  test("Recursive equality") {
    assert(MyEmpty == MyEmpty)
    assert(MyNode(5, MyEmpty) == MyNode(5, MyEmpty))
    assert(MyNode("a", MyNode("b", MyNode("c", MyEmpty))) == MyNode("a", MyNode("b", MyNode("c", MyEmpty))))
    assert(FLMyEmpty == FLMyEmpty)
    assert(FLMyNode(5, FLMyEmpty) == FLMyNode(5, FLMyEmpty))
    assert(FLMyNode("a", FLMyNode("b", FLMyNode("c", FLMyEmpty))) == FLMyNode("a", FLMyNode("b", FLMyNode("c", FLMyEmpty))))
  }
  val plus = (x: Int) => (y: Int) => x + y
  val plus1 = (x: Int) => x + 1
  val pos = (x: Int) => x > 0
  test("MyList") {
    assert(MyEmpty.map(plus1) == MyEmpty)
    assert(MyNode(1, MyNode(2, MyNode(3, MyEmpty))).map(plus1) == MyNode(2, MyNode(3, MyNode(4, MyEmpty))))
    assert(MyNode(-1, MyNode(0, MyNode(1, MyEmpty))).filter(pos) == MyNode(1, MyEmpty))
    assert(MyNode(1, MyNode(3, MyNode(-1, MyNode(3, MyNode(5, MyEmpty))))).distinct() == MyNode(1, MyNode(3, MyNode(-1, MyNode(5, MyEmpty)))))
    assert(MyNode(1, MyNode(2, MyNode(3, MyEmpty))).zip(plus)(MyNode(-1, MyNode(0, MyNode(1, MyEmpty)))) == MyNode(0, MyNode(2, MyNode(4, MyEmpty))))
  }
  test("FLMyList") {
    assert(FLMyEmpty.map(plus1) == FLMyEmpty)
    assert(FLMyNode(1, FLMyNode(2, FLMyNode(3, FLMyEmpty))).map(plus1) == FLMyNode(2, FLMyNode(3, FLMyNode(4, FLMyEmpty))))
    assert(FLMyNode(-1, FLMyNode(0, FLMyNode(1, FLMyNode(2, FLMyEmpty)))).filter(pos) == FLMyNode(1, FLMyNode(2, FLMyEmpty)))
    assert(FLMyNode(1, FLMyNode(2, FLMyNode(3, FLMyEmpty))).zip(plus)(FLMyNode(-1, FLMyNode(0, FLMyNode(1, FLMyEmpty)))) == FLMyNode(0, FLMyNode(2, FLMyNode(4, FLMyEmpty))))
  }
}