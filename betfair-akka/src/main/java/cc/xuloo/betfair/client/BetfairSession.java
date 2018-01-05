package cc.xuloo.betfair.client;

public interface BetfairSession {

    String sessionToken();

    String applicationKey();

    boolean isLoggedIn();

    static BetfairSession loggedIn(String sessionToken, String applicationKey) {
        return new ActiveBetfairSession(sessionToken, applicationKey);
    }

    static BetfairSession loggedOut() {
        return new InactiveBetfairSession();
    }

    class ActiveBetfairSession implements BetfairSession {

        private final String sessionToken;

        private final String applicationKey;

        public ActiveBetfairSession(String sessionToken, String applicationKey) {
            this.sessionToken = sessionToken;
            this.applicationKey = applicationKey;
        }

        @Override
        public String sessionToken() {
            return sessionToken;
        }

        @Override
        public String applicationKey() {
            return applicationKey;
        }

        @Override
        public boolean isLoggedIn() {
            return true;
        }
    }

    class InactiveBetfairSession implements BetfairSession {

        @Override
        public String sessionToken() {
            throw new RuntimeException("BetfairSession is inactive -> call BetfairSession::isValid first");
        }

        @Override
        public String applicationKey() {
            throw new RuntimeException("BetfairSession is inactive -> call BetfairSession::isValid first");
        }

        @Override
        public boolean isLoggedIn() {
            return false;
        }
    }
}
