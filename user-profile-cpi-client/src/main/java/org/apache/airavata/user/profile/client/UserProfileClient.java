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

package org.apache.airavata.user.profile.client;

import org.apache.airavata.model.user.UserProfile;
import org.apache.airavata.user.profile.cpi.UserProfileService;

public class UserProfileClient {

    public static void main(String [] args) {
        System.out.println("Testing User Profile CPI");

        UserProfileService.Client userProfileClient = null;

        try {
            userProfileClient = UserProfileClientFactory.userProfileClient("localhost",9190);
            System.out.println("User Profile Server Version is " + userProfileClient.getUserProfileSerbiceVersion());

            UserProfile userProfile = new UserProfile();
            userProfile.setEmail("alberteinstein@princeton.edu");

            String userId = userProfileClient.registerUserProfile(userProfile);
            System.out.println("Register User Profile Response " + userId);

            userProfile = userProfileClient.getUserProfile("alberteinstein@princeton.edu");
            System.out.println(userProfile);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}