package controllers

import play.api._
import play.api.mvc._
import scala.concurrent.Future
import play.twirl.api.Html
import services.Modules._
import scala.concurrent.ExecutionContext.Implicits.global

object Application extends Controller {

  def index = Action {

    val wpasam = toModule("wpasam", WhatPeopleAreSayingAboutYou.latestUpdate(UserPreference.showAll))
    val symbi = toModule("symbi", ServicesYouMightBeInterested.latestUpdate(UserPreference.showAll))
    val ads =  toModule("ads", Ads.latestUpdate(UserPreference.showAll))
    val wyfau = toModule("wyfau", WhatYourFriendsAreUpto.latestUpdate(UserPreference.showAll))

    Ok("")
  }

  private def toModule(moduleId: String, html: Future[Html]): Future[Html] = {
    import views.html.modules.module
    html.map(h => module(moduleId, h))
  }

}

