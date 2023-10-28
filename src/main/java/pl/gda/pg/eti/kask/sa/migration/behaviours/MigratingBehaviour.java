package pl.gda.pg.eti.kask.sa.migration.behaviours;

import jade.core.Location;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import pl.gda.pg.eti.kask.sa.migration.agents.MigratingAgent;

/**
 *
 * @author psysiu
 */
public class MigratingBehaviour extends TickerBehaviour {

    protected final MigratingAgent myAgent;
    
    public MigratingBehaviour(MigratingAgent agent, long period) {
        super(agent, period);
        myAgent = agent;
    }

    @Override
    protected void onTick() {
        int index = myAgent.getLocationIndex() + 1;
        if(myAgent.getLocations().size() <= index){
            index = 0;
        }
        myAgent.setLocationIndex(index);

        if(myAgent.getLocations().size() == 0){
            return;
        }
        
        Location location = myAgent.getLocations().get(index);
        myAgent.doMove(location);
    }

}
