package ctt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Controller {

    private Model model;
    private View view;
    private RemoveCDG rcdg;
    private FindAndReplace far;
    private ActionListener SaveAL,LoadAL,PrinAL,SetprnAL,GlobalCountsAL,DEMObuttonAL;
    private ActionListener PRINTCURRENTbuttonAL;
    private ActionListener REMCLASHbuttonAL,MULTIFRIZbuttonAL,CLEARFRIZbuttonAL;
    private ActionListener PRINTINDIbuttonAL,PRINTCLASSbuttonAL,PRINTMASTERbuttonAL;
    private ActionListener FINDREPLACEbuttonAL;
    private int PRINT_TYPE_INDI=0;
    private int PRINT_TYPE_CLASS=1;
    
    public void show(String msg) 
    {JOptionPane.showMessageDialog(null, msg);}
    public void show(int msg)
    {JOptionPane.showMessageDialog(null, msg);}
    public void show(long msg)
    {JOptionPane.showMessageDialog(null, msg);}

    
    
    public Controller(Model model, View view)
    {
        this.model = model;
        this.view = view;
    }
    
    public void control()
    {   rcdg=new RemoveCDG();
        rcdg.setView(view);
        far=new FindAndReplace();
        far.setView(view);
 /////Set Preferred Printer on Startup
        
        SetPrinter sp=new SetPrinter();
        String printername=sp.LoadPreferences();
        if(printername==null) printername="No Printer";
        model.setPrinterName(printername);
        view.getSetPRN().setText("Printer : "+ printername);
    	
        
    	String lastfile = LoadFile.GetLastFileIfAny();
    	if(lastfile.length()!=0) LoadTT(lastfile);
    	
    	
    	
    	
    	
    	FINDREPLACEbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) {                  
                  far.FindAndReplaceRoutine();
              }
        };                
        view.getFindReplaceBT().addActionListener(FINDREPLACEbuttonAL);
    	    		
    	
    	
    	
    	
    	
        SaveAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) {                  
                  SaveTT();
              }
        };                
        view.getSaveBT().addActionListener(SaveAL);

        
        
        LoadAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {         
            	   	String fnem=LoadFile.BrowseAndGetTimeTableFile();
                	if(fnem.length()==0) return;
             
                	view.ClearMasterTable();
                  LoadTT(fnem);
              }
        };                
        view.getLoadBT().addActionListener(LoadAL);
        
        
        SetprnAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  
                  SetDefaultPrinter();
              }
        };                
        view.getSetPRN().addActionListener(SetprnAL);
        
        
        GlobalCountsAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  
                  rcdg.CalculateGlobalCounts();
                  rcdg.DisplayAllCounts();
              }
        };                
        view.getGLOBALCOUNTbutton().addActionListener(GlobalCountsAL);
        
        
        
        DEMObuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {   view.ClearMasterTable();               
                  SetDemoTimeTable();
              }
        };                
        view.getDEMObutton().addActionListener(DEMObuttonAL);
        
        


        REMCLASHbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {           
        //    	  if(rcdg.ValidateAllCells())
            	  new Thread(new Runnable() {
                      @Override
                      public void run() {
                     	  rcdg.RemoveClashGapDoubles();
                      }
                  }).start();
              
                
            	  //rcdg.startnow();
              }
            	};

        view.getREMCLASHbutton().addActionListener(REMCLASHbuttonAL);
        
        
        MULTIFRIZbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {      String str = JOptionPane.showInputDialog(null, "Freeze Cells Containg Following String");            
                     view.MultiFrizCellsContaing(str);
               }
            	};
               
        view.getMULTIFRIZbutton().addActionListener(MULTIFRIZbuttonAL);
        
        
        CLEARFRIZbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  
            	view.ClearColorMatrix();       
               }
            	};
               
        view.getCLEARFRIZbutton().addActionListener(CLEARFRIZbuttonAL);

        
        
        
        PRINTINDIbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {  PrintAllIndi();                  

              }
            	};
               
        view.getPRINTINDIbutton().addActionListener(PRINTINDIbuttonAL);
        
        
        PRINTCLASSbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  
               PrintAllClases();
              }
        };
               
        view.getPRINTCLASSbutton().addActionListener(PRINTCLASSbuttonAL);
        
        PRINTMASTERbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  
            	  PrepareMaster();
                  PrintMaster();
   
              }
            	};
               
        view.getPRINTMASTERbutton().addActionListener(PRINTMASTERbuttonAL);
        
   
        PRINTCURRENTbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {       
                  PrintCurrent();
              }
       	};
               
        view.getPRINTCURRENTbutton().addActionListener(PRINTCURRENTbuttonAL);
    }
    
