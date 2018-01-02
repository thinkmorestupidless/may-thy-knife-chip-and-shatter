/**
 * Betfair: Exchange Streaming API
 * API to receive streamed updates. This is an ssl socket connection of CRLF delimited json messages (see RequestMessage & ResponseMessage)
 *
 * OpenAPI spec version: 1.0.1423
 * Contact: bdp@betfair.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package cc.xuloo.betfair.client.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;


/**
 * AuthenticationMessage
 */
@Value
@Builder
public class AuthenticationMessage implements RequestMessage  {

    /**
     * Client generated unique id to link request with response (like json rpc)
     * @return id
     **/
    private final Integer id;

    private final String session;

    private final String appKey;

    public AuthenticationMessage(@JsonProperty("id") Integer id,
                                 @JsonProperty("session") String session,
                                 @JsonProperty("appKey") String appKey) {
        this.id = id;
        this.session = session;
        this.appKey = appKey;
    }
}
