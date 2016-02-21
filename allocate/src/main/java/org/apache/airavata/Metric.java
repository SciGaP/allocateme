package org.apache.airavata;

import org.json.simple.JSONObject;

/**
 * Created by samkreter on 2/20/16.
 */
public interface Metric {
     public JSONObject load() throws Exception;
}
