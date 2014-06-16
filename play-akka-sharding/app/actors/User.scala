
package actors

import akka.actor._
import play.api.Logger
import akka.contrib.pattern.ShardRegion

case class GameState(level: Int, state: Any)


class User extends Actor {
  import User._

  //Since this actor has a state ideally it should also use Akka persistence to save the state in case of node crashes
  //akka persistence is out of the scope for this book.
  var state: GameState = _

  override def receive: Receive = {
  	case Login(userId) =>
      Logger.info("New user logged in")
      loadLastSavedStateFromStorage()
  	case Logout(userId) =>
      Logger.info("Stopping the actor as user logout")
      context.stop(self)
  	case gs: GameStateChanged =>
      updateState(gs)
  }

  private def updateState(gs: GameStateChanged) = {
    Logger.info(s"Applying the delta change for the userId = ${gs.userId}")
    state = state.copy(state = gs.newState)
  }

  private def loadLastSavedStateFromStorage() = {
    state = GameState(level = 0, "initial state")
  }
}

object User {
  def props: Props = Props(classOf[User])

  trait UserMessage { val userId: String }

  case class Login(userId: String) extends UserMessage
  case class Logout(userId: String) extends UserMessage
  case class GameStateChanged(userId: String, newState: Any) extends UserMessage

  val shardName = "users"

  val idExtractor : ShardRegion.IdExtractor = {
    case u: UserMessage => (u.userId, u)
  }

  val shardResolver: ShardRegion.ShardResolver = {
    case u: UserMessage =>  (u.userId.hashCode % 100).toString
  }

}