project(BOOST-REGEX)

add_definitions(-fPIC -frtti -fexceptions)

add_library(
  boost-regex
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/codecvt_error_category.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/operations.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/path.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/path_traits.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/portability.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/unique_path.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/utf8_codecvt_facet.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/filesystem/src/windows_file_codecvt.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/cpp_regex_traits.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/cregex.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/c_regex_traits.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/fileiter.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/icu.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/instances.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/internals.hpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/posix_api.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/regex.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/regex_debug.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/regex_raw_buffer.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/regex_traits_defaults.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/static_mutex.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/usinstances.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/w32_regex_traits.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/wc_regex_traits.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/wide_posix_api.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/winstances.cpp)
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/locale/src/encoding/codepage.cpp
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/libs/system/src/error_code.cpp

target_compile_definitions(
  boost-regex
  PUBLIC BOOST_SIGNALS2)
#  PUBLIC BOOST_LOCALE_WITH_ICONV

target_compile_options(
  boost-regex
  PUBLIC -std=c++11
  PUBLIC -Wno-deprecated-declarations)

target_include_directories(
  boost-regex
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-config/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-predef/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-throw_exception/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-assert/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-smart_ptr/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-core/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-mpl/master/include)
#  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../iconv/master/include

#target_link_libraries(
#  boost-regex
#  iconv)
