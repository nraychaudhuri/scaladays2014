package global

import actors.User
import akka.actor.{Props, ActorSystem}
import akka.contrib.pattern.{ShardRegion, ClusterSharding}
import play.api.{Application, GlobalSettings}

object AppGlobal extends GlobalSettings {

  private[this] var _system: Option[ActorSystem] = None

  def system = _system.getOrElse(throw new RuntimeException("You are screwed!"))

  override def onStart(app: Application): Unit = {
    _system = Option(ActorSystem("ClusterSystem"))
    _system.foreach(createSharding)
  }

  override def onStop(app: Application): Unit = {
    _system.foreach { s =>
      s.shutdown()
      s.awaitTermination()
    }
  }

  private def createSharding(system: ActorSystem) = {
    ClusterSharding(system).start(
      typeName = User.shardName,
      entryProps = Some(User.props),
      idExtractor = User.idExtractor,
      shardResolver = User.shardResolver
    )
  }

}
