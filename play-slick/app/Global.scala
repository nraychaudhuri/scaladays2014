import java.text.SimpleDateFormat
import play.api._
import models._
import play.api.db.slick._
import play.api.Play.current

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }

}

/** Initial set of data to be imported into the sample application. */
object InitialData {

  val sdf = new SimpleDateFormat("yyyy-MM-dd")

  def insert(): Unit = {
    DB.withSession { implicit s: Session =>
        Seq(
          Company("Apple Inc.", Option(new CompanyId(1L))),
          Company("Thinking Machines", Option(new CompanyId(2L)))).foreach(Companies.insert)
        Seq(
          Computer("MacBook Pro 15.4 inch", None, None, Option(new CompanyId(1L)), Option(new ComputerId(1L))),
          Computer("CM-2a", None, None, Option(new CompanyId(2L)), Option(new ComputerId(2L))),
          Computer("CM-200", None, None, Option(new CompanyId(2L)), Option(new ComputerId(2L))),
          Computer("CM-5e", None, None, Option(new CompanyId(2L)), Option(new ComputerId(2L)))).foreach(Computers.insert)
    }
  }
}