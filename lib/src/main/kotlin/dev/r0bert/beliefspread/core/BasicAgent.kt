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

import java.lang.Double.max
import java.lang.Double.min
import java.util.*

/**
 * A [BasicAgent] is an implementation of [Agent].
 *
 * @property uuid The [UUID] of the [BasicAgent].
 * @constructor Create a new [BasicAgent] with a supplied [UUID].
 * @author Robert Greener
 * @since v0.1.0
 */
open class BasicAgent(override var uuid: UUID) : Agent {
    /**
     * Create a new [BasicAgent] with a random [UUID].
     *
     * The [UUID] is generated using [UUID.randomUUID].
     *
     * @author Robert Greener
     * @since v0.1.0
     */
    constructor() : this(UUID.randomUUID())

    private val activation: MutableMap<UInt, MutableMap<Belief, Double>> = HashMap()

    private val friends: MutableMap<Agent, Double> = HashMap()

    private val actions: MutableMap<UInt, Behaviour> = HashMap()

    private val delta: MutableMap<Belief, Double> = HashMap()

    override fun getActivation(time: UInt, belief: Belief): Double? {
        return activation[time]?.get(belief)
    }

    override fun setActivation(time: UInt, belief: Belief, activation: Double?) {
        if (activation == null) {
            this.activation[time]?.remove(belief)
        } else if (activation > 1.0) {
            throw IllegalArgumentException("new activation is greater than 1")
        } else if (activation < -1.0) {
            throw IllegalArgumentException("new activation is less than -1")
        } else {
            if (!this.activation.containsKey(time)) {
                this.activation[time] = HashMap<Belief, Double>()
            }
            this.activation[time]!![belief] = activation
        }
    }

    override fun weightedRelationship(time: UInt, b1: Belief, b2: Belief): Double? {
        return getActivation(time, b1)?.let { b1.getRelationship(b2)?.times(it) }
    }

    override fun contextualise(time: UInt, b: Belief, beliefs: Collection<Belief>): Double {
        val beliefsSize = beliefs.size

        return if (beliefsSize == 0) {
            0.0
        } else {
            beliefs
                .asSequence()
                .map { b2 -> weightedRelationship(time, b, b2) }
                .filterNotNull()
                .fold(0.0) { acc, v -> acc + v } / beliefs.size
        }
    }

    override fun getFriends(): Collection<Pair<Agent, Double>> {
        return friends.toList()
    }

    override fun setFriendWeight(friend: Agent, weight: Double?) {
        if (weight == null) {
            friends.remove(friend)
        } else if (weight > 1) {
            throw IllegalArgumentException("weight greater than 1")
        } else if (weight < 0) {
            throw IllegalArgumentException("weight less than 0")
        } else {
            friends[friend] = weight
        }
    }

    override fun getFriendWeight(friend: Agent): Double? {
        return friends[friend]
    }

    override fun getAction(time: UInt): Behaviour? {
        return actions[time]
    }

    override fun setAction(time: UInt, behaviour: Behaviour?) {
        if (behaviour == null) {
            actions.remove(time)
        } else {
            actions[time] = behaviour
        }
    }

    override fun pressure(time: UInt, belief: Belief): Double {
        return if (friends.isEmpty()) {
            0.0
        } else {
            var returnValue = 0.0
            for ((a, w) in friends) {
                val aAction = a.getAction(time)
                aAction?.let { behaviour ->
                    belief.getPerception(behaviour)?.let { perception ->
                        returnValue += w * perception
                    }
                }
            }

            return returnValue / friends.size
        }
    }

    @Deprecated(
        "renamed to activationChange",
        replaceWith = ReplaceWith("activationChange(time, belief, beliefs)"),
        level = DeprecationLevel.WARNING
    )
    override fun contextualPressure(time: UInt, belief: Belief, beliefs: Collection<Belief>): Double {
        return activationChange(time, belief, beliefs)
    }

    override fun activationChange(time: UInt, belief: Belief, beliefs: Collection<Belief>): Double {
        val p = pressure(time, belief)
        return if (p > 0) {
            (1 + contextualise(time, belief, beliefs)) / 2.0 * p
        } else {
            (1 - contextualise(time, belief, beliefs)) / 2.0 * p
        }
    }

    override fun getDelta(belief: Belief): Double? {
        return delta[belief]
    }

    override fun setDelta(belief: Belief, delta: Double?) {
        if (delta == null) {
            this.delta.remove(belief)
        } else if (delta <= 0) {
            throw IllegalArgumentException("delta not strictly positive")
        } else {
            this.delta[belief] = delta
        }
    }

    override fun updateActivation(time: UInt, belief: Belief, beliefs: Collection<Belief>) {
        val d = this.getDelta(belief) ?: throw IllegalArgumentException("Delta for belief null")
        val a = this.getActivation(time - 1u, belief)
            ?: throw IllegalArgumentException("activation not calculated at previous time step")

        this.setActivation(
            time, belief, max(
                -1.0,
                min(
                    1.0,
                    d * a + this.activationChange(time - 1u, belief, beliefs)
                )
            )
        )
    }

    /**
     * Compare equality between this [BasicAgent] and another [BasicAgent].
     *
     * They are equal iff. the [UUID] is equal.
     *
     * @param other The other [Object].
     * @return true if the [UUID] is equal.
     * @author Robert Greener
     * @since v0.1.0
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BasicAgent

        if (uuid != other.uuid) return false

        return true
    }

    /**
     * Gets the `hashCode` of the [BasicAgent].
     *
     * This is based solely on the [UUID].
     *
     * @return The hashCode.
     * @author Robert Greener
     * @since v0.1.0
     */
    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}