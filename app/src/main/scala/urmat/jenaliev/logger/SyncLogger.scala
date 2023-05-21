package urmat.jenaliev.logger

import cats.effect.Sync
import cats.syntax.flatMap._
import org.slf4j.{LoggerFactory, Logger => Underlying}

import scala.language.higherKinds

final class SyncLogger[F[_]](logger: Underlying)(implicit F: Sync[F]) extends Logger[F] {
  private val unit = F.unit

  override def isErrorEnabled: F[Boolean] = F.delay(logger.isErrorEnabled())
  override def isWarnEnabled: F[Boolean]  = F.delay(logger.isWarnEnabled())
  override def isInfoEnabled: F[Boolean]  = F.delay(logger.isInfoEnabled())
  override def isDebugEnabled: F[Boolean] = F.delay(logger.isDebugEnabled())
  override def isTraceEnabled: F[Boolean] = F.delay(logger.isTraceEnabled())

  override def error(message: => String): F[Unit] = isErrorEnabled.ifM(F.delay(logger.error(message)), unit)
  override def warn(message: => String): F[Unit]  = isWarnEnabled.ifM(F.delay(logger.warn(message)), unit)
  override def info(message: => String): F[Unit]  = isInfoEnabled.ifM(F.delay(logger.info(message)), unit)
  override def debug(message: => String): F[Unit] = isDebugEnabled.ifM(F.delay(logger.debug(message)), unit)
  override def trace(message: => String): F[Unit] = isTraceEnabled.ifM(F.delay(logger.trace(message)), unit)

  override def error(t: Throwable)(message: => String): F[Unit] =
    isErrorEnabled.ifM(F.delay(logger.error(message, t)), unit)

  override def warn(t: Throwable)(message: => String): F[Unit] =
    isWarnEnabled.ifM(F.delay(logger.warn(message, t)), unit)

  override def info(t: Throwable)(message: => String): F[Unit] =
    isInfoEnabled.ifM(F.delay(logger.info(message, t)), unit)

  override def debug(t: Throwable)(message: => String): F[Unit] =
    isDebugEnabled.ifM(F.delay(logger.debug(message, t)), unit)

  override def trace(t: Throwable)(message: => String): F[Unit] =
    isTraceEnabled.ifM(F.delay(logger.trace(message, t)), unit)
}

object SyncLogger {
  def fromClass[F[_]: Sync](clazz: Class[_]): F[SyncLogger[F]] = Sync[F].delay(getFromClass(clazz))
  def fromName[F[_]: Sync](name: String): F[SyncLogger[F]]     = Sync[F].delay(getFromName(name))

  def getFromClass[F[_]: Sync](clazz: Class[_]): SyncLogger[F] = getFromLogger(LoggerFactory.getLogger(clazz))
  def getFromName[F[_]: Sync](name: String): SyncLogger[F]     = getFromLogger(LoggerFactory.getLogger(name))

  def getFromLogger[F[_]: Sync](logger: Underlying): SyncLogger[F] = new SyncLogger[F](logger)
}
