package pl.gda.pg.eti.kask.sa.migration.behaviours;

import jade.core.Location;
import jade.core.behaviours.Behaviour;
import pl.gda.pg.eti.kask.sa.migration.agents.MigratingAgent;

/**
 *
 * @author psysiu
 */
public class MigratingBehaviour extends Behaviour {

    protected final MigratingAgent myAgent;
    
    public MigratingBehaviour(MigratingAgent agent) {
        super(agent);
        myAgent = agent;
    }

    @Override
    public void action() {
        int index = myAgent.getLocationIndex() + 1;
        if(myAgent.getLocations().size() <= index){
            index = 0;
        }
        
        myAgent.setLocationIndex(index);

        Location location = myAgent.getLocations().get(index);
        myAgent.doMove(location);
    }

    @Override
    public boolean done() {
        return myAgent.getLocations().size() <= 1;
    }

}
