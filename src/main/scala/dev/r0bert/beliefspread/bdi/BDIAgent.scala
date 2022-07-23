package dev.r0bert.beliefspread.bdi

import dev.r0bert.beliefspread.core.Agent
import dev.r0bert.beliefspread.core.Belief

/** This is an [[Agent]] that makes decisions using BDI.
  *
  * @author
  *   Robert Greener
  * @since v0.15.0
  */
trait BDIAgent extends Agent {

  /** Chooses an action to perform at a given time.
    *
    * This updates the action using [[Agent.setAction]]
    *
    * An action is any [dev.r0bert.beliefspread.core.Behaviour].
    *
    * @param time
    *   The time.
    * @param beliefs
    *   The [[Belief]]s
    * @author
    *   Robert Greener
    * @since v0.15.0
    */
  def chooseAction(time: Int, beliefs: Iterable[Belief]): Unit
}
