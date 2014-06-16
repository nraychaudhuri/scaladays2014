package models

import java.util.Date

import play.api.db.slick.Config.driver.simple._
import scala.slick.lifted.Tag

class Companies(tag: Tag) extends Table[Company](tag, "COMPANY") {
  def id = column[CompanyId]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.NotNull)
  def * = (name, id.?) <> (Company.tupled, Company.unapply _)
}

class Computers(tag: Tag) extends Table[Computer](tag, "COMPUTER") {

  implicit val dateColumnType = MappedColumnType.base[Date, Long](d => d.getTime, d => new Date(d))

  def id = column[ComputerId]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.NotNull)
  def introduced = column[Date]("introduced", O.Nullable)
  def discontinued = column[Date]("discontinued", O.Nullable)
  def companyId = column[CompanyId]("companyId", O.Nullable)

  def * = (name, introduced.?, discontinued.?, companyId.?, id.?) <>(Computer.tupled, Computer.unapply _)
}

object Computers {

  val computers = TableQuery[Computers]
  val companies = TableQuery[Companies]

  def list()(implicit s: Session): Seq[(Computer, Option[Company])] = {
    val query =
      (for {
        (computer, company) <- computers leftJoin companies on (_.companyId === _.id)
      } yield (computer, company.id.?, company.name.?))
        .take(10)
    val result = query.list.map(row => (row._1, row._2.map(value => Company(row._3.get, Option(value)))))
    result
  }

  def insert(computer: Computer)(implicit s: Session) {
    computers.insert(computer)
  }
}

object Companies {
  val companies = TableQuery[Companies]

  def insert(company: Company)(implicit s: Session) {
    companies.insert(company)
  }
}



