package dev.r0bert.beliefspread.core

import java.util.UUID
import org.apache.commons.lang3.reflect.FieldUtils
import scala.collection.mutable
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet

class BasicAgentTest extends munit.FunSuite {
  test("constructor assigns uuid") {
    val uuid = UUID.randomUUID()
    val a = BasicAgent(uuid)
    assertEquals(a.uuid, uuid)
  }

  test("constructor assigns random uuid") {
    val a1 = BasicAgent()
    val a2 = BasicAgent()
    assertNotEquals(a1.uuid, a2.uuid)
  }

  test("equals when uuids equal") {
    val uuid = UUID.randomUUID
    val a1 = BasicAgent(uuid)
    val a2 = BasicAgent(uuid)

    assertEquals(a1, a2)
  }

  test("equals when same object") {
    val a = BasicAgent()
    assertEquals(a, a)
  }

  test("equals when uuids different") {
    val a1 = BasicAgent()
    val a2 = BasicAgent()
    assertNotEquals(a1, a2)
  }

  test("test hashCode") {
    val uuid = UUID.randomUUID
    val a = BasicAgent(uuid)
    assertEquals(a.hashCode, uuid.hashCode)
  }

  test("activation is initialized empty") {
    val agent = BasicAgent()
    assert(
      FieldUtils
        .readField(agent, "activation", true)
        .asInstanceOf[mutable.Map[Int, mutable.Map[Belief, Double]]]
        .isEmpty
    )
  }

  test("getActivation when exists") {
    val a = BasicAgent()
    val b = BasicBelief("b")
    val act = HashMap[Int, mutable.Map[Belief, Double]]()
    val actAt2 = HashMap[Belief, Double]()
    actAt2.put(b, 0.5)
    act.put(2, actAt2)
    FieldUtils.writeField(a, "activation", act, true)
    assertEquals(a.getActivation(2, b), Some(0.5))
  }

  test("getActivation when time exists but belief doesn't") {
    val a = BasicAgent()
    val b = BasicBelief("b")
    val act = HashMap[Int, mutable.Map[Belief, Double]]()
    val actAt2 = HashMap[Belief, Double]()
    act.put(2, actAt2)
    FieldUtils.writeField(a, "activation", act, true)
    assertEquals(a.getActivation(2, b), None)
  }

  test("getActivation when not exists") {
    val a = BasicAgent()
    val b = BasicBelief("b")
    val act = HashMap[Int, mutable.Map[Belief, Double]]()
    FieldUtils.writeField(a, "activation", act, true)
    assertEquals(a.getActivation(2, b), None)
  }

  test("setActivation delete when exists") {
    val a = BasicAgent()
    val b = BasicBelief("b")
    val act = HashMap[Int, mutable.Map[Belief, Double]]()
    val actAt2 = HashMap[Belief, Double]()
    actAt2.put(b, 0.5)
    act.put(2, actAt2)
    FieldUtils.writeField(a, "activation", act, true)
    a.setActivation(2, b, None)
    assertEquals(actAt2.get(b), None)
  }

  test("setActivation delete when time exists but belief doesn't") {
    val a = BasicAgent()
    val b = BasicBelief("b")
    val act = HashMap[Int, mutable.Map[Belief, Double]]()
    val actAt2 = HashMap[Belief, Double]()
    act.put(2, actAt2)
    FieldUtils.writeField(a, "activation", act, true)
    a.setActivation(2, b, None)
    assertEquals(actAt2.get(b), None)
  }

  test("setActivation delete when not exists") {
    val a = BasicAgent()
    val b = BasicBelief("b")
    val act = HashMap[Int, mutable.Map[Belief, Double]]()
    FieldUtils.writeField(a, "activation", act, true)
    a.setActivation(2, b, None)
    assertEquals(act.get(2), None)
  }

  test("setActivation throws IllegalArgumentException when too high") {
    val a = BasicAgent()
    val b = BasicBelief("b")
    interceptMessage[IllegalArgumentException](
      "new activation is greater than 1"
    ) {
      a.setActivation(2, b, Some(2.0))
    }
  }

  test("setActivation throws IllegalArgumentException when too low") {
    val a = BasicAgent()
    val b = BasicBelief("b")
    interceptMessage[IllegalArgumentException](
      "new activation is less than -1"
    ) {
      a.setActivation(2, b, Some(-2.0))
    }
  }

