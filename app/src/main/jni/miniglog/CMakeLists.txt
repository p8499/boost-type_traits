project(MINIGLOG)

add_library(
  miniglog
  ${CMAKE_CURRENT_SOURCE_DIR}/master/miniglog/logging.cc)

file(
  GLOB HPPS
  ${CMAKE_CURRENT_SOURCE_DIR}/master/miniglog/*.h)

foreach(HPP ${HPPS})
  file(
    COPY ${HPP}
    DESTINATION ${CMAKE_CURRENT_BINARY_DIR}/master/glog)
endforeach(HPP)
