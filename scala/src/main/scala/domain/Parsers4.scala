package domain

class Parser4[A](val run: String => List[(A, String)]) {
  def apply(s: String) = run(s)

  def map[B](f: A => B): Parser4[B] = {
    val runB = { s: String => run(s).map { case (a, rest) => (f(a), rest) } }
    new Parser4(runB)
  }

  def flatMap[B](f: A => Parser4[B]): Parser4[B] = {
    val runB = { s: String => run(s).flatMap { case (a, rest) => f(a)(rest) } }
    new Parser4(runB)
  }
}

package object parsers {
  def success[A](a: A): Parser4[A] = {
    val run = { s: String => List((a, s)) }
    new Parser4(run)
  }

  def failure[A](): Parser4[A] = {
    val run = { s: String => Nil }
    new Parser4[A](run)
  }
  
  def item():Parser4[Char] = {
    val run = {s: String => if (s.isEmpty()) Nil else List((s.head, s.tail))}
    new Parser4(run)
  }
}

