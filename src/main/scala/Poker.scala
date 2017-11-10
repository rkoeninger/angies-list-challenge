import scala.language.implicitConversions

/**
 * This model gets vague towards the end as there are many variations on the rules
 * to poker, which can make the game very simple or more complicated -
 * trading in cards, multiple bidding rounds, etc.
 */
object Poker {
  /**
   * Type-safe enumerations for card face values and suits.
   */
  object Face extends Enumeration {
    type Face = Value
    val Ace, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King = Value
  }
  object Suit extends Enumeration {
    type Suit = Value
    val Hearts, Diamonds, Clubs, Spades = Value
  }
  import Face._
  import Suit._

  /**
   * A Card is just a Face and a Suit.
   */
  case class Card(face: Face, suit: Suit)

  sealed trait HandType

  /**
   * Each type of hand is declared in order of increasing value.
   * A distinguishing piece of information is included to determine
   * which of two of the same HandType is the winner.
   *
   * ex: A pair of Queens beats a pair of Sevens.
   */
  case class HighCard(face: Face) extends HandType
  case class Pair(face: Face) extends HandType
  case class TwoPair(lowFace: Face, highFace: Face) extends HandType
  case class ThreeOfAKind(face: Face) extends HandType
  case class Straight(high: Face) extends HandType
  case class Flush(high: Face) extends HandType
  case class FullHouse(two: Face, three: Face) extends HandType
  case class FourOfAKind(face: Face) extends HandType
  case class StraightFlush(high: Face) extends HandType {
    def isRoyalFlush = high == Ace
  }

  /**
   * An implicit conversion can be used to build a hand out of
   * a list of cards into a Hand object and ensure there are the
   * correct number of cards in the hand (5).
   */
  implicit class Hand(cards: List[Card]) {
    def handType: HandType = ???
  }

  /**
   * Full, unshuffled deck of 52 cards.
   */
  val deck = (for (f <- Face.values; s <- Suit.values) yield Card(f, s)).toList

  /**
   * Could also use an implicit to enhance the List[A] class with a suffle method.
   */
  implicit def shuffle(cards: List[Card]): List[Card] = ???

  /**
   * But I would avoid using implicits frivolously.
   *
   * Implicits can introduce issues where it becomes hard to identify how a method
   * is being called on a particular class or whether a method invocation makes
   * use of an implicit conversion or not.
   *
   * On the other hand, if one is using an IDE, it will be able to identify
   * when an implicit is being used (because they are applied statically)
   * and lead the developer to the declaration site of the implicit.
   */

  /**
   * A Player has an id/name and an amount of cash/chips on hand.
   */
  class Player(id: String, cash: Int)

  /**
   * A Round includes what each player is holding and how much they have bid.
   */
  class Round(hands: Map[Player, List[Card]], bids: Map[Player, Int]) {
    def winner: Player = ???
    def potTotal: Int = ???
  }

  /**
   * A Deal class could be used to model a series of bidding rounds over
   * the course of one hand.
   */
  class Deal(hands: Map[Player, List[Card]], bids: List[Map[Player, Int]]) {
    def winner: Player = ???
    def potValues: List[Int] = ???
    def endingPot: Int = ???
  }

  /**
   * A Game class could be used to keep track of all the hands that have
   * been dealt, who won each one and everyone's bidding history.
   */
  class Game(players: List[Player], deals: List[Deal])
}

