package org.example;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;


public final class IpStringUtils {

    public static final String NOT_VALID_IP_ADDRESS = "Value \"%s\" is not valid IP address ";

    private IpStringUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static int parseIp(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException(
                NOT_VALID_IP_ADDRESS.formatted(address));
        }
        try {
            InetAddress ip = InetAddress.getByName(address);
            byte[] bytes = ip.getAddress();
            return ByteBuffer.wrap(bytes).getInt();
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException(
                NOT_VALID_IP_ADDRESS.formatted(address));
        }
    }
}
