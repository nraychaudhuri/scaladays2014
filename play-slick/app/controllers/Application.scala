package controllers

import models.Computers
import play.api._
import play.api.mvc._
import play.api.Play.current
import play.api.db.slick._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def list = AlwaysAllowDBAction { implicit rs =>
     val computers = Computers.list()
     Ok(views.html.list(computers))
  }
}

object AlwaysAllowDBAction extends CurrentDBAction {

  override def isDBAvailable(name: String): Boolean = true

}



