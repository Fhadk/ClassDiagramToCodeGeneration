package ClassDiagramToCode;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.graph.GraphPinScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;

import javax.swing.*;
import java.awt.*;

/**
 * @author David Kaspar
 */
public class StringGraphPinScene extends GraphPinScene.StringGraph {

    private LayerWidget mainLayer;
    private LayerWidget connectionLayer;

    private WidgetAction moveAction = ActionFactory.createMoveAction ();
    private WidgetAction popupMenuAction = ActionFactory.createPopupMenuAction (new MyPopupMenuProvider ());

    public StringGraphPinScene () {
        mainLayer = new LayerWidget (this);
        connectionLayer = new LayerWidget (this);
        addChild (mainLayer);
        addChild (connectionLayer);
    }

    protected Widget attachNodeWidget (String node) {               
        UMLClassWidget widget = new UMLClassWidget (this);
        widget.setClassName ("Class " + node);
        mainLayer.addChild (widget);

        widget.getActions ().addAction (moveAction);                        /** Action applied on the Class nodes  */
        widget.getActions ().addAction (createObjectHoverAction ());
        widget.getActions ().addAction (popupMenuAction);

        return widget;
    }

    protected Widget attachPinWidget (String node, String pin) {            /** Un-explored yet */
        UMLClassWidget classWidget = ((UMLClassWidget) findWidget (node));
        if (pin.charAt (0) == '+') {
            Widget member = classWidget.createMember (pin.substring (1));
            classWidget.addMember (member);
            return member;
        } else {
            Widget operation = classWidget.createOperation (pin.substring (1));
            classWidget.addOperation (operation);
            return operation;
        }
    }

    protected Widget attachEdgeWidget (String edge) {                           /** Un-explored yet */
        ConnectionWidget connectionWidget = new ConnectionWidget (this);
        connectionLayer.addChild (connectionWidget);
        return connectionWidget;
    }

    protected void attachEdgeSourceAnchor (String edge, String oldSourcePin, String sourcePin) {          /** Un-explored yet */
        ((ConnectionWidget) findWidget (edge)).setSourceAnchor (AnchorFactory.createCenterAnchor (findWidget (sourcePin)));
    }

    protected void attachEdgeTargetAnchor (String edge, String oldTargetPin, String targetPin) {        /** Un-explored yet */
        ((ConnectionWidget) findWidget (edge)).setTargetAnchor (AnchorFactory.createCenterAnchor (findWidget (targetPin)));
    }

    public LayerWidget getMainLayer () {
        return mainLayer;
    }

    public LayerWidget getConnectionLayer () {
        return connectionLayer;
    }

    private static class MyPopupMenuProvider implements PopupMenuProvider {

        public JPopupMenu getPopupMenu (Widget widget, Point localLocation) {
            JPopupMenu popupMenu = new JPopupMenu ();
            popupMenu.add (new JMenuItem ("Open " + ((UMLClassWidget) widget).getClassName ()));
            return popupMenu;
        }

    }

}
