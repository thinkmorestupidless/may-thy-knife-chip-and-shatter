package cc.xuloo.opta;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Constants {
    
    public final static BigDecimal THOUSAND = new BigDecimal("1000");

    public final static String DOUBLE_QUOTES    = "\"";
    public final static String FORWARD_SLASH    = "/";
    public final static String PERIOD           = ".";
    public final static String COMMA            = ",";
    
    public final static String CHAT_ADMIN_USERNAME_KEY          = "flip.matrix.admin.username";
    public final static String CHAT_ADMIN_PASSWORD_KEY          = "flip.matrix.admin.password";        
    public final static String CHAT_ENDPOINT_KEY                = "flip.chat.endpoint";
    public final static String SYNAPSE_ENDPOINT_KEY             = "flip.synapse.endpoint";

    public final static String DATABASE_URL_KEY                 = "database.url";
    public final static String DATABASE_USERNAME_KEY            = "database.username";
    public final static String DATABASE_PASSWORD_KEY            = "database.password";
    public final static String DATABASE_DRIVER_KEY              = "database.driver";
    public final static String DATABASE_DDL_GENERATE_KEY        = "database.ddl.generate";
    public final static String DATABASE_DDL_RUN_KEY             = "database.ddl.run";
    public final static String DATABASE_DDL_SEED_SQL            = "database.ddl.seed.sql";
    public final static String DATABASE_CONNECTION_POOL         = "database.connection.pool";
    
    public final static String GAMES_ENDPOINT_KEY               = "flip.games.endpoint";

    public final static String HASH_HASH_KEY = "hash";
    public final static String HASH_DATE_KEY = "date";
    public final static String HASH_DATA_KEY = "data";

    public final static String OBJECT_MAPPER      = "object-mapper-flip";

    public final static String REDIS_LAST_FEED_GEN_DATE_KEY     = "lastFeedGenDate";
    public final static String REDIS_PREFIX_SCORES              = "scores";
    public final static String REDIS_URL_KEY                    = "flip.redis.url";
    
    public final static String FIXTURE_LEAGUE_META_KEY_PREFIX   = "fixture-league-meta";
    
    public final static String LEAGUES_LEAGUE_TYPES_KEY                 = "league-types";
    public final static String LEAGUES_PUBLIC_LEAGUE_KEY                = "public-league";
    public final static String LEAGUES_PUBLIC_LEAGUES_PER_FIXTURE_KEY   = "publicLeaguesPerFixture";

    public final static String LEAGUE_TYPE_AUTO_MATCHED_HEAD_TO_HEAD_WITHOUT_INVITE     = "auto-matched-head-to-head-without-invite";
    public final static String LEAGUE_TYPE_AUTO_MATCH                                   = "auto-match";
    public final static String LEAGUE_TYPE_PUBLIC_LEAGUE                                = "public-league";
    public final static String LEAGUE_TYPE_GROUP_SEPARATOR                              = COMMA;

    public final static String DEFAULT_LEAGUE_TYPE = LEAGUE_TYPE_AUTO_MATCHED_HEAD_TO_HEAD_WITHOUT_INVITE;
    
    public final static String SETTINGS_PATH_ROOT                               = "/flip";
    public final static String SETTINGS_PATH                                    = SETTINGS_PATH_ROOT + "/settings";
    public final static String SETTINGS_BOOSTERS_PATH                           = SETTINGS_PATH + "/boosters";
    public final static String SETTINGS_PATH_BOOSTERS_FREE_PER_USER_TEAM        = SETTINGS_BOOSTERS_PATH + "/freePerUserTeam";
    public final static String SETTINGS_PATH_BOOSTERS_MAX_PER_USER_TEAM         = SETTINGS_BOOSTERS_PATH + "/maxPerUserTeam";
    public final static String SETTINGS_PATH_BOOSTERS_PRICE                     = SETTINGS_BOOSTERS_PATH + "/price";
    public final static String SETTINGS_PATH_BOOSTERS_BOOSTERS                  = SETTINGS_BOOSTERS_PATH + "/boosters";
    public final static String SETTINGS_CACHE_PATH                              = SETTINGS_PATH + "/cache";
    public final static String SETTINGS_PATH_CACHE_DURATION                     = SETTINGS_CACHE_PATH + "/duration";
    public final static String SETTINGS_CHALLENGES_PATH                         = SETTINGS_PATH + "/challenges";
    public final static String SETTINGS_PATH_CHALLENGES_MAX_PER_USER_TEAM       = SETTINGS_CHALLENGES_PATH + "/maxPerUserTeam";
    public final static String SETTINGS_PATH_CHALLENGES_PRICE                   = SETTINGS_CHALLENGES_PATH + "/price";
    public final static String SETTINGS_PATH_CHALLENGES_FREE_SPIES              = SETTINGS_CHALLENGES_PATH + "/freeSpies";
    public final static String SETTINGS_PATH_CHALLENGES_INTERVAL_IN_PLAY        = SETTINGS_CHALLENGES_PATH + "/intervals/inPlay";
    public final static String SETTINGS_PATH_CHALLENGES_INTERVAL_NOT_IN_PLAY    = SETTINGS_CHALLENGES_PATH + "/intervals/notInPlay";
    public final static String SETTINGS_PATH_CHALLENGES_SPY_PRICE               = SETTINGS_CHALLENGES_PATH + "/spyPrice";
    public final static String SETTINGS_PATH_CHALLENGES_POINT_REVEAL_PRICE      = SETTINGS_CHALLENGES_PATH + "/pointRevealPrice";
    public final static String SETTINGS_PATH_CHALLENGES_DEFAULT_STAKE           = SETTINGS_CHALLENGES_PATH + "/defaultStake";
    public final static String SETTINGS_PATH_CHALLENGES_DEFAULT_PRIZE           = SETTINGS_CHALLENGES_PATH + "/defaultPrize";
    public final static String SETTINGS_LEAGUES_PATH                            = SETTINGS_PATH + "/leagues";
    public final static String SETTINGS_PRICES_PATH                             = SETTINGS_PATH + "/prices";
    public final static String SETTINGS_PATH_PRICES_PAYMENT_REQUIRED            = SETTINGS_PRICES_PATH + "/paymentRequired";
    public final static String SETTINGS_OIDC_PATH                               = SETTINGS_PATH + "/oidc";
    public final static String SETTINGS_PATH_OIDC_AUDIENCE                      = SETTINGS_OIDC_PATH + "/audience";
    public final static String SETTINGS_PATH_OIDC_ISSUER                        = SETTINGS_OIDC_PATH + "/issuer";
    public final static String SETTINGS_PATH_OIDC_PROFILE_URL                   = SETTINGS_OIDC_PATH + "/profileUrl";
    public final static String SETTINGS_SUBSTITUTIONS_PATH                      = SETTINGS_PATH + "/substitutions";
    public final static String SETTINGS_PATH_SUBSTITUTIONS_FREE_PER_USER_TEAM   = SETTINGS_SUBSTITUTIONS_PATH + "/freePerUserTeam";
    public final static String SETTINGS_PATH_SUBSTITUTIONS_MAX_PER_USER_TEAM    = SETTINGS_SUBSTITUTIONS_PATH + "/maxPerUserTeam";
    public final static String SETTINGS_PATH_SUBSTITUTIONS_PRICE                = SETTINGS_SUBSTITUTIONS_PATH + "/price";
    public final static String SETTINGS_USER_TEAMS_PATH                         = SETTINGS_PATH + "/userTeams";
    public final static String SETTINGS_PATH_USER_TEAMS_MAX_PER_FIXTURE         = SETTINGS_USER_TEAMS_PATH + "/maxPerFixture";
    public final static String SETTINGS_PATH_LEAGUES_PUBLIC_LEAGUES_PER_FIXTURE = SETTINGS_LEAGUES_PATH + "/publicLeaguesPerFixture";

    public final static String SETTINGS_SEED_FILE_KEY   = "flip.settings.seedFile";

    public final static String ZOOKEEPER_HOSTS_KEY      = "flip.zookeeper.hosts";

    public final static String REGION_NONE              = "none";
    public final static String REGION_CHINA_NORTH       = "cn-north-1";
    public final static String REGION_EUROPE_CENTRAL    = "eu-central-1";
    public final static String REGION_ASIA_SOUTHEAST    = "ap-southeast-1";

    public final static String STATCENTRE_ENDPOINT_KEY                  = "flip.statcentre.endpoint";
    public final static String STATCENTRE_FIXTURES_KEY                  = "fixtures";
    public final static String STATCENTRE_FIXTURE_BETTABLE_LINEUP_KEY   = "fixture_bettable_lineup";
    public final static String STATCENTRE_FIXTURE_COMMENTARY_KEY        = "fixture_commentary";
    public final static String STATCENTRE_FIXTURE_LIST_KEY              = "fixture_list";
    public final static String STATCENTRE_FIXTURE_STATS_LINEUP_KEY      = "fixture_stats_lineup";
    public final static String STATCENTRE_PLAYER_KEY                    = "player";
    public final static String STATCENTRE_TEAM_FIXTURE_KEY              = "team_fixture";
    public final static String STATCENTRE_TEAM_KEY                      = "team";
    public final static String STATCENTRE_TEAM_SQUAD_KEY                = "team_squad";
    public final static String STATCENTRE_TEAM_STANDING_KEY             = "team_standing";
    public final static String STATCENTRE_COMPETITIONS_KEY              = "competitions";
    public final static String STATCENTRE_FIXTURE_KEY                   = "fixture";
    public final static String STATCENTRE_FIXTURE_STATS_KEY             = "fixture_stats";

    public final static Pattern REGEX_PATTERN_INTEGERS = Pattern.compile("\\d+");
    public final static Pattern REGEX_PATTERN_TEAM_NAME = Pattern.compile("(.*) U\\d+");

    public final static DateTimeFormatter dateformat                            = DateTimeFormat.forPattern("yyyy-MM-dd");
    public final static DateTimeFormatter dateTimeformat                        = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public final static DateTimeFormatter dateTimeformatHyphenated              = DateTimeFormat.forPattern("yyyy-MM-dd HH-mm-ss");
    public final static DateTimeFormatter dateTimeformatWithTimeZone            = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss Z");
    public final static DateTimeFormatter dateTimeformatWithTimeZoneCollapsed   = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmssZ");

    public static class Defaults {
        
        public final static String CHAT_ADMIN_USERNAME          = "admin";
        public final static String CHAT_ADMIN_PASSWORD          = "admin123";
        public final static String SYNAPSE_ENDPOINT             = "localhost:18448";
        public final static String CHAT_ENDPOINT                = "localhost:9002";

        public final static String DATABASE_URL                 = "jdbc:postgresql://127.0.0.1:15432/flipsports";
        public final static String DATABASE_URL_TEST            = "jdbc:postgresql://127.0.0.1:15432/flipsports_test";
        public final static String DATABASE_USERNAME            = "flipsports";
        public final static String DATABASE_PASSWORD            = "flipsports";
        public final static String DATABASE_DRIVER              = "org.postgresql.Driver";
        public final static boolean DATABASE_DDL_GENERATE       = false;
        public final static boolean DATABASE_DDL_RUN            = false;

        public final static DateTimeFormatter DATE_TIME_FORMAT  = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        
        public final static String GAMES_ENDPOINT               = "http://127.0.0.1:9000";
        
        public final static String LEADERBOARDS_ENDPOINT        = "http://127.0.0.1:9003";

        public final static String REDIS_URL                    = "redis://127.0.0.1:16379";
        public final static String REDIS_URL_TEST               = "redis://127.0.0.1:63790";

        public final static String SETTINGS_SEED_FILE           = "default-settings2";
        
        public final static String STATCENTRE_ENDPOINT          = "http://127.0.0.1:9001";

        public final static String ZOOKEEPER_HOSTS              = "127.0.0.1:2181";
        
        public final static String LEAGUES_PUBLIC_LEAGUE_NAME_PATTERN = "Public League %s";
    }

    public class Topics {

        public static final String CALCULATE_USER_TEAM_SCORES = "calculate-user-team-scores";
        public static final String F1                         = "f1";
        public static final String F3                         = "f3";
        public static final String F4                         = "f4";
        public static final String F9                         = "f9";
        public static final String F13                        = "f13";
        public static final String F15                        = "f15";
        public static final String F30                        = "f30";
        public static final String F40                        = "f40";
        public static final String HANDLE_FIXTURE_COMPLETE    = "handle-fixture-complete";
        public static final String HANDLE_FIXTURE_START       = "handle-fixture-start";
        public static final String REBUILD_STATS              = "rebuild-stats";
        public static final String EVENTS                     = "events";
        public static final String MONITORING                 = "logstash";
    }
}
