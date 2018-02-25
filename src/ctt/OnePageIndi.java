package ctt;

import java.awt.Graphics;

public class OnePageIndi
{
	View view;
	int timecolsize=100,othercolsize=65,linesperpage=40,cellheight=19;
	int horizontalwidth=timecolsize+6*othercolsize;
	int MidWidth=3*othercolsize;
	public void setView(View vu)  {	this.view=vu; }
	
	
	void PrintHeaderRow(int topleftx, int toplefty,Graphics g,int pageno)
	{   int  tlx=topleftx, tly=toplefty;
		g.drawLine(tlx,tly,tlx+horizontalwidth,tly); /////// top line
		g.drawLine(tlx,tly, tlx, tly+cellheight); //leftmost wall
		PrintRightWallText(view.LectureCount,tlx,tly, timecolsize, g); // time with right wall
		tlx=tlx+timecolsize;
		PrintRightWallText("SIWS COLLEGE",tlx,tly, MidWidth,g); // mid cell
		tlx=tlx+MidWidth;
		PrintRightWallText(view.allcounts,tlx,tly, 3*othercolsize,g); // last cell
		tly=tly+cellheight;
		tlx=topleftx;  ///back to leftmost position
		g.drawLine(tlx,tly,tlx+horizontalwidth,tly); /////// bot line
	}
	
	 void PrintRightWallText(String str,int tlx,int tly, int boxwidth,Graphics pg)
	 {
		 pg.drawLine(tlx+boxwidth, tly, tlx+boxwidth, tly+cellheight);
		 int stringLen = (int)  pg.getFontMetrics().getStringBounds(str, pg).getWidth(); 
		 int stringHeight=(int) pg.getFontMetrics().getStringBounds(str, pg).getHeight();
		 
	        int start = boxwidth/2 - stringLen/2;  
	        pg.drawString(str, start + tlx, tly+(cellheight-stringHeight)/2 +stringHeight-2);
	        
	 }
	
	 private void DrawWallTextLine(int x,int y,Graphics g,int TableRowNo)
		{ 
		 int currentleft=x,currenttop=y;
		 
		 
		 g.drawLine(currentleft, currenttop, currentleft, currenttop+cellheight); //leftmost wall   
	     String  temp=view.GetData(view.table2, TableRowNo,0);
	     
	     PrintRightWallText(temp,currentleft,currenttop, timecolsize, g);         /// time with right wall
	     
	     currentleft+=timecolsize;
			        
			  for(int i=1;i<7;i++) 
			  { //g.drawString("| Test", currentleft, currenttop);
				temp=view.GetData(view.table2, TableRowNo,i);
			    PrintRightWallText(temp,currentleft,currenttop, othercolsize,g);  /// week text with right wall
			    currentleft+=othercolsize;
			  }
		  currentleft=x;
		  
		  currenttop+=cellheight;
		  }
		
	void PrintOnePage(int topleftx, int toplefty,Graphics g,int pageno)
	{
		int  tlx=topleftx, tly=toplefty;
	//	int lastrow=GetLastRow();
		
	//	PrintHeaderRow(tlx,tly,g,pageno);
	//	tly+=cellheight;
	
	    for(int row=0;row<=30;row++)
	    	{ DrawWallTextLine(tlx,tly,g,row+pageno*30);     
	    	  tly+=cellheight;
	    	  g.drawLine(tlx,tly,tlx+horizontalwidth,tly); /////// bot line
	    	}
	}
	 
	 int GetLastTimeRow()
	    {    String temp="";
	    	 int currow=0;    	
	    		////Get First Time Slot
	    	    for(currow=view.MROWS-1;currow>0;currow--)
	    	    	{ temp=view.Matrix[currow][0]; 
	    	    	  if(temp.length()>0) return currow;
	    	    	}
	    		return currow;
	    }
	 
	 int GetLastRow()
	    {    String temp="";
	    	 int currow=0;    	
	    		////Get First Time Slot
	    	    for(currow=view.MROWS-1;currow>0;currow--)
	    	    	{ temp=view.Matrix[currow][1]; 
	    	    	  if(temp.length()>0) return currow;
	    	    	}
	    		return currow-1;
	    }

	 
	 
}