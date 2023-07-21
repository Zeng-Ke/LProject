plugins {
    `kotlin-dsl`
}

gradlePlugin{
    plugins.register("buildplugin"){
        id = "com.example.buildplugin"
        implementationClass = "com.example.buildplugin.BuildPlugin"
    }
}