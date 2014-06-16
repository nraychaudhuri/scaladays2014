package actors

import akka.actor._
import akka.contrib.pattern.ClusterSharding

class GameController extends Actor {

  import GameController._

  val region: ActorRef = ClusterSharding(context.system).shardRegion(User.shardName)

  def receive: Receive = {
    case CreateNewUser(userId) =>
      region ! User.Login(userId)
    case RemoveUser(userId) =>
      region ! User.Logout(userId)
    case UpdateUserState(userId, state) =>
      region ! User.GameStateChanged(userId, state)
  }
}


object GameController {

  def props = Props[GameController]

  case class UpdateUserState(userId: String, state: Any)
  case class RemoveUser(userId: String)
  case class CreateNewUser(userId: String)

}
