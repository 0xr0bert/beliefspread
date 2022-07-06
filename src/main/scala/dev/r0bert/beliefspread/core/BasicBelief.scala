package dev.r0bert.beliefspread.core

import java.util.UUID
import scala.collection.mutable
import scala.collection.mutable.HashMap

/** A [BasicBelief] is an implementation of [Belief].
  *
  * @param name
  *   The name of the [BasicBelief].
  * @param uuid
  *   The [UUID] of the [BasicBelief].
  * @constructor
  *   Create a new [BasicBelief] with a `name` and `uuid`.
  * @author
  *   Robert Greener
  * @since v0.14.0
  */
class BasicBelief(override var name: String, override var uuid: UUID)
    extends Belief {
  private val perception: mutable.Map[Behaviour, Double] = HashMap()
  private val relationship: mutable.Map[Belief, Double] = HashMap()

  /** Create a new [BasicBelief] with a random [UUID].
    *
    * [UUID.randomUUID] is used to randomly generate the [UUID].
    *
    * @param name
    *   The name of the [BasicBelief].
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def this(name: String) = this(name, UUID.randomUUID)

  override def getPerception(behaviour: Behaviour): Option[Double] =
    perception.get(behaviour)

  override def setPerception(
      behaviour: Behaviour,
      perception: Option[Double]
  ): Unit = perception match {
    case None => this.perception.remove(behaviour)
    case Some(x) if x > 1.0 =>
      throw IllegalArgumentException("perception is greater than 1")
    case Some(x) if x < -1.0 =>
      throw IllegalArgumentException("perception is less than -1")
    case Some(x) => this.perception.put(behaviour, x)
  }

  override def getRelationship(belief: Belief): Option[Double] =
    relationship.get(belief)

  override def setRelationship(
      belief: Belief,
      relationship: Option[Double]
  ): Unit = relationship match {
    case None => this.relationship.remove(belief)
    case Some(x) if x > 1.0 =>
      throw IllegalArgumentException("relationship is greater than 1")
    case Some(x) if x < -1.0 =>
      throw IllegalArgumentException("relationship is less than -1")
    case Some(x) => this.relationship.put(belief, x)
  }

  /** Compare equality between this [BasicBelief] and another [BasicBelief].
    *
    * They are equal iff. the [UUID] is equal.
    *
    * @param other
    *   The other [Any].
    * @return
    *   true If the [UUID] is equal.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  override def equals(other: Any): Boolean = other match {
    case other: BasicBelief => this.uuid == other.uuid
    case _                  => false
  }

  /** Get the `hashCode` of the [BasicBelief].
    *
    * This is solely based on the [UUID].
    *
    * @return
    *   The hashCode.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  override def hashCode(): Int = uuid.hashCode
}
