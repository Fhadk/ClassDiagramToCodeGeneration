package ClassDiagramToCode;

import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.*;
import org.openide.util.Utilities;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class UMLClassWidget extends Widget {

    private static final Image IMAGE_CLASS = Utilities.loadImage ("Resources/class.gif"); // NOI18N
    private static final Image IMAGE_MEMBER = Utilities.loadImage ("Resources/variablePublic.gif"); // NOI18N
    private static final Image IMAGE_OPERATION = Utilities.loadImage ("Resources/methodPublic.gif"); // NOI18N
    private static final Image IMAGE_RELATION = Utilities.loadImage ("Resources/relation.png");

    private LabelWidget className;
    private Widget members;
    private Widget operations;
    private Widget relation;
    private static final Border BORDER_4 = BorderFactory.createEmptyBorder (4);
 

    public UMLClassWidget (Scene scene) {
        super (scene);
        setLayout (LayoutFactory.createVerticalFlowLayout ());
        setBorder (BorderFactory.createLineBorder ());
        setOpaque (true);
        setCheckClipping (true);
        
        Widget classWidget = new Widget (scene);
        classWidget.setLayout (LayoutFactory.createHorizontalFlowLayout ());
        classWidget.setBorder (BORDER_4);

        ImageWidget classImage = new ImageWidget (scene);
        classImage.setImage (IMAGE_CLASS);
        classWidget.addChild (classImage);

        className = new LabelWidget (scene);
        className.setFont (scene.getDefaultFont ().deriveFont (Font.BOLD));
        classWidget.addChild (className);
        addChild (classWidget);

        addChild (new SeparatorWidget (scene, SeparatorWidget.Orientation.HORIZONTAL));

        members = new Widget (scene);
        members.setLayout (LayoutFactory.createVerticalFlowLayout ());
        members.setOpaque (false);
        members.setBorder (BORDER_4);
        addChild (members);

        addChild (new SeparatorWidget (scene, SeparatorWidget.Orientation.HORIZONTAL));

        operations = new Widget (scene);
        operations.setLayout (LayoutFactory.createVerticalFlowLayout ());
        operations.setOpaque (false);
        operations.setBorder (BORDER_4);
        addChild (operations);
        
        
        addChild (new SeparatorWidget (scene, SeparatorWidget.Orientation.HORIZONTAL));

        relation = new Widget (scene);
        relation.setLayout (LayoutFactory.createVerticalFlowLayout ());
        relation.setOpaque (false);
        relation.setBorder (BORDER_4);
        addChild (relation);
        
    }

    public String getClassName () {
        return className.getLabel ();
    }

    public void setClassName (String className) {
        this.className.setLabel (className);
    }

    public Widget createMember (String member) {
        Scene scene = getScene ();
        Widget widget = new Widget (scene);
        widget.setLayout (LayoutFactory.createHorizontalFlowLayout ());

        ImageWidget imageWidget = new ImageWidget (scene);
        imageWidget.setImage (IMAGE_MEMBER);
        widget.addChild (imageWidget);

        LabelWidget labelWidget = new LabelWidget (scene);
        labelWidget.setLabel (member);
        widget.addChild (labelWidget);

         return widget;
    }

    public Widget createOperation (String operation) {
        Scene scene = getScene ();
        Widget widget = new Widget (scene);
        widget.setLayout (LayoutFactory.createHorizontalFlowLayout ());

        ImageWidget imageWidget = new ImageWidget (scene);
        imageWidget.setImage (IMAGE_OPERATION);
        widget.addChild (imageWidget);

        LabelWidget labelWidget = new LabelWidget (scene);
        labelWidget.setLabel (operation);
        widget.addChild (labelWidget);

        return widget;
    }
    
    public Widget createRelation (String relation) {
        Scene scene = getScene ();
        Widget widget = new Widget (scene);
        widget.setLayout (LayoutFactory.createHorizontalFlowLayout ());

        ImageWidget imageWidget = new ImageWidget (scene);
        imageWidget.setImage (IMAGE_RELATION);
        widget.addChild (imageWidget);

        LabelWidget labelWidget = new LabelWidget (scene);
        labelWidget.setLabel (relation);
        widget.addChild (labelWidget);

        return widget;
    }

    public void addMember (Widget memberWidget) {
        members.addChild (memberWidget);
    }
    
    public void removeMember (Widget memberWidget) {
        members.removeChild (memberWidget);
    }

    public void addOperation (Widget operationWidget) {
        operations.addChild (operationWidget);
    }

    public void removeOperation (Widget operationWidget) {
        operations.removeChild (operationWidget);
    }
    
    public void addRelation (Widget relationWidget) {
        relation.addChild (relationWidget);
    }

    public void removeRelation (Widget relationWidget) {
        operations.removeChild (relationWidget);
    }

}
