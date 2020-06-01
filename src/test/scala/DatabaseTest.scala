package scalain.test

import scalain.database._
import org.squeryl.PrimitiveTypeMode._

class Database_Test extends org.scalatest.FunSuite {
  test("basic insert") {
    implicit val session = Database.start()

    using(session) {
      val country = new Country(0, "FR", "France", "EU", "wiki", None)
      Database.Tables.countries
        .insert(country)

      val res =
        from(Database.Tables.countries)(s => select(s)).headOption.get
          .toString()

      assert(country.toString() == res)
    }

    session.close
  }
}
