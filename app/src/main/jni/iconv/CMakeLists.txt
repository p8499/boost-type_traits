#download from https://github.com/ironsteel/iconv-android
project(ICONV)

add_library(
  iconv SHARED
  ${CMAKE_CURRENT_SOURCE_DIR}/master/libcharset/lib/localcharset.c
  ${CMAKE_CURRENT_SOURCE_DIR}/master/lib/relocatable.c
  ${CMAKE_CURRENT_SOURCE_DIR}/master/lib/iconv.c)

target_compile_definitions(
  iconv
  PUBLIC LIBDIR="c"
  PUBLIC BUILDING_LIBICONV
  PUBLIC IN_LIBRARY)

target_compile_options(
  iconv
  PUBLIC -fms-extensions
  PUBLIC -Wno-ignored-attributes
  PUBLIC -Wno-tautological-compare
  PUBLIC -Wno-parentheses-equality
  PUBLIC -Wno-static-in-inline
)

include_directories(
  ${CMAKE_CURRENT_SOURCE_DIR}/master
  ${CMAKE_CURRENT_SOURCE_DIR}/master/include
  ${CMAKE_CURRENT_SOURCE_DIR}/master/lib
  ${CMAKE_CURRENT_SOURCE_DIR}/master/libcharset/include)