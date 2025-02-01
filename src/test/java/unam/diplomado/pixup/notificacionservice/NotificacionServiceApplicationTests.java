package unam.diplomado.pixup.notificacionservice;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import unam.diplomado.pixup.notificacionservice.dto.NotificacionResponse;
import unam.diplomado.pixup.notificacionservice.service.NotificacionServiceImpl;
import unam.diplomado.pixup.notificacionservice.util.FileUtil;
import unam.diplomado.pixup.notificacionservice.util.MapperUtil;

import java.io.IOException;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class NotificacionServiceApplicationTests {

	@LocalServerPort
	private static int serverPort = 8081;
	@LocalManagementPort
	private static int managementPort =8084;
	private static String messageToPrint = "Some problem to load use cases for test: ";
	// MOCK VALUES
	private static String MOCK_FILE_NOTIFICACIONES = "/tests/notificaciones.json";
	private static String MOCK_FILE_NOTIFICACION = "/tests/notificacion.json";
	private static String MOCK_FILE_NOTIFICACIONES_EMPTY = "/tests/notificaciones_empty.json";
	private static String MOCK_LOOK_FOR_ID = "1003";
	private static String MOCK_ID = "1003";
	private static String MOCK_USER_ID = "groot";
	private static String MOCK_SOME_EMAIL = "somemail@domain.es";
	// TEST FOR REAL
	@Autowired
	private NotificacionServiceImpl notificacionService;
	private static String BASE_URL = "http://localhost:"+ String.valueOf(serverPort) +"/api/notificaciones";

	@Test
	public void applicationContextLoads() {

	}

	// .withBasicAuth(ADMIN_TEST_LOGIN, ADMIN_TEST_PASSWORD)

/* This test is locked as the application is not running, it's just a simple example on how to test an integration
	@Test
	public void getNotificacionesTest() {
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<NotificacionResponse[]> response = testRestTemplate.getForEntity(BASE_URL, NotificacionResponse[].class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
*/
	@Test
	public void getNotificacionByIdMockSuccessTest()  {
		try {
			String notificacionJson = FileUtil.readFromFileToString(MOCK_FILE_NOTIFICACIONES);
			List<NotificacionResponse> notificaciones = MapperUtil.deserializeNotificaciones(notificacionJson);
			NotificacionResponse notificacionResponse = notificaciones.stream()
					.filter(notificacion -> notificacion.getId().equals(MOCK_LOOK_FOR_ID))
					.findAny()
					.orElse(null);
			//HttpEntity<NotificacionResponse> httpEntity = new HttpEntity<>(notificacionResponse);
			Assert.assertTrue(notificacionResponse!=null);
		} catch (IOException e){
			System.out.println(messageToPrint + MOCK_FILE_NOTIFICACIONES);
			e.printStackTrace();
		}
	}

	@Test
	public void getNotificacionMockSuccessTest()  {
		try {
			String notificacionJson = FileUtil.readFromFileToString(MOCK_FILE_NOTIFICACION);
			NotificacionResponse notificacion = MapperUtil.deserializeNotificacion(notificacionJson);
			HttpEntity<NotificacionResponse> httpEntity = new HttpEntity<>(notificacion);
			Assert.assertTrue(
					httpEntity.getBody().getId().equals(MOCK_ID)
							&& httpEntity.getBody().getIdUsuario().equals(MOCK_USER_ID));
		} catch (IOException e){
			System.out.println(messageToPrint + MOCK_FILE_NOTIFICACION);
			e.printStackTrace();
		}
	}
	@Test
	public void getNotificacionesMockSuccessTest()  {
		try {
			String notificacionJson = FileUtil.readFromFileToString(MOCK_FILE_NOTIFICACIONES);
			List<NotificacionResponse> notificaciones = MapperUtil.deserializeNotificaciones(notificacionJson);
			NotificacionResponse notificacionResponse = notificaciones.stream()
					.filter(notificacion -> notificacion.getEmail().equals(MOCK_SOME_EMAIL))
					.findAny()
					.orElse(null);
			Assert.assertTrue(notificacionResponse!=null);
		} catch (IOException e){
			System.out.println(messageToPrint + MOCK_FILE_NOTIFICACIONES);
			e.printStackTrace();
		}
	}
	@Test
	public void getNotificacionesNotEmptyList() {
		try {
			String notificacionJson = FileUtil.readFromFileToString(MOCK_FILE_NOTIFICACIONES);
			List<NotificacionResponse> notificaciones = MapperUtil.deserializeNotificaciones(notificacionJson);
			Assert.assertFalse(notificaciones.isEmpty());
		} catch (IOException e) {
			System.out.println(messageToPrint + MOCK_FILE_NOTIFICACIONES);
			e.printStackTrace();
		}
	}
	@Test
	public void getNotificacionesEmptyList() {
		try{
			String notificacionesJson = FileUtil.readFromFileToString(MOCK_FILE_NOTIFICACIONES_EMPTY);
			List<NotificacionResponse> notificaciones = MapperUtil.deserializeNotificaciones(notificacionesJson);
			Assert.assertTrue(notificaciones.isEmpty());
		} catch (IOException e){
			System.out.println(messageToPrint + MOCK_FILE_NOTIFICACIONES_EMPTY);
			e.printStackTrace();
		}
	}

}
