package scalain.test

class ServiceTest extends org.scalatest.FunSuite {
  test("common latitudes") {
    implicit val session = Database.start()
    val service = new Service

    val runway1 = new Runway("1", "0", "surface", "1")
    val runway2 = new Runway("2", "0", "surface", "2")
    val runway3 = new Runway("3", "0", "surface", "3")
    val runway4 = new Runway("4", "0", "surface", "4")
    val runway5 = new Runway("5", "0", "surface", "5")
    val runway6 = new Runway("6", "0", "surface", None)
    val runway7 = new Runway("7", "0", "surface", "6")
    val runway8 = new Runway("8", "0", "surface", "7")
    val runway9 = new Runway("9", "0", "surface", "8")
    val runway10 = new Runway("10", "0", "surface", "9")
    val runway11 = new Runway("11", "0", "surface", "10")
    val runway12 = new Runway("12", "0", "surface", "11")
    val runway13 = new Runway("12", "0", "surface", "12")
    val runway14 = new Runway("12", "0", "surface", "8")
    val runway15 = new Runway("12", "0", "surface", "3")
    val runway16 = new Runway("12", "0", "surface", "3")
  }
}