  test("setActivation when exists") {
    val a = BasicAgent()
    val b = BasicBelief("b")
    val act = HashMap[Int, mutable.Map[Belief, Double]]()
    val actAt2 = HashMap[Belief, Double]()
    actAt2.put(b, 0.5)
    act.put(2, actAt2)
    FieldUtils.writeField(a, "activation", act, true)
    a.setActivation(2, b, Some(0.2))
    assertEquals(actAt2.get(b), Some(0.2))
  }

  test("setActivation when time exists but belief doesn't") {
    val a = BasicAgent()
    val b = BasicBelief("b")
    val act = HashMap[Int, mutable.Map[Belief, Double]]()
    val actAt2 = HashMap[Belief, Double]()
    act.put(2, actAt2)
    FieldUtils.writeField(a, "activation", act, true)
    a.setActivation(2, b, Some(0.2))
    assertEquals(actAt2.get(b), Some(0.2))
  }

  test("setActivation when not exists") {
    val a = BasicAgent()
    val b = BasicBelief("b")
    val act = HashMap[Int, mutable.Map[Belief, Double]]()
    FieldUtils.writeField(a, "activation", act, true)
    a.setActivation(2, b, Some(0.2))
    assertEquals(act.get(2).get.get(b), Some(0.2))
  }

  test("weightedRelationship when exists") {
    val a = BasicAgent()
    val b1 = BasicBelief("b1")
    val b2 = BasicBelief("b2")

    a.setActivation(2, b1, Some(0.5))
    b1.setRelationship(b2, Some(0.1))

    assertEquals(a.weightedRelationship(2, b1, b2), Some(0.05))
  }

  test("weightedRelationship when activation not exists") {
    val a = BasicAgent()
    val b1 = BasicBelief("b1")
    val b2 = BasicBelief("b2")

    b1.setRelationship(b2, Some(0.1))

    assertEquals(a.weightedRelationship(2, b1, b2), None)
  }

  test("weightedRelationship when relationship not exists") {
    val a = BasicAgent()
    val b1 = BasicBelief("b1")
    val b2 = BasicBelief("b2")

    a.setActivation(2, b1, Some(0.5))

    assertEquals(a.weightedRelationship(2, b1, b2), None)
  }

  test("weightedRelationship when activation and relationship not exists") {
    val a = BasicAgent()
    val b1 = BasicBelief("b1")
    val b2 = BasicBelief("b2")

    assertEquals(a.weightedRelationship(2, b1, b2), None)
  }

  test("contextualise when beliefs empty returns 0") {
    val b = BasicBelief("b")
    val a = BasicAgent()
    val beliefs = HashSet[Belief]()

    assertEquals(a.contextualise(2, b, beliefs), 0.0)
  }

  test(
    "contextualise when beliefs non-empty and all weightedRelationships non-null"
  ) {
    val a = BasicAgent()
    val b1 = BasicBelief("b1")
    val b2 = BasicBelief("b2")
    val beliefs = HashSet[Belief](b1, b2)
    a.setActivation(2, b1, Some(1))
    a.setActivation(2, b2, Some(1))
    b1.setRelationship(b1, Some(0.5))
    b1.setRelationship(b2, Some(-0.75))

    assertEquals(a.contextualise(2, b1, beliefs), -0.125)
  }

  test(
    "contextualise when beliefs non-empty and not all weightedRelationships non-null"
  ) {
    val a = BasicAgent()
    val b1 = BasicBelief("b1")
    val b2 = BasicBelief("b2")
    val beliefs = HashSet[Belief](b1, b2)
    a.setActivation(2, b1, Some(0.5))
    a.setActivation(2, b2, Some(1))
    b1.setRelationship(b1, Some(1))

    assertEquals(a.contextualise(2, b1, beliefs), 0.25)
  }

  test(
    "contextualise when beliefs non-empty and all weightedRelationships null"
  ) {
    val a = BasicAgent()
    val b1 = BasicBelief("b1")
    val b2 = BasicBelief("b2")
    val beliefs = HashSet[Belief](b1, b2)
    a.setActivation(2, b1, Some(0.5))
    a.setActivation(2, b2, Some(1))

    assertEquals(a.contextualise(2, b1, beliefs), 0.0)
  }

  test("getFriends is initialized empty") {
    val agent = BasicAgent()
    assert(
      FieldUtils
        .readField(agent, "friends", true)
        .asInstanceOf[mutable.Map[Agent, Double]]
        .isEmpty
    )
  }

