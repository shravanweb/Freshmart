package rest.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import d3e.core.D3ESubscription;
import d3e.core.Log;
import d3e.core.QueryProvider;
import d3e.core.TransactionWrapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import models.AllDevicesRequest;
import models.UserDevice;
import models.PushNotification;
import repository.jpa.PushNotificationRepository;
import store.Database;
import store.StoreEventType;

@Service
public class PushNotificationService {
	private static String firebaseConfigPath = "firebase.json";

	@Autowired
	private D3ESubscription subscription;
	@Autowired
	private PushNotificationRepository repo;
	@Autowired
	private TransactionWrapper txn;

	private LinkedBlockingDeque<Long> notifications = new LinkedBlockingDeque<Long>();

	@PostConstruct
	public void init() {
		try {
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(
							GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream()))
					.build();
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
				Log.info("Firebase application initialized");
			}
		} catch (IOException e) {
			Log.error(e.getMessage());
		}
		List<PushNotification> all = this.repo.findAll();
		all.stream().filter(p -> !p.isFailed()).forEach(p -> this.notifications.add(p.getId()));
		subscription.onPushNotificationChangeEvent().filter(e -> e.changeType == StoreEventType.Insert)
				.subscribe(e -> this.notifications.add(e.model.getId()));
		new Thread(this::execute, "PushNotificationService").start();
	}

	private void execute() {
		while (true) {
			try {
				Long id = notifications.take();
				PushNotification msg = this.repo.getOne(id);
				publish(msg);
			} catch (Exception e) {
				Log.printStackTrace(e);
			}
		}
	}

	private void publish(PushNotification msg) throws ServletException, IOException {
	    AllDevicesRequest req = new AllDevicesRequest();
	    req.setUsers(msg.getUsers());
	    List<UserDevice> devices = QueryProvider.get().getAllDevices(req).getItems();
	    List<UserDevice> failedList = new ArrayList<>();

	    for (UserDevice device : devices) {
	        // Skip the device if configured to do so
	        if (msg.isSkipThisDevice() && msg.getDeviceToken().equals(device.getDeviceToken())) {
	            continue;
	        }

	        String deviceToken = device.getDeviceToken();
	        if (deviceToken == null || deviceToken.isEmpty()) {
	            Log.error("Invalid device token for user: " + device.getUser().getId());
	            failedList.add(device);
	            continue;
	        }

	        Notification notification = Notification.builder()
	            .setBody(msg.getBody())
	            .setTitle(msg.getTitle())
	            .build();

	        Message firebaseMessage = Message.builder()
	            .setNotification(notification)
	            .setToken(deviceToken)
	            .build();

	        try {
	            FirebaseMessaging.getInstance().send(firebaseMessage);
	        } catch (FirebaseMessagingException e) {
	            Log.error("Failed to send notification to token: "+ deviceToken+ " "+ e);
	            failedList.add(device);
	        }
	    }

	    // Transaction for updating the database
	    txn.doInTransaction(() -> {
	        if (failedList.isEmpty()) {
	            Database.get().delete(msg);
	        } else {
	            msg.setFailed(true);
	            msg.setFailedDevices(failedList);
	            Database.get().save(msg);
	        }
	    });
	}

}
