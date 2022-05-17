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

import io.mockk.*
import org.apache.commons.lang3.reflect.FieldUtils
import java.util.*
import kotlin.test.*

class BasicAgentTest {
    @Test
    fun `constructor assigns uuid`() {
        val uuid = UUID.randomUUID()
        val a = BasicAgent(uuid)
        assertEquals(uuid, a.uuid)
    }

    @Test
    fun `constructor assigns random uuid`() {
        val a1 = BasicAgent()
        val a2 = BasicAgent()
        assertNotEquals(a1.uuid, a2.uuid)
    }

    @Test
    fun `equals when uuids equal`() {
        val uuid = UUID.randomUUID()
        val a1 = BasicAgent(uuid)
        val a2 = BasicAgent(uuid)

        assertEquals(a1, a2)
    }

    @Test
    fun `equals when same object`() {
        val a1 = BasicAgent()
        assertEquals(a1, a1)
    }

    @Test
    fun `equals when different class`() {
        val a1 = BasicAgent()
        val s = mockk<Belief>()
        assertNotEquals<Any>(s, a1)
    }

    @Test
    fun `equals when null`() {
        val a1 = BasicAgent()
        val a2: BasicAgent? = null
        assertNotEquals(a1, a2)
    }

    @Test
    fun `equals when uuids not equal`() {
        val a1 = BasicAgent()
        val a2 = BasicAgent()
        assertNotEquals(a1, a2)
    }

    @Test
    fun `test hashCode`() {
        val uuid = UUID.randomUUID()
        val a = BasicAgent(uuid)
        assertEquals(uuid.hashCode(), a.hashCode())
    }

    @Test
    fun `test activation is initialized empty`() {
        val agent = BasicAgent()
        assertTrue(
            (FieldUtils.readField(agent, "activation", true) as MutableMap<*, *>)
                .isEmpty()
        )
    }

    @Test
    fun `getActivation when exists`() {
        val a = BasicAgent()
        val b = mockk<Belief>()
        val act = HashMap<UInt, MutableMap<Belief, Double>>()
        val actAt2 = HashMap<Belief, Double>()
        actAt2[b] = 0.5
        act[2u] = actAt2
        FieldUtils.writeField(a, "activation", act, true)
        assertEquals(0.5, a.getActivation(2u, b))
    }

    @Test
    fun `getActivation when time exists but belief doesn't`() {
        val a = BasicAgent()
        val b = mockk<Belief>()
        val act = HashMap<UInt, MutableMap<Belief, Double>>()
        val actAt2 = HashMap<Belief, Double>()
        act[2u] = actAt2
        FieldUtils.writeField(a, "activation", act, true)
        assertEquals(null, a.getActivation(2u, b))
    }

    @Test
    fun `getActivation when not exists`() {
        val a = BasicAgent()
        val b = mockk<Belief>()
        val act = HashMap<UInt, MutableMap<Belief, Double>>()
        FieldUtils.writeField(a, "activation", act, true)
        assertEquals(null, a.getActivation(2u, b))
    }

    @Test
    fun `setActivation delete when exists`() {
        val a = BasicAgent()
        val b = mockk<Belief>()
        val act = HashMap<UInt, MutableMap<Belief, Double>>()
        val actAt2 = HashMap<Belief, Double>()
        actAt2[b] = 0.5
        act[2u] = actAt2
        FieldUtils.writeField(a, "activation", act, true)
        a.setActivation(2u, b, null)
        assertNull(actAt2[b])
    }

    @Test
    fun `setActivation delete when time exists but belief doesn't`() {
        val a = BasicAgent()
        val b = mockk<Belief>()
        val act = HashMap<UInt, MutableMap<Belief, Double>>()
        val actAt2 = HashMap<Belief, Double>()
        act[2u] = actAt2
        FieldUtils.writeField(a, "activation", act, true)
        a.setActivation(2u, b, null)
        assertNull(actAt2[b])
    }

    @Test
    fun `setActivation delete when not exists`() {
        val a = BasicAgent()
        val b = mockk<Belief>()
        val act = HashMap<UInt, MutableMap<Belief, Double>>()
        FieldUtils.writeField(a, "activation", act, true)
        a.setActivation(2u, b, null)
        assertNull(act[2u])
    }

