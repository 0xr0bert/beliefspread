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
}
