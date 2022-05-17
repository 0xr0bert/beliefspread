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

import io.mockk.mockk
import org.apache.commons.lang3.reflect.FieldUtils
import java.util.*
import kotlin.test.*

class BasicBeliefTest {
    @Test
    fun `constructor assigns uuid`() {
        val uuid = UUID.randomUUID()
        val belief = BasicBelief("b", uuid)
        assertEquals(uuid, belief.uuid)
    }

    @Test
    fun `constructor assigns random uuid`() {
        val b1 = BasicBelief("b")
        val b2 = BasicBelief("b")
        assertNotEquals(b1.uuid, b2.uuid)
    }

    @Test
    fun `constructor assigns name`() {
        val b = BasicBelief("b")
        assertEquals("b", b.name)
    }

    @Test
    fun `full constructor assigns name`() {
        val b = BasicBelief("b", UUID.randomUUID())
        assertEquals("b", b.name)
    }

    @Test
    fun `getPerception when exists`() {
        val belief = BasicBelief("belief")
        val behaviour = mockk<Behaviour>()
        val perception = HashMap<Behaviour, Double>()
        perception[behaviour] = 0.5
        FieldUtils.writeField(belief, "perception", perception, true)

        assertEquals(0.5, belief.getPerception(behaviour))
    }

    @Test
    fun `getPerception when not exists`() {
        val belief = BasicBelief("belief")
        val behaviour = mockk<Behaviour>()
        val perception = HashMap<Behaviour, Double>()
        FieldUtils.writeField(belief, "perception", perception, true)

        assertEquals(null, belief.getPerception(behaviour))
    }

    @Test
    fun `setPerception delete when exists`() {
        val belief = BasicBelief("belief")
        val behaviour = mockk<Behaviour>()
        val perception = HashMap<Behaviour, Double>()
        perception[behaviour] = 0.5
        FieldUtils.writeField(belief, "perception", perception, true)

        belief.setPerception(behaviour, null)
        assertEquals(null, perception[behaviour])
    }

    @Test
    fun `setPerception delete when not exists`() {
        val belief = BasicBelief("belief")
        val behaviour = mockk<Behaviour>()
        val perception = HashMap<Behaviour, Double>()
        FieldUtils.writeField(belief, "perception", perception, true)

        belief.setPerception(behaviour, null)
        assertEquals(null, perception[behaviour])
    }

    @Test
    fun `setPerception when too high`() {
        val belief = BasicBelief("belief")
        val behaviour = mockk<Behaviour>()
        val perception = HashMap<Behaviour, Double>()
        FieldUtils.writeField(belief, "perception", perception, true)

        val e = assertFailsWith<IllegalArgumentException> { belief.setPerception(behaviour, 2.0) }
        assertEquals(e.message, "perception is greater than 1")
    }

    @Test
    fun `setPerception when too low`() {
        val belief = BasicBelief("belief")
        val behaviour = mockk<Behaviour>()
        val perception = HashMap<Behaviour, Double>()
        FieldUtils.writeField(belief, "perception", perception, true)

        val e = assertFailsWith<IllegalArgumentException> { belief.setPerception(behaviour, -2.0) }
        assertEquals(e.message, "perception is less than -1")
    }

    @Test
    fun `setPerception when exists`() {
        val belief = BasicBelief("belief")
        val behaviour = mockk<Behaviour>()
        val perception = HashMap<Behaviour, Double>()
        perception[behaviour] = 0.5
        FieldUtils.writeField(belief, "perception", perception, true)

        belief.setPerception(behaviour, 0.2)
        assertEquals(0.2, perception[behaviour])
    }

    @Test
    fun `setPerception when not exists`() {
        val belief = BasicBelief("belief")
        val behaviour = mockk<Behaviour>()
        val perception = HashMap<Behaviour, Double>()
        FieldUtils.writeField(belief, "perception", perception, true)

        belief.setPerception(behaviour, 0.2)
        assertEquals(0.2, perception[behaviour])
    }

    @Test
    fun `perception is empty on initialization`() {
        val belief = BasicBelief("belief")
        assertTrue(
            (FieldUtils.readField(belief, "perception", true) as MutableMap<*, *>)
                .isEmpty()
        )
    }

