package com.goblinfire;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goblinfire.model.Fabric;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FabricControllerWebIntegrationTest {

    @Autowired
    private TestRestTemplate _restTemplate;

    private long _idToTest = 1L;
    private String _nameToTest = "jersey red";
    private String _materialToTest = "jersey";
    private float _lengthToTest = 100f;

    @Test
    public void testListAll() throws IOException {
        ResponseEntity<String> response = _restTemplate.getForEntity("/api/v1/fabrics", String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(response.getBody());

        assertThat(responseJson.isMissingNode(), is(false));
        assertThat(responseJson.toString(), equalTo("[]"));
    }

    @Test
    public void testCreateNewFabric() {
        Fabric fabricForTest = new Fabric(_idToTest, _nameToTest, _materialToTest, _lengthToTest);
        ResponseEntity<Fabric> response = _restTemplate.postForEntity("/api/v1/fabrics", fabricForTest, Fabric.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertEquals(_idToTest, response.getBody().get_id().longValue());
        assertEquals(_nameToTest, response.getBody().get_name());
        assertEquals(_materialToTest, response.getBody().get_material());
        assertEquals(_lengthToTest, response.getBody().get_length(), 0f);
    }

    @Test
    public void testGetById() throws IOException {
        Fabric fabricForTest = new Fabric(_idToTest, _nameToTest, _materialToTest, _lengthToTest);

        ResponseEntity<Fabric> response = _restTemplate.postForEntity("/api/v1/fabrics", fabricForTest, Fabric.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertEquals(_idToTest, response.getBody().get_id().longValue());
        assertEquals(_nameToTest, response.getBody().get_name());
        assertEquals(_materialToTest, response.getBody().get_material());
        assertEquals(_lengthToTest, response.getBody().get_length(), 0f);

        response = _restTemplate.getForEntity("/api/v1/fabrics/1", Fabric.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertEquals(_idToTest, response.getBody().get_id().longValue());
        assertEquals(_nameToTest, response.getBody().get_name());
        assertEquals(_materialToTest, response.getBody().get_material());
        assertEquals(_lengthToTest, response.getBody().get_length(), 0f);
    }

    @Test
    public void testUpdateById() {
        Fabric fabricForTest = new Fabric(_idToTest, _nameToTest, _materialToTest, _lengthToTest);
        ResponseEntity<Fabric> response = _restTemplate.postForEntity("/api/v1/fabrics", fabricForTest, Fabric.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertEquals(_idToTest, response.getBody().get_id().longValue());
        assertEquals(_nameToTest, response.getBody().get_name());
        assertEquals(_materialToTest, response.getBody().get_material());
        assertEquals(_lengthToTest, response.getBody().get_length(), 0f);

        fabricForTest.set_name("updated name");
        HttpEntity<Fabric> requestEntity = new HttpEntity<>(fabricForTest);
        ResponseEntity<Fabric> responsePut = _restTemplate.exchange("/api/v1/fabrics/1", HttpMethod.PUT, requestEntity, Fabric.class);

        assertThat(responsePut.getStatusCode(), equalTo(HttpStatus.OK));

        assertEquals(_idToTest, responsePut.getBody().get_id().longValue());
        assertEquals("updated name", responsePut.getBody().get_name());
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Test
    public void testDeleteById() throws IOException {
        Fabric fabricForTest = new Fabric(_idToTest, _nameToTest, _materialToTest, _lengthToTest);
        ResponseEntity<Fabric> response = _restTemplate.postForEntity("/api/v1/fabrics", fabricForTest, Fabric.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertEquals(_idToTest, response.getBody().get_id().longValue());
        assertEquals(_nameToTest, response.getBody().get_name());
        assertEquals(_materialToTest, response.getBody().get_material());
        assertEquals(_lengthToTest, response.getBody().get_length(), 0f);

        ResponseEntity<Fabric> responseDelete = _restTemplate.exchange("/api/v1/fabrics/1", HttpMethod.DELETE, null, Fabric.class);

        assertThat(responseDelete.getStatusCode(), equalTo(HttpStatus.OK));

        assertEquals(_idToTest, responseDelete.getBody().get_id().longValue());
    }
}