    @Test
    fun `setActivation throws IllegalArgumentException when too high`() {
        val a = BasicAgent()
        val b = mockk<Belief>()
        val e = assertFailsWith(
            IllegalArgumentException::class
        ) { a.setActivation(2u, b, 2.0); }
        assertEquals("new activation is greater than 1", e.message)
    }

    @Test
    fun `setActivation throws IllegalArgumentException when too low`() {
        val a = BasicAgent()
        val b = mockk<Belief>()
        val e = assertFailsWith(
            IllegalArgumentException::class
        ) { a.setActivation(2u, b, -2.0); }
        assertEquals("new activation is less than -1", e.message)
    }

    @Test
    fun `setActivation when exists`() {
        val a = BasicAgent()
        val b = mockk<Belief>()
        val act = HashMap<UInt, MutableMap<Belief, Double>>()
        val actAt2 = HashMap<Belief, Double>()
        actAt2[b] = 0.5
        act[2u] = actAt2
        FieldUtils.writeField(a, "activation", act, true)
        a.setActivation(2u, b, 0.2)
        assertEquals(0.2, actAt2[b])
    }

    @Test
    fun `setActivation when time exists but belief doesn't`() {
        val a = BasicAgent()
        val b = mockk<Belief>()
        val act = HashMap<UInt, MutableMap<Belief, Double>>()
        val actAt2 = HashMap<Belief, Double>()
        act[2u] = actAt2
        FieldUtils.writeField(a, "activation", act, true)
        a.setActivation(2u, b, 0.2)
        assertEquals(0.2, actAt2[b])
    }

    @Test
    fun `setActivation when not exists`() {
        val a = BasicAgent()
        val b = mockk<Belief>()
        val act = HashMap<UInt, MutableMap<Belief, Double>>()
        FieldUtils.writeField(a, "activation", act, true)
        a.setActivation(2u, b, 0.2)
        assertEquals(0.2, act[2u]?.get(b))
    }

    @Test
    fun `weightedRelationship when exists`() {
        val a = mockk<BasicAgent>()
        val b1 = mockk<Belief>()
        val b2 = mockk<Belief>()

        every { a.getActivation(2u, b1) } returns 0.5
        every { b1.getRelationship(b2) } returns 0.1
        every { a.weightedRelationship(2u, b1, b2) } answers { callOriginal() }

        assertEquals(0.05, a.weightedRelationship(2u, b1, b2))

        verify { a.getActivation(2u, b1) }
        verify { b1.getRelationship(b2) }
    }

    @Test
    fun `weightedRelationship when activation not exists`() {
        val a = mockk<BasicAgent>()
        val b1 = mockk<Belief>()
        val b2 = mockk<Belief>()

        every { a.getActivation(2u, b1) } returns null
        every { b1.getRelationship(b2) } returns 0.1
        every { a.weightedRelationship(2u, b1, b2) } answers { callOriginal() }

        assertEquals(null, a.weightedRelationship(2u, b1, b2))

        verify { a.getActivation(2u, b1) }
        verify(exactly = 0) { b1.getRelationship(b2) }
    }

    @Test
    fun `weightedRelationship when relationship not exists`() {
        val a = mockk<BasicAgent>()
        val b1 = mockk<Belief>()
        val b2 = mockk<Belief>()

        every { a.getActivation(2u, b1) } returns 0.5
        every { b1.getRelationship(b2) } returns null
        every { a.weightedRelationship(2u, b1, b2) } answers { callOriginal() }

        assertEquals(null, a.weightedRelationship(2u, b1, b2))

        verify { a.getActivation(2u, b1) }
        verify { b1.getRelationship(b2) }
    }

    @Test
    fun `weightedRelationship when relationship and activation not exists`() {
        val a = mockk<BasicAgent>()
        val b1 = mockk<Belief>()
        val b2 = mockk<Belief>()

        every { a.getActivation(2u, b1) } returns null
        every { b1.getRelationship(b2) } returns null
        every { a.weightedRelationship(2u, b1, b2) } answers { callOriginal() }

        assertEquals(null, a.weightedRelationship(2u, b1, b2))

        verify { a.getActivation(2u, b1) }
        verify(exactly = 0) { b1.getRelationship(b2) }
    }

