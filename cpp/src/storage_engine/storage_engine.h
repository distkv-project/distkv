#ifndef DISTKV_STORAGE_ENGINE_H
#define DISTKV_STORAGE_ENGINE_H


#include <string>
#include <unordered_map>

#include "sl_map.h"


namespace distkv {

class StorageEngine {


 public:
  void Put(const std::string &key, const std::string &value) {
    store_.insert(std::make_pair(key, value));
  }

  std::string Get(const std::string &key) {
    auto it = store_.find(key);
    if (it == store_.end()) {
      // Not found.
      return std::string();
    }

    return it->second;
  }

 private:
  sl_map<std::string, std::string> store_;
};

} // namespace distkv

#endif //DISTKV_STORAGE_ENGINE_H
