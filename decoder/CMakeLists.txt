cmake_minimum_required(VERSION 3.10)

########################################################
project(irdecode)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")
add_definitions(-DBOARD_PC)
# add_definitions(-DBOARD_PC_JNI)
# add_definitions(-DDEBUG)

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

set(SOURCE_FILES_EXECUTABLE
        src/ir_utils.c
        src/ir_tv_control.c
        src/ir_ac_apply.c
        src/ir_ac_build_frame.c
        src/ir_ac_parse_parameter.c
        src/ir_ac_parse_forbidden_info.c
        src/ir_ac_parse_frame_info.c
        src/ir_ac_control.c
        src/ir_ac_binary_parse.c
        src/ir_decode.c
        src/ir_decode_test.c)

set(SOURCE_FILES_LIB
        src/ir_utils.c
        src/ir_tv_control.c
        src/ir_ac_apply.c
        src/ir_ac_build_frame.c
        src/ir_ac_parse_parameter.c
        src/ir_ac_parse_forbidden_info.c
        src/ir_ac_parse_frame_info.c
        src/ir_ac_control.c
        src/ir_ac_binary_parse.c
        src/ir_decode.c)

set(SOURCE_FILES_JNI_SHARED_LIB
        jni/src/ir_decode_jni.c
        src/ir_utils.c
        src/ir_tv_control.c
        src/ir_ac_apply.c
        src/ir_ac_build_frame.c
        src/ir_ac_parse_parameter.c
        src/ir_ac_parse_forbidden_info.c
        src/ir_ac_parse_frame_info.c
        src/ir_ac_control.c
        src/ir_ac_binary_parse.c
        src/ir_decode.c)

# SET(CMAKE_SYSTEM_NAME Linux)
## ARMv7hf cross compile
# SET(CMAKE_C_COMPILER "/usr/bin/arm-linux-gnueabihf-gcc")
# SET(CMAKE_CXX_COMPILER "/usr/bin/arm-linux-gnueabihf-g++")
# SET(CMAKE_FIND_ROOT_PATH "/usr/bin")

# SET(CMAKE_SYSTEM_NAME Linux)
## ARMv7el cross compile
# SET(CMAKE_C_COMPILER "/usr/bin/arm-linux-gnueabi-gcc")
# SET(CMAKE_CXX_COMPILER "/usr/bin/arm-linux-gnueabi-g++")
# SET(CMAKE_FIND_ROOT_PATH "/usr/bin")

## ARM64 cross compile
# SET(CMAKE_C_COMPILER "/usr/bin/aarch64-linux-gnu-gcc")
# SET(CMAKE_CXX_COMPILER "/usr/bin/aarch64-linux-gnu-g++")
# SET(CMAKE_FIND_ROOT_PATH "/usr/bin")

# add_library(irdecode SHARED ${SOURCE_FILES_LIB})
# add_library(irdecode STATIC ${SOURCE_FILES_LIB})
add_executable(irdecode ${SOURCE_FILES_EXECUTABLE})
# add_library(irdecode SHARED ${SOURCE_FILES_JNI_SHARED_LIB})
