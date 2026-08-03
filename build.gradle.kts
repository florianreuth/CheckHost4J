import de.florianreuth.baseproject.setupProject
import de.florianreuth.baseproject.setupPublishing

plugins {
    `java-library`
    id("de.florianreuth.baseproject")
}

setupProject()
setupPublishing()

dependencies {
    compileOnly("com.google.code.gson:gson:2.14.0")
}
