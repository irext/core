cmake_minimum_required(VERSION 3.10)

########################################################
project(ir_decode_jni)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

IF (CMAKE_SYSTEM_NAME MATCHES "Linux")
    MESSAGE("compile platform : Linux")
    add_definitions(-DPLATFORM_LINUX)
ELSEIF (CMAKE_SYSTEM_NAME MATCHES "Windows")
    MESSAGE("compile platform : win32")
    add_definitions(-DPLATFORM_WIN32)
ELSE ()
    MESSAGE("invalid compile platform")
    add_definitions(-DPLATFORM_WIN32)
ENDIF ()

include_directories(
        "${CMAKE_SOURCE_DIR}/src/include"
        "src/include")

set(LIB_IRDECODE_JNI_SRC
        src/ir_decode_jni.c)

add_library(irdecode_jni SHARED
        ${LIB_IRDECODE_JNI_SRC})

target_compile_definitions(irdecode_jni PRIVATE
        -DBOARD_PC
        -DBOARD_PC_JNI)

target_link_libraries(irdecode_jni PRIVATE
        irdecode_s)
