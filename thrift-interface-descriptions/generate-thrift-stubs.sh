#! /usr/bin/env bash

# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# This script will generate/regenerate the thrift code for Airavata Server Skeletons, Client Stubs
#    and Data Model java beans in java, C++, PHP and Python.

show_usage() {
	echo -e "Usage: $0 [Languague to generate stubs]"
	echo ""
	echo "options:"
	echo -e "\tjava Generate/Update Java Stubs"
	echo -e "\tphp Generate/Update PHP Stubs"
	echo -e "\tall Generate/Update all stubs (Java, PHP)."
	echo -e "\t-h[elp] Print the usage options of this script"
}

if [ $# -lt 1 ]
then
	show_usage
	exit 1
fi

if [[ $1 == "-h" ||$1 == "--help" ]]
then
	show_usage
	exit 0
fi

# Generation of thrift files will require installing Apache Thrift. Please add thrift to your path.
#  Verify is thrift is installed, is in the path is at a specified version.

REQUIRED_THRIFT_VERSION='0.9.3'
if hash thrift &> /dev/null; then
  THRIFT_EXEC=$(which thrift)
else
  THRIFT_EXEC=/usr/local/bin/thrift
fi

VERSION=$($THRIFT_EXEC -version 2>/dev/null | grep -F "${REQUIRED_THRIFT_VERSION}" |  wc -l)
if [ "$VERSION" -ne 1 ] ; then
    echo -e "ERROR:\t Apache Thrift version ${REQUIRED_THRIFT_VERSION} is required."
    echo -e "It is either not installed or is not in the path"
    exit 1
fi

# Global Constants used across the script
BASE_TARGET_DIR='target'

# Thrift files
USER_MODEL_THRIFT_FILE='user_profile_model.thrift'
USER_SERVICE_THRIFT_FILE='user_profile_cpi.thrift'

USER_MODEL_SRC_DIR='../user-profile-model/src/main/java'
USER_API_SDK_DIR='../user-profile-cpi-stubs/src/main/java'
PHP_SDK_DIR='../user-php-sdk/src/main/resources/lib'

# Initialize the thrift arguments.
#  Since most of the Airavata API and Data Models have includes, use recursive option by default.
#  Generate all the files in target directory
THRIFT_ARGS="-r -o ${BASE_TARGET_DIR}"
# Ensure the required target directories exists, if not create.
mkdir -p ${BASE_TARGET_DIR}

# The Function fail prints error messages on failure and quits the script.
fail() {
    echo $@
    exit 1
}

# The function add_license_header adds the ASF V2 license header to all java files within the specified generated
#   directory. The function also adds suppress all warnings annotation to all public classes and enum's
#  To Call:
#   add_license_header $generated_code_directory
add_license_header() {

    # Fetch the generated code directory passed as the argument
    GENERATED_CODE_DIR=$1

    # For each source file within the generated directory, add the ASF V2 LICENSE header
    FILE_SUFFIXES=(.php .java .h .cpp)
    for file in "${FILE_SUFFIXES[@]}"; do
        for f in $(find ${GENERATED_CODE_DIR} -name "*$file"); do
            cat - ${f} >${f}-with-license <<EOF
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

EOF
        mv ${f}-with-license ${f}
        done
    done
}

# The function compares every generated java file with the one in specified existing source location. If the comparison
#   shows a difference, then it replaces with the newly generated file (with added license header).
#  To Call:
#   copy_changed_files $generated_code_directory $existing_source_directory
copy_changed_files() {

    # Read all the function arguments
    GENERATED_CODE_DIR=$1
    WORKSPACE_SRC_DIR=$2

    echo "Generated sources are in ${GENERATED_CODE_DIR}"
    echo "Destination workspace is in ${WORKSPACE_SRC_DIR}"

    # Check if the newly generated files exist in the targeted workspace, if not copy. Only changed files will be synced.
    #  the extra slash to GENERATED_CODE_DIR is needed to ensure the parent directory itself is not copied.
    rsync -auv ${GENERATED_CODE_DIR}/ ${WORKSPACE_SRC_DIR}
}

#######################################
# Generate/Update Airavata Data Model #
#######################################

generate_java_stubs() {

    #Java Beans generation directory
    JAVA_BEAN_GEN_DIR=${BASE_TARGET_DIR}/gen-javabean

    # As a precaution  remove and previously generated files if exists
    rm -rf ${JAVA_BEAN_GEN_DIR}

    # Generate the Airavata Data Model using thrift Java Beans generator. This will take generate the classes in bean style
    #   with members being private and setters returning voids.
    #   The airavata_data_models.thrift includes rest of data models.
    $THRIFT_EXEC ${THRIFT_ARGS} --gen java:beans ${USER_MODEL_THRIFT_FILE} || fail unable to generate java bean thrift classes on base data model

    # For the generated java beans add the ASF V2 License header
    add_license_header $JAVA_BEAN_GEN_DIR

    # Compare the newly generated beans with existing sources and replace the changed ones.
    copy_changed_files ${JAVA_BEAN_GEN_DIR} ${USER_MODEL_SRC_DIR}

    ###############################################################################
    # Generate/Update source used by Airavata Server Skeletons & Java Client Stubs #
    #  JAVA server and client both use generated api-boilerplate-code             #
    ###############################################################################

    #Java generation directory
    JAVA_GEN_DIR=${BASE_TARGET_DIR}/gen-java

    # As a precaution  remove and previously generated files if exists
    rm -rf ${JAVA_GEN_DIR}

    # Using thrift Java generator, generate the java classes based on Airavata API. This
    #   The airavata_api.thrift includes rest of data models.
    $THRIFT_EXEC ${THRIFT_ARGS} --gen java ${USER_SERVICE_THRIFT_FILE} || fail unable to generate java thrift classes on AiravataAPI

    # For the generated java classes add the ASF V2 License header
    add_license_header $JAVA_GEN_DIR

    # Compare the newly generated classes with existing java generated skeleton/stub sources and replace the changed ones.
    #  Only copying the API related classes and avoiding copy of any data models which already exist in the data-models.
    copy_changed_files ${JAVA_GEN_DIR}/org/apache/airavata/user ${USER_API_SDK_DIR}/org/apache/airavata/user

    echo "Successfully generated new java sources, compared against exiting code and replaced the changed files"
}

####################################
# Generate/Update PHP Stubs #
####################################

generate_php_stubs() {

    #PHP generation directory
    PHP_GEN_DIR=${BASE_TARGET_DIR}/gen-php

    # As a precaution  remove and previously generated files if exists
    rm -rf ${PHP_GEN_DIR}

    # Using thrift Java generator, generate the PHP classes based on Airavata API. This
    #   The airavata_api.thrift includes rest of data models.
    $THRIFT_EXEC ${THRIFT_ARGS} --gen php:autoload ${USERSERVICE_THRIFT_FILE}  || fail unable to generate PHP thrift classes

    # For the generated java classes add the ASF V2 License header
    ## TODO Write PHP license parser

    # Compare the newly generated classes with existing java generated skeleton/stub sources and replace the changed ones.
    #  Only copying the API related classes and avoiding copy of any data models which already exist in the data-models.
    copy_changed_files ${PHP_GEN_DIR} ${PHP_SDK_DIR}

}


for arg in "$@"
do
    case "$arg" in
    all)    echo "Generate all stubs (Java, PHP) Stubs"
            generate_java_stubs
            generate_php_stubs
            generate_cpp_stubs
            generate_python_stubs
            ;;
    java)   echo "Generating Java Stubs"
            generate_java_stubs
            ;;
    php)    echo "Generate PHP Stubs"
            generate_php_stubs
            ;;
    *)      echo "Invalid or unsupported option"
    	    show_usage
	        exit 1
            ;;
    esac
done

####################
# Cleanup and Exit #
####################
# CleanUp: Delete the base target build directory
#rm -rf ${BASE_TARGET_DIR}

exit 0
