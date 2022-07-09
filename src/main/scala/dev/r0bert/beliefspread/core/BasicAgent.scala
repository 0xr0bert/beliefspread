package dev.r0bert.beliefspread.core

import scala.collection.mutable;
import scala.collection.mutable.HashMap;
import java.util.UUID

/** A [[BasicAgent]] is an implementation of [[Agent]].
  *
  * @param uuid
  *   The [[UUID]] of the [[BasicAgent]].
  * @constructor
  *   Create a new [[BasicAgent]] with a supplied [[UUID]].
  * @author
  *   Robert Greener
  * @since v0.14.0
  */
class BasicAgent(override var uuid: UUID) extends Agent {

  private val activation: mutable.Map[Int, mutable.Map[Belief, Double]] =
    HashMap()

  private val friends: mutable.Map[Agent, Double] = HashMap()

  private val actions: mutable.Map[Int, Behaviour] = HashMap()

  /** Create a new [[BasicAgent]] with a random [[UUID]]
    *
    * The [[UUID]] is generated using [[UUID.randomUUID]]
    *
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def this() = this(UUID.randomUUID)

  override def getActivation(time: Int, belief: Belief): Option[Double] =
    activation.get(time) match {
      case Some(x) => x.get(belief)
      case None    => None
    }

  override def getAction(time: Int): Option[Behaviour] = actions.get(time)

  override def setFriendWeight(friend: Agent, weight: Option[Double]): Unit =
    weight match {
      case Some(x) if x > 1 =>
        throw IllegalArgumentException("weight greater than 1")
      case Some(x) if x < 0 =>
        throw IllegalArgumentException("weight less than 0")
      case Some(x) => friends.put(friend, x)
      case None    => friends.remove(friend)
    }

  override def updateActivation(
      time: Int,
      belief: Belief,
      beliefs: Iterable[Belief]
  ): Unit = ???

  override def weightedRelationship(
      time: Int,
      b1: Belief,
      b2: Belief
  ): Option[Double] = getActivation(time, b1) match {
    case Some(x) =>
      b1.getRelationship(b2) match {
        case Some(y) => Some(x * y)
        case None    => None
      }
    case None => None
  }

  override def setActivation(
      time: Int,
      belief: Belief,
      activation: Option[Double]
  ): Unit = activation match {
    case Some(x) if x > 1.0 =>
      throw IllegalArgumentException("new activation is greater than 1")
    case Some(x) if x < -1.0 =>
      throw IllegalArgumentException("new activation is less than -1")
    case Some(x) => {
      if (!this.activation.contains(time))
        this.activation.put(time, HashMap())
      this.activation.get(time).get.put(belief, x)
    }
    case None =>
      this.activation.get(time) match {
        case Some(x) => x.remove(belief)
        case None    => None
      }
  }

  override def getFriendWeight(friend: Agent): Option[Double] =
    friends.get(friend)

  override def contextualise(
      time: Int,
      b: Belief,
      beliefs: Iterable[Belief]
  ): Double = if (beliefs.isEmpty) 0.0
  else
    beliefs
      .map(b2 => weightedRelationship(time, b, b2))
      .flatten
      .foldLeft(0.0)((acc, v) => acc + v) / beliefs.size

  override def pressure(time: Int, belief: Belief): Double = ???

  override def getDelta(belief: Belief): Option[Double] = ???

  override def setAction(time: Int, behaviour: Option[Behaviour]): Unit =
    behaviour match {
      case None    => actions.remove(time)
      case Some(x) => actions.put(time, x)
    }

  override def getFriends(): Iterable[(Agent, Double)] = friends.toList

  override def setDelta(belief: Belief, delta: Option[Double]): Unit = ???

  override def activationChange(
      time: Int,
      belief: Belief,
      beliefs: Iterable[Belief]
  ): Double = ???

  /** Compare equality between this [[BasicAgent]] and another [[BasicAgent]].
    *
    * They are equal iff. the [[UUID]] is equal.
    *
    * @param other
    *   The other agent.
    * @return
    *   true if the [[UUID]] is equal.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  override def equals(other: Any): Boolean = other match {
    case other: BasicAgent => this.uuid == other.uuid
    case _                 => false
  }

  /** Gets the `hashCode` of the [[BasicAgent]].
    *
    * This is based solely on the [[UUID]].
    *
    * @return
    *   The hashCode.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  override def hashCode(): Int = uuid.hashCode
}
