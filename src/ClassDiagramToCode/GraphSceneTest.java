package ClassDiagramToCode;

import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import SceneSupport.SceneSupport;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author David Kaspar
 */
public class GraphSceneTest {

//    public static void mainFunction () {
//        testGraphScene ();
//    }

    public static void testLabelWidget () {
        Scene scene = new Scene ();
        scene.setBackground (Color.BLUE);

        LabelWidget label = new LabelWidget (scene);
        label.setOpaque (true);
        label.setBackground (Color.GREEN);
        label.setLabel ("ABCD");
        scene.addChild (label);

        SceneSupport.show (scene);
    }

    public static void testUMLClassWidget () {
        StringGraphScene scene = new StringGraphScene ();

        for (int a = 1; a <= 100; a ++)
            scene.addNode (String.valueOf(a)).setPreferredLocation (new Point (a * 3, a * 3));

        SceneSupport.show (scene);
    }

    private static void createConnection (StringGraphScene scene, String edgeID, String nodeID1, String nodeID2) {
        scene.addEdge (edgeID);
        scene.setEdgeSource (edgeID, nodeID1);
        scene.setEdgeTarget (edgeID, nodeID2);
    }

    public static void testConnectionWidget () {
        StringGraphScene scene = new StringGraphScene ();

        scene.addNode ("1").setPreferredLocation (new Point (0, 100));          /** location point where to Class node appear */
        scene.addNode ("2").setPreferredLocation (new Point (150, 200));
        scene.addNode ("3").setPreferredLocation (new Point (250, 300));

        createConnection (scene, "A", "1", "2");            /** creating of connection b/w two different nodes */ 
        createConnection (scene, "B", "2", "1");
        createConnection (scene, "C", "3", "2");

        SceneSupport.show (scene);
    }

    public static void testConnectionAnimation () {             /** Un-explored yet */
        StringGraphScene scene = new StringGraphScene ();

        for (int a = 0; a < 3; a ++) {
            String n1 = "A" + a;
            scene.addNode (n1).setPreferredLocation (new Point (100, 100));
            String n2 = "B" + a;
            scene.addNode (n2).setPreferredLocation (new Point (400, 400));

            createConnection (scene, "C" + a, n1, n2);
        }

        startAnimation (scene, scene.getMainLayer (), 100);
        SceneSupport.show (scene);
    }

    public static void testAnimation () {                   /** Un-explored yet */
        StringGraphScene scene = new StringGraphScene ();

        for (int a = 1; a <= 100; a ++)
            scene.addNode (String.valueOf(a)).setPreferredLocation (new Point (a * 10, a * 10));

        startAnimation (scene, scene.getMainLayer (), 0);
        SceneSupport.show (scene);
    }
    
    public static void testAddRemove () {           /** Un-explored yet */
        StringGraphScene scene = new StringGraphScene ();

        startAddRemove (scene, 500);
        SceneSupport.show (scene);
    }

    public static void testGraphScene () {      
        final StringGraphScene scene = new StringGraphScene ();
        String nodeID;
        String previousNodeID = null;
        edgeID= new ArrayList<String>();
        
        for (classNum= 0; classNum < (CodeViewClass.classAtt.size());classNum+=3) {                      /** total number of Class Nodes */
            nodeID=CodeViewClass.classAtt.get(classNum);
            
            
            scene.addNode (nodeID).setPreferredLocation (new Point (SceneSupport.randInt (1000), SceneSupport.randInt (1000)));
          
           if (previousNodeID != null) {
            }
            previousNodeID = nodeID;
       }
        count = 0;
        String relationName;
        for(String temp : CodeViewClass.relationsInfo) {
            String firstClass = temp.split(" ")[0];
            String secondClass = temp.split(" ")[2];
            String edgeID = "edge" + String.valueOf (count);
            scene.addEdge (edgeID);
            scene.setEdgeSource (edgeID, firstClass);           /**  source of the edge node */
            scene.setEdgeTarget (edgeID, secondClass);                   /** target of the edge node */ 
            
            count++;  
        }        
        SceneSupport.show (scene);
    }

    public static void testGraphPinScene () {           /** Un-explored yet */
        final StringGraphPinScene scene = new StringGraphPinScene ();

        String rootNode = "Root";
        scene.addNode (rootNode).setPreferredLocation (new Point (30, 500));

        for (int a = 0; a < 10; a ++) {
            String rootPin = "+Pin" + a;
            scene.addPin (rootNode, rootPin);

            String childNode = "Child" + a;
            scene.addNode (childNode).setPreferredLocation (new Point (500, a * 100));

            String childPin = "-Pin" + a;
            scene.addPin (childNode, childPin);

            String edge = "edge" + String.valueOf (a);
            scene.addEdge (edge);
            scene.setEdgeSource (edge, rootPin);
            scene.setEdgeTarget (edge, childPin);
        }

        SceneSupport.show (scene);
    }

    public static void startAnimation (Scene scene, Widget widget, int delay) {
        SwingUtilities.invokeLater (new Animation (scene, widget, delay));
    }

    static class Animation implements Runnable {

        private Scene scene;
        private Widget widget;
        private int delay;

        public Animation (Scene scene, Widget widget, int delay) {
            this.scene = scene;
            this.widget = widget;
            this.delay = delay;
        }

        public void run () {
            Collection<Widget> children = widget.getChildren ();
//            Widget child = children.iterator ().next ();
            for (Widget child : children)
                child.setPreferredLocation (new Point (SceneSupport.randInt (1000), SceneSupport.randInt (1000)));
            scene.validate ();

            SceneSupport.invokeLater (this, delay);
        }

    }

    private static void startAddRemove (StringGraphScene scene, int delay) {
        SwingUtilities.invokeLater (new AddRemove (scene, delay));
    }

    private static class AddRemove implements Runnable {

        private StringGraphScene scene;
        private int delay;
        private String node;

        public AddRemove (StringGraphScene scene, int delay) {
            this.scene = scene;
            this.delay = delay;
            this.node = null;
        }

        public void run () {
            if (node == null) {
                node = "Node";
                scene.addNode (node).setPreferredLocation (new Point (SceneSupport.randInt (1000), SceneSupport.randInt (1000)));
            } else {
                scene.removeNode (node);
                node = null;
            }
            scene.validate ();
            SceneSupport.invokeLater (this, delay);
        }

    }
    
 public static String field="";
 public static ArrayList<String> edgeID;
 public static int attCount=0;
 public static int funcCount=0;
 public static int classNum=0;
 public static int count=0;
}