  test("getFriends when empty") {
    val agent = BasicAgent()
    val friends: mutable.Map[Agent, Double] = HashMap()
    FieldUtils.writeField(agent, "friends", friends, true)
    assertEquals(agent.getFriends(), friends.toList)
    assert(agent.getFriends().isEmpty)
  }

  test("getFriends when not empty") {
    val agent = BasicAgent()
    val a2 = BasicAgent()
    val friends: mutable.Map[Agent, Double] = HashMap()
    friends.put(a2, 0.3)
    FieldUtils.writeField(agent, "friends", friends, true)
    assertEquals(agent.getFriends(), friends.toList)
  }

  test("setFriendWeight when not exists and valid") {
    val agent = BasicAgent()
    val friends: mutable.Map[Agent, Double] = HashMap()
    FieldUtils.writeField(agent, "friends", friends, true)

    val a2 = BasicAgent()
    agent.setFriendWeight(a2, Some(0.5))
    assertEquals(friends.get(a2), Some(0.5))
  }

  test("setFriendWeight when exists and valid") {
    val agent = BasicAgent()
    val friends: mutable.Map[Agent, Double] = HashMap()
    FieldUtils.writeField(agent, "friends", friends, true)

    val a2 = BasicAgent()
    friends.put(a2, 0.2)
    agent.setFriendWeight(a2, Some(0.5))
    assertEquals(friends.get(a2), Some(0.5))
  }

  test("setFriendWeight when exists and valid delete") {
    val agent = BasicAgent()
    val friends: mutable.Map[Agent, Double] = HashMap()
    FieldUtils.writeField(agent, "friends", friends, true)

    val a2 = BasicAgent()
    friends.put(a2, 0.2)
    agent.setFriendWeight(a2, None)
    assertEquals(friends.get(a2), None)
  }

  test("setFriendWeight when not exists and valid delete") {
    val agent = BasicAgent()
    val friends: mutable.Map[Agent, Double] = HashMap()
    FieldUtils.writeField(agent, "friends", friends, true)

    val a2 = BasicAgent()
    agent.setFriendWeight(a2, None)
    assertEquals(friends.get(a2), None)
  }

  test("setFriendWeight when exists and too high") {
    val agent = BasicAgent()
    val friends: mutable.Map[Agent, Double] = HashMap()
    FieldUtils.writeField(agent, "friends", friends, true)

    val a2 = BasicAgent()
    friends.put(a2, 0.2)
    interceptMessage[IllegalArgumentException]("weight greater than 1") {
      agent.setFriendWeight(a2, Some(1.1))
    }
    assertEquals(friends.get(a2), Some(0.2))
  }

  test("setFriendWeight when not exists and too high") {
    val agent = BasicAgent()
    val friends: mutable.Map[Agent, Double] = HashMap()
    FieldUtils.writeField(agent, "friends", friends, true)

    val a2 = BasicAgent()
    interceptMessage[IllegalArgumentException]("weight greater than 1") {
      agent.setFriendWeight(a2, Some(1.1))
    }
    assertEquals(friends.get(a2), None)
  }

  test("setFriendWeight when exists and too low") {
    val agent = BasicAgent()
    val friends: mutable.Map[Agent, Double] = HashMap()
    FieldUtils.writeField(agent, "friends", friends, true)

    val a2 = BasicAgent()
    friends.put(a2, 0.2)
    interceptMessage[IllegalArgumentException]("weight less than 0") {
      agent.setFriendWeight(a2, Some(-0.1))
    }
    assertEquals(friends.get(a2), Some(0.2))
  }

  test("setFriendWeight when not exists and too low") {
    val agent = BasicAgent()
    val friends: mutable.Map[Agent, Double] = HashMap()
    FieldUtils.writeField(agent, "friends", friends, true)

    val a2 = BasicAgent()
    interceptMessage[IllegalArgumentException]("weight less than 0") {
      agent.setFriendWeight(a2, Some(-0.1))
    }
    assertEquals(friends.get(a2), None)
  }

  test("getFriendWeight when exists") {
    val agent = BasicAgent()
    val friends: mutable.Map[Agent, Double] = HashMap()
    FieldUtils.writeField(agent, "friends", friends, true)

    val a2 = BasicAgent()
    friends.put(a2, 0.2)
    assertEquals(agent.getFriendWeight(a2), Some(0.2))
  }

  test("getFriendWeight when not exists") {
    val agent = BasicAgent()
    val friends: mutable.Map[Agent, Double] = HashMap()
    FieldUtils.writeField(agent, "friends", friends, true)

    val a2 = BasicAgent()
    assertEquals(agent.getFriendWeight(a2), None)
  }

