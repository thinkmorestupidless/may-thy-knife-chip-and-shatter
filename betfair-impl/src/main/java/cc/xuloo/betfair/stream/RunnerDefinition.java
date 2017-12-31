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
import org.joda.time.DateTime;


/**
 * RunnerDefinition
 */
@Value
public class RunnerDefinition {

  /**
   * Gets or Sets status
   */
  public enum Status {

    ACTIVE("ACTIVE"),
    WINNER("WINNER"),
    LOSER("LOSER"),
    REMOVED("REMOVED"),
    REMOVED_VACANT("REMOVED_VACANT"),
    HIDDEN("HIDDEN"),
    PLACED("PLACED");

    private String value;

    Status(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  private final Integer sortPriority;

  private final DateTime removalDate;

  private final Long id;

  private final Double hc;

  private final Double adjustmentFactor;

  private final Double bsp;

  private final Status status;

  public RunnerDefinition(@JsonProperty("sortPriority") Integer sortPriority,
                          @JsonProperty("removalDate") DateTime removalDate,
                          @JsonProperty("id") Long id,
                          @JsonProperty("hc") Double hc,
                          @JsonProperty("adjustmentFactor") Double adjustmentFactor,
                          @JsonProperty("bsp") Double bsp,
                          @JsonProperty("status") Status status) {
    this.sortPriority = sortPriority;
    this.removalDate = removalDate;
    this.id = id;
    this.hc = hc;
    this.adjustmentFactor = adjustmentFactor;
    this.bsp = bsp;
    this.status = status;
  }
}

