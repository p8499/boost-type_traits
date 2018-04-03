project(LEVELDB)

add_library(
  leveldb SHARED
  ${CMAKE_CURRENT_SOURCE_DIR}/master/db/builder.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/db/c.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/db/dbformat.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/db/db_impl.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/db/db_iter.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/db/dumpfile.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/db/filename.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/db/log_reader.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/db/log_writer.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/db/memtable.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/db/repair.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/db/table_cache.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/db/version_edit.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/db/version_set.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/db/write_batch.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/table/block.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/table/block_builder.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/table/filter_block.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/table/format.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/table/iterator.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/table/merger.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/table/table.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/table/table_builder.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/table/two_level_iterator.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/port/port_posix.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/util/arena.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/util/bloom.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/util/cache.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/util/coding.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/util/comparator.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/util/crc32c.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/util/env.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/util/env_posix.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/util/filter_policy.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/util/hash.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/util/histogram.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/util/logging.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/util/options.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/util/status.cc)

target_compile_definitions(
  leveldb
  PUBLIC _REENTRANT
  PUBLIC NDEBUG
  PUBLIC LEVELDB_PLATFORM_POSIX
  PUBLIC LEVELDB_ATOMIC_PRESENT
  PUBLIC LEVELDB_IS_BIG_ENDIAN=0
  PUBLIC OS_ANDROID
  PUBLIC SNAPPY)

target_compile_options(
  leveldb
  PUBLIC -std=c++11)

target_include_directories(
  leveldb
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../snappy/master
  PUBLIC ${CMAKE_CURRENT_BINARY_DIR}/../snappy/master)

target_link_libraries(
  leveldb
  snappy
  atomic)
