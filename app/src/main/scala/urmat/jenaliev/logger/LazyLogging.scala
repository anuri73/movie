package urmat.jenaliev.logger

import cats.effect.IO

trait LazyLogging {
  implicit protected val logger: Logger[IO] = SyncLogger.getFromClass(getClass)
}
