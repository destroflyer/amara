/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import java.util.LinkedList;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.post.*;

/**
 *
 * @author Carl
 */
public class PostFilterAppState extends BaseDisplayAppState{

    public PostFilterAppState(){
        
    }
    private FilterPostProcessor filterPostProcessor;
    private LinkedList<Filter> queuedFilters = new LinkedList<Filter>();

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        filterPostProcessor = new FilterPostProcessor(application.getAssetManager());
        for(int i=0;i<queuedFilters.size();i++){
            Filter filter = queuedFilters.get(i);
            filterPostProcessor.addFilter(filter);
        }
        setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        if(enabled){
            mainApplication.getViewPort().addProcessor(filterPostProcessor);
        }
        else{
            mainApplication.getViewPort().removeProcessor(filterPostProcessor);
        }
    }

    @Override
    public void cleanup(){
        super.cleanup();
        mainApplication.getViewPort().removeProcessor(filterPostProcessor);
    }
    
    public void addFilter(final Filter filter){
        if(isInitialized()){
            mainApplication.enqueueTask(new Runnable(){

                @Override
                public void run(){
                    filterPostProcessor.addFilter(filter);
                }
            });
        }
        else{
            queuedFilters.add(filter);
        }
    }
    
    public void removeFilter(final Filter filter){
        if(isInitialized()){
            mainApplication.enqueueTask(new Runnable(){

                @Override
                public void run(){
                    filterPostProcessor.removeFilter(filter);
                }
            });
        }
        else{
            queuedFilters.remove(filter);
        }
    }
}
