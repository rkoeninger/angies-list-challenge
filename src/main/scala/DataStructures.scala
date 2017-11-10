/**
 * This one was a bit unclear: Where is the cached data coming from?
 *
 * Considering these requirements...
 *
 * O(log n) for get of any key
 * O(log n) for put of any key
 *
 * ...the time complexity of getting and putting keys that are not already
 * in the cache depends on the time complexity of getting and putting
 * values from/into the data source.
 *
 * A generic `Source` is mocked-up here:
 */
trait Source[K, V] {
  def fetch(key: K): Option[V]
  def store(key: K, value: V): Unit
}

/**
 * The type signatures in the requirements imply that this
 * is to be a mutable object.
 */
class LRUCache[K, V](val source: Source[K, V], val recentCacheSize: Int) {
  private val mutMap = scala.collection.mutable.Map[K, (Long, V)]()
  private def newKV(key: K, value: V) = Tuple2(key, Tuple2(java.lang.System.currentTimeMillis(), value))
  private def makeRoom() {
    while (mutMap.size >= recentCacheSize) {
      val (k, (t, v)) = mutMap.minBy(ktv => ktv._2._1)
      mutMap -= k
    }
  }

  def cacheSize = mutMap.size

  /**
   * Lookup for values in cache are amortized constant time,
   * same as the hashtable that backs this `LRUCache`.
   */
  def get(key: K): Option[V] = {
    if (mutMap contains key)
      mutMap(key)

    (source fetch key).map(value => {
      makeRoom()
      mutMap += newKV(key, value)
      value
    })
  }
  def put(key: K, value: V) {
    if (! (mutMap contains key))
      makeRoom()

    mutMap += newKV(key, value)
  }
}