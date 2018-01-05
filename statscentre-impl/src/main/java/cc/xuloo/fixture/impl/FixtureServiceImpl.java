/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package cc.xuloo.fixture.impl;

import akka.NotUsed;
import cc.xuloo.fixture.api.Fixture;
import cc.xuloo.fixture.api.FixtureService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import javax.inject.Inject;
import java.util.Set;
import java.util.UUID;

/**
 * Implementation of the FixtureService.
 */
public class FixtureServiceImpl implements FixtureService {

    private final PersistentEntityRegistry persistentEntityRegistry;
    private final FixtureRepository repository;

    @Inject
    public FixtureServiceImpl(PersistentEntityRegistry persistentEntityRegistry, FixtureRepository repository) {
        this.persistentEntityRegistry = persistentEntityRegistry;
        this.repository = repository;

        persistentEntityRegistry.register(FixtureEntity.class);
    }

    private PersistentEntityRef<FixtureCommand> newEntityRef() {
        return persistentEntityRegistry.refFor(FixtureEntity.class, UUID.randomUUID().toString());
    }

    private PersistentEntityRef<FixtureCommand> entityRef(UUID itemId) {
        return persistentEntityRegistry.refFor(FixtureEntity.class, itemId.toString());
    }

    @Override
    public ServiceCall<NotUsed, Set<Fixture>> listFixtures() {
        return request -> repository.listFixtures();
    }
}
