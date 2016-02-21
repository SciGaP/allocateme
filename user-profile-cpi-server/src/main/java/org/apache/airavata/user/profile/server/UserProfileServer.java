/*
*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*
*/

package org.apache.airavata.user.profile.server;

import org.apache.airavata.user.profile.cpi.UserProfileService;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserProfileServer {

    private final static Logger logger = LoggerFactory.getLogger(UserProfileServer.class);

    public static UserProfileServerHandler userProfileServerHandler;
    public static UserProfileService.Processor userProfileProcessor;

    public static void main(String[] args) {
        try {
            userProfileServerHandler = new UserProfileServerHandler();
            userProfileProcessor = new UserProfileService.Processor(userProfileServerHandler);

            TMultiplexedProcessor airavataServerProcessor = new TMultiplexedProcessor();

            airavataServerProcessor.registerProcessor("UserProfileService", userProfileProcessor);

            TServerTransport serverTransport = new TServerSocket(9190);

            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(airavataServerProcessor));

            System.out.println("Starting User Profile server...");
            server.serve();

        } catch (Exception x) {
            x.printStackTrace();
        }
    }


}