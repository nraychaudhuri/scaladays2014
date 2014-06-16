package controllers

import akka.actor.ActorRef
import play.api._
import play.api.mvc._
import actors.GameController

object Application extends Controller {

  val gameController: ActorRef = ???

  def index = Action {
    Ok("game node is up")
  }

  def createUser(userId: String) = Action {
    gameController ! GameController.CreateNewUser(userId)
    Ok("new user is created")
  }

  def updateUserState(userId: String) = Action { request =>
    gameController ! GameController.UpdateUserState(userId, state = request.getQueryString("newState"))
    Ok("user state is updated")
  }

  def removeUser(userId: String) = Action {
    gameController ! GameController.RemoveUser(userId)
    Ok(s"user ${userId} is removed")

  }

}


