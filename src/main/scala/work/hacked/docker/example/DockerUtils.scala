package work.hacked.docker.example

import com.spotify.docker.client.{DefaultDockerClient, DockerClient}
import com.spotify.docker.client.messages.{ContainerConfig, HostConfig}

object DockerUtils {

  private val DOCKER_IMAGE = "ubuntu"
  private val IMAGE_ID = "2ca708c1c9cc"

  private val docker = DefaultDockerClient.fromEnv().build()
  //docker.pull(DOCKER_IMAGE)

  private val hostConfig = HostConfig.builder().build()
  private val containerConfig = ContainerConfig.builder()
    .hostConfig(hostConfig)
    .image(IMAGE_ID)
    .cmd("sh", "-c", "while :; do sleep 1; done")
    .build()

  private val creation = docker.createContainer(containerConfig)

  private val id = creation.id()
  private val info = docker.inspectContainer(id)

  println(s"Starting container $id with $info")
  docker.startContainer(id)

  def getDockerClient: DefaultDockerClient = docker

  def execWithOutput(command: Array[String]): String ={
    val execCreation = docker.execCreate(
      id, command, DockerClient.ExecCreateParam.attachStdout(),
      DockerClient.ExecCreateParam.attachStderr()
    )
    val output = docker.execStart(execCreation.id())
    output.readFully()
  }

}
