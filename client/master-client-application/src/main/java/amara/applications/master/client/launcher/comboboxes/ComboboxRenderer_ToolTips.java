package amara.applications.master.client.launcher.comboboxes;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;

public class ComboboxRenderer_ToolTips extends DefaultListCellRenderer {

    private String[] toolTips;

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JComponent component = (JComponent) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if ((index > -1) && (value != null) && (toolTips != null)) {
            list.setToolTipText(toolTips[index]);
        }
        return component;
    }

    public void setToolTips(String[] toolTips) {
        this.toolTips = toolTips;
    }
}