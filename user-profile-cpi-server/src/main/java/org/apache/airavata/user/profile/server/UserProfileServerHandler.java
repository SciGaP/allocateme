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

import org.apache.airavata.model.user.UserProfile;
import org.apache.airavata.user.profile.cpi.UserProfileService;
import org.apache.airavata.user.profile.cpi.UserProfileServiceException;
import org.apache.airavata.user.profile.cpi.user_profile_cpiConstants;
import org.apache.thrift.TException;

import org.apache.airavata.App;

public class UserProfileServerHandler implements UserProfileService.Iface {

    /**
     * Query CS server to fetch the CPI version
     */
    public String getUserProfileSerbiceVersion() throws TException {
        //write your implementation
        return user_profile_cpiConstants.UPS_CPI_VERSION;
    }

    /**
     * This method is to add SSHCredential which will return the token Id in success
     *
     * @param userProfile
     */
    public String registerUserProfile(UserProfile userProfile) throws UserProfileServiceException, TException {
        return App.registerUser(userProfile);
    }

    public UserProfile getUserProfile(String airavataInternalUserId) throws UserProfileServiceException, TException {
        return App.getUserByEmail(airavataInternalUserId);
    }

    public boolean deleteUserProfile(String airavataInternalUserId) throws UserProfileServiceException, TException {
        return false;
    }
}