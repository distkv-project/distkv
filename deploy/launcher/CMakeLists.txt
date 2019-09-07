cmake_minimum_required(VERSION 3.11)
project(launcher)

set(CMAKE_CXX_STANDARD 14)

add_executable(dst-server dst_server_main.cc launcher.h)
add_executable(dst-cli dst_client_tool_main.cc launcher.h)