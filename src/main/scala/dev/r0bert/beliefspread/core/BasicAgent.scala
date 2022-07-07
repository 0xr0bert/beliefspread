package dev.r0bert.beliefspread.core

import java.util.UUID

/** A [BasicAgent] is an implementation of [Agent].
  *
  * @param uuid
  *   The [UUID] of the [BasicAgent].
  * @constructor
  *   Create a new [BasicAgent] with a supplied [UUID].
  * @author
  *   Robert Greener
  * @since v0.14.0
  */
class BasicAgent(override var uuid: UUID) extends Agent {

  /** Create a new [BasicAgent] with a random [UUID]
    *
    * The [UUID] is generated using [UUID.randomUUID]
    *
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def this() = this(UUID.randomUUID)

  override def getActivation(time: Int, belief: Belief): Option[Double] = ???

  override def getAction(time: Int): Option[Behaviour] = ???

  override def setFriendWeight(friend: Agent, weight: Option[Double]): Unit =
    ???

  override def updateActivation(
      time: Int,
      belief: Belief,
      beliefs: Iterable[Belief]
  ): Unit = ???

  override def weightedRelationship(
      time: Int,
      b1: Belief,
      b2: Belief
  ): Option[Double] = ???

  override def setActivation(
      time: Int,
      belief: Belief,
      activation: Option[Double]
  ): Unit = ???

  override def getFriendWeight(friend: Agent): Option[Double] = ???

  override def contextualise(
      time: Int,
      b: Belief,
      beliefs: Iterable[Belief]
  ): Double = ???

  override def pressure(time: Int, belief: Belief): Double = ???

  override def getDelta(belief: Belief): Option[Double] = ???

  override def setAction(time: Int, behaviour: Option[Behaviour]): Unit = ???

  override def getFriends(): Iterable[(Agent, Double)] = ???

  override def setDelta(belief: Belief, delta: Option[Double]): Unit = ???

  override def activationChange(
      time: Int,
      belief: Belief,
      beliefs: Iterable[Belief]
  ): Double = ???

  /** Compare equality between this [BasicAgent] and another [BasicAgent].
    *
    * They are equal iff. the [UUID] is equal.
    *
    * @param other
    *   The other agent.
    * @return
    *   true if the [UUID] is equal.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  override def equals(other: Any): Boolean = other match {
    case other: BasicAgent => this.uuid == other.uuid
    case _                 => false
  }
}