package pl.gda.pg.eti.kask.sa.migration.behaviours;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.JADEAgentManagement.QueryPlatformLocationsAction;

import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import java.util.UUID;
import java.util.logging.Level;
import lombok.extern.java.Log;
import pl.gda.pg.eti.kask.sa.migration.agents.MigratingAgent;

/**
 *
 * @author psysiu
 */
@Log
public class RequestContainersListBehaviour extends TickerBehaviour {

    protected final MigratingAgent myAgent;
    /**
     * @param period time in milliseconds
     */
    public RequestContainersListBehaviour(MigratingAgent agent, long period) {
        super(agent, period);
        myAgent = agent;
    }

    @Override
    protected void onTick() {
        
        QueryPlatformLocationsAction query = new QueryPlatformLocationsAction();
        Action action = new Action(myAgent.getAMS(), query);

        String conversationId = UUID.randomUUID().toString();
        
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.setLanguage(new SLCodec().getName());
        request.setOntology(MobilityOntology.getInstance().getName());
        request.addReceiver(myAgent.getAMS());
        request.setConversationId(conversationId);

        try {
            myAgent.getContentManager().fillContent(request, action);
            myAgent.addBehaviour(new ReceiveContainersLisBehaviour(myAgent, conversationId));
            myAgent.send(request);
        } catch (Codec.CodecException | OntologyException ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
        }
    }

}
