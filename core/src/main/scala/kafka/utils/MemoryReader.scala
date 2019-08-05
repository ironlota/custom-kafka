package kafka.utils

import java.nio.file.{Paths, Files}

import com.sun.org.slf4j.internal.Logger
import com.sun.org.slf4j.internal.LoggerFactory

import kafka.Logging

class MemoryReader extends Runnable with Logging {
  private val MegaBytes = 1024 * 1024

  override def run(): Unit = {
    try {
      System.gc()

      // sleep for 5 seconds
      Thread.sleep(5000)

      if (Files.exists(Paths.get("/mnt/extra/holder.txt"))) {

        val runtime = Runtime.getRuntimes

        val freeMemory = runtime.freeMemory / MegaBytes
        val totalMemory = runtime.totalMemory / MegaBytes
        val maxMemory = runtime.maxMemory / MegaBytes


        info("Used Memory  : %s MB".format((totalMemory - freeMemory)))
        info("Free Memory  : %s MB".format(freeMemory))
        info("Total Memory : %s MB".format(totalMemory))
        info("Max Memory   : %s MB".format(maxMemory))
      } else {
        warn("Waiting for file exists")
      }
    }
    catch  {
      case interrupt: InterruptedException => error("Error while sleeping")
      case unknown => error("Got this unknown exception: " + unknown)
    }
  }
}