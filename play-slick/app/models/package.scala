import play.api.db.slick.Config.driver.simple._

package object models {
  implicit val companyIdType = MappedColumnType.base[CompanyId, Long](_.id, new CompanyId(_))
  implicit val computerIdType = MappedColumnType.base[ComputerId, Long](_.id, new ComputerId(_))
}
