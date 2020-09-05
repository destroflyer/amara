package amara.applications.ingame.client.systems.visualisation.colors;

import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;

import java.util.HashMap;
import java.util.LinkedList;

public class ColorizingInfo {

    private LinkedList<MaterialColorizer> colorizers = new LinkedList<>();
    private HashMap<Geometry, RenderQueue.Bucket> queueBuckets = new HashMap<>();

    public void addColorizer(MaterialColorizer colorizer) {
        colorizers.add(colorizer);
    }

    public LinkedList<MaterialColorizer> getColorizers() {
        return colorizers;
    }

    public void putQueueBucket(Geometry geometry, RenderQueue.Bucket queueBucket) {
        queueBuckets.put(geometry, queueBucket);
    }

    public RenderQueue.Bucket getQueueBucket(Geometry geometry) {
        return queueBuckets.get(geometry);
    }
}
