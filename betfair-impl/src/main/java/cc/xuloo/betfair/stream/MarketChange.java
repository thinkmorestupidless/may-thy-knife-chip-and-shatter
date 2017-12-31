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


package cc.xuloo.betfair.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;


/**
 * MarketChange
 */
@Value
public class MarketChange {

  private final List<RunnerChange> rc;

  private final Boolean img;

  private final Double tv;

  private final Boolean con;

  private final MarketDefinition marketDefinition;

  private final String id;

  public MarketChange(@JsonProperty("rc") List<RunnerChange> rc,
                      @JsonProperty("img") Boolean img,
                      @JsonProperty("tv") Double tv,
                      @JsonProperty("con") Boolean con,
                      @JsonProperty("marketDefinition") MarketDefinition marketDefinition,
                      @JsonProperty("id") String id) {
    this.rc = rc;
    this.img = img;
    this.tv = tv;
    this.con = con;
    this.marketDefinition = marketDefinition;
    this.id = id;
  }
}

