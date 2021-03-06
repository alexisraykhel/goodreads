package scalareads


import scalareads.values.{GDisjunction, ToIntError, SimpleBook}
import scalaz.{-\/, \/-}


object ScalareadsFunctions {

  def optionToInt(o: Option[String]): Option[Int] =
    o.fold(Option.empty[Int]) { s => try {
      Some(s.toInt)
    }
    catch {
      case e: NumberFormatException => Option.empty[Int]
    }
  }

  def stringToInt(s: String): GDisjunction[Int] = try {
    \/-(s.toInt)
  } catch {
    case e: NumberFormatException => -\/(ToIntError(s +
      " is not a valid integer. " + e.getMessage))
  }

  def optionToDouble(o: Option[String]): Option[Double] =
    o.fold(Option.empty[Double])(s => try {
      Some(s.toDouble)
    } catch {
      case e: NumberFormatException => Option.empty[Double]
    }
  )

  def toMaybeBoolean(os: Option[String]): Option[Boolean] =
    os.fold(Option.empty[Boolean])(s =>
      try {
        Some(s.toBoolean)
      } catch {
        case i: IllegalArgumentException => None
      }
    )

  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try { op(p) } finally { p.close() }
  }
}
