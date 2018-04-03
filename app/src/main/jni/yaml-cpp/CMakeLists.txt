project(YAML-CPP)

add_library(
  yaml-cpp SHARED
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/binary.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/convert.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/directives.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/emit.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/emitfromevents.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/emitter.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/emitterstate.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/emitterutils.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/exceptions.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/exp.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/memory.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/node.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/nodebuilder.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/nodeevents.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/node_data.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/null.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/ostream_wrapper.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/parse.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/parser.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/regex_yaml.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/scanner.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/scanscalar.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/scantag.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/scantoken.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/simplekey.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/singledocparser.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/stream.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/tag.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/contrib/graphbuilder.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/contrib/graphbuilderadapter.cpp)

target_include_directories(
  yaml-cpp
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/master/include)
