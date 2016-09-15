import javax.annotation.PostConstruct;
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
    String text = "1253";
    String text2 = "aaa";

    public String getText2() {
        //onMessage();
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText() {
        //go();
        System.out.println(text);

        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Resource(mappedName = "jms/TestPool")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/TestPoolTopic")
    private Destination destination;

    public String go(){
        try{
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(destination);

            TextMessage message = session.createTextMessage();

            message.setStringProperty("clientType", "web clien");

            message.setText("Test Text ы");

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
    String text3;

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String onMessage() {

        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(destination);
            connection.start();

            TextMessage msg = (TextMessage) consumer.receive();

            text3 = msg.getText();

            connection.close();
            consumer.close();
            session.close();

            for (int i = 0; i < 10; i++) {
                System.out.println(msg);
            }




        } catch (JMSException e) {
            e.printStackTrace();
        }

        return "nextPage";
    }
}
