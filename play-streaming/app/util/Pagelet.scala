package util

import play.api.libs.iteratee.Enumerator
import play.twirl.api.{HtmlFormat, Html}

import scala.collection.immutable.Seq


object Pagelet {
  def apply(h: Html) = new Pagelet(Enumerator(h))
}
case class Pagelet(e: Enumerator[Html]) extends play.twirl.api.Appendable[Pagelet]  {

  def toChunkedStream: Enumerator[Html] = e
  def andThen(another: Pagelet) = Pagelet(e.andThen(another.e))

}

object PageletFormat extends play.twirl.api.Format[Pagelet] {

  override def raw(text: String): Pagelet = Pagelet(HtmlFormat.raw(text))

  override def escape(text: String): Pagelet = Pagelet(HtmlFormat.escape(text))

  override def empty: Pagelet = Pagelet(HtmlFormat.empty)

  override def fill(elements: Seq[Pagelet]): Pagelet = elements.reduce(_.andThen(_))
}