    @Test
    fun `relationship is empty on initialization`() {
        val belief = BasicBelief("belief")
        assertTrue(
            (FieldUtils.readField(belief, "relationship", true) as MutableMap<*, *>)
                .isEmpty()
        )
    }

    @Test
    fun `getRelationship when exists`() {
        val b1 = BasicBelief("belief")
        val b2 = mockk<Belief>()
        val relationship = HashMap<Belief, Double>()
        relationship[b2] = 0.2
        FieldUtils.writeField(b1, "relationship", relationship, true)
        assertEquals(0.2, b1.getRelationship(b2))
    }

    @Test
    fun `getRelationship when not exists`() {
        val b1 = BasicBelief("belief")
        val b2 = mockk<Belief>()
        val relationship = HashMap<Belief, Double>()
        FieldUtils.writeField(b1, "relationship", relationship, true)
        assertEquals(null, b1.getRelationship(b2))
    }

    @Test
    fun `setRelationship delete when exists`() {
        val b1 = BasicBelief("belief")
        val b2 = mockk<Belief>()
        val relationship = HashMap<Belief, Double>()
        relationship[b2] = 0.2
        FieldUtils.writeField(b1, "relationship", relationship, true)
        b1.setRelationship(b2, null)
        assertEquals(null, relationship[b2])
    }

    @Test
    fun `setRelationship delete when not exists`() {
        val b1 = BasicBelief("belief")
        val b2 = mockk<Belief>()
        val relationship = HashMap<Belief, Double>()
        FieldUtils.writeField(b1, "relationship", relationship, true)
        b1.setRelationship(b2, null)
        assertEquals(null, relationship[b2])
    }

    @Test
    fun `setRelationship when too high`() {
        val b1 = BasicBelief("belief")
        val b2 = mockk<Belief>()
        val relationship = HashMap<Belief, Double>()
        FieldUtils.writeField(b1, "relationship", relationship, true)
        val e = assertFailsWith<IllegalArgumentException> { b1.setRelationship(b2, 2.0) }
        assertEquals("relationship is greater than 1", e.message)
    }

    @Test
    fun `setRelationship when too low`() {
        val b1 = BasicBelief("belief")
        val b2 = mockk<Belief>()
        val relationship = HashMap<Belief, Double>()
        FieldUtils.writeField(b1, "relationship", relationship, true)
        val e = assertFailsWith<IllegalArgumentException> { b1.setRelationship(b2, -2.0) }
        assertEquals("relationship is less than -1", e.message)
    }

    @Test
    fun `setRelationship when exists`() {
        val b1 = BasicBelief("belief")
        val b2 = mockk<Belief>()
        val relationship = HashMap<Belief, Double>()
        relationship[b2] = 0.2
        FieldUtils.writeField(b1, "relationship", relationship, true)
        b1.setRelationship(b2, 0.5)
        assertEquals(0.5, relationship[b2])
    }

    @Test
    fun `setRelationship when not exists`() {
        val b1 = BasicBelief("belief")
        val b2 = mockk<Belief>()
        val relationship = HashMap<Belief, Double>()
        FieldUtils.writeField(b1, "relationship", relationship, true)
        b1.setRelationship(b2, -0.5)
        assertEquals(-0.5, relationship[b2])
    }

    @Test
    fun `equals when uuids equal`() {
        val uuid = UUID.randomUUID()
        val b1 = BasicBelief("b1", uuid)
        val b2 = BasicBelief("b2", uuid)

        assertEquals(b1, b2)
    }

    @Test
    fun `equals when same object`() {
        val b = BasicBelief("b")
        assertEquals(b, b)
    }

    @Test
    fun `equals when different class`() {
        val b1 = BasicBelief("b")
        val s = mockk<Belief>()
        assertNotEquals<Any>(s, b1)
    }

    @Test
    fun `equals when null`() {
        val b1 = BasicBelief("b")
        val b2: BasicBelief? = null
        assertNotEquals(b1, b2)
    }

    @Test
    fun `equals when uuids not equal`() {
        val b1 = BasicBelief("b1")
        val b2 = BasicBelief("b2")
        assertNotEquals(b1, b2)
    }

    @Test
    fun `test hashCode`() {
        val uuid = UUID.randomUUID()
        val b = BasicBelief("b", uuid)
        assertEquals(uuid.hashCode(), b.hashCode())
    }
}