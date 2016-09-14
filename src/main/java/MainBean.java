import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;


/**
 * Created by Александр on 14.09.2016.
 */
@ManagedBean(name = "mainBean")
@SessionScoped
public class MainBean {
    String text = "123";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Resource(mappedName = "jms/TestPool")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/TestPoolTopic")
    private Destination destination;
}
