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

/** A Belief.
  */
trait Belief extends Named with UUIDd {

  /** Gets the perception. Returns an optional [Double] if found.
    *
    * The perception is the amount that an agent performing the behaviour can be
    * assumed to be driven by this belief.
    *
    * This is a value between -1 and +1.
    *
    * @param behaviour
    *   The behaviour.
    * @return
    *   The value, if found.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def getPerception(behaviour: Behaviour): Option[Double]

  /** Sets the perception.
    *
    * The perception is the amount that an agent performing the behaviour can be
    * assumed to be driven by this belief.
    *
    * Deletes a behaviour if no perception is supplied.
    *
    * The perception must be in the range [-1, +1].
    *
    * @param behaviour
    *   The behaviour.
    * @param perception
    *   The perception.
    * @throws IllegalArgumentException
    *   If perception is not in the range [-1,+1].
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  @throws(classOf[IllegalArgumentException])
  def setPerception(behaviour: Behaviour, perception: Option[Double]): Unit

  /** Gets the relationship.
    *
    * The relationship is the amount another belief can be deemed to be
    * compatible with holding this belief, given that you already hold this
    * belief.
    *
    * This is a value between -1 and +1.
    *
    * @param belief
    *   The other belief.
    * @return
    *   The relationship if found.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def getRelationship(belief: Belief): Option[Double]

  /** Sets the relationship.
    *
    * The relationship is the amount another belief can be deemed to be
    * compatible with holding this belief, given that you already hold this
    * belief.
    *
    * Deletes a relationship if no new one is supplied.
    *
    * @param belief
    *   The other belief.
    * @param relationship
    *   The new relationship.
    * @throws IllegalArgumentException
    *   If the relationship is not in the range [-1, +1].
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  @throws(classOf[IllegalArgumentException])
  def setRelationship(belief: Belief, relationship: Option[Double]): Unit
}
