cmake_minimum_required(VERSION 3.4.1)
set(BUILD_SHARED_LIBS ON)

add_subdirectory(src/main/jni/snappy)
add_subdirectory(src/main/jni/leveldb)          #on snappy
add_subdirectory(src/main/jni/yaml-cpp)
add_subdirectory(src/main/jni/marisa-trie)
add_subdirectory(src/main/jni/opencc)
add_subdirectory(src/main/jni/miniglog)
add_subdirectory(src/main/jni/iconv)
add_subdirectory(src/main/jni/boost-filesystem)
add_subdirectory(src/main/jni/boost-regex)
add_subdirectory(src/main/jni/boost-locale)     #on iconv
add_subdirectory(src/main/jni/boost-system)
add_subdirectory(src/main/jni/rime)             #on all above
add_subdirectory(src/main/jni/app)