  test("actions is initialized empty") {
    val agent = BasicAgent()
    assert(
      FieldUtils
        .readField(agent, "actions", true)
        .asInstanceOf[mutable.Map[Int, Behaviour]]
        .isEmpty
    )
  }

  test("getAction when exists") {
    val agent = BasicAgent()
    val actions: mutable.Map[Int, Behaviour] = HashMap()
    FieldUtils.writeField(agent, "actions", actions, true)
    val behaviour = BasicBehaviour("b")
    actions.put(2, behaviour)
    assertEquals(agent.getAction(2), Some(behaviour))
  }

  test("getAction when not exists") {
    val agent = BasicAgent()
    val actions: mutable.Map[Int, Behaviour] = HashMap()
    FieldUtils.writeField(agent, "actions", actions, true)
    assertEquals(agent.getAction(2), None)
  }

  test("setAction when exists") {
    val agent = BasicAgent()
    val actions: mutable.Map[Int, Behaviour] = HashMap()
    FieldUtils.writeField(agent, "actions", actions, true)
    val b1 = BasicBehaviour("b1")
    val b2 = BasicBehaviour("b2")
    actions.put(2, b1)
    agent.setAction(2, Some(b2))
    assertEquals(actions.get(2), Some(b2))
  }

  test("setAction when exists delete") {
    val agent = BasicAgent()
    val actions: mutable.Map[Int, Behaviour] = HashMap()
    FieldUtils.writeField(agent, "actions", actions, true)
    val b1 = BasicBehaviour("b1")
    actions.put(2, b1)
    agent.setAction(2, None)
    assertEquals(actions.get(2), None)
  }

  test("setAction when not exists") {
    val agent = BasicAgent()
    val actions: mutable.Map[Int, Behaviour] = HashMap()
    FieldUtils.writeField(agent, "actions", actions, true)
    val b2 = BasicBehaviour("b2")
    agent.setAction(2, Some(b2))
    assertEquals(actions.get(2), Some(b2))
  }

  test("setAction when not exists delete") {
    val agent = BasicAgent()
    val actions: mutable.Map[Int, Behaviour] = HashMap()
    FieldUtils.writeField(agent, "actions", actions, true)
    agent.setAction(2, None)
    assertEquals(actions.get(2), None)
  }

  test("pressure when no friends") {
    val agent = BasicAgent()
    val belief = BasicBelief("b")
    val friends: mutable.Map[Agent, Double] = HashMap()
    FieldUtils.writeField(agent, "friends", friends, true)
    assertEquals(agent.pressure(2, belief), 0.0)
  }

  test("pressure when friends did nothing") {
    val agent = BasicAgent()
    val f1 = BasicAgent()
    val f2 = BasicAgent()

    val belief = BasicBelief("b")
    val friends: mutable.Map[Agent, Double] = HashMap()
    friends.put(f1, 0.5)
    friends.put(f2, 1.0)
    FieldUtils.writeField(agent, "friends", friends, true)
    assertEquals(agent.pressure(2, belief), 0.0)
  }

  test("pressure when friends did something but perception is null") {
    val agent = BasicAgent()
    val f1 = BasicAgent()
    val f2 = BasicAgent()
    val b1 = BasicBehaviour("b1")
    val b2 = BasicBehaviour("b2")

    f1.setAction(2, Some(b1))
    f2.setAction(2, Some(b2))

    val belief = BasicBelief("b")
    val friends: mutable.Map[Agent, Double] = HashMap()
    friends.put(f1, 0.5)
    friends.put(f2, 1.0)
    FieldUtils.writeField(agent, "friends", friends, true)
    assertEquals(agent.pressure(2, belief), 0.0)
  }

  test("pressure when friends did something") {
    val agent = BasicAgent()
    val f1 = BasicAgent()
    val f2 = BasicAgent()
    val b1 = BasicBehaviour("b1")
    val b2 = BasicBehaviour("b2")

    f1.setAction(2, Some(b1))
    f2.setAction(2, Some(b2))

    val belief = BasicBelief("b")
    belief.setPerception(b1, Some(0.2))
    belief.setPerception(b2, Some(0.3))

    val friends: mutable.Map[Agent, Double] = HashMap()
    friends.put(f1, 0.5)
    friends.put(f2, 1.0)
    FieldUtils.writeField(agent, "friends", friends, true)
    assertEquals(agent.pressure(2, belief), 0.2)
  }
}
