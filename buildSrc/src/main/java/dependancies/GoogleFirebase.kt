package dependancies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.googleFirebase(){
    implementation(platform("com.google.firebase:firebase-bom:30.1.0"))
    implementation ("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.google.firebase:firebase-auth-ktx")
}