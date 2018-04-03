project(APP)

add_library(
  app
  ${CMAKE_CURRENT_SOURCE_DIR}/Utils.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/WrapperRimeTraits.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/WrapperRimeSchemaListItem.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/WrapperRimeSchemaList.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/WrapperRimeCandidate.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/WrapperRimeMenu.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/WrapperRimeComposition.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/WrapperRimeContext.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/WrapperRimeCommit.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/WrapperRimeStatus.cpp
  ${CMAKE_CURRENT_SOURCE_DIR}/app.cpp)

target_include_directories(
  app
  PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../rime/master/src
  PUBLIC ${CMAKE_CURRENT_BINARY_DIR}/../miniglog/master
)

target_link_libraries(
  app
  android
  miniglog
  rime)