    @Test
    fun `contextualise when Beliefs empty returns 0`() {
        val b = mockk<Belief>()
        val a = mockk<BasicAgent>()
        val beliefs = HashSet<Belief>()

        every { a.contextualise(2u, b, beliefs) } answers { callOriginal() }

        assertEquals(0.0, a.contextualise(2u, b, beliefs))
    }

    @Test
    fun `contextualise when Beliefs non-empty and all weightedRelationships non-null`() {
        val a = mockk<BasicAgent>()
        val b1 = mockk<Belief>()
        val b2 = mockk<Belief>()
        val beliefs = hashSetOf<Belief>(b1, b2)

        every { a.weightedRelationship(2u, b1, b1) } returns 0.5
        every { a.weightedRelationship(2u, b1, b2) } returns -0.75
        every { a.contextualise(2u, b1, beliefs) } answers { callOriginal() }

        assertEquals(-0.125, a.contextualise(2u, b1, beliefs))

        verify(exactly = 1) { a.weightedRelationship(2u, b1, b1) }
        verify(exactly = 1) { a.weightedRelationship(2u, b1, b2) }
    }

    @Test
    fun `contextualise when Beliefs non-empty and not all weightedRelationships non-null`() {
        val a = mockk<BasicAgent>()
        val b1 = mockk<Belief>()
        val b2 = mockk<Belief>()
        val beliefs = hashSetOf<Belief>(b1, b2)

        every { a.weightedRelationship(2u, b1, b1) } returns 0.5
        every { a.weightedRelationship(2u, b1, b2) } returns null
        every { a.contextualise(2u, b1, beliefs) } answers { callOriginal() }

        assertEquals(0.25, a.contextualise(2u, b1, beliefs))

        verify(exactly = 1) { a.weightedRelationship(2u, b1, b1) }
        verify(exactly = 1) { a.weightedRelationship(2u, b1, b2) }
    }

    @Test
    fun `contextualise when Beliefs non-empty and not all weightedRelationships null`() {
        val a = mockk<BasicAgent>()
        val b1 = mockk<Belief>()
        val b2 = mockk<Belief>()
        val beliefs = hashSetOf<Belief>(b1, b2)

        every { a.weightedRelationship(2u, b1, b1) } returns null
        every { a.weightedRelationship(2u, b1, b2) } returns null
        every { a.contextualise(2u, b1, beliefs) } answers { callOriginal() }

        assertEquals(0.0, a.contextualise(2u, b1, beliefs))

        verify(exactly = 1) { a.weightedRelationship(2u, b1, b1) }
        verify(exactly = 1) { a.weightedRelationship(2u, b1, b2) }
    }

    @Test
    fun `test friends is initialized empty`() {
        val agent = BasicAgent()
        assertTrue(
            (FieldUtils.readField(agent, "friends", true) as MutableMap<*, *>)
                .isEmpty()
        )
    }

    @Test
    fun `test getFriends when empty`() {
        val agent = BasicAgent()
        val friends: MutableMap<Agent, Double> = HashMap()
        FieldUtils.writeField(agent, "friends", friends, true)
        assertEquals<Collection<Pair<Agent, Double>>>(friends.toList(), agent.getFriends())
        assertTrue(agent.getFriends().isEmpty())
    }

    @Test
    fun `setFriendWeight when not exists and valid`() {
        val agent = BasicAgent()
        val friends: MutableMap<Agent, Double> = HashMap()
        FieldUtils.writeField(agent, "friends", friends, true)

        val a2 = mockk<Agent>()
        agent.setFriendWeight(a2, 0.5)
        assertEquals(0.5, friends[a2])
    }

    @Test
    fun `setFriendWeight when exists and valid`() {
        val agent = BasicAgent()
        val friends: MutableMap<Agent, Double> = HashMap()
        FieldUtils.writeField(agent, "friends", friends, true)

        val a2 = mockk<Agent>()
        friends[a2] = 0.2
        agent.setFriendWeight(a2, 0.5)
        assertEquals(0.5, friends[a2])
    }

    @Test
    fun `setFriendWeight when not exists and valid delete`() {
        val agent = BasicAgent()
        val friends: MutableMap<Agent, Double> = HashMap()
        FieldUtils.writeField(agent, "friends", friends, true)

        val a2 = mockk<Agent>()
        agent.setFriendWeight(a2, null)
        assertEquals(null, friends[a2])
    }

