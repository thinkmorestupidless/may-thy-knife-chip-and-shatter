/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package cc.xuloo.fixture.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import java.util.Set;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

/**
 * The lessstupidflights service interface.
 * <p>
 * This describes everything that Lagom needs to know about how to serve and
 * consume the LessstupidflightsService.
 */
public interface FixtureService extends Service {

  /**
   * Example: curl http://localhost:9000/api/hello/Alice
   */

  ServiceCall<NotUsed, Set<Fixture>> listFixtures();

  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return named("fixtures").withCalls(
        restCall(Method.GET, "/fixtures", this::listFixtures)
      ).withAutoAcl(true);
    // @formatter:on
  }
}
