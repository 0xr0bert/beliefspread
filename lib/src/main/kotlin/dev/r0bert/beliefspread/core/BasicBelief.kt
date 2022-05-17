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

import java.util.*

/**
 * A [BasicBelief] is an implementation of [Belief].
 *
 * @property name The name of the [BasicBelief].
 * @property uuid The [UUID] of the [BasicBelief]
 * @constructor Create a new [BasicBelief] with a [name] and [uuid].
 * @author Robert Greener
 * @since v0.0.1
 */
open class BasicBelief(override var name: String, override var uuid: UUID) : Belief {
    private val perception: MutableMap<Behaviour, Double> = HashMap()
    private val relationship: MutableMap<Belief, Double> = HashMap()

    /**
     * Create a new [BasicBelief] with a random [UUID].
     *
     * [UUID.randomUUID] is used to randomly generate the [UUID].
     *
     * @param name The name of the BasicBelief.
     * @author Robert Greener
     * @since v0.0.1
     */
    constructor(name: String) : this(name, UUID.randomUUID())

    override fun getPerception(behaviour: Behaviour): Double? {
        return perception[behaviour]
    }

    override fun setPerception(behaviour: Behaviour, perception: Double?) {
        if (perception == null) {
            this.perception.remove(behaviour)
        } else if (perception > 1.0) {
            throw IllegalArgumentException("perception is greater than 1")
        } else if (perception < -1.0) {
            throw IllegalArgumentException("perception is less than -1")
        } else {
            this.perception[behaviour] = perception
        }
    }

    override fun getRelationship(belief: Belief): Double? {
        return relationship[belief]
    }

    override fun setRelationship(belief: Belief, relationship: Double?) {
        if (relationship == null) {
            this.relationship.remove(belief)
        } else if (relationship > 1.0) {
            throw IllegalArgumentException("relationship is greater than 1")
        } else if (relationship < -1.0) {
            throw IllegalArgumentException("relationship is less than -1")
        } else {
            this.relationship[belief] = relationship
        }
    }

    /**
     * Compare equality between this [BasicBelief] and another [BasicBelief].
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

        other as BasicBelief

        if (uuid != other.uuid) return false

        return true
    }

    /**
     * Get the `hashCode` of the [BasicBelief].
     *
     * This is solely based of the [UUID].
     *
     * @return The hashCode
     * @author Robert Greener
     * @since v0.1.0
     */
    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}