///////////// Methods called in action listeners ////////////////////    

    

    private void PrintAllIndi()
    {
    
      PrepareIndividualPrint pip=new PrepareIndividualPrint();
      pip.setView(view);
      String printername=model.getPrinterName();
      pip.CollectAllTeachers();
      pip.PrintIndividuals(printername);
    	
    	
    }
    
    private void PrintAllClases()
    { PrepareAllClasses pac=new PrepareAllClasses();
      pac.setView(view);
      pac.CollectAllClasses();
      
      String printername=model.getPrinterName();
     
      pac.PrintIndividuals(printername);
    	
    }
    
    
    public void ClearFreez()
    {
       view.ClearColorMatrix();
    }
    
    
	public void SetDemoTimeTable()
    {
    	//view.SetData(Demo.demoarr[0][0], view.table, 0,0);
		for (int i=0;i<Demo.demoarr.length;i++)
		{    
			   for(int j=0;j<Demo.demoarr[i].length;j++)
			   {
				   view.SetData(Demo.demoarr[i][j],view.table,i,j);
			   }
		}	    

    }
    
    private void SetDefaultPrinter()
    { 	
    	SetPrinter sp=new SetPrinter();
        String printername=sp.SelectPrinter();
        view.getSetPRN().setText("Printer : "+ printername+"  (Click To Change");
        model.setPrinterName(printername);
        sp.SavePreferences();
    }
    
  
    private void PrintMaster()
    { 
      PrintMaster pm=new PrintMaster();
      pm.setView(view);
      String printername=model.getPrinterName();
      System.out.println(printername);
      pm.PrintMasterChart(printername);
    }
 
    private void PrintCurrent()
    { 
    	
    	int col = view.table.getSelectedColumn();
    	if(col<0) return;
     
    	PrintIndi pi=new PrintIndi();
        pi.setView(view);
        String printername=model.getPrinterName();
    	
    	
      if(col==0) 
        { PrepareSingleClassToPrint();
    	  pi.SetPrintType(PRINT_TYPE_CLASS);
         pi.PrintIndividuals(printername);
          return;
        
        }
      pi.SetPrintType(PRINT_TYPE_INDI);
      pi.PrintIndividuals(printername);
    //  pi.CollectAllTeachers();
    }
 
    
    
    
    String SixTupleColor(int row)
    {String temp="";
    
       for(int c=1;c<7;c++)
    	   if(view.ColorMatrix[row][c]==0) temp+="0"; else temp+="1";
    
    return temp;
    	
    }
    
    private void SaveTT()
    {
    	File f = new File(System.getProperty("java.class.path"));
    	File dir = f.getAbsoluteFile().getParentFile();
    	String path = dir.toString();
    	String fnem=path+"/test.ctt";
    	//System.out.println(fnem);
    	
    	
    	FileWriter f0=null;
    	try {f0 = new FileWriter(fnem); }       catch (IOException e1){e1.printStackTrace();}
    	 String newLine = System.getProperty("line.separator");
    	// try { f0.write(newLine);	} 
    	 int rowcount=view.table.getRowCount();
    	
    	 for(int i=0;i<rowcount-1;i++)
    	 {  
    		 for(int j=0;j<7;j++)
    	        {  try { f0.write(view.GetData(view.table,i,j)+"#"); }    
    	           catch (IOException e) {e.printStackTrace();}
    	        }
    		 
    	    try { f0.write(SixTupleColor(i));}   ////Write row cell Colors as 010010 6-tuple     
	          catch (IOException e) {e.printStackTrace();} ///no "#" after last entry
  		
    	    try { f0.write(newLine);}                        //// New line after every row
    	          catch (IOException e) {e.printStackTrace();}
    
    	 }
    	 try {f0.close();} catch (IOException e) {e.printStackTrace();}

    	 
    	 
     	// toast.AutoCloseMsg("File Saved");
     }
    	
    private void LoadTT(String ttfile)
    {
    	
    	BufferedReader reader=null;
    	try { 	reader = new BufferedReader(new FileReader(ttfile));}
    	catch (FileNotFoundException e1) {e1.printStackTrace();}
    	
    	ArrayList<String> strArray = new ArrayList<String>();
    	String line = null;
    	try { while ((line = reader.readLine()) != null) { strArray.add(line); } } 
    	catch (IOException e) {	e.printStackTrace();   }

    	String temp[],stemp;
  	  
    	for(int i=0;i<strArray.size();i++)
    	{  stemp=strArray.get(i);
    	   temp=stemp.split("#");
    	   
    	   for(int j=0;j<7;j++)
    		  view.SetData(temp[j].trim(), view.table,i,j);
    		  
           for(int c=0;c<6;c++) view.ColorMatrix[i][c+1]=temp[7].charAt(c)-'0';
    	   
    	}
    	
    	LoadFile.WriteLastFile(ttfile);
    	
       }
    
    int GetFirstRow()
    {    String temp="";
    	 int currentrow=0;    	
    		////Get First Time Slot
    	    for(currentrow=0;currentrow<view.ROWCOUNT-1;currentrow++)
    	    	{ temp=view.GetData(view.table,currentrow,0); 
    	    	  if(temp.length()>0) break;
    	    	}
    		return currentrow;
    }
    
    
    int GetLastRow()
    {    String temp="";
    	 int currentrow=0;    	
    		////Get First Time Slot
    	    for(currentrow=view.ROWCOUNT-1;currentrow>0;currentrow--)
    	    	{ temp=view.GetData(view.table,currentrow,0); 
    	    	  if(temp.length()>0) break;
    	    	}
    		return currentrow;
    }
    
    
    
    int GetLastTime()
    {    String temp="";
    	 int currentrow=0;    	
    		////Get First Time Slot
    	    for(currentrow=view.MROWS-1;currentrow>0;currentrow--)
    	    	{ temp=view.Matrix[currentrow][0]; 
    	    	  if(temp.length()>0) break;
    	    	}
    		return currentrow;
    }
    
    
    void PrepareSingleClassToPrint()
    { int lt=GetLastTime();
     // show(lt);
      view.ClearIndividualTable();
      int currentrow=0;
      int maxsplit=0; // maximum split count for time slot
      String temp,temp1[];
      for(int i=0;i<=lt;i++)
	  {
		 temp=view.Matrix[i][0];
		 if(temp.length()==0) continue; //skip blank line
	
       view.SetData2(temp,currentrow,0);  ///Time - Copy as it is
       maxsplit=1;
       for(int j=1;j<7;j++) ///check lecture cells
       {temp=view.Matrix[i][j];
    	if(!temp.contains(","))
    		{ view.SetData2(temp,currentrow,j);  ///No "," so Copy as it is
    		 continue;
    		}
    		// else at least one comma exists
    			temp1=temp.split(",");
    			int count=temp1.length;
    			for(int k=0;k<count;k++)
    				{view.SetData2(temp1[k],currentrow+k,j);
    				
    				}
    			if(maxsplit<count) maxsplit=count;
    	
       }
       currentrow=currentrow+maxsplit;
	  }
	  view.SetData2("$END",currentrow,0);
   }
      
    	
    
    
   void PrepareMaster()
   { String temp,temp1[];
     int maxsplit=0; // maximum split count for time slot
	   int m=GetFirstRow();
	  int n=GetLastRow();
	  if(m>n) System.out.println("invalid m,n");
	  int currentrow=0;
	  for(int i=m;i<=n;i++)
	  {
		 temp=view.GetData(view.table,i,0);
		 if(temp.length()==0) continue; //skip blank line
		 
		 if(temp.contains(":"))  ///New time Block, print week day names line 
		 {   if(currentrow !=0) { view.SetData2("$BLANKLINE", currentrow,0);
			                      currentrow++;  }  ///skip first exceptional blank
			 String week[]={"MON","TUE","WED","THU","FRI","SAT"};
		     view.SetData2(temp,currentrow,0);  ///time
			 for(int j=0;j<6;j++ )
				 view.SetData2(week[j],currentrow,j+1); /// week days
			 currentrow++;
			 continue;
		 }
		 
       view.SetData2(temp,currentrow,0);  ///Copy as it is, must be class name FY-A,FY-B etc
       maxsplit=1;
       for(int j=1;j<7;j++) ///check lecture cells
       {temp=view.GetData(view.table,i,j);
    	if(!temp.contains(","))
    		{ view.SetData2(temp,currentrow,j);  ///No "," so Copy as it is
    		 continue;
    		}
    		// else at least one comma exists
    			temp1=temp.split(",");
    			int count=temp1.length;
    			for(int k=0;k<count;k++)
    				{view.SetData2(temp1[k],currentrow+k,j);
    				
    				}
    			if(maxsplit<count) maxsplit=count;
    	
       }
       currentrow=currentrow+maxsplit;
	  }
	  view.SetData2("$END",currentrow,0);
	  System.out.println(m);
	  System.out.println(n);
   }
    
}  
    
    
    
    
    

   