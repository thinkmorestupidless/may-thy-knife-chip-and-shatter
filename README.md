# may-thy-knife-chip-and-shatter

A prototype for restructuring the professional-trader Betfair project (see [less-stupid-betting](https://github.com/thinkmorestupidless/less-stupid-betting) and [betfair-akka](https://github.com/thinkmorestupidless/betfair-akka)) as a set of Lagom microservices.

> On naming: software projects are usually named one utilitarian word, or nothing at all. A memorable phrase seemed more interesting - bonus points if you know the reference.

## Structure

- **`betfair-api`/`betfair-impl`** - wraps the `betfair-akka` client in a Lagom service, with `BetfairEntity` as an event-sourced persistent entity tracking Betfair session/market state (`BetfairWorker`, `BetfairMonitor`, `EventMonitor`, `MarketState`), backed by Cassandra and Kafka.
- **`fixture-api`/`fixture-impl`** - a companion Lagom service modelling fixtures as persistent entities, following the same pattern as [flight-booking-lagom](https://github.com/thinkmorestupidless/flight-booking-lagom).

## Status

An exploration of the microservices split, not the production system - superseded by later iterations of the project.

## Building

```bash
sbt compile
```