    @Test
    fun `setFriendWeight when exists and valid delete`() {
        val agent = BasicAgent()
        val friends: MutableMap<Agent, Double> = HashMap()
        FieldUtils.writeField(agent, "friends", friends, true)

        val a2 = mockk<Agent>()
        friends[a2] = 0.2
        agent.setFriendWeight(a2, null)
        assertEquals(null, friends[a2])
    }

    @Test
    fun `setFriendWeight when not exists and too high`() {
        val agent = BasicAgent()
        val friends: MutableMap<Agent, Double> = HashMap()
        FieldUtils.writeField(agent, "friends", friends, true)

        val a2 = mockk<Agent>()
        val e = assertFailsWith(IllegalArgumentException::class) { agent.setFriendWeight(a2, 1.1) }
        assertEquals(null, friends[a2])
        assertEquals("weight greater than 1", e.message)
    }

    @Test
    fun `setFriendWeight when exists and too high`() {
        val agent = BasicAgent()
        val friends: MutableMap<Agent, Double> = HashMap()
        FieldUtils.writeField(agent, "friends", friends, true)

        val a2 = mockk<Agent>()
        friends[a2] = 0.2
        val e = assertFailsWith(IllegalArgumentException::class) { agent.setFriendWeight(a2, 1.1) }
        assertEquals(0.2, friends[a2])
        assertEquals("weight greater than 1", e.message)
    }

    @Test
    fun `setFriendWeight when not exists and too low`() {
        val agent = BasicAgent()
        val friends: MutableMap<Agent, Double> = HashMap()
        FieldUtils.writeField(agent, "friends", friends, true)

        val a2 = mockk<Agent>()
        val e = assertFailsWith(IllegalArgumentException::class) { agent.setFriendWeight(a2, -0.1) }
        assertEquals(null, friends[a2])
        assertEquals("weight less than 0", e.message)
    }

    @Test
    fun `setFriendWeight when exists and too low`() {
        val agent = BasicAgent()
        val friends: MutableMap<Agent, Double> = HashMap()
        FieldUtils.writeField(agent, "friends", friends, true)

        val a2 = mockk<Agent>()
        friends[a2] = 0.2
        val e = assertFailsWith(IllegalArgumentException::class) { agent.setFriendWeight(a2, -0.1) }
        assertEquals(0.2, friends[a2])
        assertEquals("weight less than 0", e.message)
    }

    @Test
    fun `getFriendWeight when exists`() {
        val agent = BasicAgent()
        val friends: MutableMap<Agent, Double> = HashMap()
        FieldUtils.writeField(agent, "friends", friends, true)

        val a2 = mockk<Agent>()
        friends[a2] = 0.2
        assertEquals(0.2, agent.getFriendWeight(a2))
    }

    @Test
    fun `getFriendWeight when not exists`() {
        val agent = BasicAgent()
        val friends: MutableMap<Agent, Double> = HashMap()
        FieldUtils.writeField(agent, "friends", friends, true)

        val a2 = mockk<Agent>()
        assertEquals(null, agent.getFriendWeight(a2))
    }

    @Test
    fun `test actions is initialized empty`() {
        val agent = BasicAgent()
        assertTrue(
            (FieldUtils.readField(agent, "actions", true) as MutableMap<*, *>)
                .isEmpty()
        )
    }

    @Test
    fun `getAction when exists`() {
        val agent = BasicAgent()
        val actions: MutableMap<UInt, Behaviour> = HashMap()
        FieldUtils.writeField(agent, "actions", actions, true)
        val behaviour = mockk<Behaviour>()
        actions[2u] = behaviour

        assertEquals(behaviour, agent.getAction(2u))
    }

    @Test
    fun `getAction when not exists`() {
        val agent = BasicAgent()
        val actions: MutableMap<UInt, Behaviour> = HashMap()
        FieldUtils.writeField(agent, "actions", actions, true)

        assertEquals(null, agent.getAction(2u))
    }

    @Test
    fun `setAction when exists`() {
        val agent = BasicAgent()
        val actions: MutableMap<UInt, Behaviour> = HashMap()
        FieldUtils.writeField(agent, "actions", actions, true)
        val b1 = mockk<Behaviour>()
        val b2 = mockk<Behaviour>()
        actions[2u] = b1
        agent.setAction(2u, b2)
        assertEquals(b2, actions[2u])
    }

