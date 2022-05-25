/*
 * belief-spread
 * Copyright (c) 2022 Robert Greener
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the LICENSE, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public LICENSE
 * along with this program. If not, see <https://www.gnu.org/licenses>
 */
package dev.r0bert.beliefspread.core

/** An [Agent] which may exist in the model
  *
  * @author
  *   Robert Greener
  * @since v0.14.0
  */
trait Agent extends UUIDd {

  /** Get the activation (if found) of an [Agent] towards a [Belief] at a given
    * time.
    *
    * This is always between -1 and +1
    *
    * @param time
    *   The time.
    * @param belief
    *   The [Belief].
    * @return
    *   The activation (if found).
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def getActivation(time: Int, belief: Belief): Option[Double]

  /** Set the activation of an [Agent] towards a [Belief] at a given time.
    *
    * If the activation is [None], then the activation is deleted.
    *
    * @param time
    *   The time.
    * @param belief
    *   The [Belief].
    * @param activation
    *   The new activation.
    * @throws IllegalArgumentException
    *   If the activation is not between -1 and +1 (or [None]).
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  @throws(classOf[IllegalArgumentException])
  def setActivation(time: Int, belief: Belief, activation: Option[Double]): Unit

  /** Gets the weighted relationship between [Belief]s `b1` and `b2`.
    *
    * This is the compatibility for holding `b2`, given that the [Agent] already
    * holds `b1`.
    *
    * This is equal to the activation of `b1` ([Agent.getActivation]) multiplied
    * by the relationship between `b1` and `b2` ([Belief.getRelationship]).
    *
    * Returns [None] if either activation of `b1` at time `t` is [None], or the
    * relationship between `b1` and `b2` is [None].
    *
    * @param time
    *   The time.
    * @param b1
    *   The first [Belief].
    * @param b2
    *   The second [Belief].
    * @return
    *   The weighted relationship.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def weightedRelationship(time: Int, b1: Belief, b2: Belief): Option[Double]

  /** Gets the context for holding the [Belief] `b`.
    *
    * This is the compatibility for holding `b`, given all the beliefs the agent
    * holds.
    *
    * This is the average of [Agent.weightedRelationship] for every [Belief] in
    * existance.
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

  /** Gets the friends of the [Agent].
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
  def getFriends(): Iterable[(Agent, Double)]

  /** Set the weight of a friend of the [Agent].
    *
    * If they are not friends, this adds another [Agent] as a `friend` with a
    * supplied `weight`.
    *
    * `weight` *must* be in the range [0, 1].
    *
    * If the `friend` already exists, the `weight` is overwritten.
    *
    * If the `weight` is [None], the `friend` is removed if they were friends.
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

  /** Gets the weight of a friend of the [Agent].
    *
    * The weight will be in the range [0, 1].
    *
    * If they are not friends, returns [None].
    *
    * @param friend
    *   The friend.
    * @return
    *   The weight, or [None] if they are not friends.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def getFriendWeight(friend: Agent): Option[Double]
}
