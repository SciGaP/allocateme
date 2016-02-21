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

 namespace java org.apache.airavata.model.user
 namespace php Airavata.Model.User
 namespace cpp apache.airavata.model.user
 namespace py apache.airavata.model.user

const string USER_PROFILE_VERSION = "1.0"

enum Status {
    ACTIVE,
    CONFIRMED,
    APPROVED,
    DELETED,
    DUPLICATE,
    GRACE_PERIOD,
    INVITED,
    DENIED
    PENDING,
    PENDING_APPROVAL,
    PENDING_CONFIRMATION
    SUSPENDED
    DECLINED
    EXPIRED
}

struct Publication {
    1: optional string numCitations,
    2: optional string name
}

struct Institution {
    1: optional bool verified
}


/**
 * A structure holding the user profile and its child models.
 *
 * userModelVersion:
 *  Version number of profile
 *
 * airavataInternalUserId:
 *  internal to Airavata, not intended to be used outside of the Airavata platform or possibly by gateways
 *  (that is, never shown to users), never reassigned, REQUIRED
 *
*/

struct UserProfile {
    1: optional string userModelVersion = USER_PROFILE_VERSION,
    2: optional string airavataInternalUserId = "DO_NOT_ADD_AT_CLIENT",
    3: optional string email,
    4: optional string name,
    5: optional string tier,
    6: optional string verified,
    7: optional list<Publication> publications,
    8: optional list<string> funding,
    9: optional Institution institution
}