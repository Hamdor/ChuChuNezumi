package main;

import java.util.ArrayList;

import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;

import controller.Bot;
import controller.Controller;
import controller.Walker;
import main.util.Waypoint;
import main.util.WaypointGenerator;
import model.Model;
import view.View;

class Main {

  public static void main(String[] args) throws LoginFailedException, RemoteServerException {
    ArrayList<Waypoint> wps = new ArrayList<Waypoint>();
    wps.add(new Waypoint(53.46425374, 9.94877994));
    wps.add(new Waypoint(53.46351282, 9.94913399));
    wps.add(new Waypoint(53.46374915, 9.95053947));
    wps.add(new Waypoint(53.46410683, 9.95243847));
    wps.add(new Waypoint(53.46459226, 9.95203078));
    wps.add(new Waypoint(53.46484135, 9.95179474));
    wps.add(new Waypoint(53.46557586, 9.95245993));
    wps.add(new Waypoint(53.4660421, 9.95332897));
    wps.add(new Waypoint(53.46650834, 9.95438039));
    wps.add(new Waypoint(53.46574192, 9.95601118));
    wps.add(new Waypoint(53.46530122, 9.95649397));
    wps.add(new Waypoint(53.46467529, 9.95561421));
    wps.add(new Waypoint(53.46403658, 9.95624721));
    wps.add(new Waypoint(53.46422819, 9.95741665));
    wps.add(new Waypoint(53.46463697, 9.95980918));
    wps.add(new Waypoint(53.4653587, 9.96303856));
    wps.add(new Waypoint(53.46472638, 9.96452987));
    wps.add(new Waypoint(53.46421542, 9.96680439));
    wps.add(new Waypoint(53.46183933, 9.97590244));
    wps.add(new Waypoint(53.46061292, 9.98100936));
    wps.add(new Waypoint(53.46007635, 9.98358428));
    wps.add(new Waypoint(53.45861991, 9.984743));
    wps.add(new Waypoint(53.45708677, 9.98414218));
    wps.add(new Waypoint(53.45670347, 9.9858588));
    wps.add(new Waypoint(53.45721453, 9.9886483));
    wps.add(new Waypoint(53.45629462, 9.99186695));
    
    // TODO: Open initial window which asks for login credentials
    Model model = new Model(LoginDetails.USERNAME, LoginDetails.PASSWORD);
    View  view  = new View();
    Controller controller = new Controller(view, model);
    view.setController(controller);
    // TODO: Create bot window and init there...
    controller.setBot(new Bot(controller, model.getApi(),
                      new Walker(WaypointGenerator.interpolate(wps, 1.56),
                                 model.getApi(), controller)));
    try {
      Bot bot = controller.getBot();
      if (bot != null) bot.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
