plugins {
   java
   application
   `maven-publish`
}
repositories {
   jcenter()
   mavenLocal()
}
group = "inventory"
version = "1.0-SNAPSHOT"
dependencies {
   implementation("com.google.guava:guava:26.0-jre")
   implementation("org.springframework.boot:spring-boot-starter-jdbc:3.0.3")
   implementation("org.springframework.data:spring-data-solr:4.3.14")
   implementation("jakarta.validation:jakarta.validation-api:3.0.2")
   implementation("org.apache.solr:solr-solrj:9.1.1")
   implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
   implementation("org.postgresql:postgresql:42.2.5")
   implementation("org.flywaydb:flyway-core:9.15.1")
   implementation("org.springframework.boot:spring-boot-starter-web:3.0.3")
   implementation("org.springframework.boot:spring-boot-starter-security:3.0.3")
   implementation("org.springframework.boot:spring-boot-starter-actuator:3.0.3")
   implementation("de.codecentric:spring-boot-admin-starter-client:3.0.3")
   implementation("org.springframework.security:spring-security-jwt:1.1.1.RELEASE")
   implementation("org.springframework.security:spring-security-jwt:1.0.10.RELEASE")
   implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.2.RELEASE")
   implementation("com.graphql-java:graphql-java:2019-07-15T07-36-13-5761d24")
   implementation("com.graphql-java:graphiql-spring-boot-autoconfigure:5.0.2")
   implementation("io.jsonwebtoken:jjwt-api:0.12.6")
   implementation("io.jsonwebtoken:jjwt-impl:0.12.6")
   implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")
   implementation("org.springframework.boot:spring-boot-starter-websocket:3.0.3")
   implementation("io.reactivex.rxjava3:rxjava:3.1.6")
   implementation("commons-io:commons-io:2.5")
   implementation("org.apache.velocity:velocity:1.7")
   implementation("org.apache.commons:commons-text:1.7")
   implementation("org.apache.commons:commons-compress:1.20")
   implementation("io.reactivex.rxjava3:rxjava:3.0.0-RC2")
   implementation("com.google.googlejavaformat:google-java-format:1.7")
   implementation("com.jcraft:jsch:0.1.55")
   implementation("de.siegmar:logback-gelf:2.0.0")
   implementation("org.codehaus.janino:janino:2.7.8")
   implementation("org.json:json:20210307")
   implementation("org.apache.tika:tika-core:2.1.0")
   implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
   implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
   implementation("com.google.firebase:firebase-admin:9.2.0")
   implementation("com.github.gotson:webp-imageio:0.2.2")
   implementation("net.coobird:thumbnailator:0.4.14")
   testCompileOnly("com.h2database:h2:1.4.193")
   testCompileOnly("org.springframework.boot:spring-boot-starter-test:3.0.3")
   compileOnly("org.projectlombok:lombok:1.18.24")
   annotationProcessor("org.projectlombok:lombok:1.18.24")
   testImplementation("junit:junit:4.12")
   implementation("jakarta.mail:jakarta.mail-api:2.1.1")
   implementation("org.eclipse.angus:jakarta.mail:2.0.1")
   implementation("jakarta.json:jakarta.json-api:2.1.1")
   implementation("com.github.librepdf:openpdf:1.3.30")
   implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}
val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}

publishing {
    repositories {
        mavenLocal()
    }
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}
application {
   mainClassName = "main.Main"
}
// Added because H2 does not support DEFERRABLE, and without DEFERRABLE, many Mutations will fail. Remove this after figuring that out.
tasks.test {
    onlyIf {
        false
    }
}