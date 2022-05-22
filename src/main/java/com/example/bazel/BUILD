load("@rules_jvm_external//:defs.bzl", "artifact")

package(
    default_visibility = ["//src:__subpackages__"],
)

java_import(
    name = "youtube_api",
    jars = [
        "YoutubeAndroidPlayerApi.jar",
    ],
)

android_library(
    name = "eflect_benchmarks",
    srcs = [
        "EflectWrapper.java",
        "MainActivity.java",
    ],
    manifest = "AndroidManifest.xml",
    resource_files = glob(["res/**"]),
    exports = [
        artifact("androidx.activity:activity"),
        artifact("androidx.annotation:annotation"),
        artifact("androidx.core:core"),
        artifact("androidx.fragment:fragment"),
    ],
    deps = [
        "youtube_api",
        "@com_google_protobuf//:protobuf_java",
        "@eflect//java/eflect:android_eflect",
        artifact("androidx.activity:activity"),
        artifact("androidx.annotation:annotation"),
        artifact("androidx.core:core"),
        artifact("androidx.fragment:fragment"),
    ],
)
