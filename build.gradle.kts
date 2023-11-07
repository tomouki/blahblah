import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.8.22"

    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("com.netflix.dgs.codegen") version "5.12.4"
    id ("org.jetbrains.kotlin.plugin.allopen") version "1.8.22"
    id ("org.jetbrains.kotlin.plugin.noarg") version "1.8.22"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    // JPA
    kotlin("plugin.jpa") version kotlinVersion

    //  querydsl
    kotlin("kapt") version kotlinVersion
    // allOpen에서 지정한 어노테이션으로 만든 클래스에 open 키워드 적용
//    kotlin("plugin.allopen") version kotlinVersion
    // 인자 없는 기본 생성자를 자동 생성
    // - Hibernate가 사용하는 Reflection API에서 Entity를 만들기 위해 인자 없는 기본 생성자가 필요함
//    kotlin("plugin.noarg") version kotlinVersion
}

noArg {
    annotation("jakarta.persistence.Entity")
}

allOpen {
    annotation("jakarta.persistence.Entity")
//    annotation("jakarta.persistence.MappedSuperclass")
//    annotation("jakarta.persistence.Embeddable")
}

group = "com.digicap"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")

    /*redis*/
    implementation ("org.springframework.boot:spring-boot-starter-data-redis")
//    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
//    implementation("io.r2dbc:r2dbc-h2")

    // querydsl
//    implementation("com.querydsl:querydsl-jpa:$querydslVersion")
//    kapt("com.querydsl:querydsl-apt:$querydslVersion:jpa")
//    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:7.5.1"))
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
    implementation("com.netflix.graphql.dgs:graphql-dgs-webflux-starter")
    implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars")
    implementation("com.netflix.graphql.dgs:graphql-dgs-subscriptions-websockets-autoconfigure")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
    generateClientv2 = true
    packageName = "com.digicap.blahblah.generated"
    typeMapping = mutableMapOf(
        "Time" to "java.time.OffsetTime",
        "Base64String" to "String",
        "URL" to "String",
        "TimeZone" to "java.time.OffsetTime",
        "EmailAddress" to "String",
        "UUID" to "String"
    )
}

//tasks {
//    generateJava {
//        typeMapping = mutableMapOf("EmailAddress" to "kotlin.String")
//    }
//}
