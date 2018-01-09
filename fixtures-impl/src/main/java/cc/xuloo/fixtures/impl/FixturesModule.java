package cc.xuloo.fixtures.impl;

import cc.xuloo.fixtures.FixturesService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class FixturesModule extends AbstractModule implements ServiceGuiceSupport {

    @Override
    protected void configure() {
        bindService(FixturesService.class, FixturesServiceImpl.class);
    }
}
