import com.file.notification.service.KafkaConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.file.*")
public class NotificationApplication implements CommandLineRunner {

  @Autowired
  private KafkaConsumerService kafkaConsumerService;

  public static void main(String[] args) {
    SpringApplication.run(NotificationApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    kafkaConsumerService.consumeNotificationDataEvent();
  }


}
