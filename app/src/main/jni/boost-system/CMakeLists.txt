project(BOOST-SYSTEM)

add_definitions(-fPIC -frtti -fexceptions)

add_library(
  boost-system
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/codecvt_error_category.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/operations.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/path.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/path_traits.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/portability.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/unique_path.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/utf8_codecvt_facet.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/windows_file_codecvt.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/cpp_regex_traits.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/cregex.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/c_regex_traits.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/fileiter.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/icu.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/instances.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/internals.hpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/posix_api.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/regex.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/regex_debug.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/regex_raw_buffer.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/regex_traits_defaults.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/static_mutex.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/usinstances.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/w32_regex_traits.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/wc_regex_traits.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/wide_posix_api.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/regex/src/winstances.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/locale/src/encoding/codepage.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/error_code.cpp)

target_compile_definitions(
  boost-system
  PUBLIC BOOST_SIGNALS2)
#  PUBLIC BOOST_LOCALE_WITH_ICONV

target_compile_options(
  boost-system
  PUBLIC -std=c++11
  PUBLIC -Wno-deprecated-declarations)

target_include_directories(
  boost-system
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-config/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-predef/master/include)
#  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../iconv/master/include

#target_link_libraries(
#  boost-system
#  iconv)
