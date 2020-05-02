package work.hacked.docker.example

object DockerClientExample {
  def main(args: Array[String]): Unit = {
    val dcUtils = DockerUtils

    val command = Array("sh", "-c", "ls -lh")

    val output = dcUtils.execWithOutput(command)

    println(output)

  }
}
