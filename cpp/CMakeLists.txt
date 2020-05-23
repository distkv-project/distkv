cmake_minimum_required(VERSION 3.11)
project(distkv)

set(CMAKE_CXX_STANDARD 14)

find_package(JNI REQUIRED)

set(THIRDPARTY_DIRS thirdparty/)
set(SKIPLIST_SOURCE_FILES thirdparty/skiplist/src)
include_directories(${JNI_INCLUDE_DIRS}, ${THIRDPARTY_DIRS})


include_directories(src)

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/build)

set(STORAGE_ENGINE_SOURCE_FILES ${SKIPLIST_SOURCE_FILES} src/jni_lib/com_distkv_storageengine_StorageEngine.cc)

add_library(java_storage_engine SHARED ${STORAGE_ENGINE_SOURCE_FILES})

add_executable(cpp src/main.cpp)
