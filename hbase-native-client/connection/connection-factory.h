/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#pragma once

#include <wangle/service/Service.h>

#include <string>

#include "connection/pipeline.h"
#include "connection/request.h"
#include "connection/response.h"
#include "connection/service.h"

namespace hbase {
class ConnectionFactory {
public:
  ConnectionFactory();
  virtual ~ConnectionFactory() = default;

  virtual std::shared_ptr<wangle::ClientBootstrap<SerializePipeline>>
  MakeBootstrap();

  virtual std::shared_ptr<HBaseService>
  Connect(std::shared_ptr<wangle::ClientBootstrap<SerializePipeline>> client,
          const std::string &hostname, int port);

private:
  std::shared_ptr<wangle::IOThreadPoolExecutor> io_pool_;
  std::shared_ptr<RpcPipelineFactory> pipeline_factory_;
};
} // namespace hbase
