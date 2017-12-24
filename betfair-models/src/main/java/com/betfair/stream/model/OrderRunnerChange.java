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


package com.betfair.stream.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;
import java.util.Map;


/**
 * OrderRunnerChange
 */
@Value
public class OrderRunnerChange   {

  /**
   * Matched Backs - matched amounts by distinct matched price on the Back side for this runner (selection)
   * @return mb
   **/
  private final List<List<Double>> mb;

  /**
   * Strategy Matches - Matched Backs and Matched Lays grouped by strategy reference
   * @return smc
   **/
  private final Map<String, StrategyMatchChange> smc;

  /**
   * Unmatched Orders - orders on this runner (selection) that are not fully matched
   * @return uo
   **/
  private final List<Order> uo;

  /**
   * Selection Id - the id of the runner (selection)
   * @return id
   **/
  private final Long id;

  /**
   * Handicap - the handicap of the runner (selection) (null if not applicable)
   * @return hc
   **/
  private final Double hc;

  /**
   * Get fullImage
   * @return fullImage
   **/
  private final Boolean fullImage;

  /**
   * Matched Lays - matched amounts by distinct matched price on the Lay side for this runner (selection)
   * @return ml
   **/
  private final List<List<Double>> ml;

    public OrderRunnerChange(@JsonProperty("mb") List<List<Double>> mb,
                             @JsonProperty("smc") Map<String, StrategyMatchChange> smc,
                             @JsonProperty("uo") List<Order> uo,
                             @JsonProperty("id") Long id,
                             @JsonProperty("hc") Double hc,
                             @JsonProperty("fullImage") Boolean fullImage,
                             @JsonProperty("ml") List<List<Double>> ml) {
        this.mb = mb;
        this.smc = smc;
        this.uo = uo;
        this.id = id;
        this.hc = hc;
        this.fullImage = fullImage;
        this.ml = ml;
    }
}

