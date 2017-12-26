/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package cc.xuloo.fixture.impl;

import cc.xuloo.fixture.api.FixtureService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import play.libs.akka.AkkaGuiceSupport;

/**
 * The module that binds the FixtureService so that it can be served.
 */
public class FixtureModule extends AbstractModule implements ServiceGuiceSupport, AkkaGuiceSupport {

    @Override
    protected void configure() {
        bindService(FixtureService.class, FixtureServiceImpl.class);
        bind(FixtureRepository.class);
    }
}
