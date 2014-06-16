package models

import java.util.Date
import play.api.db.slick.Config.driver.simple._
import play.api.libs.json.Json

class CompanyId(val id: Long) extends AnyVal

case class Company(name: String, id: Option[CompanyId])

class ComputerId(val id: Long) extends AnyVal

case class Computer(name: String,
                    introduced: Option[Date] = None,
                    discontinued: Option[Date] = None,
                    companyId: Option[CompanyId] = None,
                    id: Option[ComputerId] = None)

