import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.jms.*;

/**
 * Created by Александр on 16.09.2016.
 */
@ManagedBean(name = "twoBean")
@SessionScoped
public class TwoBean {

    @Resource(mappedName = "jms/TestPool")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/TestPoolTopic")
    private Destination destination;

    String receivedMessage;

    public String getReceivedMessage() {
        return receivedMessage;
    }

    public void setReceivedMessage(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    public String onMessage() {

        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(destination);
            connection.start();

            TextMessage msg = (TextMessage) consumer.receive();

            receivedMessage = msg.getText();

            connection.close();
            consumer.close();
            session.close();






        } catch (JMSException e) {
            e.printStackTrace();
        }

        return "nextPage";
    }
}
