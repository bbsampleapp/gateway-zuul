package io.filer.gateway;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GatewayTest {

    @Autowired
    private RouteLocator routeLocator;

    @Before
    public void setUp() {
        assertThat(routeLocator, is(notNullValue()));
    }

    @Test
    public void testRoutingForStartGame() {
        Route route = routeLocator.getMatchingRoute("/kalah/v1/games");
        assertThat(route, is(notNullValue()));
        assertThat(route.getLocation(), is("https://kalah-service.mybluemix.net"));
        assertThat(route.getPrefix(), is("/kalah/v1"));
        assertThat(route.getPath(), is("/games"));
    }

    @Test
    public void testRoutingForMakeMove() {
        Route route = routeLocator.getMatchingRoute("/kalah/v1/games/GAMEID/pits/PITID");
        assertThat(route, is(notNullValue()));
        assertThat(route.getLocation(), is("https://kalah-service.mybluemix.net"));
        assertThat(route.getPrefix(), is("/kalah/v1"));
        assertThat(route.getPath(), is("/games/GAMEID/pits/PITID"));
    }

}
