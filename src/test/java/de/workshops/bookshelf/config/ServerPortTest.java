package de.workshops.bookshelf.config;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ServerPortTest {

    @Nested
    @ActiveProfiles("prod")
    class InProduction {

        @Value("${server.port:-1}")
        private int port;

        @Test
        void should_have_a_value() {
            assertThat(port).isEqualTo(8090);
        }
    }

    @Nested
    class ByDefault {

        @Value("${server.port:-1}")
        private int port;

        @Test
        void should_have_default_value() {
            assertThat(port).isEqualTo(-1);
        }
    }
}
