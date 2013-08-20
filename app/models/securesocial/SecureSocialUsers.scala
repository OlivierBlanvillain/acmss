package models.securesocial

import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB
import securesocial.core.{AuthenticationMethod, Identity, PasswordInfo, SocialUser, IdentityId}

/** This file holds all the code related to the storage of SecureSocial Users
  * in the database.
  * @see models.Members.scala for a similar implementation with ScalaDoc.
  */

case class User(
  uid: String,
  pid: String,
  email: String,
  firstname: String,
  lastname: String,
  authmethod: String,
  hasher: Option[String],
  password: Option[String],
  salt: Option[String]
) {
  def id: IdentityId = IdentityId(uid, pid)
  def toIdentity: Identity = SocialUser(
    IdentityId(uid, pid), s"$firstname $lastname", firstname, lastname, Some(email), None, AuthenticationMethod(authmethod),
    None, None, password.map(p => PasswordInfo(hasher.getOrElse(""), p, salt))
  )
}
object User {
  // IMPORTANT: At this point (i.email.get) we assume that the provider gives
  // us an email, which is not the case for some of them (eg twitter).
  def fromIdentity(i: Identity) = User(
    i.identityId.userId, i.identityId.providerId, i.email.get, i.firstName,
    i.lastName, i.authMethod.method, i.passwordInfo.map(_.hasher),
    i.passwordInfo.map(_.password), i.passwordInfo.map(_.salt).getOrElse(None)
  )
}

object SecureSocialUsers extends Table[User]("SECURE_SOCIAL_USERS") {
  def uid = column[String]("UID", O.DBType("TEXT"))
  def pid = column[String]("PID", O.DBType("TEXT"))
  def email = column[String]("EMAIL", O.DBType("TEXT"))
  def firstname = column[String]("FIRSTNAME", O.DBType("TEXT"))
  def lastname = column[String]("LASTNAME", O.DBType("TEXT"))
  def authmethod = column[String]("AUTHMETHOD", O.DBType("TEXT"))
  def hasher = column[Option[String]]("HASHER", O.DBType("TEXT"))
  def password = column[Option[String]]("PASSWORD", O.DBType("TEXT"))
  def salt = column[Option[String]]("SALT", O.DBType("TEXT"))
  
  def pk = primaryKey("SECURESOCIALUSERS_PK", uid ~ pid)
  def * = uid ~ pid ~ email ~ firstname ~ lastname ~ authmethod ~ hasher ~ password ~ salt <> (User.apply _, User.unapply _)

  def UserByidentityId(identityId: IdentityId) = 
    Query(SecureSocialUsers).filter( user =>
      (user.uid is identityId.userId) && (user.pid is identityId.providerId) )
  
  def userByEmailAndProvider(email: String, pid: String) = 
    Query(SecureSocialUsers).filter( user =>
      (user.email is email) && (user.pid is pid) )
  
  def withEmail(email: String) = DB.withTransaction(implicit session =>
    Query(SecureSocialUsers).filter(_.email is email).list.headOption
  )

  trait Queries {
    def save(identity: Identity): Identity = DB.withTransaction { implicit session =>
      val user = User.fromIdentity(identity)
      find(user.id) match {
        case None =>
          SecureSocialUsers.insert(user)
        case Some(u) =>
          UserByidentityId(u.identityId).update(user)
      }
      identity
    }
    
    def find(identityId: IdentityId): Option[Identity] = DB.withSession(implicit session =>
      UserByidentityId(identityId).firstOption.map(_.toIdentity)
    )
    
    def findByEmailAndProvider(email: String, pid: String): Option[Identity] = DB.withSession(implicit session =>
      userByEmailAndProvider(email, pid).firstOption.map(_.toIdentity)
    )
  }
}