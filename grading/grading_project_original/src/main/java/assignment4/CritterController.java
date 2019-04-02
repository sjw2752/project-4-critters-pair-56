package assignment4;

import java.awt.*;
import java.util.List;

/**
 * Created by Jerry Wang on 2019/2/26.
 */
public interface CritterController {

    public void worldTimeStep();

    public void runStats(List<Critter> critters);

    public void clearWorld();

    void genClover();

    void removeFromMap(Point p, Critter c);

}
