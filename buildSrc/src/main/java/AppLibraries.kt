import org.gradle.api.artifacts.dsl.DependencyHandler
import dependancies.*

fun DependencyHandler.libraries() {
    androidCore()
    androidPaging()
    androidX()
    coroutine()
    daggerHilt()
    gander()
    glide()
    googleFirebase()
    gson()
    materialDesign()
    navGraph()
    okHttp()
    recycleView()
    retrofit()
    testUnit()
    vmLifeCycle()
    youtubePlayer()
}