    @Test
    fun `setAction when exists delete`() {
        val agent = BasicAgent()
        val actions: MutableMap<UInt, Behaviour> = HashMap()
        FieldUtils.writeField(agent, "actions", actions, true)
        val b1 = mockk<Behaviour>()
        actions[2u] = b1
        agent.setAction(2u, null)
        assertEquals(null, actions[2u])
    }

    @Test
    fun `setAction when not exists`() {
        val agent = BasicAgent()
        val actions: MutableMap<UInt, Behaviour> = HashMap()
        FieldUtils.writeField(agent, "actions", actions, true)
        val b1 = mockk<Behaviour>()
        agent.setAction(2u, b1)
        assertEquals(b1, actions[2u])
    }

    @Test
    fun `setAction when not exists delete`() {
        val agent = BasicAgent()
        val actions: MutableMap<UInt, Behaviour> = HashMap()
        FieldUtils.writeField(agent, "actions", actions, true)
        agent.setAction(2u, null)
        assertEquals(null, actions[2u])
    }

    @Test
    fun `pressure when no friends`() {
        val agent = BasicAgent()
        val belief = mockk<Belief>()
        val friends: MutableMap<Agent, Double> = HashMap()
        FieldUtils.writeField(agent, "friends", friends, true)
        assertEquals(0.0, agent.pressure(2u, belief))
    }

    @Test
    fun `pressure when friends did nothing`() {
        val agent = BasicAgent()
        val f1 = mockk<Agent>()
        val f2 = mockk<Agent>()

        every { f1.getAction(2u) } returns null
        every { f2.getAction(2u) } returns null

        val belief = mockk<Belief>()
        val friends: MutableMap<Agent, Double> = HashMap()
        friends[f1] = 0.5
        friends[f2] = 1.0
        FieldUtils.writeField(agent, "friends", friends, true)
        assertEquals(0.0, agent.pressure(2u, belief))

        verify(exactly = 1) { f1.getAction(2u) }
        verify(exactly = 1) { f2.getAction(2u) }
    }

    @Test
    fun `pressure when friends did something but perception is null`() {
        val agent = BasicAgent()
        val f1 = mockk<Agent>()
        val f2 = mockk<Agent>()
        val b1 = mockk<Behaviour>()
        val b2 = mockk<Behaviour>()

        every { f1.getAction(2u) } returns b1
        every { f2.getAction(2u) } returns b2

        val belief = mockk<Belief>()

        every { belief.getPerception(b1) } returns null
        every { belief.getPerception(b2) } returns null

        val friends: MutableMap<Agent, Double> = HashMap()
        friends[f1] = 0.5
        friends[f2] = 1.0
        FieldUtils.writeField(agent, "friends", friends, true)
        assertEquals(0.0, agent.pressure(2u, belief))

        verify(exactly = 1) { f1.getAction(2u) }
        verify(exactly = 1) { f2.getAction(2u) }
        verify(exactly = 1) { belief.getPerception(b1) }
        verify(exactly = 1) { belief.getPerception(b2) }
    }

    @Test
    fun `pressure when friends did something`() {
        val agent = BasicAgent()
        val f1 = mockk<Agent>()
        val f2 = mockk<Agent>()
        val b1 = mockk<Behaviour>()
        val b2 = mockk<Behaviour>()

        every { f1.getAction(2u) } returns b1
        every { f2.getAction(2u) } returns b2

        val belief = mockk<Belief>()

        every { belief.getPerception(b1) } returns 0.2
        every { belief.getPerception(b2) } returns 0.3

        val friends: MutableMap<Agent, Double> = HashMap()
        friends[f1] = 0.5
        friends[f2] = 1.0
        FieldUtils.writeField(agent, "friends", friends, true)
        assertEquals(0.2, agent.pressure(2u, belief))

        verify(exactly = 1) { f1.getAction(2u) }
        verify(exactly = 1) { f2.getAction(2u) }
        verify(exactly = 1) { belief.getPerception(b1) }
        verify(exactly = 1) { belief.getPerception(b2) }
    }

