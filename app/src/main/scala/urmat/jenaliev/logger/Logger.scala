package urmat.jenaliev.logger

import scala.language.higherKinds

trait Logger[F[_]] {
  def isErrorEnabled: F[Boolean]
  def isWarnEnabled: F[Boolean]
  def isInfoEnabled: F[Boolean]
  def isDebugEnabled: F[Boolean]
  def isTraceEnabled: F[Boolean]

  def error(message: => String): F[Unit]
  def warn(message: => String): F[Unit]
  def info(message: => String): F[Unit]
  def debug(message: => String): F[Unit]
  def trace(message: => String): F[Unit]

  def error(t: Throwable)(message: => String): F[Unit]
  def warn(t: Throwable)(message: => String): F[Unit]
  def info(t: Throwable)(message: => String): F[Unit]
  def debug(t: Throwable)(message: => String): F[Unit]
  def trace(t: Throwable)(message: => String): F[Unit]
}
