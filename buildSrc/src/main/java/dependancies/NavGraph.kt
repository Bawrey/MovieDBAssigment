package dependancies

import org.gradle.api.artifacts.dsl.DependencyHandler



fun DependencyHandler.navGraph(){
    implementation ("androidx.navigation:navigation-fragment-ktx:2.4.2")
    implementation ("androidx.navigation:navigation-ui-ktx:2.4.2")
}