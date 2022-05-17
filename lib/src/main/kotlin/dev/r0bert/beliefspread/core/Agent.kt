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

/**
 * An Agent which may exist in the model
 *
 * @author Robert Greener
 * @since v0.1.0
 */
interface Agent : UUIDd {
    /**
     * Get the activation (if found) of an [Agent] towards a [Belief] at a given time.
     *
     * This is always between -1 and +1.
     *
     * @param time The time.
     * @param belief The belief.
     * @return The activation (if found).
     * @author Robert Greener
     * @since v0.2.0
     */
    fun getActivation(time: UInt, belief: Belief): Double?

    /**
     * Set the activation of an [Agent] towards a [Belief] at a given time.
     *
     * If the activation is `null`, the activation is deleted.
     *
     * @param time The time.
     * @param belief The new belief.
     * @param activation The new activation.
     * @throws IllegalArgumentException If activation is not between -1 and +1 (or null).
     * @author Robert Greener
     * @since v0.2.0
     */
    @Throws(IllegalArgumentException::class)
    fun setActivation(time: UInt, belief: Belief, activation: Double?)

    /**
     * Gets the weighted relationship between [Belief]s `b1` and `b2`.
     *
     * This is the compatibility for holding `b2`, given that the agent
     * already holds `b1`.
     *
     * This is equal to the activation of `b1` ([Agent.getActivation])
     * multiplied by the relationship between `b1` and `b2`
     * ([Belief.getRelationship]).
     *
     * Returns `null` if either the activation of `b1` at time `t` is `null`,
     * or the relationship between `b1` and `b2` is `null`.
     *
     * @param time The time.
     * @param b1 The first [Belief].
     * @param b2 The second [Belief].
     * @return The weighted relationship.
     * @author Robert Greener
     * @since v0.3.0
     */
    fun weightedRelationship(time: UInt, b1: Belief, b2: Belief): Double?

    /**
     * Gets the context for holding the [Belief] `b`.
     *
     * This is the compatibility for holding `b`, given all the beliefs
     * the agent holds.
     *
     * This is the average of [Agent.weightedRelationship] for every [Belief].
     *
     * @param time The time.
     * @param b The belief.
     * @param beliefs All the beliefs.
     * @return The context.
     * @author Robert Greener
     * @since v0.4.0
     */
    fun contextualise(time: UInt, b: Belief, beliefs: Collection<Belief>): Double

    /**
     * Gets the friends of the [Agent].
     *
     * This gets the friends of the agent with their weight of connection.
     *
     * All weights are in the range [0, 1].
     *
     * @return The friends with their weight of connection.
     * @author Robert Greener
     * @since v0.5.0
     */
    fun getFriends(): Collection<Pair<Agent, Double>>

    /**
     * Set the weight of a friend of the [Agent].
     *
     * If they are not friends, this adds another [Agent] as a
     * `friend` with a supplied `weight`.
     *
     * `weight` must be in the range [0, 1].
     *
     * If the `friend` already exists, the weight is overwritten.
     *
     * If the `weight` is null, the friend is removed if they were friends.
     *
     * @param friend The friend.
     * @param weight The weight.
     * @throws IllegalArgumentException If weight not in range [0, 1]
     * @author Robert Greener
     * @since v0.5.0
     */
    @Throws(IllegalArgumentException::class)
    fun setFriendWeight(friend: Agent, weight: Double?)

    /**
     * Gets the weight of a friend of the [Agent].
     *
     * The weight will be in the range [0, 1].
     *
     * If they are not friends, returns null.
     *
     * @param friend The friend.
     * @return The weight, or null if they are not friends.
     * @author Robert Greener
     * @since v0.5.0
     */
    fun getFriendWeight(friend: Agent): Double?

    /**
     * Gets the [Behaviour] the [Agent] performed at a given time.
     *
     * Returns null if nothing has/was performed.
     *
     * @param time The time.
     * @return The [Behaviour] performed.
     * @author Robert Greener
     * @since v0.6.0
     */
    fun getAction(time: UInt): Behaviour?

    /**
     * Sets the [Behaviour] the [Agent] performed at a given time.
     *
     * If `null` it unsets the [Behaviour].
     *
     * @param time The time.
     * @param behaviour The [Behaviour].
     * @author Robert Greener
     * @since v0.6.0
     */
    fun setAction(time: UInt, behaviour: Behaviour?)

    /**
     * Gets the pressure the [Agent] feels to adopt a [Belief] given the actions of their friends.
     *
     * This does not take into account the beliefs the agent already holds.
     *
     * @param time The time.
     * @param belief The belief.
     * @return The pressure.
     * @author Robert Greener
     * @since v0.7.0
     */
    fun pressure(time: UInt, belief: Belief): Double

    /**
     * Gets the contextual pressure that the [Agent] feels to adopt a [Belief].
     *
     * This takes into account the beliefs that the agent already holds.
     *
     * @param time The Time.
     * @param belief The belief.
     * @param beliefs All the beliefs.
     * @return The contextual pressure.
     * @see Agent.pressure
     * @see Agent.contextualise
     * @author Robert Greener
     * @since v0.8.0
     */
    @Deprecated(
        "renamed to activationChange",
        replaceWith = ReplaceWith("activationChange(time, belief, beliefs)"),
        level = DeprecationLevel.WARNING
    )
    fun contextualPressure(time: UInt, belief: Belief, beliefs: Collection<Belief>): Double

    /**
     * Gets the change in activation for the [Agent] as a result of the [Behaviour] observed.
     *
     * This takes into account the beliefs that the agent already holds.
     *
     * @param time The Time.
     * @param belief The belief.
     * @param beliefs All the beliefs.
     * @return The change in activation..
     * @see Agent.pressure
     * @see Agent.contextualise
     * @author Robert Greener
     * @since v0.13.0
     */
    fun activationChange(time: UInt, belief: Belief, beliefs: Collection<Belief>): Double

    /**
     * Gets the delta for a given [Belief].
     *
     * This is the value that the activation for the [Belief]
     * changes by (multiplicatively) at every time step.
     *
     * This is a strictly positive (non-zero) value.
     *
     * @param belief The [Belief].
     * @return The delta for the [Belief] and this [Agent].
     * @author Robert Greener
     * @since v0.10.0
     */
    fun getDelta(belief: Belief): Double?

    /**
     * Sets the delta for a given [Belief].
     *
     * This is the value that the activation for the [Belief]
     * changes by (multiplicatively) at every time step.
     *
     * If `delta` is `null` it removes the `delta`.
     *
     * @param belief The [Belief].
     * @param delta The new strictly positive (non-zero) delta.
     * @throws IllegalArgumentException if delta <= 0.
     * @author Robert Greener
     * @since v0.10.0
     */
    @Throws(IllegalArgumentException::class)
    fun setDelta(belief: Belief, delta: Double?)

    /**
     * Updates the activation for a given time and [Belief].
     *
     * @param time The time.
     * @param belief The [Belief].
     * @param beliefs All the [Belief]s.
     * @throws IllegalArgumentException If [Agent.getActivation] for `time` and `belief` is null, or
     *  if [Agent.getDelta] for `belief` is null.
     * @author Robert Greener
     * @since v0.11.0
     */
    @Throws(IllegalArgumentException::class)
    fun updateActivation(time: UInt, belief: Belief, beliefs: Collection<Belief>)
}