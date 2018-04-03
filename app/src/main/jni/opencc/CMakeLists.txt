add_subdirectory(master)

file(
  GLOB HPPS
  ${CMAKE_CURRENT_SOURCE_DIR}/master/src/*.hpp)

foreach(HPP ${HPPS})
  file(
    COPY ${HPP}
    DESTINATION ${CMAKE_CURRENT_BINARY_DIR}/master/opencc)
endforeach(HPP)
