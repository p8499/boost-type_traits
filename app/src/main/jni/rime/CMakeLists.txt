project(RIME)

add_library(
  rime
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime_api.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/candidate.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/commit_history.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/composition.cc
#  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/config.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/context.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/core_module.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/deployer.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/engine.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/key_event.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/key_table.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/menu.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/module.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/registry.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/resource.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/schema.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/segmentation.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/service.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/setup.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/signature.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/switcher.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/ticket.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/translation.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/algo/algebra.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/algo/calculus.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/algo/encoder.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/algo/syllabifier.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/algo/utilities.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/db.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/db_utils.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/dict_compiler.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/dict_module.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/dict_settings.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/dictionary.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/entry_collector.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/level_db.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/mapped_file.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/preset_vocabulary.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/prism.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/reverse_lookup_dictionary.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/string_table.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/table.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/table_db.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/text_db.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/tsv.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/user_db.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/user_db_recovery_task.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/user_dictionary.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/dict/vocabulary.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/abc_segmentor.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/affix_segmentor.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/ascii_composer.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/ascii_segmentor.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/charset_filter.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/chord_composer.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/codepoint_translator.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/echo_translator.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/editor.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/fallback_segmentor.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/filter_commons.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/gears_module.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/history_translator.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/key_binder.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/matcher.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/memory.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/navigator.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/poet.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/punctuator.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/recognizer.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/reverse_lookup_filter.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/reverse_lookup_translator.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/schema_list_translator.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/script_translator.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/selector.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/shape.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/simplifier.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/single_char_filter.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/speller.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/switch_translator.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/table_translator.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/translator_commons.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/uniquifier.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/gear/unity_table_encoder.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/lever/custom_settings.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/lever/customizer.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/lever/deployment_tasks.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/lever/levers_module.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/lever/switcher_settings.cc
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/rime/lever/user_dict_manager.cc)

target_compile_definitions(
  rime
  PUBLIC RIME_VERSION="1.2.9"
  PUBLIC RIME_ENABLE_LOGGING
  PUBLIC RIME_EXPORTS)

if(BUILD_SHARED_LIBS)
  target_compile_definitions(
    rime
    PUBLIC RIME_BUILD_SHARED_LIBS)
endif(BUILD_SHARED_LIBS)

target_compile_options(
  rime
  PUBLIC -Wno-undefined-var-template)

target_include_directories(
  rime
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master/src
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master/thirdparty/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-format/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-algorithm/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-optional/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-move/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-functional/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-signals2/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-function/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-type_index/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-bind/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-multi_index/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-variant/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-any/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-date_time/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-numeric_conversion/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-crc/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-lexical_cast/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-interprocess/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-array/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-intrusive/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-container/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-math/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-filesystem/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-regex/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-locale/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../boost-system/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../yaml-cpp/master/include
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../marisa-trie/master/include
  PUBLIC ${CMAKE_CURRENT_BINARY_DIR}/../miniglog/master
  PUBLIC ${CMAKE_CURRENT_BINARY_DIR}/../opencc/master)

find_package(Threads REQUIRED)
target_link_libraries(
  rime
  libopencc
  yaml-cpp
  marisa-trie
  miniglog
  leveldb
  boost-filesystem
  boost-regex
  boost-locale
  boost-system
  log
  atomic
  ${CMAKE_THREAD_LIBS_INIT})