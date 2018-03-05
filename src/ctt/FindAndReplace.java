package ctt;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;

public class FindAndReplace 
{ View view;

	public void setView(View vu)
	{
		this.view=vu;
		
	}
	
	
	public void FindAndReplaceRoutine()
	{
		JTextField Find = new JTextField();
		JTextField Replace = new JTextField();
		String find,replace;
		Object[] message = 
		{
		    "Find :", Find,
		    "Replace :", Replace
		};

		int option = JOptionPane.showConfirmDialog(null, message, "Find and Replace", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) 
		{
			  find=Find.getText();
			  replace=Replace.getText();
		
		        System.out.println(Find.getText());
		        System.out.println(Replace.getText());
		    }  
		else return;
		
		
		
		String temp;
		
		for(int r=0;r<view.ROWCOUNT-1;r++)	
		 for(int c=1;c<7;c++)
	     	 { 
		      temp=view.GetData(view.table, r, c);
		      
		      if(temp.length()==0) continue;
		      if(temp.contains(find)) 
		   		 { String str=temp.replace(find,replace);	
		   		   view.SetData(str, view.table,r,c);
		   		 }
	     	 
	     	 } //for loop ends
		
	} // function ends
	

	
	
	
	
	
	public static void scrollToVisible(JTable table, int rowIndex, int vColIndex) {
        if (!(table.getParent() instanceof JViewport)) {
            return;
        }
        JViewport viewport = (JViewport)table.getParent();

        // This rectangle is relative to the table where the
        // northwest corner of cell (0,0) is always (0,0).
        Rectangle rect = table.getCellRect(rowIndex, vColIndex, true);

        // The location of the viewport relative to the table
        Point pt = viewport.getViewPosition();

        // Translate the cell location so that it is relative
        // to the view, assuming the northwest corner of the
        // view is (0,0)
        rect.setLocation(rect.x-pt.x, rect.y-pt.y);

        table.scrollRectToVisible(rect);

        // Scroll the area into view
        //viewport.scrollRectToVisible(rect);
    }
	

	
	
}