package persistence.models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.H2Profile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = KeyValue.schema ++ PlayEvolutions.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table KeyValue
   *  @param key Database column KEY SqlType(VARCHAR), PrimaryKey
   *  @param value Database column VALUE SqlType(VARCHAR) */
  case class KeyValueRow(key: String, value: Option[String])
  /** GetResult implicit for fetching KeyValueRow objects using plain SQL queries */
  implicit def GetResultKeyValueRow(implicit e0: GR[String], e1: GR[Option[String]]): GR[KeyValueRow] = GR{
    prs => import prs._
    KeyValueRow.tupled((<<[String], <<?[String]))
  }
  /** Table description of table KEY_VALUE. Objects of this class serve as prototypes for rows in queries. */
  class KeyValue(_tableTag: Tag) extends profile.api.Table[KeyValueRow](_tableTag, "KEY_VALUE") {
    def * = (key, value) <> (KeyValueRow.tupled, KeyValueRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(key), value).shaped.<>({r=>import r._; _1.map(_=> KeyValueRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column KEY SqlType(VARCHAR), PrimaryKey */
    val key: Rep[String] = column[String]("KEY", O.PrimaryKey)
    /** Database column VALUE SqlType(VARCHAR) */
    val value: Rep[Option[String]] = column[Option[String]]("VALUE")
  }
  /** Collection-like TableQuery object for table KeyValue */
  lazy val KeyValue = new TableQuery(tag => new KeyValue(tag))

  /** Entity class storing rows of table PlayEvolutions
   *  @param id Database column ID SqlType(INTEGER), PrimaryKey
   *  @param hash Database column HASH SqlType(VARCHAR), Length(255,true)
   *  @param appliedAt Database column APPLIED_AT SqlType(TIMESTAMP)
   *  @param applyScript Database column APPLY_SCRIPT SqlType(CLOB)
   *  @param revertScript Database column REVERT_SCRIPT SqlType(CLOB)
   *  @param state Database column STATE SqlType(VARCHAR), Length(255,true)
   *  @param lastProblem Database column LAST_PROBLEM SqlType(CLOB) */
  case class PlayEvolutionsRow(id: Int, hash: String, appliedAt: java.sql.Timestamp, applyScript: Option[java.sql.Clob], revertScript: Option[java.sql.Clob], state: Option[String], lastProblem: Option[java.sql.Clob])
  /** GetResult implicit for fetching PlayEvolutionsRow objects using plain SQL queries */
  implicit def GetResultPlayEvolutionsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[java.sql.Clob]], e4: GR[Option[String]]): GR[PlayEvolutionsRow] = GR{
    prs => import prs._
    PlayEvolutionsRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<?[java.sql.Clob], <<?[java.sql.Clob], <<?[String], <<?[java.sql.Clob]))
  }
  /** Table description of table PLAY_EVOLUTIONS. Objects of this class serve as prototypes for rows in queries. */
  class PlayEvolutions(_tableTag: Tag) extends profile.api.Table[PlayEvolutionsRow](_tableTag, "PLAY_EVOLUTIONS") {
    def * = (id, hash, appliedAt, applyScript, revertScript, state, lastProblem) <> (PlayEvolutionsRow.tupled, PlayEvolutionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(hash), Rep.Some(appliedAt), applyScript, revertScript, state, lastProblem).shaped.<>({r=>import r._; _1.map(_=> PlayEvolutionsRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INTEGER), PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.PrimaryKey)
    /** Database column HASH SqlType(VARCHAR), Length(255,true) */
    val hash: Rep[String] = column[String]("HASH", O.Length(255,varying=true))
    /** Database column APPLIED_AT SqlType(TIMESTAMP) */
    val appliedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("APPLIED_AT")
    /** Database column APPLY_SCRIPT SqlType(CLOB) */
    val applyScript: Rep[Option[java.sql.Clob]] = column[Option[java.sql.Clob]]("APPLY_SCRIPT")
    /** Database column REVERT_SCRIPT SqlType(CLOB) */
    val revertScript: Rep[Option[java.sql.Clob]] = column[Option[java.sql.Clob]]("REVERT_SCRIPT")
    /** Database column STATE SqlType(VARCHAR), Length(255,true) */
    val state: Rep[Option[String]] = column[Option[String]]("STATE", O.Length(255,varying=true))
    /** Database column LAST_PROBLEM SqlType(CLOB) */
    val lastProblem: Rep[Option[java.sql.Clob]] = column[Option[java.sql.Clob]]("LAST_PROBLEM")
  }
  /** Collection-like TableQuery object for table PlayEvolutions */
  lazy val PlayEvolutions = new TableQuery(tag => new PlayEvolutions(tag))
}
