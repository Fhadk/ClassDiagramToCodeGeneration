
package ClassDiagramToCode;

import org.netbeans.api.visual.action.*;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;

import javax.swing.*;
import java.awt.*;

/**
 * @author David Kaspar
 */
public class StringGraphScene extends GraphScene.StringGraph {

    private LayerWidget mainLayer;
    private LayerWidget connectionLayer;

    private WidgetAction moveAction = ActionFactory.createMoveAction ();
    private WidgetAction mouseHoverAction = ActionFactory.createHoverAction (new MyHoverProvider ());
    private WidgetAction popupMenuAction = ActionFactory.createPopupMenuAction (new MyPopupMenuProvider ());

    public StringGraphScene () {
        mainLayer = new LayerWidget (this);
        connectionLayer = new LayerWidget (this);
        addChild (mainLayer);
        addChild (connectionLayer);

        getActions ().addAction (mouseHoverAction);
    }

    @Override
    public Widget attachNodeWidget (String node) {           /** Input desired Values */
        UMLClassWidget widget = new UMLClassWidget (this);
//        String delim=" ";
//        String field="\\-";
//        String temp=null,flag=null;
//        StringTokenizer token= new StringTokenizer(node, field/*delim*/);
//        while(token.hasMoreTokens()){
//            flag=token.nextToken();
//            temp=token.nextToken();
//            System.out.println(flag);
//            System.out.println(temp);            
//        }
        
//        StringTokenizer tokens= new StringTokenizer(flag, field);
//        
//        while(tokens.hasMoreTokens()){
//            temp=tokens.nextToken();
//              System.out.println("*");            
//              System.out.println("*");            
//              System.out.println(temp);            
//        }
                
//        System.out.println(GraphSceneTest.field);
            widget.setClassName (node);
//            CodeViewClass.data.get(i);
            
//            for(int i=GraphSceneTest.count;i>GraphSceneTest.count;i++){
//                rawData=CodeViewClass.data.get(i);
//                String []test=rawData.split("\\#");
//                String att=test[0];
//                String func=test[1];
//                
//                String []arrAtt=att.split("\\|");
//                String []arrFunc=func.split("\\|");        
//                
                
                 
//                /** Creating DataMembers  */
//                for(int j=0;j<Integer.parseInt(CodeViewClass.classAtt.get((GraphSceneTest.classNum)+1));j++){
//                    widget.addMember (widget.createMember (arrAtt[j]));
//                }
//                
//                /** Creating DataMembers  */
//                for(int k=0;k<Integer.parseInt(CodeViewClass.classAtt.get((GraphSceneTest.classNum)+2));k++){
//                    widget.addOperation (widget.createOperation (arrFunc[k]));
//                }                
//            }
//    
            
//            for(int i=1;i<CodeViewClass.testData.size();i++){
//                if(Integer.parseInt(CodeViewClass.testData.get(i))==9){
//                    break;
//                }
//                widget.addMember (widget.createMember (CodeViewClass.testData.get(i)));
//            }
            
//            int check=0;
//                for(int i=GraphSceneTest.count;CodeViewClass.testData.get(i).equals("*");i++){
//                     if((!CodeViewClass.testData.get(i).equals("*")) ||(check==0)){
//                         widget.addMember (widget.createMember (CodeViewClass.testData.get(i)));
//                     }else{
//                         check=1;
//                         widget.addOperation (widget.createOperation (CodeViewClass.testData.get(i)));
//                     }
//                }
//            check=0;
//            
            
            for(int i=0;i<Integer.parseInt(CodeViewClass.classAtt.get((GraphSceneTest.classNum)+1));i++){
                widget.addMember (widget.createMember (CodeViewClass.att.get(GraphSceneTest.attCount)));
                GraphSceneTest.attCount++;                
            }
            for(int j=0;j<Integer.parseInt(CodeViewClass.classAtt.get((GraphSceneTest.classNum)+2));j++){
                widget.addOperation (widget.createOperation (CodeViewClass.func.get(GraphSceneTest.funcCount)));
                GraphSceneTest.funcCount++;                
            }
            
            for(String relation : CodeViewClass.relationsInfo) {
                if(node.equals(relation.split(" ")[0])){
                    widget.addRelation(widget.createRelation(relation));
                }
            }
            
            
            
            
//                widget.addMember (widget.createMember (CodeViewClass.data.get(i)));
//                widget.addOperation (widget.createOperation (CodeViewClass.data.get(i)));
       
            mainLayer.addChild (widget);
//        }else{
//            widget.setClassName (node);
//            widget.addMember (widget.createMember ("x: double"));
//            widget.addMember (widget.createMember ("y: double"));
//            widget.addMember (widget.createMember ("radius: double"));
//            widget.addOperation (widget.createOperation ("move(x,y): void"));
//            widget.addOperation (widget.createOperation ("paint(): void"));
//            widget.addOperation (widget.createOperation ("isInside(x,y): boolean"));
//            mainLayer.addChild (widget);
//        }
//        
        widget.getActions ().addAction (moveAction);
        widget.getActions ().addAction (mouseHoverAction);
        widget.getActions ().addAction (popupMenuAction);

        return widget;
    }
    
    /*public Widget attachNodeWidget_custom(String node) {
        UMLClassWidget widget = new UMLClassWidget (this);
        widget.setClassName ("Class" + node);
        widget.addMember (widget.createMember ("x: double"));
        widget.addMember (widget.createMember ("y: double"));
        widget.addMember (widget.createMember ("radius: double"));
        widget.addOperation (widget.createOperation ("move(x,y): void"));
        widget.addOperation (widget.createOperation ("paint(): void"));
        widget.addOperation (widget.createOperation ("isInside(x,y): boolean"));
        mainLayer.addChild (widget);

        widget.getActions ().addAction (moveAction);
        widget.getActions ().addAction (mouseHoverAction);
        widget.getActions ().addAction (popupMenuAction);

        return widget;
    }*/

    protected Widget attachEdgeWidget (String edge) {           /** Un-explored yet */
        ConnectionWidget connectionWidget = new ConnectionWidget (this);
        connectionLayer.addChild (connectionWidget);
        return connectionWidget;
    }

    protected void attachEdgeSourceAnchor (String edge, String oldSourceNode, String sourceNode) {      /** Un-explored yet */
        ((ConnectionWidget) findWidget (edge)).setSourceAnchor (AnchorFactory.createRectangularAnchor (findWidget (sourceNode)));
    }

    protected void attachEdgeTargetAnchor (String edge, String oldTargetNode, String targetNode) {      /** Un-explored yet */
        ((ConnectionWidget) findWidget (edge)).setTargetAnchor (AnchorFactory.createRectangularAnchor (findWidget (targetNode)));
    }

    public LayerWidget getMainLayer () {
        return mainLayer;
    }

    public LayerWidget getConnectionLayer () {
        return connectionLayer;
    }

    private static class MyHoverProvider implements TwoStateHoverProvider {

        public void unsetHovering (Widget widget) {
            widget.setBackground (Color.WHITE);
        }

        public void setHovering (Widget widget) {
            widget.setBackground (Color.CYAN);
        }

    }

    private static class MyPopupMenuProvider implements PopupMenuProvider {

        public JPopupMenu getPopupMenu (Widget widget, Point localLocation) {
            JPopupMenu popupMenu = new JPopupMenu ();
            popupMenu.add (new JMenuItem ("Open " + ((UMLClassWidget) widget).getClassName ()));
            return popupMenu;
        }

    }
    public String rawData; 
}
