/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package cc.xuloo.fixture.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import cc.xuloo.fixture.api.FixtureService;

/**
 * The module that binds the FixtureService so that it can be served.
 */
public class FixtureModule extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {
    System.out.println("starting up...");
    bindService(FixtureService.class, FixtureServiceImpl.class);
    bind(FixtureRepository.class);

    bind(BetfairWorker.class).asEagerSingleton();

  }
}