    @Test
    fun `activationChange when pressure positive`() {
        val agent = mockk<BasicAgent>()
        val belief = mockk<Belief>()
        val beliefs = mockk<Collection<Belief>>()

        every { agent.contextualise(2u, belief, beliefs) } returns 0.5
        every { agent.pressure(2u, belief) } returns 0.1
        every { agent.activationChange(2u, belief, beliefs) } answers { callOriginal() }

        assertEquals(0.075, agent.activationChange(2u, belief, beliefs), 0.001)

        verify(exactly = 1) { agent.contextualise(2u, belief, beliefs) }
        verify(exactly = 1) { agent.pressure(2u, belief) }
    }

    @Test
    fun `activationChange when pressure negative`() {
        val agent = mockk<BasicAgent>()
        val belief = mockk<Belief>()
        val beliefs = mockk<Collection<Belief>>()

        every { agent.contextualise(2u, belief, beliefs) } returns 0.5
        every { agent.pressure(2u, belief) } returns -0.1
        every { agent.activationChange(2u, belief, beliefs) } answers { callOriginal() }

        assertEquals(-0.025, agent.activationChange(2u, belief, beliefs))

        verify(exactly = 1) { agent.contextualise(2u, belief, beliefs) }
        verify(exactly = 1) { agent.pressure(2u, belief) }
    }

    @Test
    fun `test delta is initialized empty`() {
        val agent = BasicAgent()
        assertTrue(
            (FieldUtils.readField(agent, "delta", true) as MutableMap<*, *>)
                .isEmpty()
        )
    }

    @Test
    fun `getDelta when exists`() {
        val agent = BasicAgent()
        val belief = mockk<Belief>()
        val delta: MutableMap<Belief, Double> = HashMap()
        delta[belief] = 1.1
        FieldUtils.writeField(agent, "delta", delta, true)

        assertEquals(1.1, agent.getDelta(belief))
    }

    @Test
    fun `getDelta when not exists`() {
        val agent = BasicAgent()
        val belief = mockk<Belief>()
        val delta: MutableMap<Belief, Double> = HashMap()
        FieldUtils.writeField(agent, "delta", delta, true)

        assertEquals(null, agent.getDelta(belief))
    }

    @Test
    fun `setDelta when exists and valid`() {
        val agent = BasicAgent()
        val belief = mockk<Belief>()
        val delta: MutableMap<Belief, Double> = HashMap()
        delta[belief] = 1.1
        FieldUtils.writeField(agent, "delta", delta, true)
        agent.setDelta(belief, 1.2)
        assertEquals(1.2, delta[belief])
    }

    @Test
    fun `setDelta when exists and valid delete`() {
        val agent = BasicAgent()
        val belief = mockk<Belief>()
        val delta: MutableMap<Belief, Double> = HashMap()
        delta[belief] = 1.1
        FieldUtils.writeField(agent, "delta", delta, true)
        agent.setDelta(belief, null)
        assertEquals(null, delta[belief])
    }

    @Test
    fun `setDelta when not exists and valid`() {
        val agent = BasicAgent()
        val belief = mockk<Belief>()
        val delta: MutableMap<Belief, Double> = HashMap()
        FieldUtils.writeField(agent, "delta", delta, true)
        agent.setDelta(belief, 1.2)
        assertEquals(1.2, delta[belief])
    }

    @Test
    fun `setDelta when not exists and valid delete`() {
        val agent = BasicAgent()
        val belief = mockk<Belief>()
        val delta: MutableMap<Belief, Double> = HashMap()
        FieldUtils.writeField(agent, "delta", delta, true)
        agent.setDelta(belief, null)
        assertEquals(null, delta[belief])
    }

    @Test
    fun `setDelta when exists and too low`() {
        val agent = BasicAgent()
        val belief = mockk<Belief>()
        val delta: MutableMap<Belief, Double> = HashMap()
        delta[belief] = 1.1
        FieldUtils.writeField(agent, "delta", delta, true)
        val e = assertFailsWith(IllegalArgumentException::class) { agent.setDelta(belief, -0.1) }
        assertEquals(1.1, delta[belief])
        assertEquals("delta not strictly positive", e.message)
    }

