package models

import play.api.db.slick.Config.driver.simple._
import com.github.tototoshi.slick.H2JodaSupport._

trait RepoQuery[T <: Table[M] with RepoTable[M], M <: Model[M]] { 
  this: TableQuery[T] =>
  def get(id: Id[M])(implicit s: Session): M = this.filter(_.id is id).first
  def all(implicit s: Session): List[M] = this.list
  def count(implicit s: Session): Int = this.list.length
  def ins(m: M)(implicit s: Session): Id[M] = {
    (this returning this.map(_.id)) insert m
  }
  def updt(m: M)(implicit s: Session): Id[M] = {
    (this returning this.map(_.id)) forceInsert m
  }
}

object Topics extends TableQuery(new TopicTable(_)) with RepoQuery[TopicTable, Topic] {
  def of(paper: Paper)(implicit s: Session): List[Topic] = {
    Nil // TODO
  }
}

object Persons extends TableQuery(new PersonTable(_)) with RepoQuery[PersonTable, Person] {
  def withEmail(email: String)(implicit s: Session): Option[Person] = { // TODO -> List[Paper]
    None// TODO: DO Authors.withEmail(email)
  }
}

object Papers extends TableQuery(new PaperTable(_)) with RepoQuery[PaperTable, Paper] {
  def withEmail(email: String)(implicit s: Session): Option[Paper] = { // TODO -> List[Paper]
    None// TODO: DO Authors.withEmail(email)
  }
}

object PaperTopics extends TableQuery(new PaperTopicTable(_)) with RepoQuery[PaperTopicTable, PaperTopic] {
}

object Authors extends TableQuery(new AuthorTable(_)) with RepoQuery[AuthorTable, Author] {
  def of(paper: Paper)(implicit s: Session): List[Person] = {
    Nil // TODO
  }
}

object Comments extends TableQuery(new CommentTable(_)) with RepoQuery[CommentTable, Comment] {
}

object Reviews extends TableQuery(new ReviewTable(_)) with RepoQuery[ReviewTable, Review] {
}

object Files extends TableQuery(new FileTable(_)) with RepoQuery[FileTable, File] {
}

object Emails extends TableQuery(new EmailTable(_)) with RepoQuery[EmailTable, Email] {
}
