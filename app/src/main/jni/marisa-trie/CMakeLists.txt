project(MARISA-TRIE)

add_library(
  marisa-trie SHARED
  ${CMAKE_CURRENT_SOURCE_DIR}/master/lib/marisa/agent.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/lib/marisa/keyset.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/lib/marisa/trie.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/lib/marisa/grimoire/io/mapper.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/lib/marisa/grimoire/io/reader.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/lib/marisa/grimoire/io/writer.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/lib/marisa/grimoire/trie/louds-trie.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/lib/marisa/grimoire/trie/tail.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/lib/marisa/grimoire/vector/bit-vector.cc)
target_include_directories(
  marisa-trie BEFORE
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master/lib)

add_executable(
  marisa-benchmark
  ${CMAKE_CURRENT_SOURCE_DIR}/master/tools/cmdopt.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/tools/marisa-benchmark.cc)
target_include_directories(
  marisa-benchmark BEFORE
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master/include)
target_link_libraries(
  marisa-benchmark
  marisa-trie)

add_executable(
  marisa-build
  ${CMAKE_CURRENT_SOURCE_DIR}/master/tools/cmdopt.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/tools/marisa-build.cc)
target_include_directories(
  marisa-build BEFORE
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master/include)
target_link_libraries(
  marisa-build
  marisa-trie)

add_executable(
  marisa-common-prefix-search
  ${CMAKE_CURRENT_SOURCE_DIR}/master/tools/cmdopt.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/tools/marisa-common-prefix-search.cc)
target_include_directories(
  marisa-common-prefix-search BEFORE
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master/include)
target_link_libraries(
  marisa-common-prefix-search
  marisa-trie)

add_executable(
  marisa-dump
  ${CMAKE_CURRENT_SOURCE_DIR}/master/tools/cmdopt.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/tools/marisa-dump.cc)
target_include_directories(
  marisa-dump
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master/include)
target_link_libraries(
  marisa-dump
  marisa-trie)

add_executable(
  marisa-lookup
  ${CMAKE_CURRENT_SOURCE_DIR}/master/tools/cmdopt.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/tools/marisa-lookup.cc)
target_include_directories(
  marisa-lookup
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master/include)
target_link_libraries(
  marisa-lookup
  marisa-trie)

add_executable(
  marisa-predictive-search
  ${CMAKE_CURRENT_SOURCE_DIR}/master/tools/cmdopt.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/tools/marisa-predictive-search.cc)
target_include_directories(
  marisa-predictive-search
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master/include)
target_link_libraries(
  marisa-predictive-search
  marisa-trie)

add_executable(
  marisa-reverse-lookup
  ${CMAKE_CURRENT_SOURCE_DIR}/master/tools/cmdopt.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/tools/marisa-reverse-lookup.cc)
target_include_directories(
  marisa-reverse-lookup
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master/include)
target_link_libraries(
  marisa-reverse-lookup
  marisa-trie)
