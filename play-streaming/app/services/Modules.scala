package services

import scala.concurrent.Future
import play.api.libs.concurrent.Promise
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import play.twirl.api.Html

object Modules {

  trait UserPreference

  object UserPreference {
    def showAll = new UserPreference {}
  }

  sealed trait Module {
    def latestUpdate(user: UserPreference): Future[Html]
  }

  object WhatPeopleAreSayingAboutYou extends Module {
    def latestUpdate(user: UserPreference): Future[Html] = {
      Promise.timeout(Html("This is What people are saying about you"), 2.seconds)
    }
  }

  object ServicesYouMightBeInterested extends Module {
    def latestUpdate(user: UserPreference): Future[Html] = {
      Promise.timeout(Html("here are few services you might like"), 5.seconds)
    }
  }

  object WhatYourFriendsAreUpto extends Module {
    def latestUpdate(user: UserPreference): Future[Html] = {
      Promise.timeout(Html("Your friend just got a new job"), 4.seconds)
    }
  }

  object Ads extends Module {
    def latestUpdate(user: UserPreference): Future[Html] = {
      Promise.timeout(Html("Take Play training course from Typesafe"), 3.seconds)
    }
  }

}
