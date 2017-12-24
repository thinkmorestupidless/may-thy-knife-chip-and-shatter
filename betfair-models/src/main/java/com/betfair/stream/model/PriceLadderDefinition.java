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


/**
 * PriceLadderDefinition
 */
@Value
public class PriceLadderDefinition   {
  /**
   * Gets or Sets type
   */
  public enum Type {

    CLASSIC("CLASSIC"),

    FINEST("FINEST"),

    LINE_RANGE("LINE_RANGE");

    private String value;

    Type(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  private final Type type;

  public PriceLadderDefinition(@JsonProperty("type") Type type) {
    this.type = type;
  }
}

