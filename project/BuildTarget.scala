import sbt._

object BuildTarget {
  private sealed trait DeploymentRuntime
  private case object Kubernetes extends DeploymentRuntime

  private val deploymentRuntime: DeploymentRuntime = sys.props.get("buildTarget") match {
    case Some(v) if v.toLowerCase == "kubernetes" =>
      Kubernetes

    case Some(v) =>
      sys.error(s"The build target $v is not supported. Only supports 'kubernetes'")

    case None =>
      Kubernetes
  }

  val additionalLibraryDependencies: Seq[ModuleID] =
    if (deploymentRuntime == Kubernetes) Seq(Library.serviceLocatorDns) else Seq.empty

}