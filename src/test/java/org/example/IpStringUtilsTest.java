package org.example;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class IpStringUtilsTest {

    @ParameterizedTest()
    @MethodSource("provideValidIps")
    void parseValidIps(String ipString, int expected) {
        int value =  IpStringUtils.parseIp(ipString);
        Assertions.assertEquals(expected, value);
    }

    @ParameterizedTest()
    @MethodSource("provideInValidIps")
    void parseInValidIps(String ipString, Class<Exception> expected) {
        assertThrows(expected, () -> IpStringUtils.parseIp(ipString));
    }
    private static Stream<Arguments> provideInValidIps() {
        return Stream.of(
            Arguments.of("0.0..0", IllegalArgumentException.class),
            Arguments.of("255.300.255.255", IllegalArgumentException.class),
            Arguments.of(null, IllegalArgumentException.class),
            Arguments.of("192.34.0.-1", IllegalArgumentException.class),
            Arguments.of("\n", IllegalArgumentException.class),
            Arguments.of("", IllegalArgumentException.class)
        );
    }

    private static Stream<Arguments> provideValidIps() {
        return Stream.of(
            Arguments.of("0.0.0.0", 0),
            Arguments.of("255.255.255.255", -1),
            Arguments.of("192.169.0.1", -1062666239),
            Arguments.of("192.34.0.1", -1071513599),
            Arguments.of("172.169.0.1", -1398210559)
        );
    }

}