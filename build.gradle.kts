plugins {
  java
  idea
  eclipse
}

allprojects {
  group = "de.jeromewolff"
  version = "1.0.0-SNAPSHOT"

  repositories {
    mavenCentral()
  }
}

subprojects {
  apply(plugin = "java")
  apply(plugin = "idea")
  apply(plugin = "eclipse")

  val mockitoAgent = configurations.create("mockitoAgent")

  dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    compileOnly("org.projectlombok:lombok:1.18.36")
    implementation("jakarta.annotation:jakarta.annotation-api:3.0.0")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
    implementation("com.google.guava:guava:33.4.0-jre")
    mockitoAgent("org.mockito:mockito-core:5.16.0") {
      isTransitive = false
    }
    testImplementation("org.mockito:mockito-core:5.16.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.12.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-params:5.12.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.12.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  }

  tasks.withType<JavaCompile> {
    options.release.set(22)
    options.encoding = "UTF-8"
  }

  tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
  }

  tasks.named<Test>("test") {
    jvmArgs("-javaagent:${mockitoAgent.asPath}")
  }
}