    @Test
    fun `setDelta when not exists and too low`() {
        val agent = BasicAgent()
        val belief = mockk<Belief>()
        val delta: MutableMap<Belief, Double> = HashMap()
        FieldUtils.writeField(agent, "delta", delta, true)
        val e = assertFailsWith(IllegalArgumentException::class) { agent.setDelta(belief, -0.1) }
        assertEquals(null, delta[belief])
        assertEquals("delta not strictly positive", e.message)
    }

    @Test
    fun `updateActivation when previous activation null`() {
        val agent = mockk<BasicAgent>()
        val belief = mockk<Belief>()
        val beliefs = mockk<Collection<Belief>>()

        every { agent.getActivation(1u, belief) } returns null
        every { agent.getDelta(belief) } returns 1.1
        every { agent.updateActivation(2u, belief, beliefs) } answers { callOriginal() }

        val e = assertFailsWith(IllegalArgumentException::class) { agent.updateActivation(2u, belief, beliefs) }
        assertEquals("activation not calculated at previous time step", e.message)

        verify(exactly = 1) { agent.getActivation(1u, belief) }
    }

    @Test
    fun `updateActivation when delta is null`() {
        val agent = mockk<BasicAgent>()
        val belief = mockk<Belief>()
        val beliefs = mockk<Collection<Belief>>()

        every { agent.getDelta(belief) } returns null
        every { agent.updateActivation(2u, belief, beliefs) } answers { callOriginal() }

        val e = assertFailsWith(IllegalArgumentException::class) { agent.updateActivation(2u, belief, beliefs) }
        assertEquals("Delta for belief null", e.message)

        verify(exactly = 1) { agent.getDelta(belief) }
    }

    @Test
    fun `updateActivation when new value within range`() {
        val agent = mockk<BasicAgent>()
        val belief = mockk<Belief>()
        val beliefs = mockk<Collection<Belief>>()

        every { agent.getActivation(1u, belief) } returns 0.2
        every { agent.getDelta(belief) } returns 1.1
        every { agent.activationChange(1u, belief, beliefs) } returns 0.3
        every { agent.updateActivation(2u, belief, beliefs) } answers { callOriginal() }
        every { agent.setActivation(any(), any(), any()) } just Runs

        agent.updateActivation(2u, belief, beliefs)

        verify(exactly = 1) { agent.getActivation(1u, belief) }
        verify(exactly = 1) { agent.getDelta(belief) }
        verify(exactly = 1) { agent.activationChange(1u, belief, beliefs) }
        verify(exactly = 1) { agent.setActivation(2u, belief, 0.2 * 1.1 + 0.3) }
    }

    @Test
    fun `updateActivation when new value greater than 1`() {
        val agent = mockk<BasicAgent>()
        val belief = mockk<Belief>()
        val beliefs = mockk<Collection<Belief>>()

        every { agent.getActivation(1u, belief) } returns 0.2
        every { agent.getDelta(belief) } returns 1.1
        every { agent.activationChange(1u, belief, beliefs) } returns 1.0
        every { agent.updateActivation(2u, belief, beliefs) } answers { callOriginal() }
        every { agent.setActivation(any(), any(), any()) } just Runs

        agent.updateActivation(2u, belief, beliefs)

        verify(exactly = 1) { agent.getActivation(1u, belief) }
        verify(exactly = 1) { agent.getDelta(belief) }
        verify(exactly = 1) { agent.activationChange(1u, belief, beliefs) }
        verify(exactly = 1) { agent.setActivation(2u, belief, 1.0) }
    }

    @Test
    fun `updateActivation when new value less than 1`() {
        val agent = mockk<BasicAgent>()
        val belief = mockk<Belief>()
        val beliefs = mockk<Collection<Belief>>()

        every { agent.getActivation(1u, belief) } returns -0.2
        every { agent.getDelta(belief) } returns 1.1
        every { agent.activationChange(1u, belief, beliefs) } returns -1.0
        every { agent.updateActivation(2u, belief, beliefs) } answers { callOriginal() }
        every { agent.setActivation(any(), any(), any()) } just Runs

        agent.updateActivation(2u, belief, beliefs)

        verify(exactly = 1) { agent.getActivation(1u, belief) }
        verify(exactly = 1) { agent.getDelta(belief) }
        verify(exactly = 1) { agent.activationChange(1u, belief, beliefs) }
        verify(exactly = 1) { agent.setActivation(2u, belief, -1.0) }
    }
}