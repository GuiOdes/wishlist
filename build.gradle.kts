plugins {
	java
	jacoco
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.guiodes"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

val cucumberVersion = "7.23.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-log4j2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:mongodb")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.junit.platform:junit-platform-suite-api:1.13.2")
	implementation("io.cucumber:cucumber-jvm:$cucumberVersion")
	testImplementation("io.cucumber:cucumber-junit-platform-engine:$cucumberVersion")
	implementation("io.cucumber:cucumber-spring:$cucumberVersion")
	implementation("io.cucumber:cucumber-java:$cucumberVersion")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jacoco {
	toolVersion = "0.8.13"
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)

	reports {
		xml.required = false
		csv.required = false
		html.required = true
	}
}

configurations {
	all {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
		exclude(group = "ch.qos.logback", module = "logback-classic")
		exclude(group = "ch.qos.logback", module = "logback-core")
	}
}

