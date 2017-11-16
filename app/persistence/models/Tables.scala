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
  lazy val schema: profile.SchemaDescription = KeyValue.schema ++ PlayEvolutions.schema ++ SaAnswer.schema ++ SaQuestion.schema ++ Survey.schema
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

  /** Entity class storing rows of table SaAnswer
   *  @param id Database column ID SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param questionId Database column QUESTION_ID SqlType(BIGINT)
   *  @param choice1 Database column CHOICE1 SqlType(INTEGER)
   *  @param choice2 Database column CHOICE2 SqlType(INTEGER)
   *  @param choice3 Database column CHOICE3 SqlType(INTEGER)
   *  @param choice4 Database column CHOICE4 SqlType(INTEGER)
   *  @param choice5 Database column CHOICE5 SqlType(INTEGER)
   *  @param createAt Database column CREATE_AT SqlType(TIMESTAMP)
   *  @param updateAt Database column UPDATE_AT SqlType(TIMESTAMP) */
  case class SaAnswerRow(id: Long, questionId: Long, choice1: Option[Int], choice2: Option[Int], choice3: Option[Int], choice4: Option[Int], choice5: Option[Int], createAt: java.sql.Timestamp, updateAt: java.sql.Timestamp)
  /** GetResult implicit for fetching SaAnswerRow objects using plain SQL queries */
  implicit def GetResultSaAnswerRow(implicit e0: GR[Long], e1: GR[Option[Int]], e2: GR[java.sql.Timestamp]): GR[SaAnswerRow] = GR{
    prs => import prs._
    SaAnswerRow.tupled((<<[Long], <<[Long], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table SA_ANSWER. Objects of this class serve as prototypes for rows in queries. */
  class SaAnswer(_tableTag: Tag) extends profile.api.Table[SaAnswerRow](_tableTag, "SA_ANSWER") {
    def * = (id, questionId, choice1, choice2, choice3, choice4, choice5, createAt, updateAt) <> (SaAnswerRow.tupled, SaAnswerRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(questionId), choice1, choice2, choice3, choice4, choice5, Rep.Some(createAt), Rep.Some(updateAt)).shaped.<>({r=>import r._; _1.map(_=> SaAnswerRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column QUESTION_ID SqlType(BIGINT) */
    val questionId: Rep[Long] = column[Long]("QUESTION_ID")
    /** Database column CHOICE1 SqlType(INTEGER) */
    val choice1: Rep[Option[Int]] = column[Option[Int]]("CHOICE1")
    /** Database column CHOICE2 SqlType(INTEGER) */
    val choice2: Rep[Option[Int]] = column[Option[Int]]("CHOICE2")
    /** Database column CHOICE3 SqlType(INTEGER) */
    val choice3: Rep[Option[Int]] = column[Option[Int]]("CHOICE3")
    /** Database column CHOICE4 SqlType(INTEGER) */
    val choice4: Rep[Option[Int]] = column[Option[Int]]("CHOICE4")
    /** Database column CHOICE5 SqlType(INTEGER) */
    val choice5: Rep[Option[Int]] = column[Option[Int]]("CHOICE5")
    /** Database column CREATE_AT SqlType(TIMESTAMP) */
    val createAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("CREATE_AT")
    /** Database column UPDATE_AT SqlType(TIMESTAMP) */
    val updateAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("UPDATE_AT")

    /** Foreign key referencing SaQuestion (database name CONSTRAINT_87) */
    lazy val saQuestionFk = foreignKey("CONSTRAINT_87", questionId, SaQuestion)(r => r.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
  }
  /** Collection-like TableQuery object for table SaAnswer */
  lazy val SaAnswer = new TableQuery(tag => new SaAnswer(tag))

  /** Entity class storing rows of table SaQuestion
   *  @param id Database column ID SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param surveyId Database column SURVEY_ID SqlType(BIGINT)
   *  @param question Database column QUESTION SqlType(VARCHAR)
   *  @param choice1 Database column CHOICE1 SqlType(VARCHAR)
   *  @param choice2 Database column CHOICE2 SqlType(VARCHAR)
   *  @param choice3 Database column CHOICE3 SqlType(VARCHAR)
   *  @param choice4 Database column CHOICE4 SqlType(VARCHAR)
   *  @param choice5 Database column CHOICE5 SqlType(VARCHAR)
   *  @param createAt Database column CREATE_AT SqlType(TIMESTAMP)
   *  @param updateAt Database column UPDATE_AT SqlType(TIMESTAMP) */
  case class SaQuestionRow(id: Long, surveyId: Long, question: Option[String], choice1: Option[String], choice2: Option[String], choice3: Option[String], choice4: Option[String], choice5: Option[String], createAt: java.sql.Timestamp, updateAt: java.sql.Timestamp)
  /** GetResult implicit for fetching SaQuestionRow objects using plain SQL queries */
  implicit def GetResultSaQuestionRow(implicit e0: GR[Long], e1: GR[Option[String]], e2: GR[java.sql.Timestamp]): GR[SaQuestionRow] = GR{
    prs => import prs._
    SaQuestionRow.tupled((<<[Long], <<[Long], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table SA_QUESTION. Objects of this class serve as prototypes for rows in queries. */
  class SaQuestion(_tableTag: Tag) extends profile.api.Table[SaQuestionRow](_tableTag, "SA_QUESTION") {
    def * = (id, surveyId, question, choice1, choice2, choice3, choice4, choice5, createAt, updateAt) <> (SaQuestionRow.tupled, SaQuestionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(surveyId), question, choice1, choice2, choice3, choice4, choice5, Rep.Some(createAt), Rep.Some(updateAt)).shaped.<>({r=>import r._; _1.map(_=> SaQuestionRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8, _9.get, _10.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column SURVEY_ID SqlType(BIGINT) */
    val surveyId: Rep[Long] = column[Long]("SURVEY_ID")
    /** Database column QUESTION SqlType(VARCHAR) */
    val question: Rep[Option[String]] = column[Option[String]]("QUESTION")
    /** Database column CHOICE1 SqlType(VARCHAR) */
    val choice1: Rep[Option[String]] = column[Option[String]]("CHOICE1")
    /** Database column CHOICE2 SqlType(VARCHAR) */
    val choice2: Rep[Option[String]] = column[Option[String]]("CHOICE2")
    /** Database column CHOICE3 SqlType(VARCHAR) */
    val choice3: Rep[Option[String]] = column[Option[String]]("CHOICE3")
    /** Database column CHOICE4 SqlType(VARCHAR) */
    val choice4: Rep[Option[String]] = column[Option[String]]("CHOICE4")
    /** Database column CHOICE5 SqlType(VARCHAR) */
    val choice5: Rep[Option[String]] = column[Option[String]]("CHOICE5")
    /** Database column CREATE_AT SqlType(TIMESTAMP) */
    val createAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("CREATE_AT")
    /** Database column UPDATE_AT SqlType(TIMESTAMP) */
    val updateAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("UPDATE_AT")

    /** Foreign key referencing Survey (database name CONSTRAINT_3B) */
    lazy val surveyFk = foreignKey("CONSTRAINT_3B", surveyId, Survey)(r => r.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
  }
  /** Collection-like TableQuery object for table SaQuestion */
  lazy val SaQuestion = new TableQuery(tag => new SaQuestion(tag))

  /** Entity class storing rows of table Survey
   *  @param id Database column ID SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param surveyTitle Database column SURVEY_TITLE SqlType(VARCHAR)
   *  @param createAt Database column CREATE_AT SqlType(TIMESTAMP)
   *  @param updateAt Database column UPDATE_AT SqlType(TIMESTAMP) */
  case class SurveyRow(id: Long, surveyTitle: Option[String], createAt: java.sql.Timestamp, updateAt: java.sql.Timestamp)
  /** GetResult implicit for fetching SurveyRow objects using plain SQL queries */
  implicit def GetResultSurveyRow(implicit e0: GR[Long], e1: GR[Option[String]], e2: GR[java.sql.Timestamp]): GR[SurveyRow] = GR{
    prs => import prs._
    SurveyRow.tupled((<<[Long], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table SURVEY. Objects of this class serve as prototypes for rows in queries. */
  class Survey(_tableTag: Tag) extends profile.api.Table[SurveyRow](_tableTag, "SURVEY") {
    def * = (id, surveyTitle, createAt, updateAt) <> (SurveyRow.tupled, SurveyRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), surveyTitle, Rep.Some(createAt), Rep.Some(updateAt)).shaped.<>({r=>import r._; _1.map(_=> SurveyRow.tupled((_1.get, _2, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column SURVEY_TITLE SqlType(VARCHAR) */
    val surveyTitle: Rep[Option[String]] = column[Option[String]]("SURVEY_TITLE")
    /** Database column CREATE_AT SqlType(TIMESTAMP) */
    val createAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("CREATE_AT")
    /** Database column UPDATE_AT SqlType(TIMESTAMP) */
    val updateAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("UPDATE_AT")
  }
  /** Collection-like TableQuery object for table Survey */
  lazy val Survey = new TableQuery(tag => new Survey(tag))
}
