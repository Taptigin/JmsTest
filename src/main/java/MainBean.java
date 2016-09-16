import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.jms.*;


/**
 * Created by Александр on 14.09.2016.
 */
@ManagedBean(name = "mainBean")
@SessionScoped
public class MainBean {
    String writeMessage;

    public String getWriteMessage() {
        return writeMessage;
    }

    public void setWriteMessage(String writeMessage) {
        this.writeMessage = writeMessage;
    }

    @Resource(mappedName = "jms/TestPool")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/TestPoolTopic")
    private Destination destination;

    public String go(){
        try{
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(destination);

            TextMessage message = session.createTextMessage();

            message.setStringProperty("clientType", "web clien");

            message.setText(writeMessage);

            messageProducer.send(message);



            messageProducer.close();
            session.close();
            connection.close();



        } catch (JMSException e) {
            e.printStackTrace();
        }

        return "nextPage";
    }

    //---------------------
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
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
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
