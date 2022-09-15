package dev.r0bert.beliefspread.core

import scala.collection.immutable;

/** An [Agent]] which may exist in the model
  *
  * @author
  *   Robert Greener
  * @since v0.14.0
  */
trait Agent extends UUIDd {

  /** Get the activation (if found) of an [[Agent]] towards a [[Belief]] at a
    * given time.
    *
    * This is always between -1 and +1
    *
    * @param time
    *   The time.
    * @param belief
    *   The [[Belief]].
    * @return
    *   The activation (if found).
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def getActivation(time: Int, belief: Belief): Option[Double]

  /** Get the activations of an [[Agent]] towards all [[Belief]]s at all times.
    *
    * This is always between -1 and +1
    *
    * @return
    *   The immutable activations.
    * @author
    *   Robert Greener
    * @since v0.16.0
    */
  def getActivations: immutable.Map[Int, immutable.Map[Belief, Double]]

  /** Set the activation of an [[Agent]] towards a [[Belief]] at a given time.
    *
    * If the activation is [[None]], then the activation is deleted.
    *
    * @param time
    *   The time.
    * @param belief
    *   The [[Belief]].
    * @param activation
    *   The new activation.
    * @throws IllegalArgumentException
    *   If the activation is not between -1 and +1 (or [[None]]).
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  @throws(classOf[IllegalArgumentException])
  def setActivation(time: Int, belief: Belief, activation: Option[Double]): Unit

  /** Gets the weighted relationship between [[Belief]]s `b1` and `b2`.
    *
    * This is the compatibility for holding `b2`, given that the [[Agent]]
    * already holds `b1`.
    *
    * This is equal to the activation of `b1` ([[Agent.getActivation]])
    * multiplied by the relationship between `b1` and `b2`
    * ([[Belief.getRelationship]]).
    *
    * Returns [[None]] if either activation of `b1` at time `t` is [[None]], or
    * the relationship between `b1` and `b2` is [[None]].
    *
    * @param time
    *   The time.
    * @param b1
    *   The first [[Belief]].
    * @param b2
    *   The second [[Belief]].
    * @return
    *   The weighted relationship.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def weightedRelationship(time: Int, b1: Belief, b2: Belief): Option[Double]

  /** Gets the context for holding the [[Belief]] `b`.
    *
    * This is the compatibility for holding `b`, given all the beliefs the agent
    * holds.
    *
    * This is the average of [[Agent.weightedRelationship]] for every [[Belief]]
    * in existance.
    *
    * @param time
    *   The time.
    * @param b
    *   The belief.
    * @param beliefs
    *   All the beliefs in existence.
    * @return
    *   The context.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def contextualise(time: Int, b: Belief, beliefs: Iterable[Belief]): Double

  /** Gets the friends of the [[Agent]].
    *
    * This gets the friends of the agent with their weight of connection.
    *
    * All weights are in the range [0, 1].
    *
    * @return
    *   The friends with their weight of connection.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def getFriends(): immutable.Map[Agent, Double]

  /** Set the weight of a friend of the [[Agent]].
    *
    * If they are not friends, this adds another [[Agent]] as a `friend` with a
    * supplied `weight`.
    *
    * `weight` *must* be in the range [0, 1].
    *
    * If the `friend` already exists, the `weight` is overwritten.
    *
    * If the `weight` is [[None]], the `friend` is removed if they were friends.
    *
    * @param friend
    *   The friend.
    * @param weight
    *   The weight.
    * @throws IllegalArgumentException
    *   If `weight` is not range [0, 1].
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  @throws(classOf[IllegalArgumentException])
  def setFriendWeight(friend: Agent, weight: Option[Double]): Unit

  /** Gets the weight of a friend of the [[Agent]].
    *
    * The weight will be in the range [0, 1].
    *
    * If they are not friends, returns [[None]].
    *
    * @param friend
    *   The friend.
    * @return
    *   The weight, or [[None]] if they are not friends.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def getFriendWeight(friend: Agent): Option[Double]

  /** Gets the [[Behaviour]] the [[Agent]] performed at a given `time`.
    *
    * Returns [[None]] if nothing was performed.
    *
    * @param time
    *   The time.
    * @return
    *   The [[Behaviour]] performed.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def getAction(time: Int): Option[Behaviour]

  /** Gets all of the [[Behaviour]]s that the [[Agent]] has performed.
    *
    * @return
    *   an [[immutable.Map]] from time to the behaviour performed.
    * @author
    *   Robert Greener
    * @since v0.16.0
    */
  def getActions: immutable.Map[Int, Behaviour]

  /** Sets the [[Behaviour]] the [[Agent]] performed at a given time.
    *
    * If [[None]], it unsets the [[Behaviour]]
    *
    * @param time
    *   The time.
    * @param behaviour
    *   The [[Behaviour]].
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def setAction(time: Int, behaviour: Option[Behaviour]): Unit

  /** Gets the pressure the [[Agent]] feels to adopt a [[Belief]] given the
    * actions of their friends.
    *
    * This does not take into account the beliefs that the agent already holds.
    *
    * @param time
    *   The time.
    * @param belief
    *   The [[Belief]].
    * @return
    *   The pressure.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def pressure(time: Int, belief: Belief): Double

  /** Gets the change in activation for the [[Agent]] as a result of the
    * [[Behaviour]] observed.
    *
    * This takes into account the beliefs that the agent already holds.
    *
    * @param time
    *   The time.
    * @param belief
    *   The [[Belief]].
    * @param beliefs
    *   all the [[Belief]]s.
    * @return
    *   The change in activation.
    * @see
    *   Agent.pressure
    * @see
    *   Agent.contextualise
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def activationChange(
      time: Int,
      belief: Belief,
      beliefs: Iterable[Belief]
  ): Double

  /** Gets the delta for a given [[Belief]].
    *
    * This is the value that the activation for the [[Belief]] changes by
    * (multiplicatively) at every time step.
    *
    * This is a strictly positive value (i.e., > 0).
    *
    * @param belief
    *   The [[Belief]].
    * @return
    *   The delta for the [[Belief]] and this [[Agent]].
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def getDelta(belief: Belief): Option[Double]

  /** Sets the delta for a given [[Belief]].
    *
    * This is the value that the activation for the [[Belief]] changes by
    * (multiplicatively) at every time step.
    *
    * If `delta` is [[None]], then this function removes `delta`.
    *
    * @param belief
    *   The [[Belief]].
    * @param delta
    *   The new strictly positive delta.
    * @throws IllegalArgumentException
    *   If `delta` <= 0.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  @throws(classOf[IllegalArgumentException])
  def setDelta(belief: Belief, delta: Option[Double]): Unit

  /** Updates the activation for a given `time` and [[Belief]].
    *
    * @param time
    *   The time.
    * @param belief
    *   The [[Belief]].
    * @param beliefs
    *   All the [[Belief]]s in existance.
    * @throws IllegalArgumentExcpetion
    *   If [[Agent.getActivation]] for `time` and `belief` is [[None]], or if
    *   [[Agent.getDelta]] for `belief` is [[None]].
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  @throws(classOf[IllegalArgumentException])
  def updateActivation(
      time: Int,
      belief: Belief,
      beliefs: Iterable[Belief]
  ): Unit
}
