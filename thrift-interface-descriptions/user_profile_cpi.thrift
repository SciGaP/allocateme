/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

/*
 * Component Programming Interface definition for Apache Airavata GFac Service.
 *
*/

include "user_profile_model.thrift"

namespace java org.apache.airavata.user.profile.cpi

const string UPS_CPI_VERSION = "0.16.0"

exception UserProfileServiceException {
  1: required string message
}

service UserProfileService {

  /** Query CS server to fetch the CPI version */
  string getUserProfileSerbiceVersion(),

  /**
  * This method is to add SSHCredential which will return the token Id in success
  **/
  string registerUserProfile (1: required user_profile_model.UserProfile userProfile)
                        throws (1:UserProfileServiceException upsException);

  user_profile_model.UserProfile getUserProfile (1: required string airavataInternalUserId)
                        throws (1:UserProfileServiceException upsException);

  bool deleteUserProfile(1: required string airavataInternalUserId)
                        throws (1:UserProfileServiceException upsException);

}