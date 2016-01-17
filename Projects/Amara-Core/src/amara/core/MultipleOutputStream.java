/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Carl
 */
public class MultipleOutputStream extends OutputStream{
 
    public MultipleOutputStream(OutputStream... outputStreams){
        this.outputStreams.addAll(Arrays.asList(outputStreams));
    }
    private ArrayList<OutputStream> outputStreams = new ArrayList<OutputStream>();
    
    @Override
    public void write(int i) throws IOException{
        for(int r=0;r<outputStreams.size();r++){
            OutputStream outputStream = outputStreams.get(r);
            outputStream.write(i);
        }
    }
    
    public void addOutputStream(OutputStream outputStream){
        outputStreams.add(outputStream);
    }
    
    public void removeOutputStream(OutputStream outputStream){
        outputStreams.remove(outputStream);
    }
    
    public List<OutputStream> getStreams(){
        return outputStreams;
    }
}